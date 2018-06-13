package net.sf.jclec.realarray.rec;

import java.util.ArrayList;

import net.sf.jclec.IIndividual;
import net.sf.jclec.realarray.RealArrayIndividual;
import net.sf.jclec.realarray.RealArrayRecombinatorTest;

public class WrightCrossoverTest extends RealArrayRecombinatorTest<WrightCrossover> 
{

	public WrightCrossoverTest(String name) 
	{
		super(WrightCrossover.class, name);
	}

	@Override
	protected void createExpected() {
		expected = new ArrayList<IIndividual> ();
		expected.add(new RealArrayIndividual(new double [] {-4.5, -3.9, 2.769112527074747, 5.12}));	
	}

	@Override
	protected void initTool() 
	{
		tool.setLocusRecProb(0.6);
	}
}
