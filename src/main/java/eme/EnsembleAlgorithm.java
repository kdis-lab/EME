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
import net.sf.jclec.selector.WorsesSelector;

import org.apache.commons.configuration.Configuration;

import eme.mut.IntraModelMutator;
import eme.mut.PhiBasedIntraModelMutator;
import eme.rec.UniformModelCrossover;


public class EnsembleAlgorithm extends SGE
{
	/////////////////////////////////////////////////////////////////
	// --------------------------------------- Serialization constant
	/////////////////////////////////////////////////////////////////
	
	private static final long serialVersionUID = -2649346083463795286L;
	
	/////////////////////////////////////////////////////////////////
	// --------------------------------------------------- Properties
	/////////////////////////////////////////////////////////////////
	
	/* Dataset to build the ensembles */
	private MultiLabelInstances datasetTrain;
	
	/* Dataset to evaluate the individuals */
	private MultiLabelInstances datasetValidation;
	
	/* Dataset to test the final ensemble */
	private MultiLabelInstances datasetTest;
	
	/* Final ensemble classifier */
	private EnsembleClassifier classifier;

	/* Number of base classifiers of the ensemble */
	private int numberClassifiers;
	
	/* Number of active labels in each base classifier */
	private int maxNumberLabelsClassifier;

	/* Threshold for voting process prediction*/
	private double predictionThreshold;
	
	/* Indicates if the number of active labels is variable for each base classifier */
	private boolean variable;
	
	/* Table that stores all base classifiers built */
	private Hashtable<String, MultiLabelLearner> tableClassifiers;
	
	/* Table that stores the fitness of all evaluated individuals */
	private Hashtable<String, Double> tableFitness;
	
	/* Indicates if a validation set is used to evaluate the individuals */
	private boolean useValidationSet;
	
	/* Indicates if the individual fitness contemplates the phi correlation between labels */
	private boolean phiInFitness;

	/* Indicates if the coverage is used in fitness */
	private boolean useCoverage;
	
	/* Coverage ratio of the final ensemble */
	public double finalEnsembleCoverage;
	
	/* Multi-label evaluation measure of the final ensemble */
	public double finalEnsembleMeasure;
	
	/* Fitness of the final ensemble */
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
	
	public MultiLabelInstances getDatasetTrain()
	{
		return datasetTrain;
	}
	
	public MultiLabelInstances getDatasetValidation()
	{
		return datasetValidation;
	}
	
	public MultiLabelInstances getDatasetTest()
	{
		return datasetTest;
	}
	
	public EnsembleClassifier getClassifier()
	{
		return classifier;
	}
		
	public int getNumberClassifiers()
	{
		return numberClassifiers;
	}
	
	public int getMaxNumberLabelsClassifier()
	{
		return maxNumberLabelsClassifier;
	}
	
	public double getPredictionThreshold()
	{
		return predictionThreshold;
	}
	
	public boolean getVariable()
	{
		return variable;
	}
	
	public boolean getUseValidationSet()
	{
		return useValidationSet;
	}
	
	public int getTableClassifiersSize() {
		return tableClassifiers.size();
	}
	
	public int getTableFitnessSize() {
		return tableFitness.size();
	}

	
	/**
	 * {@inheritDoc}
	 */

	@Override
	public void configure(Configuration configuration)
	{
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
			
			phiInFitness = configuration.getBoolean("phi-in-fitness");
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
			((EnsembleMLCEvaluator) evaluator).setTableMeasures(tableFitness);
			((EnsembleMLCEvaluator) evaluator).setRandGenFactory(randGenFactory);
			((EnsembleMLCEvaluator) evaluator).setPhiInFitness(phiInFitness);
			((EnsembleMLCEvaluator) evaluator).setUseCoverage(useCoverage);

			// Set genetic operator settingsS
			((IntraModelMutator) mutator.getDecorated()).setNumberLabels(numberLabels);
			((UniformModelCrossover) recombinator.getDecorated()).setNumberLabels(numberLabels);
			
			
			//Calculate PhiMatrix only if its necessary
			if(mutator.getDecorated().getClass().toString().contains("PhiBasedIntraModelMutator"))
			{
				Statistics s = new Statistics();
				double [][] phi = s.calculatePhi(getDatasetTrain());
				
				s.printPhiCorrelations();
				
				// Send Phi matrix to the mutator if it needs it
				if(mutator.getDecorated().getClass().toString().contains("PhiBasedIntraModelMutator"))
					((PhiBasedIntraModelMutator) mutator.getDecorated()).setPhiMatrix(phi);
				
				//Send phi matrix to evaluator
				if(phiInFitness)
					((EnsembleMLCEvaluator) evaluator).setPhiMatrix(phi);
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	
	/////////////////////////////////////////////////////////////////
	// -------------------------------------------- Protected methods
	/////////////////////////////////////////////////////////////////
	
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