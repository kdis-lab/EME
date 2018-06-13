package net.sf.jclec.realarray;

import net.sf.jclec.ISpecies;

import net.sf.jclec.util.range.IRange;

/**
 * Abstract implementation for IRealArraySpecies.
 * 
 * @author Sebastian Ventura
 */

@SuppressWarnings("serial")
public abstract class RealArraySpecies implements ISpecies 
{
	/////////////////////////////////////////////////////////////////
	// ----------------------------------------------------Properties
	/////////////////////////////////////////////////////////////////
	
	/** Genotype schema */
	
	protected IRange[] genotypeSchema;
	
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Empty constructor
	 */
	
	public RealArraySpecies() 
	{
		super();
	}

	/////////////////////////////////////////////////////////////////
	// ----------------------------------------------- Public methods
	/////////////////////////////////////////////////////////////////
	
	// Factory method
	
	/**
	 * Factory method.
	 * 
	 * @param genotype Individual genotype.
	 * 
	 * @return A new instance of represented class
	 */
	
	public abstract RealArrayIndividual createIndividual(double [] genotype);
	
	// Genotype information

	/**
	 * Informs about individual genotype length.
	 * 
	 * @return getGenotypeSchema().length
	 */
	
	public int getGenotypeLength() 
	{
		return genotypeSchema.length;
	}

	/**
	 * @return This genotype schema
	 */
	
	public IRange[] getGenotypeSchema() 
	{
		return genotypeSchema;
	}
}
