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
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;

import mulan.data.MultiLabelInstances;
import mulan.classifier.MultiLabelLearner;
import mulan.classifier.transformation.LabelPowerset;
import mulan.evaluation.Evaluation;
import mulan.evaluation.Evaluator;
import mulan.evaluation.measure.ExampleBasedFMeasure;
import mulan.evaluation.measure.Measure;
import weka.classifiers.trees.J48;
import net.sf.jclec.IFitness;
import net.sf.jclec.IIndividual;
import net.sf.jclec.base.AbstractParallelEvaluator;
import net.sf.jclec.binarray.BinArrayIndividual;
import net.sf.jclec.fitness.SimpleValueFitness;
import net.sf.jclec.fitness.ValueFitnessComparator;
import net.sf.jclec.util.random.IRandGenFactory;

/**
 * @author Jose M. Moyano: jmoyano@uco.es
 * 
 * Class implementing the evaluator for individuals in EME
 */
public class EnsembleMLCEvaluator extends AbstractParallelEvaluator
{
	/////////////////////////////////////////////////////////////////
	// --------------------------------------- Serialization constant
	/////////////////////////////////////////////////////////////////

	/**
	 * Serialization constant
	 */
	protected static final long serialVersionUID = -2635335580011827514L;
	
	/////////////////////////////////////////////////////////////////
	// --------------------------------------------------- Properties
	/////////////////////////////////////////////////////////////////
	
	/**
	 *  Dataset to build the ensemble */
	protected MultiLabelInstances datasetTrain;
	
	/**
	 *  Dataset to evaluate the individuals */
	protected MultiLabelInstances datasetValidation;
	
	/**
	 *  Max number of active labels in each base classifier 
	 *  
	 *  Currently, only fixed has been proved
	 */
	protected int maxNumberLabelsClassifier;
	
	/**
	 *  Number of base classifiers of the ensemble */
	protected int numberClassifiers;
	
	/**
	 * Threshold for voting process prediction */
	protected double predictionThreshold;
	
	/** 
	 * Indicates if the number of active labels is variable for each base classifier */
	protected boolean variable;
	
	/**
	 *  Indicates if the fitness is a value to maximize */
	protected boolean maximize = true;
	
	/**
	 *  Base learner for the classifiers of the ensemble */
	public MultiLabelLearner baseLearner;
	
	/**
	 *  Fitness values comparator */
	protected Comparator<IFitness> COMPARATOR = new ValueFitnessComparator(!maximize);
	
	/**
	 *  Table that stores all base classifiers built */
	public Hashtable<String, MultiLabelLearner> tableClassifiers;
	
	/**
	 *  Table that stores the fitness of all evaluated individuals */
	public Hashtable<String, Double> tableFitness;

	/**
	 *  Random numbers generator */
	protected IRandGenFactory randGenFactory;
	
	/**
	 *  Indicates if the coverage ratio is used in fitness */
	private boolean useCoverage;
	
	/**
	 *  Indicate if the final individual is going to be evaluated */
	public boolean finalInd = false;
	
	/**
	 *  Coverage of the final ensemble */
	public double finalCoverage;
	
	/**
	 *  Multi-label measure of the final ensemble */
	public double finalMeasure;
	
	/**
	 *  Fitness of the final ensemble */
	public double finalFitness;
	
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////
	
	/**
	* Empty constructor.
	*/
	public EnsembleMLCEvaluator()
	{
		super();
	}
	
	/////////////////////////////////////////////////////////////////
	// ----------------------------------------------- Public methods
	/////////////////////////////////////////////////////////////////
	
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
	 * Get training dataset
	 * 
	 * @return Multi-label training dataset
	 */
	public MultiLabelInstances getDatasetTrain()
	{
		return datasetTrain;
	}
	
	/**
	 * Get validation dataset
	 * 
	 * @return Multi-label validation dataset
	 */
	public MultiLabelInstances getDatasetValidation()
	{
		return datasetValidation;
	}
	
	/**
	 * Set training dataset
	 * 
	 * @param datasetTrain Multi-label training dataset
	 */
	public void setDatasetTrain(MultiLabelInstances datasetTrain) {
		this.datasetTrain = datasetTrain;
	}
	
	/**
	 * Set validation dataset
	 * 
	 * @param datasetValidation Multi-label validation dataset
	 */
	public void setDatasetValidation(MultiLabelInstances datasetValidation) {
		this.datasetValidation = datasetValidation;
	}

	/**
	 * Set the number of classifiers in the ensemble
	 * 
	 * @param numberClassifiers Number of classifiers
	 */
	public void setNumberClassifiers(int numberClassifiers) {
		this.numberClassifiers = numberClassifiers;
	}

	/**
	 * Set the max number of labels in each base classifier (k)
	 * At the moment, the number of labels for each classifier is fixed, but it could be variable
	 * 
	 * @param maxNumberLabelsClassifier Max number of labels for each classifier
	 */
	public void setMaxNumberLabelsClassifier(int maxNumberLabelsClassifier) {
		this.maxNumberLabelsClassifier = maxNumberLabelsClassifier;
	}
	
	/**
	 * Set the threshold used for the prediction in the ensemble
	 * 
	 * @param predictionThreshold Prediction threshold
	 */
	public void setPredictionThreshold(double predictionThreshold) {
		this.predictionThreshold = predictionThreshold;
	}
	
	/**
	 * Set variable value
	 * 
	 * @param variable FALSE if the number of labels for each base classifier is fixed, and TRUE if it wold be variable
	 */
	public void setVariable(boolean variable) {
		this.variable = variable;
	}
	
	/**
	 * Set the multi-label base learner used. LP(J48) is proposed as default.
	 * 
	 * @param baseLearner Multi-label learner
	 */
	public void setBaseLearner(MultiLabelLearner baseLearner)
	{
		this.baseLearner = baseLearner;
	}
	
	/**
	 * Fitness comparator
	 */
	public Comparator<IFitness> getComparator() {
		return COMPARATOR;
	}
	
	/**
	 * Set the table storing the classifiers built so far
	 * 
	 * @param tableClassifiers Table with built classifiers
	 */
	public void setTable(Hashtable<String, MultiLabelLearner> tableClassifiers) {
		this.tableClassifiers = tableClassifiers;
	}
	
	/**
	 * Set the table storing the fitness of the individuals
	 * 
	 * @param tableFitness Table storing the fitness values
	 */
	public void setTableFitness(Hashtable<String, Double> tableFitness) {
		this.tableFitness = tableFitness;
	}
	
	/**
	 * Set the random numbers generator
	 * 
	 * @param randGenFactory Factory of random numbers generator
	 */
	public void setRandGenFactory(IRandGenFactory randGenFactory)
	{
		this.randGenFactory = randGenFactory;
	}

	/**
	 * Set if coverage ratio is used or not in the evaluation of individuals
	 * 
	 * @param useCoverage TRUE if coverage ratio is included in fitness and FALSE otherwise
	 */
	public void setUseCoverage(boolean useCoverage) 
	{
		this.useCoverage = useCoverage;
	}
	
	/////////////////////////////////////////////////////////////////
	// ------------------------ Overwriting AbstractEvaluator methods
	/////////////////////////////////////////////////////////////////
	
	@Override
	protected void evaluate(IIndividual ind) 
	{
		// Individual genotype
		byte[] genotype = ((BinArrayIndividual) ind).getGenotype();

		EnsembleClassifier classifier = new EnsembleClassifier(maxNumberLabelsClassifier, numberClassifiers, predictionThreshold, variable, new LabelPowerset(new J48()), genotype, tableClassifiers, randGenFactory.createRandGen());
		
		Evaluator eval = new Evaluator();

        try {
        	// Build classifier using train data
        	classifier.build(datasetTrain);
        	    
        	List<Measure> measures = new ArrayList<Measure>();  
        	//Add only the measure to use
//  	       	measures.add(new HammingLoss());
  	       	measures.add(new ExampleBasedFMeasure());
  	       	Evaluation results;
  	       	  	
  	       	// Obtain ensembleMatrix
  	       	String s = classifier.getOrderedStringFromEnsembleMatrix();
  	       	  	
  	       	double fitness = -1;
  	       	//Try to get the individual fitness from the table
  	       	
  	       	if(tableFitness.containsKey(s) && !finalInd)
  	       	{
  	       		fitness = tableFitness.get(s).doubleValue();
  	       	}
  	       	else
  	       	{
  	       		//Calculate base fitness (1-HLoss) with validation set
  	       		results = eval.evaluate(classifier, datasetValidation, measures);
//  	       		MultipleEvaluation mResults = eval.crossValidate(classifier, datasetTrain, measures, 3);
  	       		
  	       		fitness = results.getMeasures().get(0).getValue();
//	       		fitness = mResults.getMean("Example-Based F Measure");
  	       		if(finalInd) {
	       			finalMeasure = fitness;
	       		}

     	  		if(useCoverage)
     	  		{
     	  			int [] v = classifier.getVotesPerLabel();
     	  			double expectedVotes = 0;
     	  			for(int i=0; i<v.length; i++)
     	  			{
     	  				expectedVotes += v[i];
     	  			}
     	  			expectedVotes = expectedVotes/v.length;
     	  			//System.out.println("expectedVotes: " + expectedVotes);
     	  			double distance = 0;
     				for(int i=0; i<getDatasetTrain().getNumLabels(); i++)
     				{
     					distance += (double)Math.pow(expectedVotes - v[i], 2);
     				}
     				
     				distance = Math.sqrt(distance) / datasetTrain.getNumLabels();
     				if(finalInd) {
     					System.out.println("distance: " + distance + " -> fitness: " + fitness);
     				}
     				//Maximize [(ExF) + (1-coverage)]
     				if(finalInd) {
    	       			finalCoverage = distance;
    	       		}
     				fitness = (fitness + (1-distance))/2;
     				
     	  		}
     	  		
     	  		if(finalInd) {
     	  			finalFitness = fitness;
	       		}     	  		
  	       	  	tableFitness.put(s, fitness);
  	       	  	if(finalInd) {
					System.out.println("finalFitness: " + fitness);
				}
  	       	}
  	       	  	
  	       	ind.setFitness(new SimpleValueFitness(fitness));

			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}	
	}

}