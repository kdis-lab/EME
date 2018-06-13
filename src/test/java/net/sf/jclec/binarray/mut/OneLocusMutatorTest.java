package net.sf.jclec.binarray.mut;

import java.util.ArrayList;

import net.sf.jclec.IIndividual;

import net.sf.jclec.binarray.BinArrayIndividual;
import net.sf.jclec.binarray.BinArrayMutatorTest;

/**
 * Unit test for OneLocusMutator
 *  
 * @author Sebastian Ventura
 */

public class OneLocusMutatorTest extends BinArrayMutatorTest<OneLocusMutator> 
{
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////

	/**
	 * Default constructor.
	 */
	
	public OneLocusMutatorTest(String name) 
	{
		super(OneLocusMutator.class, name);
	}

	/////////////////////////////////////////////////////////////////
	// ----------------------------- Overwriting IMutatorTest methods
	/////////////////////////////////////////////////////////////////

	@Override
	protected void createExpected() 
	{
		expected = new ArrayList<IIndividual> (); 
		expected.add(new BinArrayIndividual(new byte [] {1, 0, 1, 1, 1, 1}));		
	}

	@Override
	protected void initTool() 
	{
		// Do nothing		
	}
}
