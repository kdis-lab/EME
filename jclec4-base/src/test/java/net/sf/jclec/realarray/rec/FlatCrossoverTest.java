package net.sf.jclec.realarray.rec;

import java.util.ArrayList;

import net.sf.jclec.IIndividual;
import net.sf.jclec.realarray.RealArrayIndividual;
import net.sf.jclec.realarray.RealArrayRecombinatorTest;

public class FlatCrossoverTest extends RealArrayRecombinatorTest<FlatCrossover> 
{

	public FlatCrossoverTest(String name) 
	{
		super(FlatCrossover.class, name);
	}

	@Override
	protected void createExpected() {
		expected = new ArrayList<IIndividual> ();
		expected.add(new RealArrayIndividual
				(new double [] {-4.5, -3.9,1.469112527074747, 4.180574762235598}));	
	}

	@Override
	protected void initTool() 
	{
		tool.setLocusRecProb(0.6);
	}
}
