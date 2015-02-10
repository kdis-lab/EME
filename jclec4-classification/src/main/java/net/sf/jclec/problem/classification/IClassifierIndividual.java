package net.sf.jclec.problem.classification;

import net.sf.jclec.IIndividual;

/**
 * Individual definition for evolutionary algorithms on classification problems.<p/>
 * 
 * Extends the general-purpose individual interface with a getPhenotype()
 * method to get the classifier represented by the individual genotype.
 * 
 * @author Sebastian Ventura
 * @author Amelia Zafra
 * @author Jose M. Luna 
 * @author Alberto Cano 
 * @author Juan Luis Olmo
 */

public interface IClassifierIndividual extends IIndividual 
{
	/**
	 * Access to individual phenotype
	 * 
	 * @return A classifier contained in this individual
	 */
	
	public IClassifier getPhenotype();
}