package net.sf.jclec.problem.classification.multilabel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

import mulan.data.LabelPowersetStratification;
import mulan.data.MultiLabelInstances;
import mulan.data.Statistics;
import mulan.evaluation.Evaluation;
import mulan.evaluation.Evaluator;
import mulan.evaluation.measure.HammingLoss;
import mulan.evaluation.measure.Measure;
import mulan.classifier.MultiLabelLearner;
import mulan.classifier.transformation.BinaryRelevance;
import mulan.classifier.transformation.ClassifierChain;
import mulan.classifier.transformation.EnsembleOfClassifierChains;
import mulan.classifier.transformation.LabelPowerset;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
import net.sf.jclec.IIndividual;
import net.sf.jclec.algorithm.classic.SGE;
import net.sf.jclec.binarray.BinArrayIndividual;
import net.sf.jclec.problem.classification.multilabel.mut.IntraModelMutator;
import net.sf.jclec.problem.classification.multilabel.mut.PhiBasedIntraModelMutator;
import net.sf.jclec.problem.classification.multilabel.rec.UniformModelCrossover;
import net.sf.jclec.selector.BettersSelector;

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
	
	/* Dataset to build the ensembles */
	private MultiLabelInstances fullDatasetTrain;
	
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
	
	/* Table that stores the phi fitness values of all base classifiers */
	private Hashtable<String, Double> tablePhi;
	
	/* Indicates if a validation set is used to evaluate the individuals */
	private boolean useValidationSet;
	
	/* Indicates if the individual fitness contemplates the phi correlation between labels */
	private boolean phiInFitness;

	/* Indicates if the coverage is used in fitness */
	private boolean useCoverage;
	
	/* Learner to predict the labels to use as attributes */
	private MultiLabelLearner labelsPredictor;
	
	/* Phi matrix */
	double [][] phi;
	
	/* Classifier to use to extend instances with predictions of non-used labels */
	String labelsPredictorType;
	
	/* Base classifier */
	String baseClassifierType;

	
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
		tablePhi = new Hashtable<String, Double> ();
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

	
	/**
	 * {@inheritDoc}
	 */

	@Override
	public void configure(Configuration configuration)
	{
		super.configure(configuration);
		
		try {
			/* Obtain settings */
			
			// Read train/test datasets
			String datasetTrainFileName = configuration.getString("dataset.train-dataset");
			String datasetTestFileName = configuration.getString("dataset.test-dataset");
			String datasetXMLFileName = configuration.getString("dataset.xml");
			
			fullDatasetTrain = new MultiLabelInstances(datasetTrainFileName, datasetXMLFileName);
			datasetTest = new MultiLabelInstances(datasetTestFileName, datasetXMLFileName);			
			
			//Use or not a validation set to evaluate individuals
			useValidationSet = configuration.getBoolean("validation-set");
			if(useValidationSet)
			{
//				IterativeStratification strat = new IterativeStratification();
				LabelPowersetStratification strat = new LabelPowersetStratification();
				// 80% for train ; 100% for validation
				MultiLabelInstances [] m = strat.stratify(fullDatasetTrain, 5);
				//Train set have 4 folds
				datasetTrain = m[0].clone();
				datasetTrain.getDataSet().addAll(m[1].getDataSet());
				datasetTrain.getDataSet().addAll(m[2].getDataSet());
				datasetTrain.getDataSet().addAll(m[3].getDataSet());

				//Validation set have all 5 folds
				datasetValidation = datasetTrain.clone();
				datasetValidation.getDataSet().addAll(m[4].getDataSet());
			}
			else
			{
				//Train and validation set are the same, the full set
				datasetTrain = fullDatasetTrain;				
				datasetValidation = datasetTrain;
			}

			int numberLabels = datasetTrain.getNumLabels();
			
			variable = configuration.getBoolean("variable");
			if(variable){
				/*
				 * If the number of labels in each base classifier is variable,
				 *  we select sqrt(numLabels) as max number of labels for each classifier.
				 */
				maxNumberLabelsClassifier = (int) Math.ceil(Math.sqrt(numberLabels));
			}
			else{
				maxNumberLabelsClassifier = configuration.getInt("number-labels-classifier");
			}
			
			numberClassifiers = configuration.getInt("number-classifiers"); 
			predictionThreshold = configuration.getDouble("prediction-threshold");
			
			phiInFitness = configuration.getBoolean("phi-in-fitness");
			useCoverage = configuration.getBoolean("use-coverage");
			
			labelsPredictorType = configuration.getString("labels-predictor");
			baseClassifierType = configuration.getString("base-classifier");

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
			((EnsembleMLCEvaluator) evaluator).setTablePhi(tablePhi);
			((EnsembleMLCEvaluator) evaluator).setBaseClassifierType(baseClassifierType);
			
			//Build a learner to obtain label predictions to add as attributes in test phase	
			if(labelsPredictorType.equals("BR")){
				System.out.println("Building BR as label attributes predictor");
				labelsPredictor = new BinaryRelevance(new J48());
			}
			else if(labelsPredictorType.equals("CC")){
				System.out.println("Building CC as label attributes predictor");
				labelsPredictor = new ClassifierChain(new J48());
			}
			else{
				System.out.println("Building BR as default label attributes predictor");
				labelsPredictor = new BinaryRelevance(new J48());
			}
			
			labelsPredictor.build(datasetTrain);
			
			((EnsembleMLCEvaluator) evaluator).setLabelsPredictor(labelsPredictor);

			// Set genetic operator settingsS
			((IntraModelMutator) mutator.getDecorated()).setNumberLabels(numberLabels);
			((UniformModelCrossover) recombinator.getDecorated()).setNumberLabels(numberLabels);
			
			//Calculate PhiMatrix only if its necessary
			if((mutator.getDecorated().getClass().toString().contains("PhiBased")) || (phiInFitness))
			{
				Statistics s = new Statistics();
				phi = s.calculatePhi(datasetValidation);
				
				s.printPhiCorrelations();
				
				// Send Phi matrix to the mutator if it needs it
				if(mutator.getDecorated().getClass().toString().contains("PhiBasedIntraModelMutator")){
					((PhiBasedIntraModelMutator) mutator.getDecorated()).setPhiMatrix(phi);
				}

				//Send phi matrix to evaluator
				if(phiInFitness){
					((EnsembleMLCEvaluator) evaluator).setPhiMatrix(phi);
				}
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
		bset = bselector.select(bset, bset.size());		

		// If maximum number of generations is exceeded, evolution is finished
		if (generation >= maxOfGenerations)
		{
			/* Get the best individual */
			IIndividual bestInd = bselector.select(bset, 1).get(0);
			System.out.println(bestInd.getFitness());
			byte[] genotype = ((BinArrayIndividual) bestInd).getGenotype();
			
			MultiLabelLearner baseLearner = null;
			
			if(baseClassifierType.equals("LP")){
				System.out.println("Using LP as base classifier");
				J48 j48 = new J48();
				baseLearner = new LabelPowerset(j48);
			}
			else if(baseClassifierType.equals("CC")){
				System.out.println("Using CC as base classifier");
				J48 j48 = new J48();
				baseLearner = new ClassifierChain(j48);
			}
			else{
				System.out.println("Using LP as default base classifier");
				J48 j48 = new J48();
				baseLearner = new LabelPowerset(j48);
			}

			classifier = new EnsembleClassifier(maxNumberLabelsClassifier, numberClassifiers, predictionThreshold, 
					variable, baseLearner, genotype, tableClassifiers, randGenFactory.createRandGen(), labelsPredictor);
			
			
			try {	
				//Build the ensemble
				classifier.build(datasetTrain);
				
				/* Print the final ensemble */
				System.out.println("Final ensemble");
				for(int i=0; i<numberClassifiers; i++)
				{
					System.out.println(Arrays.toString(classifier.EnsembleMatrix[i]));
				}
				
				System.out.println("numVotes");
				System.out.println(Arrays.toString(classifier.getVotesPerLabel()));
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			state = FINISHED;
		}
	}

}