package net.sf.jclec.realarray.rec;

import net.sf.jclec.IConfigureTest;

public class BLXAlphaCrossoverConfigureTest extends IConfigureTest<BLXAlphaCrossover> 
{
	public BLXAlphaCrossoverConfigureTest(String name) 
	{
		super(BLXAlphaCrossover.class, name);
	}

	@Override
	protected void initObject1() 
	{
		object1.setLocusRecProb(0.6);
		object1.setAlpha(0.5);
	}

	@Override
	protected void setConfigurationFilename() 
	{
		configurationFilename = "src/test/resources/net/sf/jclec/realarray/rec/BLXAlphaCrossover.cfg";
	}
}
