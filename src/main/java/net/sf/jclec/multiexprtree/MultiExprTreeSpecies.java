package net.sf.jclec.multiexprtree;

import net.sf.jclec.ISpecies;
import net.sf.jclec.exprtree.ExprTree;
import net.sf.jclec.exprtree.ExprTreeSchema;

/**
 * IExprTreeSpecies abstract implementation.
 * 
 * @author Sebastian Ventura
 */

public abstract class MultiExprTreeSpecies implements ISpecies
{
	/////////////////////////////////////////////////////////////////
	// --------------------------------------------------- Properties
	/////////////////////////////////////////////////////////////////
	
	private static final long serialVersionUID = 6621806579957171209L;
	
	/** Genotype schema */
	
	protected ExprTreeSchema [] genotypeSchema;	
	
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Empty constructor
	 */
	
	public MultiExprTreeSpecies() 
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

	public abstract MultiExprTreeIndividual createIndividual(ExprTree [] genotype);
	
	// Genotype information
	
	/**
	 * Get the number of expression trees in genotype.
	 * 
	 * @return Number of expression trees in genotype
	 */
	
	public int numberOfExprTrees() 
	{
		return genotypeSchema.length;
	}

	/**
	 * Access to genotype schema.
	 * 
	 * @return Genotype schema
	 */
	
	public ExprTreeSchema[] getGenotypeSchema() 
	{
		return genotypeSchema;
	}
}
