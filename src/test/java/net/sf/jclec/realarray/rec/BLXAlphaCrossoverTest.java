package net.sf.jclec.realarray.rec;

import java.util.ArrayList;

import net.sf.jclec.IIndividual;
import net.sf.jclec.realarray.RealArrayIndividual;
import net.sf.jclec.realarray.RealArrayRecombinatorTest;

public class BLXAlphaCrossoverTest extends RealArrayRecombinatorTest<BLXAlphaCrossover> 
{
	public BLXAlphaCrossoverTest(String name) 
	{
		super(BLXAlphaCrossover.class, name);
	}

	@Override
	protected void createExpected() {
		expected = new ArrayList<IIndividual> ();
		expected.add(new RealArrayIndividual(new double [] {-4.5, -3.9,1.4882250541494937, 4.461149524471197}));
	}

	@Override
	protected void initTool() 
	{
		tool.setLocusRecProb(0.6);
		tool.setAlpha(0.5);
	}
}
