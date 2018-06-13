package net.sf.jclec.multiexprtree;

import net.sf.jclec.ISpecies;
import net.sf.jclec.IConfigure;

import net.sf.jclec.base.AbstractMutator;
import net.sf.jclec.exprtree.ExprTree;
import net.sf.jclec.exprtree.ExprTreeSchema;
import net.sf.jclec.exprtree.IMutateExprTree;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationRuntimeException;

/**
 * Mutator for ExprTreeIndividual (and its subclasses).
 * 
 * @author Sebastian Ventura
 */

public class MultiExprTreeMutator extends AbstractMutator implements IConfigure
{
	/////////////////////////////////////////////////////////////////
	// --------------------------------------------------- Properties
	/////////////////////////////////////////////////////////////////

	private static final long serialVersionUID = 1999446621338451013L;

	/** Target tree index */
	
	protected int targetTreeIndex = -1;
	
	/** Base operation for this expression tree mutator */
	
	protected IMutateExprTree baseOp;
	
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

	public MultiExprTreeMutator() 
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

	public IMutateExprTree getBaseOp() 
	{
		return baseOp;
	}

	public void setBaseOp(IMutateExprTree baseOp) 
	{
		this.baseOp = baseOp;
	}

	// IConfigure method

	/**
	 * {@inheritDoc}
	 */
	
	@SuppressWarnings("unchecked")
	public void configure(Configuration settings)
	{
		// Get target-tree-index parameter
		int targetTreeIndex = settings.getInt("target-tree-index", -1);
		setTargetTreeIndex(targetTreeIndex);
		// Set base mutator name
		try {
			// Base mutator classname
			String baseOpClassname = 
				settings.getString("base-op[@type]");
			// Evaluator class
			Class<? extends IMutateExprTree> baseOpClass = 
				(Class<? extends IMutateExprTree>) Class.forName(baseOpClassname);
			// Evaluator instance
			IMutateExprTree baseOp = baseOpClass.newInstance();
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
	// ----------------------------------------------- Public methods
	/////////////////////////////////////////////////////////////////

	// AbstractMutator methods

	@Override
	protected void prepareMutation() 
	{
		ISpecies spc = context.getSpecies();
		if (spc instanceof MultiExprTreeSpecies) {
			// 
			this.species = (MultiExprTreeSpecies) spc;
			// Sets genotype schema
			this.schema = ((MultiExprTreeSpecies) spc).getGenotypeSchema();
		}
		else {
			throw new IllegalStateException("Illegal species in context");
		}
	}

	@Override
	protected void mutateNext() 
	{
		// Number of expression trees
		int nOfExprTrees = species.numberOfExprTrees();
		// Select the tree to mutate
		int actTargetExprIndex = 
			(targetTreeIndex == -1) ? randgen.choose(0, nOfExprTrees) : targetTreeIndex;
		// Actual individual
		ExprTree [] parentGenotype = 
			((MultiExprTreeIndividual) parentsBuffer.get(parentsCounter)).getGenotype();
		// Mutate target, copy rest of trees
		ExprTree [] sonGenotype = new ExprTree[nOfExprTrees];
		for (int i=0; i<nOfExprTrees; i++) {
			if (i==actTargetExprIndex) {
				sonGenotype[i] = baseOp.mutateExprTree(parentGenotype[i], schema[i], randgen);
			}
			else {
				sonGenotype[i] = parentGenotype[i].copy();
			}
		}
		
		sonsBuffer.add(species.createIndividual(sonGenotype));
	}
}
