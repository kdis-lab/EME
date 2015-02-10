package net.sf.jclec.problem.classification.multilabel;

import java.util.Hashtable;
import java.util.Random;

import weka.core.Instances;
import weka.filters.unsupervised.attribute.Remove;
import mulan.classifier.InvalidDataException;
import mulan.classifier.MultiLabelLearner;
import mulan.data.InvalidDataFormatException;
import mulan.data.MultiLabelInstances;



public class EnsembleClassifierTable extends EnsembleClassifier {
	
	private static final long serialVersionUID = 6438594079635832123L;
	
	/////////////////////////////////////////////////////////////////
	// --------------------------------------------------- Properties
	/////////////////////////////////////////////////////////////////
	
	private Hashtable<String, MultiLabelLearner> tableClassifiers;
	
	
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////
	
	public EnsembleClassifierTable(int maxSubsetSize, int numClassifiers, double threshold, boolean variable, MultiLabelLearner baseLearner, byte[] genotype, Hashtable<String, MultiLabelLearner> tableClassifiers)
	{
		super(maxSubsetSize, numClassifiers, threshold, variable, baseLearner, genotype);
		this.tableClassifiers = tableClassifiers;
	}	

	
	/////////////////////////////////////////////////////////////////
	// ----------------------------------------------- Protected methods
	/////////////////////////////////////////////////////////////////
	
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
		    //Transform the multilabel dataset using LP and the labels of the current individual's classifier
			Instances instances = transformInstances(multilabelDatasetTrain, i);	
		   
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
					Ensemble[i] = getBaseLearner().makeCopy();
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
	

}
