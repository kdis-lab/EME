package net.sf.jclec.problem.classification;

import net.sf.jclec.JCLEC;

import net.sf.jclec.problem.util.dataset.IDataset;
import net.sf.jclec.problem.util.dataset.instance.IInstance;
import net.sf.jclec.problem.util.dataset.metadata.IMetadata;

/**
 * Interface for classifiers.<p/>
 * 
 * Defines the methods to classify an instance classify(IInstance) and a complete dataset classify(IDataset),
 * and a method to get the confusion matrix getConfusionMatrix(IDataset).
 * The toString() method returns a human-readable text representation of the classifier, e.g. rule base.
 * 
 * @author Sebastian Ventura
 * @author Amelia Zafra
 * @author Jose M. Luna 
 * @author Alberto Cano 
 * @author Juan Luis Olmo
 */

public interface IClassifier extends JCLEC
{
	/**
	 * Instance classification
	 * 
	 * @param instance Instance to classify
	 * 
	 * @return A double that represents the class label for this instance
	 */
	
	public double classify(IInstance instance);
	
	/**
	 * Dataset classification
	 * 
	 * Classify all the instances contained in this dataset
	 * 
	 * @param dataset Dataset which instances will be classified
	 * 
	 * @return Array of class labels
	 */
	
	public double[] classify(IDataset dataset);
	
	/** 
	 *  Shows the classifier
	 *  
	 *  @param metadata Metadata to show the attribute names
	 *  
	 *  @return the classifier
	 */
	
	public String toString(IMetadata metadata);
	
	/**
	 * Obtains the confusion matrix for a dataset
	 * 
	 * @param dataset the dataset to classify
	 * 
	 * @return the confusion matrix
	 */
	
	public int[][] getConfusionMatrix(IDataset dataset);

	/**
	 * Copy method
	 * 
	 * @return a copy of the classifier
	 */
	
	public IClassifier copy();
}