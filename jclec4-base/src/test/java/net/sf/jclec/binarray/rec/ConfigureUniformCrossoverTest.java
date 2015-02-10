package net.sf.jclec.binarray.rec;

import net.sf.jclec.IConfigureTest;

public class ConfigureUniformCrossoverTest extends IConfigureTest<UniformCrossover> 
{
	public ConfigureUniformCrossoverTest(String name) 
	{
		super(UniformCrossover.class, name);
	}

	@Override
	protected void initObject1() 
	{
		object1.setLocusCrossoverProb(0.64);
	}

	@Override
	protected void setConfigurationFilename() 
	{
		configurationFilename = "src/test/resources/net/sf/jclec/binarray/rec/UniformCrossover.config.xml";
	}
}
