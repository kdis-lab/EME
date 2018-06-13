package net.sf.jclec.problem.classification.multilabel;

import mulan.data.InvalidDataFormatException;
import mulan.data.LabelsMetaDataImpl;
import mulan.data.MultiLabelInstances;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Random;

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
import mulan.classifier.transformation.BinaryRelevance;
import net.sf.jclec.util.random.IRandGen;
import net.sf.jclec.util.random.Ranecu;


public class EnsembleClassifier extends MultiLabelMetaLearner
{	
	/////////////////////////////////////////////////////////////////
	// --------------------------------------- Serialization constant
	/////////////////////////////////////////////////////////////////
	
	private static final long serialVersionUID = 1402348312634173068L;

	
	/////////////////////////////////////////////////////////////////
	// --------------------------------------------------- Properties
	/////////////////////////////////////////////////////////////////
	
	/* Dataset to build the classifier */
	protected MultiLabelInstances multilabelDatasetTrain;
	
	/* Max number of active labels in a base classifier */
	protected int maxSubsetSize; 
	
	/* Number of base classifiers in the ensemble */
	protected int numClassifiers;
	
	/* Threshold for voting process prediction */
	protected double threshold;
	
	/* Array of classifiers forming the ensemble */
	protected MultiLabelLearner[] Ensemble;
	
	/* Size of the subset of labels of each base classifier */
	protected int[] SizeSubsets;
	
	/* Binary matrix that identifies the ensemble */
	protected byte EnsembleMatrix[][]=null;
	
	/* Binary matrix that identifies the labels that will be attributes */
	protected byte ExtendMatrix[][]=null;
	
	/* Indicates if the number of active labels is variable for each base classifier */
	protected boolean variable=false;
	
	/* Individual genotype that identifies the individual */
	protected byte genotype[];
	
	/* Random numbers generator */
	protected IRandGen randGen;
	
	/* Table that stores all base classifiers built */
	private Hashtable<String, MultiLabelLearner> tableClassifiers;
	
	/* Array with number of votes of the ensemble for each label */
	private int [] votesPerLabel;
	
	/* Learner to predict the labels to use as attributes */
	private MultiLabelLearner predictor;


	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////
	
	public EnsembleClassifier(int maxSubsetSize, int numClassifiers, double threshold, boolean variable, MultiLabelLearner baseLearner, 
			byte[] genotype, Hashtable<String, MultiLabelLearner> tableClassifiers, IRandGen randGen, MultiLabelLearner predictor)
	{
		super(baseLearner);
		this.maxSubsetSize = maxSubsetSize;
		this.numClassifiers = numClassifiers;
		this.threshold = threshold;
	    this.variable = variable;
		this.genotype = genotype;
		this.tableClassifiers = tableClassifiers;
		this.randGen = randGen;	
		try {
			this.predictor = predictor.makeCopy();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public EnsembleClassifier(int maxSubsetSize, int numClassifiers, double threshold, boolean variable, MultiLabelLearner baseLearner)
	{
		super(baseLearner);
		this.maxSubsetSize = maxSubsetSize;
		this.numClassifiers = numClassifiers;
		this.threshold = threshold;
	    this.variable = variable;
		this.genotype = null;
		this.tableClassifiers = new Hashtable<String, MultiLabelLearner>();
		this.randGen = new Ranecu(1, 2);	
		this.predictor = null;		
	}
	
	
	/////////////////////////////////////////////////////////////////
	// ----------------------------------------------- Public methods
	/////////////////////////////////////////////////////////////////
	
	public boolean isVariable() {
		return variable;
	}

	public void setVariable(boolean variable) {
		this.variable = variable;
	}

	public int getMaxSubsetSize() {
		return maxSubsetSize;
	}

	public void setMaxSubsetSize(int maxSubsetSize){ 
		this.maxSubsetSize = maxSubsetSize;
	}

	public int getNumClassifiers() {
		return numClassifiers;
	}

	public void setNumClassifiers(int numClassifiers) {
		this.numClassifiers = numClassifiers;
	}

	public double getPredictionThreshold() {
		return threshold;
	}

	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}
	
	public int getNumLabels()
	{
		return numLabels;
	}
	
	public byte[] getGenotype()
	{
		return genotype;
	}
	
	public byte[][] getEnsembleMatrix()
	{
		return EnsembleMatrix;
	}
	
	public void setMultiLabelDatasetTrain (MultiLabelInstances multilabelDatasetTrain)
	{
		this.multilabelDatasetTrain = multilabelDatasetTrain;
	}
      
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
		   
		   if(genotype==null){
			   initEnsembleMatrix(); 
		   } 
		   else{
			   this.genotypeToEnsembleMatrix();
		   }


		   votesPerLabel = new int[numLabels];
		   for(int i=0; i<genotype.length; i++)
		   {
			   votesPerLabel[i%numLabels] += genotype[i];
		   }
		   
		   labelNames = multilabelDatasetTrain.getLabelNames();
		   
		   if(predictor == null){
			   try {
					predictor = new BinaryRelevance(new J48());
					predictor.build(multilabelDatasetTrain);
				} catch (Exception e) {
					e.printStackTrace();
				}
		   }
		   
		   //Key for tableClassifiers
		   String keyClassifier = new String();
		   
		   for(int i = 0; i < numClassifiers; i++)
		   {		
			   //Try to get built classifier
			   keyClassifier = Arrays.toString(EnsembleMatrix[i]);
			   
			   if(tableClassifiers.get(keyClassifier) != null){
				   //Obtain classifier
				   Ensemble[i] = tableClassifiers.get(keyClassifier).makeCopy();
			   }
			   else{ //Build the classifier and put in the table				   
				   //Get base learner
				   Ensemble[i] = getBaseLearner().makeCopy();
  		
				   //Build classifier and put in the table
				   Ensemble[i].build(transformInstances(multilabelDatasetTrain, i));	
				   tableClassifiers.put(keyClassifier, Ensemble[i]);
			   }

			}
		   
	}
	 


	@Override
	protected MultiLabelOutput makePredictionInternal(Instance instance) throws Exception
	{		
	    double[] sumConf = new double[numLabels];
	    double[] sumVotes = new double[numLabels];
	    double[] lengthVotes = new double[numLabels];

	    //Instance copy
	    //If instance is not copied, the changes in it will be too in original instance
	    Instance extInstance = null;
	    
	    //Preduction of a classifier
	    MultiLabelOutput subsetMLO = null;
	    
	    //Extend instance with predicted label values to use as attributes
	    extInstance = transformInstance(instance);
	    for (int model = 0; model < numClassifiers; model++) 
	    {	
	    	subsetMLO = Ensemble[model].makePrediction(extInstance);
	    	
	        //Sum votes
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
	    
	    //Calculate ensemble predictions
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
	         
	         if (confidence1[i] >= threshold) {
	             bipartition[i] = true;
	         } else {
	             bipartition[i] = false;
	         }
	         
	     }

	     MultiLabelOutput mlo = new MultiLabelOutput(bipartition, confidence1);
	     
	     return mlo;
	}	
	
	
	
	protected MultiLabelInstances transformInstances(MultiLabelInstances mlData, int numModel) 
			throws InvalidDataFormatException{
				
		//Get actual LabelsMetaData
		 LabelsMetaDataImpl lMeta = (LabelsMetaDataImpl) mlData.getLabelsMetaData().clone();
	        	
		for(int i=0; i<numLabels; i++){
			if(EnsembleMatrix[numModel][i] == 0){
				lMeta.removeLabelNode(labelNames[i]);
			}
		}
		
		return(new MultiLabelInstances(mlData.getDataSet(), lMeta));
	}
	
	
	protected Instance transformInstance(Instance instance, int numModel){
		
		Instance extInstance = (Instance) instance.copy();
		
		try {
			MultiLabelOutput predict = predictor.makePrediction(extInstance);
			
			for(int i=0; i<numLabels; i++){
				
				if(EnsembleMatrix[numModel][i] == 0){
					if(predict.getBipartition()[i] == true){
						extInstance.setValue(labelIndices[i], 1);
					}
					else if(predict.getBipartition()[i] == false){
						extInstance.setValue(labelIndices[i], 0);
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return(extInstance);
	}
	
	
	protected Instance transformInstance(Instance instance){
		
		Instance extInstance = (Instance) instance.copy();
		
		try {
			MultiLabelOutput predict = predictor.makePrediction(extInstance);
			
			for(int i=0; i<numLabels; i++){
				if(predict.getBipartition()[i] == true){
					extInstance.setValue(labelIndices[i], 1);
				}
				else if(predict.getBipartition()[i] == false){
					extInstance.setValue(labelIndices[i], 0);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return(extInstance);
	}
	

	
	protected void initEnsembleMatrix()
	{
		EnsembleMatrix = new byte[numClassifiers][numLabels];		
		ExtendMatrix = new byte[numClassifiers][numLabels];
		SizeSubsets = new int[numClassifiers];
		
		genotype = new byte[numClassifiers*numLabels];
	    
		HashSet<String> Combinations = new HashSet<String>();
		   
		//For each classifier in the ensemble
		for(int model=0; model<numClassifiers;)
		{  			
		   if (variable == false)
		   {SizeSubsets[model]=maxSubsetSize;}
		   else
		   {
//			   SizeSubsets[model]= 2 + randGen.choose(maxSubsetSize-2+1);
			   SizeSubsets[model] = randGen.choose(2, maxSubsetSize + 1);
		   }
		   
		   //Inicializations
		   StringBuffer comb2 = new StringBuffer("");
		   boolean visited[] = new boolean[numLabels];	
		   for(int label=0; label<numLabels; label++)
		   {	   
		      visited[label]=false;
		      EnsembleMatrix[model][label]=0;
		      genotype[model*numLabels+label] = 0;
		      comb2.append('0');
		   }
		   
		   for(int label=0; label<SizeSubsets[model]; )
		   {
               //Random selection of one label		
//               int randomLabel=rand.nextInt(numLabels);
			   int randomLabel=randGen.choose(numLabels+1)-1;
               if(!visited[randomLabel])
               {	   
			      visited[randomLabel]=true;
			      EnsembleMatrix[model][randomLabel]=1;
			      genotype[model*numLabels+randomLabel] = 1;
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
	
	   		    
    protected void genotypeToEnsembleMatrix()
    {    	
    	EnsembleMatrix = new byte[numClassifiers][numLabels];
    	ExtendMatrix = new byte[numClassifiers][numLabels];
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
    
    
    /*
     * Get a String identifying the ensemble
     * The base classifiers are ordered, in order to compare ensembles
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
