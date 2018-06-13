package net.sf.jclec.multisyntaxtree;

import net.sf.jclec.ISpecies;

import net.sf.jclec.syntaxtree.SyntaxTree;
import net.sf.jclec.syntaxtree.SyntaxTreeSchema;

/**
 * ISyntaxTreeSpecies abstract implementation.
 * 
 * @author Sebastian Ventura
 */

@SuppressWarnings("serial")
public abstract class MultiSyntaxTreeSpecies implements ISpecies
{
	/////////////////////////////////////////////////////////////////
	// --------------------------------------------------- Properties
	/////////////////////////////////////////////////////////////////
	
	/** Genotype schema */
	
	protected SyntaxTreeSchema [] genotypeSchema;	
	
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Empty constructor
	 */
	
	public MultiSyntaxTreeSpecies() 
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

	public abstract MultiSyntaxTreeIndividual createIndividual(SyntaxTree [] genotype);
	
	// Genotype information
	
	/**
	 * Get the number of expression trees in genotype.
	 * 
	 * @return Number of expression trees in genotype
	 */
	
	public int numberOfSyntaxTrees() 
	{
		return genotypeSchema.length;
	}

	/**
	 * Access to genotype schema.
	 * 
	 * @return Genotype schema
	 */
	
	public SyntaxTreeSchema [] getGenotypeSchema() 
	{
		return genotypeSchema;
	}
}
