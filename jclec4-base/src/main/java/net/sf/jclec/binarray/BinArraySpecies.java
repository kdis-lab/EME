package net.sf.jclec.binarray;

import net.sf.jclec.ISpecies;

/**
 * Abstract implementation for IBinArraySpecies.
 * 
 * This class  contains a byte array  that contains the genotype schema for all
 * represented individuals. This schema can be set in a subclass of this or can 
 * be calculated from other problem information.
 * 
 * @author Sebastian Ventura
 * 
 * @see BinArrayIndividualSpecies
 */

@SuppressWarnings("serial")
public abstract class BinArraySpecies implements ISpecies
{
	/////////////////////////////////////////////////////////////////
	// ----------------------------------------------------Properties
	/////////////////////////////////////////////////////////////////
	
	/** Genotype schema */
	
	protected byte [] genotypeSchema;
	
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Empty constructor
	 */
	
	public BinArraySpecies() 
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
	
	public abstract BinArrayIndividual createIndividual(byte [] genotype);	

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
	
	public byte[] getGenotypeSchema() 
	{
		return genotypeSchema;
	}
}
