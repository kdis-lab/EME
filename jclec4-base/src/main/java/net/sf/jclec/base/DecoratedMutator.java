package net.sf.jclec.base;

import net.sf.jclec.ISystem;
import net.sf.jclec.IMutator;
import net.sf.jclec.IConfigure;

import org.apache.commons.configuration.Configuration;

/**
 * Decorated mutator.
 * 
 * @author Sebastian Ventura
 */

@SuppressWarnings("serial")
public abstract class DecoratedMutator implements IMutator, IConfigure  
{
	/////////////////////////////////////////////////////////////////
	// --------------------------------------------------- Properties
	/////////////////////////////////////////////////////////////////
	
	/** Mutator to decorate */
	
	protected IMutator decorated;
	
	/////////////////////////////////////////////////////////////////
	// -------------------------- Internal variables (Operator state)
	/////////////////////////////////////////////////////////////////
	
	/** Execution context */
	
	protected ISystem context;
	
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////
	
	/** Empty constructor */

	public DecoratedMutator() 
	{
		super();
	}

	/** 
	 * Constructor that contextualizes this mutator
	 * 
	 * @param context New execution context
	 */

	public DecoratedMutator(ISystem context) 
	{
		super();
		contextualize(context);
	}

	/////////////////////////////////////////////////////////////////
	// ----------------------------------------------- Public methods
	/////////////////////////////////////////////////////////////////

	// Setting and getting properties

	/**
	 * Access to decorated mutator.
	 * 
	 * @return Decorated mutator
	 */
	
	public final IMutator getDecorated()
	{
		return decorated;
	}

	/**
	 * Set the mutator to decorate
	 * 
	 * @param decorated Mutator to decorate
	 */
	
	public final void setDecorated(IMutator decorated)
	{
		// Set decorated mutator
		this.decorated = decorated;
		// Contextualize "decorated" (if context was previously set)
		if (context != null) {
			decorated.contextualize(context);
		}
	}
	
	// IMutator interface
	
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

	// IConfigure interface

	/**
	 * Configuration parameters for DecoratedMutator are:
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
			// Decorated mutator classname
			String decoratedClassname = 
				settings.getString("decorated[@type]");
			// Decorated mutator class
			Class<? extends IMutator> decoratedClass = 
				(Class<? extends IMutator>) Class.forName(decoratedClassname);
			// Decorated mutator instance
			IMutator decorated = decoratedClass.newInstance();
			// Configure decorated mutator (if necessary) 
			if (decorated instanceof IConfigure) {
				((IConfigure) decorated).configure(settings.subset("decorated"));				
			}
			// Set decorated mutator
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
 * Estos objetos modifican el comportamiento de un objeto Mutator, aadindole
 * funcionalidad adicional... 
 */

