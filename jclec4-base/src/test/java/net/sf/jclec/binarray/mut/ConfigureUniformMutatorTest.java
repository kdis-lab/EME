package net.sf.jclec.binarray.mut;

import net.sf.jclec.IConfigureTest;

public class ConfigureUniformMutatorTest extends IConfigureTest<UniformMutator> 
{

	public ConfigureUniformMutatorTest(String name) 
	{
		super(UniformMutator.class, name);
	}

	@Override
	protected void initObject1() 
	{
		object1.setLocusMutationProb(0.9);
	}

	@Override
	protected void setConfigurationFilename() 
	{
		configurationFilename = "src/test/resources/net/sf/jclec/binarray/mut/UniformMutator.config.xml";
	}
}
