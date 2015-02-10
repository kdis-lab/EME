package net.sf.jclec.problem.classification.multiexprtree;

import net.sf.jclec.IFitness;
import net.sf.jclec.exprtree.ExprTree;
import net.sf.jclec.multiexprtree.MultiExprTreeIndividual;
import net.sf.jclec.problem.classification.IClassifierIndividual;
import net.sf.jclec.problem.classification.base.RuleBase;

/**
 * MultiExprTreeRuleIndividual for MultiExprTree.<p/>
 * 
 * Implements an individual whose genotype is a set of ExprTree (extends MultiExprTreeIndividual) and the phenotype is a rule-base.
 * 
 * There is a correspondence that each ExprTree from the genotype defines a classification rule on the rule-base from the phenotype.
 * 
 * @author Amelia Zafra
 * @author Sebastian Ventura
 * @author Jose M. Luna 
 * @author Alberto Cano 
 * @author Juan Luis Olmo
 */

public class MultiExprTreeRuleIndividual extends MultiExprTreeIndividual implements IClassifierIndividual 
{
	/////////////////////////////////////////////////////////////////
	// --------------------------------------------------- Properties
	/////////////////////////////////////////////////////////////////

	private static final long serialVersionUID = -4704573280824780256L;
	
	/** Individual phenotype */
	
	protected RuleBase phenotype;
	
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Default (empty) constructor
	 */
	
	public MultiExprTreeRuleIndividual() 
	{
		super();
	}
	
	/**
	 * Constructor
	 * 
	 * @param genotype the genotype of the individual
	 */
	
	public MultiExprTreeRuleIndividual(ExprTree[] genotype) 
	{
		super(genotype);
	}
	
	/**
	 * Constructor
	 * 
	 * @param genotype the genotype of the individual
	 * @param phenotype the phenotype of the individual
	 */
	
	public MultiExprTreeRuleIndividual(ExprTree[] genotype, RuleBase phenotype) 
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

	public MultiExprTreeRuleIndividual(ExprTree[] genotype, RuleBase phenotype, IFitness fitness) 
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
	
	public RuleBase getPhenotype() 
	{
		return phenotype;
	}
	
	/**
	 * Copy the individual
	 * 
	 * @return the copy
	 */
	
	@Override
	public MultiExprTreeRuleIndividual copy() 
	{
		int gl = genotype.length;
		ExprTree [] genotypeCopy = new ExprTree[gl];
		for (int i=0; i<gl; i++) {
			genotypeCopy[i] = genotype[i].copy();
		}
		
		if(this.getFitness() != null)
			return new MultiExprTreeRuleIndividual(genotypeCopy, phenotype.copy(), fitness.copy());
		else
			return new MultiExprTreeRuleIndividual(genotypeCopy, phenotype.copy());
	}
}