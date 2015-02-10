package net.sf.jclec.binarray.mut;

import java.util.ArrayList;

import net.sf.jclec.IIndividual;

import net.sf.jclec.binarray.BinArrayIndividual;
import net.sf.jclec.binarray.BinArrayMutatorTest;

/**
 * Unit test for SeveralLociMutator.
 * 
 * @author Sebastian Ventura
 */

public class SeveralLociMutatorTest extends BinArrayMutatorTest<SeveralLociMutator> 
{
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////

	/**
	 * Default constructor
	 */
	
	public SeveralLociMutatorTest(String name) 
	{
		super(SeveralLociMutator.class, name);
	}

	/////////////////////////////////////////////////////////////////
	// ----------------------------- Overwriting IMutatorTest methods
	/////////////////////////////////////////////////////////////////
	
	@Override
	protected void createExpected() 
	{
		expected = new ArrayList<IIndividual>();
		expected.add(new BinArrayIndividual(new byte [] {1, 0, 0, 0, 1, 1}));		
	}

	@Override
	protected void initTool() 
	{
		tool.setNumberOfMutationPoints(3);	
	}
}
