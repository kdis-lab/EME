package net.sf.jclec.realarray.mut;

import net.sf.jclec.IConfigureTest;

public class RandomMutatorConfigureTest extends IConfigureTest<RandomMutator> 
{
	public RandomMutatorConfigureTest(String name) 
	{
		super(RandomMutator.class, name);
	}

	@Override
	protected void initObject1() 
	{
		object1.setLocusMutProb(0.6);
	}

	@Override
	protected void setConfigurationFilename() 
	{
		configurationFilename = "src/test/resources/net/sf/jclec/realarray/mut/RandomMutator.cfg";	
	}
}
