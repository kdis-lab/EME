package net.sf.jclec.multisyntaxtree;

import net.sf.jclec.ISpecies;
import net.sf.jclec.IConfigure;


import net.sf.jclec.base.AbstractRecombinator;
import net.sf.jclec.syntaxtree.IRecombineSyntaxTree;
import net.sf.jclec.syntaxtree.SyntaxTree;
import net.sf.jclec.syntaxtree.SyntaxTreeSchema;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationRuntimeException;

/**
 * Recombinator for MultiSyntaxTreeIndividual (and its subclasses).
 * 
 * @author Sebastian Ventura
 */

public class MultiSyntaxTreeRecombinator extends AbstractRecombinator implements IConfigure 
{
	/////////////////////////////////////////////////////////////////
	// --------------------------------------------------- Properties
	/////////////////////////////////////////////////////////////////

	private static final long serialVersionUID = 232062658224599146L;

	/** Target tree index */
	
	protected int targetTreeIndex = -1;
	
	/** Base operation for this expression tree mutator */
	
	protected IRecombineSyntaxTree baseOp;
	
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

	/**
	 * Empty constructor
	 */
	
	public MultiSyntaxTreeRecombinator() 
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

	public IRecombineSyntaxTree getBaseOp() 
	{
		return baseOp;
	}

	public void setBaseOp(IRecombineSyntaxTree baseOp) 
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
			Class<? extends IRecombineSyntaxTree> baseOpClass = 
				(Class<? extends IRecombineSyntaxTree>) Class.forName(baseOpClassname);
			// Evaluator instance
			IRecombineSyntaxTree baseOp = baseOpClass.newInstance();
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
		if (spc instanceof MultiSyntaxTreeSpecies) {
			//  Assign species
			this.species = (MultiSyntaxTreeSpecies) spc;
			// Sets genotype schema
			this.schema = ((MultiSyntaxTreeSpecies) spc).getGenotypeSchema();
		}
		else {
			throw new IllegalStateException("Illegal species in context");
		}
	}

	@Override
	protected void recombineNext() 
	{
		// Number of expression trees
		int nOfSyntaxTrees = species.numberOfSyntaxTrees();
		// Select the tree to mutate
		int actTargetSyntaxIndex = 
			(targetTreeIndex == -1) ? randgen.choose(0, nOfSyntaxTrees) : targetTreeIndex;
		// Parents genotypes
		SyntaxTree [] p0_genotype = 
			((MultiSyntaxTreeIndividual) parentsBuffer.get(parentsCounter)).getGenotype();
		SyntaxTree [] p1_genotype = 
			((MultiSyntaxTreeIndividual) parentsBuffer.get(parentsCounter+1)).getGenotype();
		// Sons genotypes
		SyntaxTree [] s0_genotype = new SyntaxTree[nOfSyntaxTrees]; 
		SyntaxTree [] s1_genotype = new SyntaxTree[nOfSyntaxTrees];
		
		for(int i=0; i<nOfSyntaxTrees; i++){
			s0_genotype[i] = new SyntaxTree();
			s1_genotype[i] = new SyntaxTree();
		}
		// Fill sons
		for (int i=0; i<nOfSyntaxTrees; i++) {
			if (i==actTargetSyntaxIndex) {
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
