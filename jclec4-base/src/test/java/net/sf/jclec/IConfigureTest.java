package net.sf.jclec;

import junit.framework.TestCase;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.XMLConfiguration;

/**
 * Configuration tests.
 * 
 * @author Sebastian Ventura
 *
 * @param <C> Class to be tested
 */

public abstract class IConfigureTest<C extends IConfigure> extends TestCase 
{
	/////////////////////////////////////////////////////////////////
	// --------------------------------------------------- Attributes
	/////////////////////////////////////////////////////////////////

	/** Object class */
	
	protected final Class<C> objectsClass;
	
	/** Object to marshall */
	
	protected C object1; 

	/** Object to unmarshall */

	protected C object2; 
	
	/** Configuration filename */
	
	protected String configurationFilename;

	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////

	public IConfigureTest(Class<C> objectsClass, String name) 
	{
		super(name);
		this.objectsClass = objectsClass;
	}

	/////////////////////////////////////////////////////////////////
	// --------------------------------- Overwriting TestCase methods
	/////////////////////////////////////////////////////////////////
	
	@Override
	protected void setUp() throws Exception 
	{
		// Call super.setUp()
		super.setUp();
		// Initialize filename
		setConfigurationFilename();	
		// Create first object
		object1 = objectsClass.newInstance();		
		// Init first object
		initObject1();
	}

	/////////////////////////////////////////////////////////////////
	// --------------------------------------------------- Unit tests
	/////////////////////////////////////////////////////////////////

	/**
	 * Configure test.
	 */
	
	public void testConfigure() throws Exception
	{
		// Init configuration
		Configuration configuration = new XMLConfiguration(configurationFilename);
		// Create object2
		object2 = objectsClass.newInstance();
		// Configure object2
		object2.configure(configuration);
		// Assert objects equallity
		assertEquals(object1, object2);
	}
	
	/////////////////////////////////////////////////////////////////
	// -------------------------------------------- Protected methods
	/////////////////////////////////////////////////////////////////

	/**
	 * Sets object1 (used in marshall/unmarshall processes).
	 */
	
	protected abstract void initObject1();		

	/**
	 * Set the filename of the configuration object 
	 */
	
	protected abstract void setConfigurationFilename();	
}
