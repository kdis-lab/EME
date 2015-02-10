package net.sf.jclec.binarray.mut;

import net.sf.jclec.IConfigureTest;

public class ConfigureSeveralLociMutatorTest extends IConfigureTest<SeveralLociMutator> 
{

	public ConfigureSeveralLociMutatorTest(String name) 
	{
		super(SeveralLociMutator.class, name);
	}

	@Override
	protected void initObject1() 
	{
		object1.setNumberOfMutationPoints(3);
	}

	@Override
	protected void setConfigurationFilename() 
	{
		configurationFilename = "src/test/resources/net/sf/jclec/binarray/mut/SeveralLociMutator.config.xml";
	}
}
