package net.sf.jclec.problem.classification.exprtree;

import net.sf.jclec.IFitness;
import net.sf.jclec.exprtree.ExprTree;
import net.sf.jclec.exprtree.ExprTreeIndividual;
import net.sf.jclec.problem.classification.IClassifierIndividual;
import net.sf.jclec.problem.classification.base.Rule;

/**
 * ExprTreeRuleIndividual for ExprTree rules.<p/>
 * 
 * Implements an individual whose genotype is an ExprTree (extends ExprTreeIndividual) and the phenotype is a rule.
 * 
 * @author Amelia Zafra
 * @author Sebastian Ventura
 * @author Jose M. Luna 
 * @author Alberto Cano 
 * @author Juan Luis Olmo
 */

public class ExprTreeRuleIndividual extends ExprTreeIndividual implements IClassifierIndividual 
{
	/////////////////////////////////////////////////////////////////
	// --------------------------------------------------- Properties
	/////////////////////////////////////////////////////////////////

	private static final long serialVersionUID = -4704573280824780256L;
	
	/** Individual phenotype */
	
	protected Rule phenotype;
	
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Default (empty) constructor
	 */
	
	public ExprTreeRuleIndividual() 
	{
		super();
	}
	
	/**
	 * Constructor
	 * 
	 * @param genotype the genotype of the individual
	 */
	
	public ExprTreeRuleIndividual(ExprTree genotype) 
	{
		super(genotype);
	}
	
	/**
	 * Constructor
	 * 
	 * @param genotype the genotype of the individual
	 * @param phenotype the phenotype of the individual
	 */
	
	public ExprTreeRuleIndividual(ExprTree genotype, Rule phenotype) 
	{
		this(genotype);
		this.phenotype = phenotype;
	}
	
	/**
	 * Constructor
	 * @param genotype the genotype of the individual
	 * @param phenotype the phenotype of the individual
	 * @param fitness the fitness of the individual
	 */

	public ExprTreeRuleIndividual(ExprTree genotype, Rule phenotype, IFitness fitness) 
	{
		this(genotype, phenotype);
		setFitness(fitness);
	}
	
	/////////////////////////////////////////////////////////////////
	// ----------------------------------------------- Public methods
	/////////////////////////////////////////////////////////////////

	/**
	 * Access to individual phenotype
	 * 
	 * @return the phenotype
	 */
	
	public Rule getPhenotype() 
	{
		return phenotype;
	}
	
	/**
	 * Copy the rule
	 * 
	 * @return rule copy
	 */
	
	@Override
	public ExprTreeRuleIndividual copy() 
	{
		if(this.getFitness() != null)
			return new ExprTreeRuleIndividual(genotype.copy(), phenotype.copy(), fitness.copy());
		else
			return new ExprTreeRuleIndividual(genotype.copy(), phenotype.copy());
	}
}