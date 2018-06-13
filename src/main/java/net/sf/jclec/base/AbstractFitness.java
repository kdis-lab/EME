package net.sf.jclec.base;

import net.sf.jclec.IFitness;

/**
 * IFitness abstract implementation.
 * 
 * @author Sebastian Ventura
 */

@SuppressWarnings("serial")
public abstract class AbstractFitness implements IFitness 
{
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Empty constructor
	 */
	
	public AbstractFitness() 
	{
		super();
	}

	/////////////////////////////////////////////////////////////////
	// ------------------------------ Implementing IFitness interface
	/////////////////////////////////////////////////////////////////
	
	/**
	 * This default implementation returns false everytime. 
	 * 
	 * @return false
	 */
	
	public boolean isAcceptable() 
	{
		return false;
	}
}
