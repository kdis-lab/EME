package net.sf.jclec;

/**
 * General purpose individual.
 *
 * This interface  defines common operations for a  general-purpose individual.
 * Methods implementation  depends on the individual's  encoding -that is, what 
 * is the type of genotype it presents, the presence of phenotype, ... </p>
 * 
 * @author Sebastian Ventura
 */

public interface IIndividual extends JCLEC
{
	/////////////////////////////////////////////////////////////////
	// ---------------------------------- Setting and getting fitness
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Access to individual fitness.
	 * 
	 * @return Individual fitness
	 */
	
	public IFitness getFitness();
	
	/**
	 * Sets the fitness of this individual.
	 * 
	 * @param fitness New fitness value.
	 */
	
	public void setFitness(IFitness fitness);
	
	/////////////////////////////////////////////////////////////////
	// ---------------------------------------------- Distance method
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Distance method.
	 * 
	 * @return Distance between this individual and <code>other</code>
	 * 
	 * @deprecated Use the interface IDistance
	 * @see net.sf.jclec.IDistance
	 */
	
	public abstract double distance(IIndividual other);

	/////////////////////////////////////////////////////////////////
	// -------------------------------------------------- Copy method
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Copy method. 
	 * 
	 * @return a copy of this individual.
	 */
	
	public IIndividual copy();
	
	/**
	 * Equals method. 
	 * 
	 * @param other object to compare
	 * @return true if the individual is equal to the other, false otherwise
	 */
	
	public boolean equals(Object other);
}
