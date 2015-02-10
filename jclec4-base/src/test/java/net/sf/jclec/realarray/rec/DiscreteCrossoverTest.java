package net.sf.jclec.realarray.rec;

import java.util.ArrayList;

import net.sf.jclec.IIndividual;
import net.sf.jclec.realarray.RealArrayIndividual;
import net.sf.jclec.realarray.RealArrayRecombinatorTest;

public class DiscreteCrossoverTest extends RealArrayRecombinatorTest<DiscreteCrossover> 
{

	public DiscreteCrossoverTest(String name) 
	{
		super(DiscreteCrossover.class, name);
	}

	@Override
	protected void createExpected() {
		expected = new ArrayList<IIndividual> ();
		expected.add(new RealArrayIndividual(new double [] {-4.5, -3.9, 0.8, 3.3}));
		expected.add(new RealArrayIndividual(new double [] {1.4, -2.6, 2.1, 4.5}));
	}

	@Override
	protected void initTool() 
	{
		tool.setLocusRecProb(0.6);
	}
}
