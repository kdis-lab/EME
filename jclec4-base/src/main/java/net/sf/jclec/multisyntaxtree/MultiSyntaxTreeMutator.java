package net.sf.jclec.multisyntaxtree;

import net.sf.jclec.ISpecies;
import net.sf.jclec.IConfigure;

import net.sf.jclec.base.AbstractMutator;

import net.sf.jclec.syntaxtree.IMutateSyntaxTree;
import net.sf.jclec.syntaxtree.SyntaxTree;
import net.sf.jclec.syntaxtree.SyntaxTreeSchema;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationRuntimeException;

/**
 * Mutator for ExprTreeIndividual (and its subclasses).
 * 
 * @author Sebastian Ventura
 */

public class MultiSyntaxTreeMutator extends AbstractMutator implements IConfigure
{
	/////////////////////////////////////////////////////////////////
	// --------------------------------------------------- Properties
	/////////////////////////////////////////////////////////////////

	private static final long serialVersionUID = 1603346721798004106L;

	/** Target tree index */
	
	protected int targetTreeIndex = -1;
	
	/** Base operation for this expression tree mutator */
	
	protected IMutateSyntaxTree baseOp;
	
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------- Internal variables
	/////////////////////////////////////////////////////////////////

	/** Individual species */
	
	protected transient MultiSyntaxTreeSpecies species;
	
	/** Individuals schema */
	
	protected transient SyntaxTreeSchema [] schema;
	
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////

	public MultiSyntaxTreeMutator() 
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

	public IMutateSyntaxTree getBaseOp() 
	{
		return baseOp;
	}

	public void setBaseOp(IMutateSyntaxTree baseOp) 
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
			Class<? extends IMutateSyntaxTree> baseOpClass = 
				(Class<? extends IMutateSyntaxTree>) Class.forName(baseOpClassname);
			// Evaluator instance
			IMutateSyntaxTree baseOp = baseOpClass.newInstance();
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
		if (spc instanceof MultiSyntaxTreeSpecies) {
			// 
			this.species = (MultiSyntaxTreeSpecies) spc;
			// Sets genotype schema
			this.schema = ((MultiSyntaxTreeSpecies) spc).getGenotypeSchema();
		}
		else {
			throw new IllegalStateException("Illegal species in context");
		}
	}

	@Override
	protected void mutateNext() 
	{
		// Number of expression trees
		int nOfSyntaxTrees = species.numberOfSyntaxTrees();
		// Select the tree to mutate
		int actTargetExprIndex = 
			(targetTreeIndex == -1) ? randgen.choose(0, nOfSyntaxTrees) : targetTreeIndex;
		// Actual individual
		SyntaxTree [] parentGenotype = 
			((MultiSyntaxTreeIndividual) parentsBuffer.get(parentsCounter)).getGenotype();
		// Mutate target, copy rest of trees
		SyntaxTree [] sonGenotype = new SyntaxTree[nOfSyntaxTrees];
		for (int i=0; i<nOfSyntaxTrees; i++) {
			if (i==actTargetExprIndex) {
				sonGenotype[i] = baseOp.mutateSyntaxTree(parentGenotype[i], schema[i], randgen);
			}
			else {
				sonGenotype[i] = parentGenotype[i].copy();
			}
		}
		sonsBuffer.add(species.createIndividual(sonGenotype));
	}
}
