package eme;

import mulan.data.InvalidDataFormatException;
import mulan.data.MultiLabelInstances;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;

import weka.classifiers.trees.J48;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;
import mulan.classifier.InvalidDataException;
import mulan.classifier.ModelInitializationException;
import mulan.classifier.MultiLabelLearner;
import mulan.classifier.MultiLabelOutput;
import mulan.classifier.meta.MultiLabelMetaLearner;
import mulan.classifier.transformation.LabelPowerset;
import net.sf.jclec.util.random.IRandGen;

/**
 * @author Jose M. Moyano: jmoyano@uco.es
 * 
 * Class implementing the ensemble classifier which EME uses, i.e., an ensemble where each classifier uses only a subset of the labels.
 */
public class EnsembleClassifier extends MultiLabelMetaLearner
{	
	/////////////////////////////////////////////////////////////////
	// --------------------------------------- Serialization constant
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Serialization constant
	 */
	private static final long serialVersionUID = 1402348312634173068L;

	
	/////////////////////////////////////////////////////////////////
	// --------------------------------------------------- Properties
	/////////////////////////////////////////////////////////////////
	
	/**
	 *  Dataset to build the classifier */
	protected MultiLabelInstances multilabelDatasetTrain;
	
	/**
	 *  Max number of active labels in a base classifier */
	protected int maxSubsetSize; 
	
	/**
	 *  Number of base classifiers in the ensemble */
	protected int numClassifiers;
	
	/**
	 *  Threshold for voting process prediction */
	protected double threshold;
	
	/**
	 *  Array of classifiers that form the ensemble */
	protected MultiLabelLearner[] Ensemble;
	
	/**
	 *  Size of the subset of labels of each base classifier
	 *  At the moment, all subsets are the same size, but in the future it could be variable
	 */
	protected int[] SizeSubsets;
	
	/**
	 *  Filter to remove the non-active labels */
	protected Remove[] Filters;
	
	/**
	 *  Binary matrix that identifies the ensemble */
	protected byte EnsembleMatrix[][]=null;
	
	/**
	 *  Indicates if the number of active labels is variable for each base classifier */
	protected boolean variable;
	
	/**
	 *  Individual genotype that identifies the individual */
	protected byte genotype[];
	
	/**
	 *  Random numbers generator */
	protected IRandGen randGen;
	
	/**
	 *  Table that stores all base classifiers built */
	private Hashtable<String, MultiLabelLearner> tableClassifiers;
	
	/**
	 *  Array with number of votes of the ensemble for each label */
	private int [] votesPerLabel;

	

	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Constructor with parameters
	 * 
	 * @param maxSubsetSize Max number of labels in each base classifier. At the moment, the max is really the fixed number of labels
	 * @param numClassifiers Number of classifier in the ensemble
	 * @param threshold Prediction threshold
	 * @param variable Indicates if the number of labels is variable or not for each base classifier (at the moment, only fixed has been proved)
	 * @param baseLearner Type of multi-label learner used for EME. LP(J48) is proposed as default.
	 * @param genotype Genotype of the individual
	 * @param tableClassifiers Table storing the classifiers built
	 * @param randGen Random numbers generator
	 */
	public EnsembleClassifier(int maxSubsetSize, int numClassifiers, double threshold, boolean variable, MultiLabelLearner baseLearner, byte[] genotype, Hashtable<String, MultiLabelLearner> tableClassifiers, IRandGen randGen)
	{
		super(baseLearner);
		this.maxSubsetSize = maxSubsetSize;
		this.numClassifiers = numClassifiers;
		this.threshold = threshold;
	    this.variable = variable;
		this.genotype = genotype;
		this.tableClassifiers = tableClassifiers;
		this.randGen = randGen;		
	}
	
	/**
	 * Constructor with parameters
	 * 
	 * @param maxSubsetSize Max number of labels in each base classifier. At the moment, the max is really the fixed number of labels
	 * @param threshold Prediction threshold
	 * @param variable Indicates if the number of labels is variable or not for each base classifier (at the moment, only fixed has been proved)
	 * @param baseLearner Type of multi-label learner used for EME. LP(J48) is proposed as default.
	 * @param genotype Genotype of the individual
	 * @param tableClassifiers Table storing the classifiers built
	 * @param randGen Random numbers generator
	 */
	public EnsembleClassifier(int maxSubsetSize, double threshold, boolean variable, MultiLabelLearner baseLearner, byte[] genotype, Hashtable<String, MultiLabelLearner> tableClassifiers, IRandGen randGen)
	{
		super(baseLearner);
		this.maxSubsetSize = maxSubsetSize;
		this.numClassifiers = -1;
		this.threshold = threshold;
	    this.variable = variable;
		this.genotype = genotype;
		this.tableClassifiers = tableClassifiers;
		this.randGen = randGen;
	}
	
	/**
	 * Constructor with parameters
	 * 
	 * @param maxSubsetSize Max number of labels in each base classifier. At the moment, the max is really the fixed number of labels
	 * @param numClassifiers Number of classifier in the ensemble
	 * @param threshold Prediction threshold
	 * @param variable Indicates if the number of labels is variable or not for each base classifier (at the moment, only fixed has been proved)
	 * @param baseLearner Type of multi-label learner used for EME. LP(J48) is proposed as default.
	 * @param tableClassifiers Table storing the classifiers built
	 * @param randGen Random numbers generator
	 */
	public EnsembleClassifier(int maxSubsetSize, int numClassifiers, double threshold, boolean variable, MultiLabelLearner baseLearner, Hashtable<String, MultiLabelLearner> tableClassifiers, IRandGen randGen)
	{
		super(baseLearner);
		this.maxSubsetSize = maxSubsetSize;
		this.numClassifiers = numClassifiers;
		this.threshold = threshold;
	    this.variable = variable;
		this.tableClassifiers = tableClassifiers;
		this.randGen = randGen;
	}
	
	/**
	 * Constructor with parameters
	 * 
	 * @param maxSubsetSize Max number of labels in each base classifier. At the moment, the max is really the fixed number of labels
	 * @param numClassifiers Number of classifier in the ensemble
	 * @param threshold Prediction threshold
	 * @param variable Indicates if the number of labels is variable or not for each base classifier (at the moment, only fixed has been proved)
	 * @param baseLearner Type of multi-label learner used for EME. LP(J48) is proposed as default.
	 * @param genotype Genotype of the individual
	 * @param randGen Random numbers generator
	 */
	public EnsembleClassifier(int maxSubsetSize, int numClassifiers, double threshold, boolean variable, MultiLabelLearner baseLearner, byte[] genotype, IRandGen randGen)
	{
		super(baseLearner);
		this.maxSubsetSize = maxSubsetSize;
		this.numClassifiers = numClassifiers;
		this.threshold = threshold;
	    this.variable = variable;
		this.genotype = genotype;
		tableClassifiers = new Hashtable<String, MultiLabelLearner>();
		this.randGen = randGen;
	}
	
	/**
	 * Constructor with parameters
	 * 
	 * @param maxSubsetSize Max number of labels in each base classifier. At the moment, the max is really the fixed number of labels
	 * @param numClassifiers Number of classifier in the ensemble
	 * @param threshold Prediction threshold
	 * @param variable Indicates if the number of labels is variable or not for each base classifier (at the moment, only fixed has been proved)
	 * @param baseLearner Type of multi-label learner used for EME. LP(J48) is proposed as default.
	 * @param randGen Random numbers generator
	 */
	public EnsembleClassifier(int maxSubsetSize, int numClassifiers, double threshold, boolean variable, MultiLabelLearner baseLearner, IRandGen randGen)
	{
		super(baseLearner);
		this.maxSubsetSize = maxSubsetSize;
		this.numClassifiers = numClassifiers;
		this.threshold = threshold;
	    this.variable = variable;
		tableClassifiers = new Hashtable<String, MultiLabelLearner>();
		this.randGen = randGen;
	}
		
	
	
	/////////////////////////////////////////////////////////////////
	// ----------------------------------------------- Public methods
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Get if the number of labels in each base classifier is variable
	 * At the moment, only the fixed version has been proved
	 * 
	 * @return FALSE if the number of labels for each base classifier is fixed, and TRUE if it wold be variable
	 */
	public boolean isVariable() {
		return variable;
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
	 * Get the max number of labels in each base classifier (k)
	 * At the moment, the number of labels for each classifier is fixed, but it could be variable
	 * 
	 * @return Number of labels in each base classifier
	 */
	public int getMaxSubsetSize() {
		return maxSubsetSize;
	}

	/**
	 * Set the max number of labels in each base classifier (k)
	 * At the moment, the number of labels for each classifier is fixed, but it could be variable
	 * 
	 * @param maxSubsetSize Number of labels in each base classifier
	 */
	public void setMaxSubsetSize(int maxSubsetSize){ 
		this.maxSubsetSize = maxSubsetSize;
	}

	/**
	 * Get the number of classifiers in the ensemble
	 * 
	 * @return Number of classifiers in the ensemble
	 */
	public int getNumClassifiers() {
		return numClassifiers;
	}

	/**
	 * Set the number of classifiers in the ensemble
	 * 
	 * @param numClassifiers Number of classifiers in the ensemble
	 */
	public void setNumClassifiers(int numClassifiers) {
		this.numClassifiers = numClassifiers;
	}

	/**
	 * Get the threshold used for prediction
	 * 
	 * @return Prediction threshold
	 */
	public double getPredictionThreshold() {
		return threshold;
	}

	/**
	 * Set the threshold used for prediction
	 * 
	 * @param threshold Prediction threshold
	 */
	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}
	
	/**
	 * Get the number of labels in the dataset
	 * 
	 * @return Number of labels
	 */
	public int getNumLabels()
	{
		return numLabels;
	}
	
	/**
	 * Get the genotype of the individual
	 * 
	 * @return Genotype of the individual
	 */
	public byte[] getGenotype()
	{
		return genotype;
	}
	
	/**
	 * Get the binary matrix that represent the individual
	 * 
	 * @return Binary matrix representing the individual
	 */
	public byte[][] getEnsembleMatrix()
	{
		return EnsembleMatrix;
	}
	
	/**
	 * Set the multi-label dataset to train the models
	 * 
	 * @param multilabelDatasetTrain Training multi-label dataset
	 */
	public void setMultiLabelDatasetTrain (MultiLabelInstances multilabelDatasetTrain)
	{
		this.multilabelDatasetTrain = multilabelDatasetTrain;
	}
      
	/**
	 * Get number of votes per label
	 * 
	 * @return Number of votes per label
	 */
	public int[] getVotesPerLabel() {
		return votesPerLabel;
	}
	
	@Override
	public String toString()
	{
		String str = "";
		str+="\nnumLabels: "+numLabels;
		str+="\nnumClassifiers:"+numClassifiers;
		str+="\nmaxSubsetSize:"+maxSubsetSize;
		str+="\nEnsembleMatrix:\n";		
		for(int model=0; model<numClassifiers; model++)
		{	
			for (int label=0; label<numLabels; label++)
			{	
				if(EnsembleMatrix[model][label]==0)
				    str+="0 ";
				else
					str+="1 ";
			}
			str+="\n";
		}
		return str;
	}
	
	/**
	 * Classify a set of instances
	 * 
	 * @param mlData multi-label dataset
	 * 
	 * @return Binary matrix with bipartitions for the dataset
	 */
	public int[][] classify(MultiLabelInstances mlData)
	{		
		int[][] predictions = new int[mlData.getNumInstances()][numLabels];

		Instances data = mlData.getDataSet();
		
		MultiLabelOutput mlo = null;
		
		for (int i=0; i<mlData.getNumInstances(); i++)
		{ 	
		    try {
				mlo = this.makePrediction(data.get(i));
				for(int j=0; j<this.numLabels; j++)
				{	
					if(mlo.getBipartition()[j])
					{
						predictions[i][j]=1;
					}	
					else
					{
						predictions[i][j]=0;
					}	  
				}
				
			} catch (InvalidDataException e) {
				e.printStackTrace();
			} catch (ModelInitializationException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return(predictions);		
	}
	
	
	/*
	 * Build the ensemble
	 */
	@Override
	protected void buildInternal(MultiLabelInstances trainingData) throws Exception {
		 
		   this.multilabelDatasetTrain = trainingData;		
		   
		   //If numClassifiers was not set in the constructor it will be set to 2*numLabels
		   if (this.numClassifiers==-1) 
			  this.numClassifiers = numLabels*2;
		   if (this.numClassifiers>(Math.pow(2, this.numLabels))/2) // 0011 and 1100 are the same classifier
			   throw new Exception("The number of models exceed the number of combinations");
		   
		   Ensemble = new MultiLabelLearner[numClassifiers];		
		   Filters = new Remove[numClassifiers];	
		   
		   votesPerLabel = new int[numLabels];
		   for(int i=0; i<genotype.length; i++)
		   {
			   votesPerLabel[i%numLabels] += genotype[i];
		   }

		   if(genotype==null)
			  initEnsembleMatrix(); 
		   else
			 this.genotypeToEnsembleMatrix();
		   
		   Instances instances = null;
		   for(int i = 0; i < numClassifiers; i++)
		   {			
			    //Transform the multilabel dataset using LP and the labels of the current individual's classifier
				instances = transformInstances(multilabelDatasetTrain, i);
				
				// build a MultiLabelLearner for the selected label subset;
				try {
					
					//Try to get a classifier from the tableClassifiers					
					String s = new String();				
					for(int j=0; j<numLabels; j++)
					{
						s = s+EnsembleMatrix[i][j];
					}
					
					if(tableClassifiers.get(s) == null)
					{
						//Build the classifier and put in the table
						Ensemble[i] = new LabelPowerset(new J48());
			        	Ensemble[i].build(this.multilabelDatasetTrain.reintegrateModifiedDataSet(instances));	
			        	tableClassifiers.put(s, Ensemble[i]);
					}
					else
					{
						//Get the classifier from the table
						Ensemble[i] = tableClassifiers.get(s);
					}					
				} catch (InvalidDataException e) {	
					e.printStackTrace();
				} catch (InvalidDataFormatException e){
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	 
	
	/*
	 * Make prediction of the ensemble for a instance
	 */
	@Override
	protected MultiLabelOutput makePredictionInternal(Instance instance) throws Exception
	{
	    double[] sumConf = new double[numLabels];
	    double[] sumVotes = new double[numLabels];
	    double[] lengthVotes = new double[numLabels];
	    
	    // gather votes
	    for (int model = 0; model < numClassifiers; model++) 
	    {	        
	    	Filters[model].input(instance);
	        Filters[model].batchFinished();
	        Instance newInstance = Filters[model].output();
	        MultiLabelOutput subsetMLO = Ensemble[model].makePrediction(newInstance);
	        	
	        for (int label=0, k=0; label < numLabels; label++)
	        { 
	        	if (EnsembleMatrix[model][label]==1)
	            {	
	        		sumConf[label] += subsetMLO.getConfidences()[k];
	                sumVotes[label] += subsetMLO.getBipartition()[k] ? 1 : 0;
	                lengthVotes[label]++;

	                k++;
	            }
	        }	       
	    }	      
	    
	    double[] confidence1 = new double[numLabels];
	    double[] confidence2 = new double[numLabels];
	    boolean[] bipartition = new boolean[numLabels];
	        
	    for (int i = 0; i < numLabels; i++)
	    {
	    	if (lengthVotes[i] != 0)
	        {
	        	confidence1[i] = sumVotes[i] / lengthVotes[i];
	            confidence2[i] = sumConf[i] / lengthVotes[i];
	        }else 
	        {
	        	confidence1[i] = 0;
	            confidence2[i] = 0;
	        }
	         
	        if (confidence2[i] >= threshold) {
	            bipartition[i] = true;
	        } else {
	            bipartition[i] = false;
	        }
	    }

	    MultiLabelOutput mlo = new MultiLabelOutput(bipartition, confidence1);
	    return mlo;
	}	


	/////////////////////////////////////////////////////////////////
	// ---------------------------------------------- protected methods
	/////////////////////////////////////////////////////////////////	
	/**
	 * Transform the instances for a given k-labelset (for a given model with different subset of labels)
	 * 
	 * @param mlData Multi-label dataset
	 * @param model Number identifying the model in the ensemble
	 * 
	 * @return Instances dataset including only the labels for this model
	 * 
	 * @throws Exception exception
	 */
	protected Instances transformInstances(MultiLabelInstances mlData, int model) throws Exception
	{   
		int labelsToRemove[]=new int[numLabels-SizeSubsets[model]];
	    Instances data = mlData.getDataSet();
	    
	    for (int label=0, k=0; k <(numLabels-SizeSubsets[model]); label++)
	    {
	        if (EnsembleMatrix[model][label] == 0)
	        {  	
	        	labelsToRemove[k]=labelIndices[label];
	        	k++;
	        }
	    }	       
	    Filters[model] = new Remove();
	    Filters[model].setAttributeIndicesArray(labelsToRemove);	    
	    try 
	    {
		    Filters[model].setInputFormat(data);
	    }		
		catch (Exception e)
		{
			e.printStackTrace();
		}
	    Filters[model].setInvertSelection(false);
	    Instances trainSubset = Filter.useFilter(data, Filters[model]);
	        
        return(trainSubset);		
	}
	
	/**
	 * Initialize a random ensemble matrix
	 */
	protected void initEnsembleMatrix()
	{
		EnsembleMatrix = new byte[numClassifiers][numLabels];		
		SizeSubsets = new int[numClassifiers];
	    
		HashSet<String> Combinations = new HashSet<String>();
		
		//For each classifier in the ensemble
		for(int model=0; model<numClassifiers;)
		{  			
		   if (variable == false)
		   {SizeSubsets[model]=maxSubsetSize;}
		   else
		   {
			   SizeSubsets[model] = randGen.choose(2, maxSubsetSize + 1);
		   }
		   
		   //Inicializations
		   StringBuffer comb2 = new StringBuffer("");
		   boolean visited[] = new boolean[numLabels];	
		   for(int label=0; label<numLabels; label++)
		   {	   
		      visited[label]=false;
		      EnsembleMatrix[model][label]=0;
		      comb2.append('0');
		   }
		   
		   for(int label=0; label<SizeSubsets[model]; )
		   {
               //Random selection of one label
			   int randomLabel=randGen.choose(numLabels);
               if(!visited[randomLabel])
               {	   
			      visited[randomLabel]=true;
			      EnsembleMatrix[model][randomLabel]=1;
			      comb2.setCharAt(randomLabel, '1');
			      label++;
               }   
		   }		
			 
		   if(checkModel(model)==true && Combinations.add(comb2.toString())==true)
		   {   		   
			    model++;                 
		   }   
		 }
	}
	
	/**
	 * Check that a model has been correctly created
	 * 
	 * @param model Number identifying the model in the ensemble
	 * 
	 * @return TRUE if it has been correctly created and FALSE otherwise
	 */
	protected boolean checkModel(int model)
	{		
		boolean check = true;
        		
		//Check if the model is 0, maxInteger-1 (all 1s), pow of 2 (0 or 1 labels selected)
        int count0s=0, count1s=0;
        for(int label=0; label< numLabels; label++)
        {
        	if(EnsembleMatrix[model][label]==0)
        		count0s+=1;
        	if(EnsembleMatrix[model][label]==1)
        		count1s+=1;        	
        }
        if ((count0s==numLabels)||(count1s==numLabels)||(count1s==1))
        	check=false;
        
        return(check);		
	}
	
	/**
	 * Create an ensemble matrix given the genotype of an individual
	 */
    protected void genotypeToEnsembleMatrix()
    {    	
    	EnsembleMatrix = new byte[numClassifiers][numLabels];
    	SizeSubsets = new int[numClassifiers];
    	
        for(int model=0; model<numClassifiers; model++)
    	{	
    			int subsetSize=0;	
    			for(int label=0; label<numLabels; label++)
    			{	
    				EnsembleMatrix[model][label]=genotype[model*numLabels + label];
    			    if(EnsembleMatrix[model][label]==1) subsetSize++;
    			}	
    			this.SizeSubsets[model]=subsetSize;    			
    	}   	
    }
    
    
    /**
     * Get a String identifying the ensemble
     * The base classifiers are ordered, in order to compare ensembles
     * 
     * @return A string that identifies the ensemble, independently of the order of its base classifiers
     */
    protected String getOrderedStringFromEnsembleMatrix()
	{		
		String [] matrix = new String[numClassifiers];
     	  	
		// EnsembleMatrix to Strings array
     	for(int i=0; i<numClassifiers; i++)
     	{
     		String s = new String();
     			
     		for(int j=0; j<numLabels; j++)
     		{
     			s = s+EnsembleMatrix[i][j];
     		}
     	 		
     		matrix[i] = s;
     	}

     	// Ordered list of rows of the EnsembleMatrix
     	Arrays.sort(matrix);
     		
     	String s2 = new String();
     	 	
	    for(int i=0; i<numClassifiers; i++)
	    {
	    	s2 = s2 + matrix[i];
	    }
		
		return s2;
	}
}
