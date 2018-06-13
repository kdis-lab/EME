package net.sf.jclec.intarray;

import net.sf.jclec.ISpecies;
import net.sf.jclec.util.intset.IIntegerSet;

/**
 * Abstract implementation for IIntArraySpecies.
 * 
 * @author Sebastian Ventura
 */

@SuppressWarnings("serial")
public abstract class IntArraySpecies implements ISpecies 
{
	/////////////////////////////////////////////////////////////////
	// --------------------------------------------------- Properties
	/////////////////////////////////////////////////////////////////
	
	/** Schema */
	
	protected IIntegerSet [] genotypeSchema;
	
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Empty constructor
	 */
	
	public IntArraySpecies() 
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
	
	public abstract IntArrayIndividual createIndividual(int [] genotype);

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

	public IIntegerSet [] getGenotypeSchema() 
	{
		return genotypeSchema;
	}
}
