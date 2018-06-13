package net.sf.jclec.intarray.mut;

import java.util.ArrayList;

import net.sf.jclec.IIndividual;

import net.sf.jclec.intarray.IntArrayIndividual;
import net.sf.jclec.intarray.IntArrayMutatorTest;

/**
 * Unit test for OneLocusMutator
 *  
 * @author Sebastian Ventura
 */

public class OneLocusMutatorTest extends IntArrayMutatorTest<OneLocusMutator> 
{
	/**
	 * Default constructor.
	 */
	
	public OneLocusMutatorTest(String name) 
	{
		super(OneLocusMutator.class, name);
	}

	/**
	 * Set evaluate flag to false 
	 */
	
	@Override
	protected void initTool() 
	{
		// Do nothing
	}

	@Override
	protected void createExpected() 
	{
		expected = new ArrayList<IIndividual> (); 
		expected.add(new IntArrayIndividual(new int [] {1, 3, 1, 1, 1, 1}));		
	}
}
