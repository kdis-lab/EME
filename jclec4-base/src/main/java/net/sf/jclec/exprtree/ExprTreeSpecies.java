package net.sf.jclec.exprtree;

import net.sf.jclec.ISpecies;

/**
 * IExprTreeSpecies abstract implementation.
 * 
 * @author Sebastian Ventura
 */

@SuppressWarnings("serial")
public abstract class ExprTreeSpecies implements ISpecies
{
	/////////////////////////////////////////////////////////////////
	// --------------------------------------------------- Properties
	/////////////////////////////////////////////////////////////////
	
	/** Genotype schema */
	
	protected ExprTreeSchema genotypeSchema;	
	
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Empty constructor
	 */
	
	public ExprTreeSpecies() 
	{
		super();
	}

	/////////////////////////////////////////////////////////////////
	// ----------------------------------------------- Public methods
	/////////////////////////////////////////////////////////////////
	
	// Factory method
	
	/**
	 * Factory method. Set individual genotype.
	 * 
	 * @param genotype Individual genotype
	 * 
	 * @return A new instance of individual class, with its genotype set
	 */

	public abstract ExprTreeIndividual createIndividual(ExprTree genotype);
	
	// Genotype information
	
	/**
	 * Access to genotype schema.
	 * 
	 * @return Genotype schema
	 */
	
	public ExprTreeSchema getGenotypeSchema() 
	{
		return genotypeSchema;
	}
}
