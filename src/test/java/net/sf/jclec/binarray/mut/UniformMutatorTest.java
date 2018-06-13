package net.sf.jclec.binarray.mut;

import java.util.ArrayList;

import net.sf.jclec.IIndividual;

import net.sf.jclec.binarray.BinArrayIndividual;
import net.sf.jclec.binarray.BinArrayMutatorTest;

/**
 * Unit tests for UniformMutator.
 * 
 * @author Sebastian Ventura
 */

public class UniformMutatorTest extends BinArrayMutatorTest<UniformMutator> 
{
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////

	/**
	 * Default constructor
	 */
	
	public UniformMutatorTest(String name) 
	{
		super(UniformMutator.class, name);
	}

	/////////////////////////////////////////////////////////////////
	// ----------------------------- Overwriting IMutatorTest methods
	/////////////////////////////////////////////////////////////////


	@Override
	protected void createExpected() 
	{
		expected = new ArrayList<IIndividual>();
		expected.add(new BinArrayIndividual(new byte [] {0, 0, 0, 1, 1, 1}));		
	}

	@Override
	protected void initTool() 
	{
		tool.setLocusMutationProb(0.4);
	}
}
