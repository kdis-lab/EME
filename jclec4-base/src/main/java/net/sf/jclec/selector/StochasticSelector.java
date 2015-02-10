package net.sf.jclec.selector;

import net.sf.jclec.ISystem;

import net.sf.jclec.base.AbstractSelector;

import net.sf.jclec.util.random.IRandGen;

/**
 * Stochastic (random) selector. Use a random numbers generator to perform the
 * selection operation. This random generator is taken from execution context. 
 * 
 * @author Sebastian Ventura
 */

public abstract class StochasticSelector extends AbstractSelector 
{
	private static final long serialVersionUID = 6397831583918883359L;
	
	/////////////////////////////////////////////////////////////////
	// --------------------------------------------------- Properties
	/////////////////////////////////////////////////////////////////
	
	/** Random generator used in selection process */
	
	protected IRandGen randgen;
	
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////

	/**
	 * Empty constructor
	 */
	
	public StochasticSelector() 
	{
		super();
	}

	/**
	 * Constructor that contextualize selector
	 * 
	 * @param context Execution context
	 */
	
	public StochasticSelector(ISystem context) 
	{
		super(context);
	}

	/////////////////////////////////////////////////////////////////
	// ----------------------------------------------- Public methods
	/////////////////////////////////////////////////////////////////	
	
	// ISelector interface
	
	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public void contextualize(ISystem context)
	{
		// Call super method
		super.contextualize(context);
		// Attach a random generator to this object
		this.randgen = context.createRandGen();
	}
}
