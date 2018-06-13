package net.sf.jclec.problem.classification.multilabel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;

import mulan.data.MultiLabelInstances;
import mulan.classifier.MultiLabelLearner;
import mulan.classifier.transformation.ClassifierChain;
import mulan.classifier.transformation.LabelPowerset;
import mulan.evaluation.Evaluation;
import mulan.evaluation.Evaluator;
import mulan.evaluation.measure.HammingLoss;
import mulan.evaluation.measure.Measure;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
import weka.classifiers.trees.RandomTree;
import net.sf.jclec.IFitness;
import net.sf.jclec.IIndividual;
import net.sf.jclec.base.AbstractEvaluator;
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
	protected boolean maximize = false;
	
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
	
	/* Table that stores the phi fitness values of all base classifiers */
	private Hashtable<String, Double> tablePhi;
	
	/* Learner to predict the labels to use as attributes */
	private MultiLabelLearner labelsPredictor;
	
	/* Base classifier */
	String baseClassifierType;
	
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
	
	public void setTablePhi(Hashtable<String, Double> tablePhi) {
		this.tablePhi = tablePhi;
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
	 
	 public void setLabelsPredictor(MultiLabelLearner predictor) 
	 {
		this.labelsPredictor = predictor;
	 }
	 
	 public void setBaseClassifierType(String baseClassifierType){
		 this.baseClassifierType = baseClassifierType;
	 }
	
	/////////////////////////////////////////////////////////////////
	// ------------------------ Overwriting AbstractEvaluator methods
	/////////////////////////////////////////////////////////////////
	
	@Override
	protected void evaluate(IIndividual ind) 
	{
		// Individual genotype
		byte[] genotype = ((BinArrayIndividual) ind).getGenotype();
		
		EnsembleClassifier classifier = null;
		
		if(baseClassifierType.equals("LP")){
			J48 j48 = new J48();
			LabelPowerset lp = new LabelPowerset(j48);
			classifier = new EnsembleClassifier(maxNumberLabelsClassifier, numberClassifiers, predictionThreshold, 
					variable, lp, genotype, tableClassifiers, randGenFactory.createRandGen(), labelsPredictor);
		}
		else if(baseClassifierType.equals("CC")){
			J48 j48 = new J48();
			ClassifierChain cc = new ClassifierChain(j48);			
			classifier = new EnsembleClassifier(maxNumberLabelsClassifier, numberClassifiers, predictionThreshold, 
					variable, cc, genotype, tableClassifiers, randGenFactory.createRandGen(), labelsPredictor);
		}
		else{
			J48 j48 = new J48();
			LabelPowerset lp = new LabelPowerset(j48);
			classifier = new EnsembleClassifier(maxNumberLabelsClassifier, numberClassifiers, predictionThreshold, 
					variable, lp, genotype, tableClassifiers, randGenFactory.createRandGen(), labelsPredictor);
		}
		

//		EnsembleClassifier classifier = new EnsembleClassifier(maxNumberLabelsClassifier, numberClassifiers, predictionThreshold, 
//				variable, baseLearner, genotype, tableClassifiers, randGenFactory.createRandGen(), labelsPredictor, phiMatrix);
	
		Evaluator eval = new Evaluator();

        try {
        	// Build classifier using train data
        	classifier.build(datasetTrain);
        	    
        	List<Measure> measures = new ArrayList<Measure>();  
        	//Add only the measure to use
  	       	measures.add(new HammingLoss());
  	       	Evaluation results;
  	       	
  	       	  	
  	       	// Obtain ensembleMatrix
  	       	byte [][] ensembleMatrix = classifier.getEnsembleMatrix();
  	       	String s = classifier.getOrderedStringFromEnsembleMatrix();
  	       	  	
  	       	double fitness = -1;
  	       	//Try to get the individual fitness from the table
  	       	if(tableFitness.containsKey(s))
  	       	{
  	       		fitness = tableFitness.get(s).doubleValue();
  	       	}
  	       	else
  	       	{
  	       		//Calculate base fitness: HLoss
  	       		results = eval.evaluate(classifier, datasetValidation, measures);
  	       		fitness = results.getMeasures().get(0).getValue();	  	       		
  	       		
     	  		if(phiInFitness)
     	  		{
     	  			double phiTotal = 0;
	       	  		double sumPhi, active, phiActual;
	       	  		String keyPhi;

	       	  		//Calculate sumPhi for all base classifiers
	       	  		for(int c=0; c<getNumberClassifiers(); c++)
	       	  		{   	  			
	       	  			keyPhi = Arrays.toString(ensembleMatrix[c]);
	       	  			
	       	  			if(tablePhi.containsKey(keyPhi))
	       	  			{
	       	  				//Obtain fitness for this classifier
	       	  				phiTotal += tablePhi.get(keyPhi).doubleValue();
	       	  			}
	       	  			else
	       	  			{
	       	  				sumPhi = 0;
	       	  				active = 0;
	       	  				
	       	  				//Calculate sum of phi label correlations for a base classifier
		       	  			for(int i=0; i<getDatasetTrain().getNumLabels(); i++)
		       	  			{
		       	  				if(ensembleMatrix[c][i] == 1)
		       	  				{
		       	  					active ++;
		       	  					
			       	  				for(int j=i+1; j<getDatasetTrain().getNumLabels(); j++)
			       	  				{
			       	  					if(ensembleMatrix[c][j] == 1)
			       	  					{
			       	  						if(!Double.isNaN(phiMatrix[i][j])) {
			       	  							sumPhi += Math.abs(phiMatrix[i][j]);
			       	  						}
			       	  					}
			       	  				}
		       	  				}
		       	  			}
		       	  			
		       	  			phiActual = sumPhi / (double)(((active-1)*active) / 2);
		       	  			tablePhi.put(keyPhi, phiActual);
		       	  			phiTotal += phiActual;
		       	  			
	       	  			}	  			
	       	  		}
	       	  		
	       	  		phiTotal = phiTotal/(double)getNumberClassifiers();
	       	  		//Minimize HLoss - phiTotal
	       	  		fitness = fitness - phiTotal;
     	  		}
     	  			
     	  		if(useCoverage)
     	  		{
     	  			int [] v = classifier.getVotesPerLabel();
     	  			int totalVotes = 0;
     	  			for(int i=0; i<v.length; i++)
     	  			{
     	  				totalVotes += v[i];
     	  			}
     	  			
     	  			//DesvEst of worst case votes vector (all votes in few labels)
     	  			int [] worstVector = new int[classifier.getNumLabels()];
     	  			for(int i=0; i<classifier.getNumLabels(); i++) {
     	  				worstVector[i] = 0;
     	  			}
     	  			
     	  			int i = 0;
     	  			while(totalVotes > 0) {
     	  				if(totalVotes > numberClassifiers) {
     	  					worstVector[i] = numberClassifiers;
     	  					totalVotes -= numberClassifiers;
     	  				}
     	  				else {
     	  					worstVector[i] = totalVotes;
     	  					totalVotes = 0;
     	  				}
     	  				i++;
     	  			}
     	  			
     	  			double stdevActualVotes = stdev(v);
     	  			//System.out.println("stdevActualVotes: " + stdevActualVotes);
     	  			double stdevWorst = stdev(worstVector);
     	  			//System.out.println("stdevWorst: " + stdevWorst);
     				
     				double coverage = stdevActualVotes/stdevWorst;
     				//System.out.println("Coverage: " + coverage);
     				
     				//Coverage is to minimize, as base fitness (HLoss)
     				fitness = fitness + coverage;

     	  		}
     	  		
//     	  		System.out.println(fitness);
  	       	  	tableFitness.put(s, fitness);
  	       	}
  	       	  	
//  	       	  	System.out.println("Fitness: " + fitness);
  	       	  	ind.setFitness(new SimpleValueFitness(fitness));

			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}	
	}

	private double stdev(int [] v) {
		
		double stdev = 0;
		
		double mean = 0;
		for(int i=0; i<v.length; i++) {
			mean += v[i];
		}
		mean = mean/v.length;
		
		double variance = 0;
		for(int i=0; i<v.length; i++) {
			variance += Math.pow(v[i]-mean, 2);
		}
		variance = variance/v.length;
		
		stdev = Math.sqrt(variance);
		
		
		return stdev;
	}
	
}