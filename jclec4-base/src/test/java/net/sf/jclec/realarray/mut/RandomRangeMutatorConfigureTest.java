package net.sf.jclec.realarray.mut;

import net.sf.jclec.IConfigureTest;

public class RandomRangeMutatorConfigureTest extends IConfigureTest<RandomRangeMutator> 
{
	public RandomRangeMutatorConfigureTest(String name) 
	{
		super(RandomRangeMutator.class, name);
	}

	@Override
	protected void initObject1() 
	{
		object1.setLocusMutProb(0.6);
		object1.setInterval(2.0);
	}

	@Override
	protected void setConfigurationFilename() 
	{
		configurationFilename = "src/test/resources/net/sf/jclec/realarray/mut/RandomRangeMutator.cfg";	
	}
}
