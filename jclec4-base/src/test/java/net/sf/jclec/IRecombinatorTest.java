package net.sf.jclec;

import java.util.List;

/**
 * Recombinator tests.
 * 
 * @author Sebastian Ventura
 *
 * @param <R> Type of recombinator to test
 */

public abstract class IRecombinatorTest<R extends IRecombinator> extends IToolTest<R> 
{
	/////////////////////////////////////////////////////////////////
	// --------------------------------------------------- Attributes
	/////////////////////////////////////////////////////////////////
	
	/** Individual recombined in testRecombine() */
	
	protected List<IIndividual> parents;

	/** Individual expected in testRecombine() */

	protected List<IIndividual> expected;
	
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////

	/**
	 * Default constructor
	 */
	
	public IRecombinatorTest(Class<R> toolClass, String testName) 
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
		// Create parents
		createParents();
		// Create expected
		createExpected();
	}

	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Unitary test
	/////////////////////////////////////////////////////////////////

	/**
	 * Unit test for the recombine() method 
	 */
	
	public void testRecombine()
	{
		// Mutate parents
		List<IIndividual> result = tool.recombine(parents);
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
	
	/** Create "parents" */
	
	protected abstract void createParents();

	/** Creaet expected */
	
	protected abstract void createExpected();
}
