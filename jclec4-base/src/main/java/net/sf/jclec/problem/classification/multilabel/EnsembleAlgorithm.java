package net.sf.jclec.problem.classification.multilabel;

import mulan.data.IterativeStratification;
import mulan.data.LabelPowersetStratification;
import mulan.data.MultiLabelInstances;
import mulan.classifier.transformation.LabelPowerset;
import weka.classifiers.trees.J48;
import net.sf.jclec.algorithm.classic.SGE;
import net.sf.jclec.binarray.BinArrayIndividual;
import net.sf.jclec.problem.classification.multilabel.mut.IntraModelMutator;
import net.sf.jclec.problem.classification.multilabel.rec.ModelCrossover;

import org.apache.commons.configuration.Configuration;


public class EnsembleAlgorithm extends SGE
{
	/////////////////////////////////////////////////////////////////
	// --------------------------------------- Serialization constant
	/////////////////////////////////////////////////////////////////

	/** Generated by Eclipse */
	
	private static final long serialVersionUID = -2649346083463795286L;
	
	/////////////////////////////////////////////////////////////////
	// --------------------------------------------------- Properties
	/////////////////////////////////////////////////////////////////
	
	private MultiLabelInstances datasetTrain;
	
	private MultiLabelInstances datasetValidation;
	
	private MultiLabelInstances datasetTest;
	
	private EnsembleClassifier classifier;

	private int numberClassifiers;
	
	private int numberLabelsClassifier;

	private double predictionThreshold;
	
	private boolean variable; //if true the individual will have [2..numberLabelsClassifier] 1s
	
	private boolean validationSet;

	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////

	/**
	 * Empty (default) constructor
	 */
	
	public EnsembleAlgorithm() 
	{
		super();
	}
	
	/////////////////////////////////////////////////////////////////
	// ----------------------------------------------- Public methods
	/////////////////////////////////////////////////////////////////
	
	public EnsembleClassifier getClassifier()
	{
		return classifier;
	}
	
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
		
	public int getNumberClassifiers()
	{
		return numberClassifiers;
	}
	
	public int getNumberLabelsClassifier()
	{
		return numberLabelsClassifier;
	}
	
	public double getPredictionThreshold()
	{
		return predictionThreshold;
	}
	
	public boolean getVariable()
	{
		return variable;
	}
	
	public boolean isValidationSet()
	{
		return validationSet;
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
			validationSet = configuration.getBoolean("validation-set");
			if(validationSet)
			{
				System.out.println("Use a validation set to evaluate individuals");
				// 75% for train ; 100% for validation
				IterativeStratification strat = new IterativeStratification();
				MultiLabelInstances [] m = strat.stratify(fullDatasetTrain, 4);
				datasetTrain = m[0];
				datasetTrain.getDataSet().addAll(m[1].getDataSet());
				datasetTrain.getDataSet().addAll(m[2].getDataSet());
//				datasetTrain.getDataSet().addAll(m[3].getDataSet());
//				datasetValidation = m[4];
				datasetValidation = datasetTrain;
				datasetValidation.getDataSet().addAll(m[3].getDataSet());
			}
			else
			{
				System.out.println("Evaluate individuals with all train set");
				datasetTrain = fullDatasetTrain;
				datasetValidation = datasetTrain;
			}
			
			
			// Obtain settings
			int numberLabels = datasetTrain.getNumLabels();
			numberLabelsClassifier = configuration.getInt("number-labels-classifier");
			numberClassifiers = configuration.getInt("number-classifiers"); 
			predictionThreshold = configuration.getDouble("prediction-threshold");
			variable = configuration.getBoolean("variable");
			
			// Set provider settings
			((EnsembleMLCCreator) provider).setNumberClassifiers(numberClassifiers);
			((EnsembleMLCCreator) provider).setNumberLabelsClassifier(numberLabelsClassifier);
			((EnsembleMLCCreator) provider).setNumberLabels(numberLabels);
			((EnsembleMLCCreator) provider).setVariable(variable); 
			
			// Set evaluator settings			
			((EnsembleMLCEvaluator) evaluator).setDatasetTrain(datasetTrain);
			((EnsembleMLCEvaluator) evaluator).setDatasetValidation(datasetValidation);
			((EnsembleMLCEvaluator) evaluator).setNumberClassifiers(numberClassifiers);
			((EnsembleMLCEvaluator) evaluator).setNumberLabelsClassifier(numberLabelsClassifier);
			((EnsembleMLCEvaluator) evaluator).setPredictionThreshold(predictionThreshold);
			((EnsembleMLCEvaluator) evaluator).setVariable(variable);
			
			// Set genetic operator settingsS
			((IntraModelMutator) mutator.getDecorated()).setNumberLabels(numberLabels);
			((ModelCrossover) recombinator.getDecorated()).setNumberLabels(numberLabels);
			
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
		// If maximum number of generations is exceeded, evolution is finished
		if (generation >= maxOfGenerations)
		{
			
			byte[] genotype = ((BinArrayIndividual) bset.get(0)).getGenotype();

			classifier = new EnsembleClassifier(numberLabelsClassifier, numberClassifiers, predictionThreshold, variable, new LabelPowerset(new J48()), genotype);
			
			try {	
				classifier.build(datasetTrain);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			state = FINISHED;
		}
	}
}