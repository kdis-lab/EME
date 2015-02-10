package net.sf.jclec.realarray.mut;

import net.sf.jclec.IConfigureTest;

public class ModalContinuousMutatorConfigureTest extends IConfigureTest<ModalContinuousMutator> 
{

	public ModalContinuousMutatorConfigureTest(String name) 
	{
		super(ModalContinuousMutator.class, name);
	}

	@Override
	protected void initObject1() 
	{
		object1.setBm(2.0);
		object1.setLocusMutProb(0.6);
		object1.setMutationRange(0.1);
		object1.setMinimumRange(1E-5);
	}

	@Override
	protected void setConfigurationFilename() 
	{	
		configurationFilename = "src/test/resources/net/sf/jclec/realarray/mut/ModalContinuousMutator.cfg";
	}

}
