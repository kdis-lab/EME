package net.sf.jclec.realarray.rec;

import net.sf.jclec.IConfigureTest;

public class ArithmeticCrossoverConfigureTest extends IConfigureTest<ArithmeticCrossover> 
{

	public ArithmeticCrossoverConfigureTest(String name) 
	{
		super(ArithmeticCrossover.class, name);
	}

	@Override
	protected void initObject1() 
	{
		object1.setLocusRecProb(0.5);
		object1.setLambda(0.8);
	}

	@Override
	protected void setConfigurationFilename() 
	{
		configurationFilename = "src/test/resources/net/sf/jclec/realarray/rec/ArithmeticCrossover.cfg";
	}
}
