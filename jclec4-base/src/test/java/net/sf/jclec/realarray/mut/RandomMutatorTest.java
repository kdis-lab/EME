package net.sf.jclec.realarray.mut;

import java.util.ArrayList;

import net.sf.jclec.IIndividual;
import net.sf.jclec.realarray.RealArrayIndividual;
import net.sf.jclec.realarray.RealArrayMutatorTest;

public class RandomMutatorTest extends RealArrayMutatorTest<RandomMutator> 
{
	public RandomMutatorTest(String name) 
	{
		super(RandomMutator.class, name);
	}

	@Override
	protected void createExpected() 
	{
		expected = new ArrayList<IIndividual> (); 
		expected.add(new RealArrayIndividual
				(new double [] {-4.5, -3.9, 0.15054790557339093, 2.394237971077108}));
	}

	@Override
	protected void initTool() 
	{
		tool.setLocusMutProb(0.6);
	}
}
