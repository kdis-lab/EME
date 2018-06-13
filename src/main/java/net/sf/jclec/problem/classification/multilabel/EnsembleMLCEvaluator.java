package net.sf.jclec.problem.classification.multilabel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;

import mulan.data.MultiLabelInstances;
import mulan.classifier.MultiLabelLearner;
import mulan.classifier.transformation.LabelPowerset;
import mulan.evaluation.Evaluation;
import mulan.evaluation.Evaluator;
import mulan.evaluation.MultipleEvaluation;
import mulan.evaluation.measure.ExampleBasedFMeasure;
import mulan.evaluation.measure.HammingLoss;
import mulan.evaluation.measure.Measure;
import weka.classifiers.trees.J48;
import net.sf.jclec.IFitness;
import net.sf.jclec.IIndividual;
import net.sf.jclec.base.AbstractParallelEvaluator;
import net.sf.jclec.binarray.BinArrayIndividual;
import net.sf.jclec.fitness.SimpleValueFitness;
import net.sf.jclec.fitness.ValueFitnessComparator;
import net.sf.jclec.util.random.IRandGenFactory;


public class EnsembleMLCEvaluator extends AbstractParallelEvaluator
{
	/////////////////////////////////////////////////////////////////
	// --------------------------------------- Serialization constant
	/////////////////////////////////////////////////////////////////

	protected static final long serialVersionUID = -2635335580011827514L;
	
	/////////////////////////////////////////////////////////////////
	// --------------------------------------------------- Properties
	/////////////////////////////////////////////////////////////////
	
	/* Dataset to build the ensemble */
	protected MultiLabelInstances datasetTrain;
	
	/* Dataset to evaluate the individuals */
	protected MultiLabelInstances datasetValidation;
	
	/* Max number of active labels in each base classifier */
	protected int maxNumberLabelsClassifier;
	
	/* Number of base classifiers of the ensemble */
	protected int numberClassifiers;
	
	/* Threshold for voting process prediction*/
	protected double predictionThreshold;
	
	/* Indicates if the number of active labels is variable for each base classifier */
	protected boolean variable;
	
	/* Indicates if the fitness is a value to maximize */
	protected boolean maximize = true;
	
	/* Base learner for the classifiers of the ensemble */
	public MultiLabelLearner baseLearner;
	
	/* Fitness values comparator */
	protected Comparator<IFitness> COMPARATOR = new ValueFitnessComparator(!maximize);
	
	/* Table that stores all base classifiers built */
	public Hashtable<String, MultiLabelLearner> tableClassifiers;
	
	/* Table that stores the fitness of all evaluated individuals */
	public Hashtable<String, Double> tableFitness;
	
	/* Matrix with phi correlations between labels */
	double [][] phiMatrix;
	
	/* Indicates if the individual diversity is contemplated in fitness */
	private boolean fitnessWithIndividualDiversity = false;
	
	/* Random numbers generator */
	protected IRandGenFactory randGenFactory;
	
	/* Indicates if the individual fitness contemplates the phi correlation between labels */
	private boolean phiInFitness;
	
	/* Indicates if the coverage is used in fitness */
	private boolean useCoverage;
	
	/* Indicate if the final individual is going to be evaluated */
	public boolean finalInd = false;
	
	/* Coverage of the final ensemble */
	public double finalCoverage;
	
	/* Multi-label measure of the final ensemble */
	public double finalMeasure;
	
	/* Fitness of the final ensemble */
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
	
	public int getNumberClassifiers()
	{
		return numberClassifiers;
	}
	
	public MultiLabelInstances getDatasetTrain()
	{
		return datasetTrain;
	}
	
	public MultiLabelInstances getDatasetValidation()
	{
		return datasetValidation;
	}
	
	public void setDatasetTrain(MultiLabelInstances datasetTrain) {
		this.datasetTrain = datasetTrain;
	}
	
	public void setDatasetValidation(MultiLabelInstances datasetValidation) {
		this.datasetValidation = datasetValidation;
	}

	public void setNumberClassifiers(int numberClassifiers) {
		this.numberClassifiers = numberClassifiers;
	}

	public void setMaxNumberLabelsClassifier(int maxNumberLabelsClassifier) {
		this.maxNumberLabelsClassifier = maxNumberLabelsClassifier;
	}
	
	public void setPredictionThreshold(double predictionThreshold) {
		this.predictionThreshold = predictionThreshold;
	}
	
	public void setVariable(boolean variable) {
		this.variable = variable;
	}
	
	public void setBaseLearner(MultiLabelLearner baseLearner)
	{
		this.baseLearner = baseLearner;
	}
	
	public Comparator<IFitness> getComparator() {
		return COMPARATOR;
	}
	
	public boolean getFitnessWithIndividualDiversity()
	{
		return fitnessWithIndividualDiversity;
	}
	
	public void setFitnessWithIndividualDiversity(boolean b)
	{
		fitnessWithIndividualDiversity = b;
	}
	
	public void setTable(Hashtable<String, MultiLabelLearner> tableClassifiers) {
		this.tableClassifiers = tableClassifiers;
	}
	
	public void setTableMeasures(Hashtable<String, Double> tableFitness) {
		this.tableFitness = tableFitness;
	}
	
	public void setPhiMatrix(double [][] matrix)
	{
		phiMatrix = matrix;
	}
	
	 public void setRandGenFactory(IRandGenFactory randGenFactory)
	 {
		 this.randGenFactory = randGenFactory;
	 }
	 
	 public void setPhiInFitness(boolean phiInFitness)
	 {
		 this.phiInFitness = phiInFitness;
	 }

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
  	       	byte [][] ensembleMatrix = classifier.getEnsembleMatrix();
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

  	       	  		
     	  		if(phiInFitness)
     	  		{
     	  			/*
	       	  		 * Introduces Phi correlation in fitness
	       	  		 * 	Maximize [(1-HLoss) + PhiSum]
	       	  		 */
	       	  			   	  	       	  			
	       	  		double phiTotal = 0;
//	       	  		double minPhi = Double.MAX_VALUE;
	       	  		
	       	  		double sumPhi, phiActual;
	       	  		int active;

	       	  		//Calculate sumPhi for all base classifiers
	       	  		for(int c=0; c<getNumberClassifiers(); c++)
	       	  		{
	       	  			sumPhi = 0;
	       	  			active = 0;
	       	  			//calculate sum of phi label correlations for a base classifier
	       	  			for(int i=0; i<getDatasetTrain().getNumLabels()-1; i++)
	       	  			{
	       	  				if(ensembleMatrix[c][i] == 1)
	       	  					active ++;
	       	  					
	       	  				for(int j=i+1; j<getDatasetTrain().getNumLabels(); j++)
	       	  				{
	       	  					if((ensembleMatrix[c][i] == 1) && (ensembleMatrix[c][j] == 1))
	       	  					{
	       	  						if(!Double.isNaN(phiMatrix[i][j]))
	       	  							sumPhi += Math.abs(phiMatrix[i][j]);
	       	  					}
	       	  				}
	       	  			}
	       	  			
	       	  			phiActual = sumPhi/(double)active;
//	       	  			if(phiActual<minPhi)
//	       	  				minPhi = phiActual;
	       	  			phiTotal += phiActual;
	       	  		}
	       	  		
	       	  		phiTotal = phiTotal/(double)getNumberClassifiers();
	       	  		System.out.println("phiTotal: " + phiTotal);
	       	  		//Maximize [(1-HLoss) + PhiSum]
	       	  		fitness = fitness + phiTotal;
	       	  			
//	       	  		System.out.println("minPhi: " + minPhi);
//	       	  		fitness = fitness + minPhi;
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
     				//Maximize [(1-HLoss) + (1-coverage)]
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
  	       	  	
//  	       	  	System.out.println("Fitness: " + fitness);
  	       	  	ind.setFitness(new SimpleValueFitness(fitness));

			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}	
	}

}