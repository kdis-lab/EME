package net.sf.jclec.realarray.rec;

import java.util.ArrayList;

import net.sf.jclec.IIndividual;
import net.sf.jclec.realarray.RealArrayIndividual;
import net.sf.jclec.realarray.RealArrayRecombinatorTest;

public class ArithmeticCrossoverTest extends RealArrayRecombinatorTest<ArithmeticCrossover> 
{

	public ArithmeticCrossoverTest(String name) 
	{
		super(ArithmeticCrossover.class, name);
	}

	@Override
	protected void createExpected() {
		expected = new ArrayList<IIndividual> ();
		expected.add(new RealArrayIndividual(new double [] {-4.5, -3.9,1.84, 4.5}));
		expected.add(new RealArrayIndividual(new double [] {1.4, -2.6, 1.06, 3.3}));
	}

	@Override
	protected void initTool() 
	{
		tool.setLocusRecProb(0.5);
		tool.setLambda(0.8);
	}
}
