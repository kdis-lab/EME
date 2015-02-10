package net.sf.jclec.base;

import net.sf.jclec.ISystem;
import net.sf.jclec.IConfigure;
import net.sf.jclec.IRecombinator;

import org.apache.commons.configuration.Configuration;

/**
 * Decorated recombinator.
 * 
 * @author Sebastian Ventura
 */

@SuppressWarnings("serial")
public abstract class DecoratedRecombinator implements IRecombinator, IConfigure 
{
	/////////////////////////////////////////////////////////////////
	// --------------------------------------------------- Properties
	/////////////////////////////////////////////////////////////////
	
	/** Recombinator to decorate */
	
	protected IRecombinator decorated;
	
	/////////////////////////////////////////////////////////////////
	// -------------------------- Internal variables (Operator state)
	/////////////////////////////////////////////////////////////////
	
	/** Execution context */
	
	protected ISystem context;
	
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////
	
	/** Empty constructor */

	public DecoratedRecombinator() 
	{
		super();
	}

	/** 
	 * Constructor that contextualizes this recombinator
	 * 
	 * @param context New execution context
	 */

	public DecoratedRecombinator(ISystem context) 
	{
		super();
		contextualize(context);
	}

	/////////////////////////////////////////////////////////////////
	// ----------------------------------------------- Public methods
	/////////////////////////////////////////////////////////////////

	// Setting and getting properties

	/**
	 * Access to decorated recombinator.
	 * 
	 * @return Decorated recombinator
	 */
	
	public final IRecombinator getDecorated()
	{
		return decorated;
	}

	/**
	 * Set the mutator to decorate
	 * 
	 * @param decorated Mutator to decorate
	 */
	
	public final void setDecorated(IRecombinator decorated)
	{
		// Set decorated recombinator
		this.decorated = decorated;
		// Contextualize "decorated" (if context was previously set)
		if (context != null) {
			decorated.contextualize(context);
		}
	}

	// IRecombinator interface
	
	/**
	 * Contextualize decorated mutator (if exists)
	 * 
	 * {@inheritDoc}
	 */
	
	public void contextualize(ISystem context)
	{
		// Call super method
		this.context = context;
		// Contextualize "decorated" (if context was previously set)
		if (decorated != null) {
			decorated.contextualize(context);
		}
	}	

	/**
	 * {@inheritDoc}
	 * 
	 * @return decorated.getPpl()
	 */
	
	public final int getPpl()
	{
		return decorated.getPpl();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return decorated.getSpl()
	 */
	
	public final int getSpl()
	{
		return decorated.getSpl();
	}
	
	// IConfigure interface
	
	/**
	 * Configuration parameters for DecoratedRecombinator are:
	 * 
	 * <ul>
	 * <li>
	 * <code>decorated: complex ... </code> 
	 * </li>
	 * </ul>
	 */
	
	@SuppressWarnings("unchecked")
	public void configure(Configuration settings)
	{
		try {
			// Decorated recombinator classname
			String decoratedClassname = 
				settings.getString("decorated[@type]");
			// Decorated recombinator class
			Class<? extends IRecombinator> decoratedClass = 
				(Class<? extends IRecombinator>) Class.forName(decoratedClassname);
			// Decorated recombinator instance
			IRecombinator decorated = decoratedClass.newInstance();
			// Configure decorated recombinator
			if (decorated instanceof IConfigure) {
				((IConfigure) decorated).configure(settings.subset("decorated"));				
			}
			// Set decorated 
			setDecorated(decorated);
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		catch (InstantiationException e) {
			e.printStackTrace();
		} 
		catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}	
}

/*
 * Estos objetos modifican el comportamiento de un objeto Recombinator, aadindole
 * funcionalidad adicional... 
 */
