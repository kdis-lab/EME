package net.sf.jclec;

import junit.framework.TestCase;

/**
 * Operation test for an ITool object.
 *  
 * @author Sebastian Ventura
 *
 * @param <T> Type of object to test
 */

public abstract class IToolTest<T extends ITool> extends TestCase 
{
	/////////////////////////////////////////////////////////////////
	// --------------------------------------------------- Attributes
	/////////////////////////////////////////////////////////////////

	/** Object class */
	
	protected final Class<T> toolClass;
	
	/** Object to marshall */
	
	protected T tool; 

	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////

	/**
	 * Default constructor.
	 * 
	 * @param toolClass Tool class
	 * @param testName  Test name
	 */
	
	public IToolTest(Class<T> toolClass, String testName) 
	{
		super(testName);
		this.toolClass = toolClass;
	}

	/////////////////////////////////////////////////////////////////
	// ------------------------------------- Overwriting Test methods
	/////////////////////////////////////////////////////////////////

	protected void setUp() throws Exception 
	{
		// Call super method
		super.setUp();
		// Create first object
		tool = toolClass.newInstance();		
		// Init first object
		initTool();
		// Set execution context
		tool.contextualize(createContext());
	}

	/**
	 * Initialize tool object.
	 */
	
	protected abstract void initTool();		

	/**
	 * Create an instance of ISystem that will act as execution
	 * context for Tool operation tests.
	 *  
	 * @return Execution context for this tool
	 */
	
	protected abstract ISystem createContext();
}
