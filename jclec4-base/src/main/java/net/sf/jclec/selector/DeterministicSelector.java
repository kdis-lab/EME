package net.sf.jclec.selector;

import net.sf.jclec.ISystem;

import net.sf.jclec.base.AbstractSelector;

/**
 * Deterministic selector.
 * 
 * @author Sebastian Ventura
 */

public abstract class DeterministicSelector extends AbstractSelector
{
	private static final long serialVersionUID = 4909196860032671219L;
	
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Empty selector.
	 */
	
	public DeterministicSelector() 
	{
		super();
	}

	/**
	 * Empty selector.
	 */
	
	public DeterministicSelector(ISystem context) 
	{
		super(context);
	}
}
