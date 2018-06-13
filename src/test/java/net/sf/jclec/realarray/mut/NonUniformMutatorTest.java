package net.sf.jclec.realarray.mut;

import java.util.ArrayList;

import net.sf.jclec.IIndividual;
import net.sf.jclec.realarray.RealArrayIndividual;
import net.sf.jclec.realarray.RealArrayMutatorTest;

public class NonUniformMutatorTest extends RealArrayMutatorTest<NonUniformMutator> 
{

	public NonUniformMutatorTest(String name) 
	{
		super(NonUniformMutator.class, name);
	}

	@Override
	protected void createExpected() 
	{
		expected = new ArrayList<IIndividual> (); 
		expected.add(new RealArrayIndividual(new double [] {-4.5, -3.9, 3.8944548975329436, 4.5}));
	}

	@Override
	protected void initTool() 
	{
		tool.setLocusMutProb(0.5);	
		tool.setB(5.0);
		tool.setGmax(100);
	}
}
