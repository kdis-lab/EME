package net.sf.jclec.realarray.mut;

import net.sf.jclec.IConfigureTest;

public class MuhlehbeinMutatorConfigureTest extends IConfigureTest<MuhlenbeinMutator> 
{

	public MuhlehbeinMutatorConfigureTest(String name) 
	{
		super(MuhlenbeinMutator.class, name);
	}

	@Override
	protected void initObject1() 
	{
		object1.setLocusMutProb(0.6);
		object1.setMutationRange(0.1);
	}

	@Override
	protected void setConfigurationFilename()
	{
		configurationFilename = "src/test/resources/net/sf/jclec/realarray/mut/MuhlenbeinMutator.cfg";
	}
}
