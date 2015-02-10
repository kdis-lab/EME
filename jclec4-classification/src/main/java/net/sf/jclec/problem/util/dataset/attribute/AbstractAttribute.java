package net.sf.jclec.problem.util.dataset.attribute;

/**
 * Abstract definition of an attribute.
 * 
 * Implements the IAttribute interface and defines the attribute name.
 * 
 * @author Amelia Zafra
 * @author Sebastian Ventura
 * @author Alberto Cano
 */

public abstract class AbstractAttribute implements IAttribute 
{
	/////////////////////////////////////////////////////////////////
	// --------------------------------------------------- Properties
	/////////////////////////////////////////////////////////////////

	private static final long serialVersionUID = -2103423458042526762L;
	
	/** Attribute name */
	
	protected String name;
	
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////

	/**
	 * Empty (default) constructor
	 */
	
	public AbstractAttribute() 
	{
		super();
	}

	/**
	 * Constructor that sets attribute name
	 * 
	 * @param name Attribute name
	 */
	
	public AbstractAttribute(String name) 
	{
		super();
		setName(name);
	}
	
	/////////////////////////////////////////////////////////////////
	// ----------------------------------------------- Public methods
	/////////////////////////////////////////////////////////////////

	// Setting properties
	
	/**
	 * Set attribute name.
	 * 
	 * @param name New attribute name
	 */
	
	public final void setName(String name) 
	{
		this.name = name;
	}

	/**
	 * {@inheritDoc}
	 */
	
	public String getName() 
	{
		return name;
	}
}
