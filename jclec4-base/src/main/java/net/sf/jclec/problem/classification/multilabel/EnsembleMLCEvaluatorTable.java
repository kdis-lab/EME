package net.sf.jclec.problem.classification.multilabel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import net.sf.jclec.IIndividual;
import net.sf.jclec.binarray.BinArrayIndividual;
import net.sf.jclec.fitness.SimpleValueFitness;
import weka.classifiers.trees.J48;
import mulan.classifier.MultiLabelLearner;
import mulan.classifier.transformation.LabelPowerset;
import mulan.evaluation.Evaluation;
import mulan.evaluation.Evaluator;
import mulan.evaluation.measure.Measure;


public class EnsembleMLCEvaluatorTable extends EnsembleMLCEvaluator {
	

	private static final long serialVersionUID = 4320073096859272538L;
	
	
	/////////////////////////////////////////////////////////////////
	// --------------------------------------------------- Properties
	/////////////////////////////////////////////////////////////////
	
	public Hashtable<String, MultiLabelLearner> tableClassifiers;
	public Hashtable<String, Double> tableFitness;
	
	double [][] phiMatrix;
	
	private boolean fitnessWithIndividualDiversity = false;
	
	
	public EnsembleMLCEvaluatorTable()
	{
		super();
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

	
	@Override
	protected void evaluate(IIndividual ind) 
	{
		// Individual genotype
		byte[] genotype = ((BinArrayIndividual) ind).getGenotype();
		
		// Create classifier
		EnsembleClassifierTable classifierTable = new EnsembleClassifierTable(numberLabelsClassifier, numberClassifiers, predictionThreshold, variable, new LabelPowerset(new J48()), genotype, tableClassifiers);
		
		Evaluator eval = new Evaluator();          
		
        try {
        	    // Build classifier using train data
        	    classifierTable.build(datasetTrain);

        	    List<Measure> measures = new ArrayList<Measure>();  	       
  	       	  	measures = prepareMeasures(classifierTable, datasetTrain);
  	       	  	Evaluation results;
  	       	  	
  	       	  	// Obtain ensembleMatrix  	       	  	
  	       	  	byte [][] ensembleMatrix = classifierTable.getEnsembleMatrix();
  	       	  	
  	       	  	String s = getStringFromEnsembleMatrix(ensembleMatrix, classifierTable.getNumClassifiers(), classifierTable.getNumLabels());
  	       	  	
  	       	  	double fitness = -1;
  	       	  	//Try to get the individual fitness from the table
  	       	  	if(tableFitness.containsKey(s))
  	       	  	{
  	       	  		fitness = tableFitness.get(s).doubleValue();
  	       	  	}
  	       	  	else
  	       	  	{
  	       	  		results = eval.evaluate(classifierTable, datasetValidation, measures);
  	       	  		String mName = new String();
  	       	  		mName = "Hamming Loss";
  	       	  		
  	       	  		if(fitnessWithIndividualDiversity)
  	       	  		{
  	       	  			int max = 0;
	  				
		  				for(int i=0; i<getDatasetTrain().getNumLabels(); i++)
		  				{
		  					int sum = 0;
		  					
		  					for(int j=0; j<getNumberClassifiers(); j++)
		  					{
		  						sum = sum + genotype[i+j*getDatasetTrain().getNumLabels()];
		  					}
		  					
		  					//System.out.println("sum: " + sum);
		  					if (sum > max)
		  						max = sum;
		  				}
		  				
		  				//The diversity is the opposite of the max number of label repeat
		  				double div = 1 - (double)max/getNumberClassifiers();
	  	       	  		
		  				if(maximize)
		  					fitness = getMeasureValue(mName, results)*0.6 + div*0.4;
		  				else
		  					fitness = getMeasureValue(mName, results)*0.6 + (1-div)*0.4;
  	       	  		}
  	       	  		else
  	       	  		{
  	       	  			//Introduces phi correlation in fitness
  	       	  			double measure = getMeasureValue(mName, results);
  	       	  			//measure is Hloss -> 1-Hloss is to maximize
  	       	  			measure = 1 - measure;
  	       	  			fitness = measure;	
  	       	  			
  	       	  			/*
  	       	  			 * Introduces Phi correlation in fitness
  	       	  			
  	       	  			double phiTotal = 0;
  	       	  			
  	       	  			//Calculate sumPhi for all base classifiers
  	       	  			for(int c=0; c<getNumberClassifiers(); c++)
  	       	  			{
  	       	  				double sumPhi = 0;
  	       	  				//calculate sum of phi label correlations for a base classifier
  	       	  				for(int i=0; i<getDataset().getNumLabels()-1; i++)
  	       	  				{
  	       	  					for(int j=i+1; j<getDataset().getNumLabels(); j++)
  	       	  					{
  	       	  						if((ensembleMatrix[c][i] == 1) && (ensembleMatrix[c][j] == 1))
  	       	  							sumPhi += Math.abs(phiMatrix[i][j]);
  	       	  					}
  	       	  				}
  	       	  				
  	       	  				phiTotal += sumPhi;
  	       	  			}
  	       	  			
  	       	  			fitness = measure + phiTotal;
  	       	  			
  	       	  			*/
  	       	  			
  	       	  		}	  	       	  	

	  				
  	       	  		tableFitness.put(s, fitness);
  	       	  	}
  	       	  	
  	       	  	ind.setFitness(new SimpleValueFitness(fitness));

			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}	
	}
	
	
	
	protected double getMeasureValue (String measureName, Evaluation results)
	{
		double mvalue = -1;
		
		for(int i=0; i<results.getMeasures().size(); i++)
		{
			if(results.getMeasures().get(i).getName().equals(measureName))
			{
				mvalue = results.getMeasures().get(i).getValue();
				break;
			}
		}
		
		if(mvalue == -1)
		{
			System.out.println("Incorrect name of the measure. Correct are: ");
			for(int i=0; i<results.getMeasures().size(); i++)
			{
				System.out.println("   " + results.getMeasures().get(i).getName());
			}
			System.exit(-1);
		}
		
		return mvalue;
	}
	
	protected String getStringFromEnsembleMatrix(byte [][] ensembleMatrix, int numClassifiers, int numLabels)
	{
		
		String [] matrix = new String[numClassifiers];
     	  	
		// EnsembleMatrix to Strings array
     	for(int i=0; i<numClassifiers; i++)
     	{
     		String s = new String();
     			
     		for(int j=0; j<numLabels; j++)
     		{
     			s = s+ensembleMatrix[i][j];
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
