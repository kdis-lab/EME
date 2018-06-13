package net.sf.jclec.problem.classification.multilabel;

import java.util.ArrayList;
import java.util.List;

import weka.classifiers.functions.Logistic;
import weka.classifiers.trees.J48;
import mulan.classifier.transformation.LabelPowerset;
import mulan.data.InvalidDataFormatException;
import mulan.data.MultiLabelInstances;
import mulan.evaluation.Evaluation;
import mulan.evaluation.Evaluator;
import mulan.evaluation.measure.HammingLoss;
import mulan.evaluation.measure.Measure;

public class Prueba {

	public static void main(String [] args){
		
		EnsembleClassifier classifier = new EnsembleClassifier(3, 12, 0.5, false, new LabelPowerset(new J48()));
		
		String datasetTrainFileName = "emotions_train_1.arff";
		String datasetTestFileName = "emotions_test_1.arff";
		String datasetXMLFileName = "emotions.xml";
		
		MultiLabelInstances datasetTrain = null;
		MultiLabelInstances datasetTest = null;
		
		try {
			datasetTrain = new MultiLabelInstances(datasetTrainFileName, datasetXMLFileName);
			datasetTest = new MultiLabelInstances(datasetTestFileName, datasetXMLFileName);		
			
			System.out.println(datasetTrain.getNumInstances());
			
			classifier.build(datasetTrain);
			
			System.out.println("End Build");
			
			Evaluator eval = new Evaluator();
			List<Measure> measures = new ArrayList<Measure>();  
        	//Add only the measure to use
  	       	measures.add(new HammingLoss());
  	       	Evaluation results;
			
			for(int i=0; i<5; i++){
				
	  	       	results = eval.evaluate(classifier.makeCopy(), datasetTest, measures);
	       		double fitness = results.getMeasures().get(0).getValue();	  
	       		
	       		System.out.println("fitness " + i + ": " + fitness);
			}
			
			
		} catch (InvalidDataFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
