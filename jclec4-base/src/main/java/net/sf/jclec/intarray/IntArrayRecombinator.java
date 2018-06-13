package net.sf.jclec.intarray;

import net.sf.jclec.ISpecies;
import net.sf.jclec.base.AbstractRecombinator;

/**
 * IntArrayIndividual (and subclasses) specific recombinator.  
 * 
 * @author Sebastian Ventura
 */

public abstract class IntArrayRecombinator extends AbstractRecombinator 
{
	private static final long serialVersionUID = 1527304795896439539L;
	
	/////////////////////////////////////////////////////////////////
	// --------------------------------------------------- Attributes
	/////////////////////////////////////////////////////////////////
	
	/** Individual species */
	
	protected IntArraySpecies species;
	
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Empty constructor.
	 */
	
	public IntArrayRecombinator() 
	{
		super();
	}

	/////////////////////////////////////////////////////////////////
	// -------------------------------------------- Protected methods
	/////////////////////////////////////////////////////////////////	
	
	// AbstractRecombinator methods
	
	/**
	 * Sets ppl = 2
	 * 
	 * {@inheritDoc}
	 */
	
	@Override
	protected void setPpl() 
	{
		this.ppl = 2;
	}

	/**
	 * Sets spl = 2
	 * 
	 * {@inheritDoc}
	 */

	@Override
	protected void setSpl() 
	{
		this.spl = 2;
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Override	
	protected void prepareRecombination()
	{
		// Sets individual species
		ISpecies spc = context.getSpecies();
		if (spc instanceof IntArraySpecies) {
			this.species = (IntArraySpecies) spc;
		}
		else {
			throw new IllegalStateException("Invalid population species");
		}		
	}
}
