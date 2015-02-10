package net.sf.jclec.realarray;

import net.sf.jclec.ISpecies;

import net.sf.jclec.base.AbstractMutator;

import net.sf.jclec.util.range.IRange;

/**
 * RealArrayIndividual (and subclasses) specific mutator.  
 * 
 * @author Sebastian Ventura
 */

public abstract class RealArrayMutator extends AbstractMutator 
{
	private static final long serialVersionUID = 3585720835936638168L;

	/////////////////////////////////////////////////////////////////
	// ------------------------------------------- Internal variables
	/////////////////////////////////////////////////////////////////

	/** Individuals species */
	
	protected transient RealArraySpecies species; 

	/** Genotype length */
	
	protected transient int genotypeLength;
	
	/** Genotype schema */
	
	protected transient IRange [] genotypeSchema;
	
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Empty constructor
	 */
	
	public RealArrayMutator() 
	{
		super();
	}

	/////////////////////////////////////////////////////////////////
	// -------------------------------------------- Protected methods
	/////////////////////////////////////////////////////////////////

	// AbstractMutator methods

	/**
	 * {@inheritDoc}
	 */
	
	@Override	
	protected void prepareMutation()
	{
		// Context species
		ISpecies spc = context.getSpecies(); 
		if (spc instanceof RealArraySpecies) {
			this.species = (RealArraySpecies) spc;
		}
		else {
			throw new IllegalArgumentException("IRealArraySpecies expected");
		}
		// Genome length
		genotypeLength = species.getGenotypeLength();
		// Genome schema
		genotypeSchema = species.getGenotypeSchema();
	}
}
