package net.sf.jclec.realarray.mut;

import java.util.ArrayList;

import net.sf.jclec.IIndividual;
import net.sf.jclec.realarray.RealArrayIndividual;
import net.sf.jclec.realarray.RealArrayMutatorTest;

public class RandomRangeMutatorTest extends RealArrayMutatorTest<RandomRangeMutator> 
{
	public RandomRangeMutatorTest(String name) 
	{
		super(RandomRangeMutator.class, name);
	}

	@Override
	protected void createExpected() 
	{
		expected = new ArrayList<IIndividual> (); 
		expected.add(new RealArrayIndividual
				(new double [] {-4.5, -3.9, 2.158807775614606, 5.12}));
	}

	@Override
	protected void initTool() 
	{
		tool.setLocusMutProb(0.6);
		tool.setInterval(2.0);
	}
}
