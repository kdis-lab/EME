package net.sf.jclec.intarray.mut;

import java.util.ArrayList;

import net.sf.jclec.IIndividual;
import net.sf.jclec.intarray.IntArrayIndividual;
import net.sf.jclec.intarray.IntArrayMutatorTest;

/**
 * Unit test for SeveralLociMutator.
 * 
 * @author Sebasitan Ventura
 */

public class SeveralLociMutatorTest extends IntArrayMutatorTest<SeveralLociMutator> 
{
	public SeveralLociMutatorTest(String name) 
	{
		super(SeveralLociMutator.class, name);
	}

	/**
	 * {@inheritDoc} 
	 */
	
	@Override
	protected void initTool() 
	{
		tool.setNumberOfMutationPoints(3);
	}

	@Override
	protected void createExpected() 
	{
		expected = new ArrayList<IIndividual>();
		expected.add(new IntArrayIndividual(new int [] {1, 6, 7, 8, 1, 1}));		
	}
}
