package net.sf.jclec.realarray;

import net.sf.jclec.ISpecies;

import net.sf.jclec.base.AbstractRecombinator;

import net.sf.jclec.util.range.IRange;

/**
 * RealArrayIndividual (and subclasses) specific recombinator.  
 * 
 * @author Sebastian Ventura
 */

public abstract class RealArrayRecombinator extends AbstractRecombinator 
{
	private static final long serialVersionUID = -8707182433153296963L;
	
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------- Internal variables
	/////////////////////////////////////////////////////////////////
	
	/** Individual species */
	
	protected transient RealArraySpecies species;
	
	/** Genotype length */
	
	protected transient int genotypeLength;
	
	/** Genotype schema */
	
	protected transient IRange [] genotypeSchema;
		
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Empty constructor.
	 */
	
	public RealArrayRecombinator() 
	{
		super();
	}

	/////////////////////////////////////////////////////////////////
	// -------------------------------------------- Protected methods
	/////////////////////////////////////////////////////////////////

	// AbstractRecombinator methods

	/**
	 * {@inheritDoc}
	 */
	
	@Override	
	protected void prepareRecombination()
	{
		// Context species
		ISpecies spc = context.getSpecies(); 
		if (spc instanceof RealArraySpecies) {
			this.species = (RealArraySpecies) spc;
		}
		else {
			throw new IllegalArgumentException("IRealArraySpecies expected");
		}
		// Genotype length
		genotypeLength = species.getGenotypeLength();
		// Genotype schema
		genotypeSchema = species.getGenotypeSchema();
	}	
}
