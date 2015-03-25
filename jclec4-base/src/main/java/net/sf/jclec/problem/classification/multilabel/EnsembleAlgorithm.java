package net.sf.jclec.problem.classification.multilabel;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;

import mulan.data.LabelPowersetStratification;
import mulan.data.MultiLabelInstances;
import mulan.data.Statistics;
import mulan.classifier.MultiLabelLearner;
import mulan.classifier.transformation.LabelPowerset;
import weka.classifiers.trees.J48;
import net.sf.jclec.IIndividual;
import net.sf.jclec.algorithm.classic.SGE;
import net.sf.jclec.binarray.BinArrayIndividual;
import net.sf.jclec.fitness.SimpleValueFitness;
import net.sf.jclec.problem.classification.multilabel.mut.IntraModelMutator;
import net.sf.jclec.problem.classification.multilabel.mut.PhiBasedIntraModelMutator;
import net.sf.jclec.problem.classification.multilabel.rec.UniformModelCrossover;
import net.sf.jclec.selector.BettersSelector;
import net.sf.jclec.selector.WorsesSelector;

import org.apache.commons.configuration.Configuration;


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
	private boolean isValidationSet;
	
	/* Indicates if the diversity of the population is controlled */
	private boolean controlPopulationDiversity;
	
	/* Indicates if the individual fitness contemplates the individual diversity */
	private boolean fitnessWithIndividualDiversity;
	
	/* Indicates if the individual fitness contemplates the phi correlation between labels */
	private boolean phiInFitness;
	
	/* Indicates if the entropy is used in fitness */
	private boolean useEntropy;
	
	/* Indicates if the measure of difficulty is used in fitness */
	private boolean useMeasureOfDifficulty;

	/* Indicates if the coverage is used in fitness */
	private boolean useCoverage;
	
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
	
	public boolean getIsValidationSet()
	{
		return isValidationSet;
	}
	
	public boolean getControlPopulationDiversity()
	{
		return controlPopulationDiversity;
	}
	
	public boolean getFitnessWithIndividualDiversity()
	{
		return fitnessWithIndividualDiversity;
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
			isValidationSet = configuration.getBoolean("validation-set");
			if(isValidationSet)
			{
//				IterativeStratification strat = new IterativeStratification();
				LabelPowersetStratification strat = new LabelPowersetStratification();
				// 75% for train ; 100% for validation
				MultiLabelInstances [] m = strat.stratify(fullDatasetTrain, 4);
				//Train set have 3 folds
				datasetTrain = m[0];
				datasetTrain.getDataSet().addAll(m[1].getDataSet());
				datasetTrain.getDataSet().addAll(m[2].getDataSet());

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
			
			
			controlPopulationDiversity = configuration.getBoolean("controlPopulationDiversity");
			fitnessWithIndividualDiversity = configuration.getBoolean("fitnessWithIndividualDiversity");
			phiInFitness = configuration.getBoolean("phi-in-fitness");
			useEntropy = configuration.getBoolean("use-entropy");
			useMeasureOfDifficulty = configuration.getBoolean("use-measure-of-difficulty");
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
			((EnsembleMLCEvaluator) evaluator).setFitnessWithIndividualDiversity(fitnessWithIndividualDiversity);
			((EnsembleMLCEvaluator) evaluator).setRandGenFactory(randGenFactory);
			((EnsembleMLCEvaluator) evaluator).setPhiInFitness(phiInFitness);
			((EnsembleMLCEvaluator) evaluator).setUseEntropy(useEntropy);
			((EnsembleMLCEvaluator) evaluator).setUseMeasureOfDifficulty(useMeasureOfDifficulty);
			((EnsembleMLCEvaluator) evaluator).setUseCoverage(useCoverage);

			// Set genetic operator settingsS
			((IntraModelMutator) mutator.getDecorated()).setNumberLabels(numberLabels);
			((UniformModelCrossover) recombinator.getDecorated()).setNumberLabels(numberLabels);
			
			
			//Calculate PhiMatrix only if its necessary
			if((mutator.getDecorated().getClass().toString().contains("PhiBasedIntraModelMutator")) || (phiInFitness))
			{
				Statistics s = new Statistics();
				double [][] phi = s.calculatePhi(getDatasetTrain());
				
//				s.printPhiCorrelations();
				
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
		bset = bselector.select(bset);		
		
		// If maximum number of generations is exceeded, evolution is finished
		if (generation >= maxOfGenerations)
		{
			/* Build the best individual */
			IIndividual bestInd = bselector.select(bset, 1).get(0);
			byte[] genotype = ((BinArrayIndividual) bestInd).getGenotype();

			classifier = new EnsembleClassifier(maxNumberLabelsClassifier, numberClassifiers, predictionThreshold, variable, new LabelPowerset(new J48()), genotype, tableClassifiers, randGenFactory.createRandGen());
			
			System.out.println("Final ensemble");
			for(int i=0; i<getNumberClassifiers(); i++)
			{
				for(int j=0; j<getDatasetTrain().getNumLabels(); j++)
				{
					System.out.print(genotype[i*getDatasetTrain().getNumLabels()+j] + " ");
				}
				System.out.println();
			}
			
			
			try {	
				classifier.build(datasetTrain);
				
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
	
	
	/* 
	 * Obtain the number of distinct base classifiers in the population 
	 */
	public int getNumberOfDistinctBaseClassifiers(List<IIndividual> bset)
	{
		int n = 0;
		
		HashSet<String> set = new HashSet<String>();
		
		for(int p=0; p<bset.size(); p++)
		{
			String p1 = getGenotypeFromIIndividual(bset.get(p));
			for(int i=0; i<p1.length()/getDatasetTrain().getNumLabels(); i++)
			{
				//Add all base classifiers to the set
				set.add(p1.substring(i*getDatasetTrain().getNumLabels(), i*getDatasetTrain().getNumLabels()+getDatasetTrain().getNumLabels()));
			}
		}
		
		//The number of distinct base classifiers is the size of the set
			//because repeated individuals are overwritten
		n = set.size();
		
		return n;
	}
	

	public String getGenotypeFromIIndividual (IIndividual i)
	{		
		String s = i.toString();
		
		s = s.split("=")[1];
		
		s = s.split(",fitness")[0];
		
		s = s.replace("{","");
		s = s.replace("}","");
		
		s = s.replace(",", "");
		
		return s;
	}
	
	public int factorial (int n)
	{
		if(n == 1)
			return 1;
		else
			return n*factorial(n-1);
	}

}