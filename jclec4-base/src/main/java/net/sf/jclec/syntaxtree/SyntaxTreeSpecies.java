package net.sf.jclec.syntaxtree;

import net.sf.jclec.ISpecies;

/**
 * ISyntaxTreeSpecies abstract implementation.
 * 
 * @author Sebastian Ventura
 */

@SuppressWarnings("serial")
public abstract class SyntaxTreeSpecies implements ISpecies
{
	/////////////////////////////////////////////////////////////////
	// --------------------------------------------------- Properties
	/////////////////////////////////////////////////////////////////
	
	/** Genotype schema */
	
	protected SyntaxTreeSchema genotypeSchema;	
	
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Empty constructor
	 */
	
	public SyntaxTreeSpecies() 
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

	public abstract SyntaxTreeIndividual createIndividual(SyntaxTree genotype);
	
	// Genotype information
	
	/**
	 * Access to genotype schema.
	 * 
	 * @return Genotype schema
	 */
	
	public SyntaxTreeSchema getGenotypeSchema() 
	{
		return genotypeSchema;
	}
}
