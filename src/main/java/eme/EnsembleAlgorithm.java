/*
 * This file is part of EME algorithm.
 *
 * (c)  Jose Maria Moyano Murillo <jmoyano@uco.es>
 *      Eva Lucrecia Gibaja Galindo <egibaja@uco.es>
 *      Sebastian Ventura Soto <sventura@uco.es>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package eme;

import java.util.ArrayList;
import java.util.Hashtable;

import mulan.data.LabelPowersetStratification;
import mulan.data.MultiLabelInstances;
import mulan.data.Statistics;
import mulan.classifier.MultiLabelLearner;
import mulan.classifier.transformation.LabelPowerset;
import weka.classifiers.trees.J48;
import net.sf.jclec.IIndividual;
import net.sf.jclec.algorithm.classic.SGE;
import net.sf.jclec.binarray.BinArrayIndividual;
import net.sf.jclec.selector.BettersSelector;
//import net.sf.jclec.selector.WorsesSelector;

import org.apache.commons.configuration.Configuration;

import eme.mut.IntraModelMutator;
import eme.mut.PhiBasedIntraModelMutator;
import eme.rec.UniformModelCrossover;

/**
 * @author Jose M. Moyano: jmoyano@uco.es
 *
 * Class implementing the evolutionary algorithm EME. It extends the SGE class from JCLEC.
 */
public class EnsembleAlgorithm extends SGE
{
	/////////////////////////////////////////////////////////////////
	// --------------------------------------- Serialization constant
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Serialization constant
	 */
	private static final long serialVersionUID = -2649346083463795286L;
	
	/////////////////////////////////////////////////////////////////
	// --------------------------------------------------- Properties
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Dataset to build the ensembles */
	private MultiLabelInstances datasetTrain;
	
	/**
	 *  Dataset to evaluate the individuals */
	private MultiLabelInstances datasetValidation;
	
	/**
	 *  Dataset to test the final ensemble */
	private MultiLabelInstances datasetTest;
	
	/**
	 *  Final ensemble classifier */
	private EnsembleClassifier classifier;

	/**
	 *  Number of base classifiers of the ensemble */
	private int numberClassifiers;
	
	/**
	 * Number of active labels in each base classifier */
	private int maxNumberLabelsClassifier;

	/**
	 *  Threshold for voting process prediction*/
	private double predictionThreshold;
	
	/** 
	 * Indicates if the number of active labels is variable for each base classifier */
	private boolean variable;
	
	/**
	 * Table that stores all built base classifiers */
	private Hashtable<String, MultiLabelLearner> tableClassifiers;
	
	/**
	 * Table that stores the fitness of all evaluated individuals */
	private Hashtable<String, Double> tableFitness;
	
	/**
	 *  Indicates if a validation set is used to evaluate the individuals
	 *  If it is TRUE, a different dataset is used to build and to evaluate the individuals
	 *  	The dataset to evaluate may be totally different or include more instances to the training one
	 *  If it is FALSE, the same dataset is used to build and to evaluate the individuals
	 */
	private boolean useValidationSet;

	/**
	 *  Indicates if the coverage ratio is used in fitness */
	private boolean useCoverage;
	
	/**
	 *  Coverage ratio of the final ensemble */
	public double finalEnsembleCoverage;
	
	/**
	 *  Multi-label evaluation measure of the final ensemble */
	public double finalEnsembleMeasure;
	
	/**
	 *  Fitness of the final ensemble */
	public double finalEnsembleFitness;
	
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////

	/**
	 * Empty (default) constructor
	 */	
	public EnsembleAlgorithm() 
	{
		super();
		
		tableClassifiers = new Hashtable<String, MultiLabelLearner> ();
		tableFitness = new Hashtable<String, Double> ();
	}
	
	/////////////////////////////////////////////////////////////////
	// ----------------------------------------------- Public methods
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Get the train dataset
	 * 
	 * @return The dataset used for training the models
	 */
	public MultiLabelInstances getDatasetTrain()
	{
		return datasetTrain;
	}
	
	/**
	 * Get the validation dataset
	 * 
	 * @return The dataset used for the evaluation of the individuals
	 */
	public MultiLabelInstances getDatasetValidation()
	{
		return datasetValidation;
	}
	
	/**
	 * Get the test dataset
	 * 
	 * @return The dataset used for testing the final ensemble obtained by EME
	 */
	public MultiLabelInstances getDatasetTest()
	{
		return datasetTest;
	}
	
	/**
	 * Get the ensemble classifier obtained by EME
	 * 
	 * @return Ensemble classifier
	 */
	public EnsembleClassifier getClassifier()
	{
		return classifier;
	}
		
	/**
	 * Get the number of classifiers in the ensemble
	 * 
	 * @return Number of classifiers in the ensemble
	 */
	public int getNumberClassifiers()
	{
		return numberClassifiers;
	}
	
	/**
	 * Get the max number of labels in each base classifier (k)
	 * At the moment, the number of labels for each classifier is fixed, but it could be variable
	 * 
	 * @return Number of labels in each base classifier
	 */
	public int getMaxNumberLabelsClassifier()
	{
		return maxNumberLabelsClassifier;
	}
	
	/**
	 * Get the threshold used for prediction in the ensemble
	 * 
	 * @return Prediction threshold
	 */
	public double getPredictionThreshold()
	{
		return predictionThreshold;
	}
	
	/**
	 * Get if the number of labels in each base classifier is variable
	 * At the moment, only the fixed version has been proved
	 * 
	 * @return FALSE if the number of labels for each base classifier is fixed, and TRUE if it wold be variable
	 */
	public boolean isVariable()
	{
		return variable;
	}
	
	/**
	 * Get if the validation dataset is used
	 * 
	 * @return TRUE if a different dataset is used to evaluate the individuals, and FALSE if the same than to build them is used
	 */
	public boolean getUseValidationSet()
	{
		return useValidationSet;
	}
	
	/**
	 * Get the size of the TableClassifiers.
	 * That means the number of base classifiers that have been built over the evolutionary process.
	 * 
	 * @return Size of the table storing the built base classifiers
	 */
	public int getTableClassifiersSize() {
		return tableClassifiers.size();
	}
	
	/**
	 * Get the size of the TableFitness.
	 * That means the number of individuals that have been evaluated over the evolutionary process.
	 * 
	 * @return Size of the table storing the fitness of the individuals
	 */
	public int getTableFitnessSize() {
		return tableFitness.size();
	}

	/**
	 * Configure some default aspects and parameters of EME to make the configuration easier
	 * 
	 * @param configuration Configuration
	 */
	private void configureEmeDefaults(Configuration configuration) {
		//Species
		configuration.setProperty("species[@type]", "net.sf.jclec.binarray.BinArrayIndividualSpecies");
		configuration.setProperty("species[@genotype-length]", "1");
		
		//Variable
		configuration.addProperty("variable", "false");
		
		//Validation set (only if not provided)
		if(! configuration.containsKey("validation-set")) {
			configuration.addProperty("validation-set", "false");
		}
		
		//Evaluator (only if not provided)
		if(! configuration.containsKey("evaluator[@type]")) {
			configuration.addProperty("evaluator[@type]", "eme.EnsembleMLCEvaluator");
		}
		
		//Provider (only if not provided)
		if(! configuration.containsKey("provider[@type]")) {
			configuration.addProperty("provider[@type]", "eme.EnsembleMLCCreator");
		}
		
		//Randgen type (only if not provided)
		if(! configuration.containsKey("rand-gen-factory[@type]")) {
			configuration.addProperty("rand-gen-factory[@type]", "net.sf.jclec.util.random.RanecuFactory");
		}
		
		//Parents-selector (only if not provided)
		if(! configuration.containsKey("parents-selector[@type]")) {
			configuration.addProperty("parents-selector[@type]", "net.sf.jclec.selector.TournamentSelector");
		}
		if(! configuration.containsKey("parents-selector.tournament-size")) {
			configuration.addProperty("parents-selector.tournament-size", "2");
		}
		
		//Listener type (only if not provided)
		if(! configuration.containsKey("listener[@type]")) {
			configuration.addProperty("listener[@type]", "eme.EnsembleListener");
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void configure(Configuration configuration)
	{
		configureEmeDefaults(configuration);
		super.configure(configuration);
		
		try {
			// Read train/test datasets
			String datasetTrainFileName = configuration.getString("dataset.train-dataset");
			String datasetTestFileName = configuration.getString("dataset.test-dataset");
			String datasetXMLFileName = configuration.getString("dataset.xml");
			
			MultiLabelInstances fullDatasetTrain = new MultiLabelInstances(datasetTrainFileName, datasetXMLFileName);
			datasetTest = new MultiLabelInstances(datasetTestFileName, datasetXMLFileName);			
			
			//Use or not a validation set to evaluate individuals
			useValidationSet = configuration.getBoolean("validation-set");
			if(useValidationSet)
			{
				LabelPowersetStratification strat = new LabelPowersetStratification();
				// 75% for train ; 100% for validation
				MultiLabelInstances [] m = strat.stratify(fullDatasetTrain, 4);
				//Train set have 3 folds
				datasetTrain = m[0];
				datasetTrain.getDataSet().addAll(m[1].getDataSet());
				datasetTrain.getDataSet().addAll(m[2].getDataSet());

				//4th fold for validation
				//datasetTrain = m[3];
				
				//Validation set have all 4 folds
				datasetValidation = datasetTrain;
				datasetValidation.getDataSet().addAll(m[3].getDataSet());

			}
			else
			{
				//Train and validation set are the same, the full set
				datasetTrain = fullDatasetTrain;
				datasetValidation = datasetTrain;
			}
			
			// Obtain settings
			int numberLabels = datasetTrain.getNumLabels();
			variable = configuration.getBoolean("variable");
			if(variable)
				maxNumberLabelsClassifier = (int) Math.ceil(Math.sqrt(numberLabels));
			else
				maxNumberLabelsClassifier = configuration.getInt("number-labels-classifier");
			numberClassifiers = configuration.getInt("number-classifiers"); 
			predictionThreshold = configuration.getDouble("prediction-threshold");
			
			useCoverage = configuration.getBoolean("use-coverage");
			
			// Set provider settings
			((EnsembleMLCCreator) provider).setNumberClassifiers(numberClassifiers);
			((EnsembleMLCCreator) provider).setMaxNumberLabelsClassifier(maxNumberLabelsClassifier);
			((EnsembleMLCCreator) provider).setNumberLabels(numberLabels);
			((EnsembleMLCCreator) provider).setVariable(variable); 
			
			// Set evaluator settings			
			((EnsembleMLCEvaluator) evaluator).setDatasetTrain(datasetTrain);
			((EnsembleMLCEvaluator) evaluator).setDatasetValidation(datasetValidation);
			((EnsembleMLCEvaluator) evaluator).setNumberClassifiers(numberClassifiers);
			((EnsembleMLCEvaluator) evaluator).setMaxNumberLabelsClassifier(maxNumberLabelsClassifier);
			((EnsembleMLCEvaluator) evaluator).setPredictionThreshold(predictionThreshold);
			((EnsembleMLCEvaluator) evaluator).setVariable(variable);
			((EnsembleMLCEvaluator) evaluator).setTable(tableClassifiers);
			((EnsembleMLCEvaluator) evaluator).setTableFitness(tableFitness);
			((EnsembleMLCEvaluator) evaluator).setRandGenFactory(randGenFactory);
			((EnsembleMLCEvaluator) evaluator).setUseCoverage(useCoverage);

			// Set genetic operator settings
			((IntraModelMutator) mutator.getDecorated()).setNumberLabels(numberLabels);
			((UniformModelCrossover) recombinator.getDecorated()).setNumberLabels(numberLabels);
			
			
			//Calculate PhiMatrix only if its necessary
			if(mutator.getDecorated().getClass().toString().contains("PhiBasedIntraModelMutator"))
			{
				Statistics s = new Statistics();
				double [][] phi = s.calculatePhi(getDatasetTrain());
				
				s.printPhiCorrelations();
				
				// Send Phi matrix to the mutator
				((PhiBasedIntraModelMutator) mutator.getDecorated()).setPhiMatrix(phi);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
		
	/////////////////////////////////////////////////////////////////
	// -------------------------------------------- Protected methods
	/////////////////////////////////////////////////////////////////
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doControl()
	{				
		System.out.println("--- Generation " + generation + " ---");
		System.out.println("----------------------");
		
		//Order the individual by fitness
		BettersSelector bselector = new BettersSelector(this);
//		WorsesSelector wselector = new WorsesSelector(this);
		bset = bselector.select(bset);	
		
		// If maximum number of generations is exceeded, evolution is finished
		if (generation >= maxOfGenerations)
		{
			/* Build the best individual */
			IIndividual bestInd = bselector.select(bset, 1).get(0);
			byte[] genotype = ((BinArrayIndividual) bestInd).getGenotype();

			classifier = new EnsembleClassifier(maxNumberLabelsClassifier, numberClassifiers, predictionThreshold, variable, new LabelPowerset(new J48()), genotype, tableClassifiers, randGenFactory.createRandGen());			
			//Indicate that it is the final individual, to export some measures
			((EnsembleMLCEvaluator) evaluator).finalInd = true;
			
			ArrayList<IIndividual> finalList = new ArrayList<IIndividual>();
			finalList.add(bestInd);			
			System.out.println(((EnsembleMLCEvaluator) evaluator).finalInd);
			System.out.println(finalList.size());
			((EnsembleMLCEvaluator) evaluator).evaluate(bestInd);
			finalEnsembleCoverage = ((EnsembleMLCEvaluator) evaluator).finalCoverage;
			finalEnsembleMeasure = ((EnsembleMLCEvaluator) evaluator).finalMeasure;
			finalEnsembleFitness = ((EnsembleMLCEvaluator) evaluator).finalFitness;
			
			try {	
				classifier.build(datasetTrain);
				
				/* Print the final ensemble */
				System.out.println("Final ensemble");
				for(int i=0; i<getNumberClassifiers(); i++)
				{
					for(int j=0; j<getDatasetTrain().getNumLabels(); j++)
					{
						System.out.print(genotype[i*getDatasetTrain().getNumLabels()+j] + " ");
					}
					System.out.println();
				}
				
				int [] v = classifier.getVotesPerLabel();
				System.out.println("numVotes");
				for(int j=0; j<getDatasetTrain().getNumLabels(); j++)
					System.out.print(v[j] + " ");
				System.out.println();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			state = FINISHED;
		}
	}

}