package net.sf.jclec.intarray.mut;

import java.util.ArrayList;

import net.sf.jclec.IIndividual;
import net.sf.jclec.intarray.IntArrayIndividual;
import net.sf.jclec.intarray.IntArrayMutatorTest;

/**
 * Unit tests for UniformMutator.
 * 
 * @author Sebastian Ventura
 */

public class UniformMutatorTest extends IntArrayMutatorTest<UniformMutator> 
{
	public UniformMutatorTest(String name) 
	{
		super(UniformMutator.class, name);
	}

	/**
	 * {@inheritDoc} 
	 */

	@Override
	protected void initTool() 
	{		
		tool.setLocusMutationProb(0.9);
	}

	@Override
	protected void createExpected() 
	{
		expected = new ArrayList<IIndividual>();
		expected.add(new IntArrayIndividual(new int [] {3, 5, 7, 9, 2, 4}));		
	}
}
