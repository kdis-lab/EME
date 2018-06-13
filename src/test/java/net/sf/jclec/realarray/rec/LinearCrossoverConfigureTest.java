package net.sf.jclec.realarray.rec;

import net.sf.jclec.IConfigureTest;

public class LinearCrossoverConfigureTest extends IConfigureTest<LinearCrossover> 
{

	public LinearCrossoverConfigureTest(String name) 
	{
		super(LinearCrossover.class, name);
	}

	@Override
	protected void initObject1() 
	{
		object1.setLocusRecProb(0.6);
	}

	@Override
	protected void setConfigurationFilename() 
	{
		configurationFilename = "src/test/resources/net/sf/jclec/realarray/rec/LinearCrossover.cfg";
	}

}
