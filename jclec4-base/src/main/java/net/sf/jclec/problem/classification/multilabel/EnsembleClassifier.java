package net.sf.jclec.problem.classification.multilabel;

import mulan.data.InvalidDataFormatException;
import mulan.data.MultiLabelInstances;

import java.util.Random;
import java.util.HashSet;

import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;
import mulan.classifier.InvalidDataException;
import mulan.classifier.ModelInitializationException;
import mulan.classifier.MultiLabelLearner;
import mulan.classifier.MultiLabelOutput;
import mulan.classifier.meta.MultiLabelMetaLearner;

@SuppressWarnings("serial")

public class EnsembleClassifier extends MultiLabelMetaLearner
{
	/////////////////////////////////////////////////////////////////
	// --------------------------------------------------- Properties
	/////////////////////////////////////////////////////////////////
	
	protected MultiLabelInstances multilabelDatasetTrain;
	
	protected int maxSubsetSize; 
	
	protected int numClassifiers;
	
	protected double threshold;
			
	protected MultiLabelLearner[] Ensemble; // Array of classifiers
	
	protected int[] SizeSubsets; //size of the subsets of each classifier
	
	protected Remove[] Filters;
	
	protected byte EnsembleMatrix[][]=null;
	
	protected boolean variable=false; //The subset size is variable for each classifier
	
	protected byte genotype[];
	
	protected Random rnd;	
	

	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////
	
	public EnsembleClassifier(int maxSubsetSize, int numClassifiers, double threshold, boolean variable, MultiLabelLearner baseLearner)
	{
		super(baseLearner);
		this.maxSubsetSize = maxSubsetSize;
		this.numClassifiers = numClassifiers;
		this.threshold = threshold;
		this.variable = variable;
	}	
	
	public EnsembleClassifier(int maxSubsetSize, int numClassifiers, double threshold, boolean variable, MultiLabelLearner baseLearner, byte[] genotype)
	{
		super(baseLearner);
		this.maxSubsetSize = maxSubsetSize;
		this.numClassifiers = numClassifiers;
		this.threshold = threshold;
	    this.variable = variable;
		this.genotype = genotype;
	}
	
	public EnsembleClassifier(int maxSubsetSize, double threshold, boolean variable, MultiLabelLearner baseLearner)
	{
		super(baseLearner);
		this.maxSubsetSize = maxSubsetSize;
		this.numClassifiers=-1;
		this.threshold = threshold;
		this.variable = variable;
	}	
	
	public EnsembleClassifier(int maxSubsetSize, double threshold, boolean variable, MultiLabelLearner baseLearner, byte[] genotype)
	{
		super(baseLearner);
		this.maxSubsetSize = maxSubsetSize;
		this.numClassifiers = -1;
		this.threshold = threshold;
		this.variable = variable;
		this.genotype = genotype;
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
	
	public String globalInfo() {
		String str = "";
		str+="Class implementing a generalized ML-ECOC ensemble algorithm";
		return str;
	}
	
	public int[][] classify(MultiLabelInstances mlData)
	{		
		int[][] predictions = new int[mlData.getNumInstances()][numLabels];

		Instances data = mlData.getDataSet();
		
		for (int i=0; i<mlData.getNumInstances(); i++)
		{ 	
		    try {
				MultiLabelOutput mlo = this.makePrediction(data.get(i));
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
	   rnd = new Random(System.currentTimeMillis());
	   
	   
	   if(genotype==null)
		  initEnsembleMatrix(); 
	   else
		 this.genotypeToEnsembleMatrix();
	   
	   for(int i = 0; i < numClassifiers; i++)
	   {
			// Transform the multilabel dataset using LP and the labels of the current individual's classifier
			Instances instances = transformInstances(multilabelDatasetTrain, i);			
			
			// build a MultiLabelLearner for the selected label subset;
			try {
					Ensemble[i] = getBaseLearner().makeCopy();
		        	Ensemble[i].build(this.multilabelDatasetTrain.reintegrateModifiedDataSet(instances));	

				
			} catch (InvalidDataException e) {			
				e.printStackTrace();
			} catch (InvalidDataFormatException e){
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
	  
		}
	}
	 

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
	
	protected void initEnsembleMatrix()
	{
		EnsembleMatrix = new byte[numClassifiers][numLabels];		
		SizeSubsets = new int[numClassifiers];
	    
		HashSet<String> Combinations = new HashSet<String>();
		Random rand = new Random(rnd.nextInt());
		   
		//For each classifier in the ensemble
		for(int model=0; model<numClassifiers;)
		{  			
		   if (variable == true)
		   {SizeSubsets[model]=maxSubsetSize;}
		   else
		   {SizeSubsets[model]= 2 + rand.nextInt(maxSubsetSize-2+1);}
		   
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
               int randomLabel=rand.nextInt(numLabels);
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
    
}
