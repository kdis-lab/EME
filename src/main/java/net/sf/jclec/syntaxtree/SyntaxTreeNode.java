package net.sf.jclec.syntaxtree;

import net.sf.jclec.JCLEC;

/**
 * Node in a syntax tree.
 *  
 * @author Sebastian Ventura
 */

public abstract class SyntaxTreeNode implements JCLEC 
{
	/////////////////////////////////////////////////////////////////
	// --------------------------------------------------- Properties
	/////////////////////////////////////////////////////////////////

	private static final long serialVersionUID = 6515472868824368494L;
	
	/** Symbol name */
	
	protected String symbol;
	
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Empty constructor.
	 */
	
	public SyntaxTreeNode() 
	{
		super();
	}

	/**
	 * Constructor that sets symbol name
	 */
	
	public SyntaxTreeNode(String name) 
	{
		super();
		this.symbol = name;
	}
	
	/////////////////////////////////////////////////////////////////
	// ----------------------------------------------- Public methods
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Access to symbol name.
	 * 
	 * @return Symbol name
	 */
	
	public String getSymbol()
	{
		return symbol;
	}
	
	/**
	 * Set the symbol name.
	 * 
	 * @param symbol Symbol name 
	 */
	
	public void setSymbol(String symbol)
	{
		this.symbol = symbol;
	}
	
	// Symbol information
	
	/**
	 * Number of sons of this node
	 * 
	 * @return arity of the node
	 */

	public abstract int arity();
	
	// Abstract methods
	
	/**
	 * Instance method. Create a new instance of this symbol
	 * 
	 * @return A new instance of this symbol
	 */
	
	public abstract SyntaxTreeNode instance();

	/**
	 * Copy method. Create a copy of this symbol.
	 * 
	 * @return A copy of this symbol
	 */
	
	public abstract SyntaxTreeNode copy();
	
	/////////////////////////////////////////////////////////////////
	// ------------------------- Overwriting java.lang.Object methods
	/////////////////////////////////////////////////////////////////
	
	/**
	 * {@inheritDoc} 
	 */
	
	public String toString()
	{
		return symbol;
	}
}
