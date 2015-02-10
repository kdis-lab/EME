package net.sf.jclec.realarray.mut;

import net.sf.jclec.IConfigureTest;

public class NonUniformMutatorConfigureTest extends IConfigureTest<NonUniformMutator> 
{

	public NonUniformMutatorConfigureTest(String name) 
	{
		super(NonUniformMutator.class, name);
	}

	@Override
	protected void initObject1() 
	{
		object1.setLocusMutProb(0.5);	
		object1.setB(5.0);
		object1.setGmax(100);
	}
	
	@Override
	protected void setConfigurationFilename()
	{
		configurationFilename = "src/test/resources/net/sf/jclec/realarray/mut/NonUniformMutator.cfg";		
	}
}
