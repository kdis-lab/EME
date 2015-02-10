package net.sf.jclec.realarray.rec;

import java.util.ArrayList;

import net.sf.jclec.IIndividual;
import net.sf.jclec.realarray.RealArrayIndividual;
import net.sf.jclec.realarray.RealArrayRecombinatorTest;

public class LinearCrossoverTest extends RealArrayRecombinatorTest<LinearCrossover> 
{

	public LinearCrossoverTest(String name) 
	{
		super(LinearCrossover.class, name);
	}

	@Override
	protected void createExpected() {
		expected = new ArrayList<IIndividual> ();
		expected.add(new RealArrayIndividual(new double [] {-4.5, -3.9, 1.4500000000000002, 3.9}));
		expected.add(new RealArrayIndividual(new double [] {-4.5, -3.9, 2.7500000000000004 , 5.1}));
		expected.add(new RealArrayIndividual(new double [] {1.4, -2.6, 0.15000000000000013, 2.6999999999999993}));
	}

	@Override
	protected void initTool() 
	{
		tool.setLocusRecProb(0.6);
	}
}
