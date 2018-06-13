package net.sf.jclec.base;

import java.util.List;
import java.util.ArrayList;

import net.sf.jclec.ISystem;
import net.sf.jclec.IProvider;
import net.sf.jclec.IIndividual;
import net.sf.jclec.IPopulation;

import net.sf.jclec.util.random.IRandGen;

/**
 * Provide new individuals creating them at random.
 * 
 * @author Sebastian Ventura
 */

public abstract class AbstractCreator implements IProvider 
{
	private static final long serialVersionUID = -3789689625549934801L;
	
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------- Internal variables
	/////////////////////////////////////////////////////////////////

	// Operator state
	
	/** Execution context */
	
	protected IPopulation context;
	
	/** Random generator used in creation */
	
	protected IRandGen randgen;

	// Auxiliary (provide method) variables 
	
	/** Number of individuals to create */
	
	protected transient int numberOfIndividuals;
	
	/** Creation buffer */
	 
	protected transient List<IIndividual> createdBuffer;

	/** Created counter */
	
	protected transient int createdCounter;

	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////

	/**
	 * Empty (default) constructor.
	 */
	
	public AbstractCreator() 
	{
		super();
	}

	/////////////////////////////////////////////////////////////////
	// ----------------------------------------------- Public methods
	/////////////////////////////////////////////////////////////////

	// IProvider interface
	
	/**
	 * {@inheritDoc}
	 */
	
	public void contextualize(ISystem context)
	{
		if(context instanceof IPopulation) {
			// Set execution context
			this.context = (IPopulation) context;
			// Attach a random generator to this object
			this.randgen = context.createRandGen();
		}
		else {
			throw new IllegalArgumentException
				("This object uses a population as execution context");
		}
	}
	
	/**
	 * This method performs the following actions:
	 * 
	 * <ol>
	 * <li>
	 * Assigns the <code>numberOfIndividuals</code> parameter to the
	 * auxiliary class variable <code>this.numberOfIndividuals</code>
	 * </li>
	 * <li>
	 * Assigns a new list of <code>IIndividual</code> objects to the
	 * auxiliary class variable <code>createdBuffer</code>.
	 * </li>  
	 * <li>
	 * Call the method <code>prepareCreation</code>, that prepares the
	 * individuals creation process.
	 * </li>
	 * <li>
	 * Call the method <code>createNext</code> until <code>createdCounter</code> 
	 * is equals to <code>numberOfIndividuals</code> 
	 * </li>
	 * <li>
	 * Return <code>createdBuffer</code>.
	 * </li>
	 * </ol>
	 * 
	 * {@inheritDoc}
	 */
	
	public List<IIndividual> provide(int numberOfIndividuals) 
	{
		// Set numberOfIndividuals
		this.numberOfIndividuals = numberOfIndividuals;
		// Result list
		createdBuffer = new ArrayList<IIndividual> (numberOfIndividuals);
		// Prepare process
		prepareCreation();
		// Provide individuals
		for (createdCounter=0; createdCounter<numberOfIndividuals; createdCounter++) {
			createNext();
		}
		// Returns result
		return createdBuffer;
	}
	
	/////////////////////////////////////////////////////////////////
	// -------------------------------------------- Protected methods
	/////////////////////////////////////////////////////////////////

	/**
	 * Prepare creation process.
	 */
	
	protected abstract void prepareCreation();
	
	/**
	 * Creation method.
	 * 
	 * @param ind Individual to create
	 * 
	 * @return New individual
	 */
	
	protected abstract void createNext();
}
