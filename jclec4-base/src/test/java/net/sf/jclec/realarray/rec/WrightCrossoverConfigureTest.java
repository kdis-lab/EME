package net.sf.jclec.realarray.rec;

import net.sf.jclec.IConfigureTest;

public class WrightCrossoverConfigureTest extends IConfigureTest<WrightCrossover> 
{

	public WrightCrossoverConfigureTest(String name) 
	{
		super(WrightCrossover.class, name);
	}

	@Override
	protected void initObject1() 
	{
		object1.setLocusRecProb(0.6);
	}

	@Override
	protected void setConfigurationFilename() 
	{
		configurationFilename = "src/test/resources/net/sf/jclec/realarray/rec/WrightCrossover.cfg";
	}
}
