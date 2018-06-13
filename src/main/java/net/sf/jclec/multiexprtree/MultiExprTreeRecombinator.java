package net.sf.jclec.multiexprtree;

import net.sf.jclec.ISpecies;
import net.sf.jclec.IConfigure;

import net.sf.jclec.exprtree.ExprTree;
import net.sf.jclec.exprtree.ExprTreeSchema;
import net.sf.jclec.exprtree.IRecombineExprTree;

import net.sf.jclec.base.AbstractRecombinator;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationRuntimeException;

/**
 * Recombinator for ExprTreeIndividual (and its subclasses).
 * 
 * @author Sebastian Ventura
 */

public class MultiExprTreeRecombinator extends AbstractRecombinator implements IConfigure 
{
	/////////////////////////////////////////////////////////////////
	// --------------------------------------------------- Properties
	/////////////////////////////////////////////////////////////////

	private static final long serialVersionUID = -2968870255147742240L;

	/** Target tree index */
	
	protected int targetTreeIndex = -1;
	
	/** Base operation for this expression tree mutator */
	
	protected IRecombineExprTree baseOp;
	
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------- Internal variables
	/////////////////////////////////////////////////////////////////

	/** Individual species */
	
	protected transient MultiExprTreeSpecies species;
	
	/** Individuals schema */
	
	protected transient ExprTreeSchema [] schema;
	
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////

	/**
	 * Empty constructor
	 */
	
	public MultiExprTreeRecombinator() 
	{
		super();
	}

	/////////////////////////////////////////////////////////////////
	// ----------------------------------------------- Public methods
	/////////////////////////////////////////////////////////////////
	
	// Setting and getting properties
	
	public int getTargetTreeIndex() 
	{
		return targetTreeIndex;
	}

	public void setTargetTreeIndex(int targetTreeIndex) 
	{
		this.targetTreeIndex = targetTreeIndex;
	}

	public IRecombineExprTree getBaseOp() 
	{
		return baseOp;
	}

	public void setBaseOp(IRecombineExprTree baseOp) 
	{
		this.baseOp = baseOp;
	}
	
	// IConfigure interface

	/**
	 * {@inheritDoc}
	 */
	
	@SuppressWarnings("unchecked")
	public void configure(Configuration settings)
	{
		// Get target-tree-index parameter
		int targetTreeIndex = settings.getInt("target-tree-index", -1);
		setTargetTreeIndex(targetTreeIndex);
		// Set base recombinator name
		try {
			// Base recombinator classname
			String baseOpClassname = 
				settings.getString("base-op[@type]");
			// Evaluator class
			Class<? extends IRecombineExprTree> baseOpClass = 
				(Class<? extends IRecombineExprTree>) Class.forName(baseOpClassname);
			// Evaluator instance
			IRecombineExprTree baseOp = baseOpClass.newInstance();
			// Configure species
			if (baseOp instanceof IConfigure) {
				((IConfigure) baseOp).configure(settings.subset("base-op"));
			}
			// Set species
			setBaseOp(baseOp);
		} 
		catch (ClassNotFoundException e) {
			throw new ConfigurationRuntimeException("Illegal operator classname");
		} 
		catch (InstantiationException e) {
			throw new ConfigurationRuntimeException("Problems creating an instance of operator", e);
		} 
		catch (IllegalAccessException e) {
			throw new ConfigurationRuntimeException("Problems creating an instance of operator", e);
		}
	}
	
	/////////////////////////////////////////////////////////////////
	// -------------------------------------------- Protected methods
	/////////////////////////////////////////////////////////////////

	// AbstractRecombinator methods

	@Override
	protected void setPpl() 
	{
		this.ppl = 2;
	}

	@Override
	protected void setSpl() 
	{
		this.spl = 2;
	}

	@Override
	protected void prepareRecombination() 
	{
		ISpecies spc = context.getSpecies();
		if (spc instanceof MultiExprTreeSpecies) {
			//  Assign species
			this.species = (MultiExprTreeSpecies) spc;
			// Sets genotype schema
			this.schema = ((MultiExprTreeSpecies) spc).getGenotypeSchema();
		}
		else {
			throw new IllegalStateException("Illegal species in context");
		}
	}

	@Override
	protected void recombineNext() 
	{
		// Number of expression trees
		int nOfExprTrees = species.numberOfExprTrees();
		// Select the tree to mutate
		int actTargetExprIndex = 
			(targetTreeIndex == -1) ? randgen.choose(0, nOfExprTrees) : targetTreeIndex;
		// Parents genotypes
		ExprTree [] p0_genotype = 
			((MultiExprTreeIndividual) parentsBuffer.get(parentsCounter)).getGenotype();
		ExprTree [] p1_genotype = 
			((MultiExprTreeIndividual) parentsBuffer.get(parentsCounter+1)).getGenotype();
		// Sons genotypes
		ExprTree [] s0_genotype = new ExprTree[nOfExprTrees]; 
		ExprTree [] s1_genotype = new ExprTree[nOfExprTrees];
		
		for(int i=0; i<nOfExprTrees; i++){
			s0_genotype[i] = new ExprTree();
			s1_genotype[i] = new ExprTree();
		}
		// Fill sons
		for (int i=0; i<nOfExprTrees; i++) {
			if (i==actTargetExprIndex) {
				baseOp.recombine(p0_genotype[i], p1_genotype[i], s0_genotype[i], s1_genotype[i], schema[i], randgen);
			}
			else {
				s0_genotype[i] = p0_genotype[i].copy();
				s1_genotype[i] = p1_genotype[i].copy();
			}
		}		
		// Add sons
		sonsBuffer.add(species.createIndividual(s0_genotype));
		sonsBuffer.add(species.createIndividual(s1_genotype));
	}
}
