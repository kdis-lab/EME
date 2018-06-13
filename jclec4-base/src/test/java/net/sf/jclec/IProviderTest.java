package net.sf.jclec;

import java.util.List;

/**
 * Provider tests.
 * 
 * @author Sebastian Ventura
 *
 * @param <P> Type of provider to test
 */

public abstract class IProviderTest<P extends IProvider> extends IToolTest<P> 
{
	/////////////////////////////////////////////////////////////////
	// --------------------------------------------------- Attributes
	/////////////////////////////////////////////////////////////////
	
	/** Individual expected in testRecombine() */

	protected List<IIndividual> expected;
	
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////

	/**
	 * Default constructor
	 */
	
	public IProviderTest(Class<P> toolClass, String testName) 
	{
		super(toolClass, testName);
	}

	/////////////////////////////////////////////////////////////////
	// -------------------------------- Overwriting IToolTest methods
	/////////////////////////////////////////////////////////////////

	@Override
	protected void setUp() throws Exception 
	{
		// Call super method
		super.setUp();
		// Create expected
		createExpected();
	}

	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Unitary test
	/////////////////////////////////////////////////////////////////

	/**
	 * Unit test for the provide() method
	 */
	
	public void testProvide()
	{
		// Mutate parents
		List<IIndividual> result = tool.provide(expected.size());
		// Assert result and expected have the same size
		assertEquals(expected.size(), result.size());
		// Assert result individuals are expected
		int es = expected.size();
		for (int i=0; i<es; i++) {
			assertEquals(expected.get(i), result.get(i));
		}
	}
	
	/////////////////////////////////////////////////////////////////
	// -------------------------------------------- Protected methods
	/////////////////////////////////////////////////////////////////
	
	/** Create expected */
	
	protected abstract void createExpected();
}
