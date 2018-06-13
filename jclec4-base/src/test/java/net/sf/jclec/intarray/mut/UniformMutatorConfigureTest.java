package net.sf.jclec.intarray.mut;

import net.sf.jclec.IConfigureTest;

/**
 * Unit tests for UniformMutator.
 * 
 * @author Sebastian Ventura
 */

public class UniformMutatorConfigureTest extends IConfigureTest<UniformMutator> 
{
	public UniformMutatorConfigureTest(String name) 
	{
		super(UniformMutator.class, name);
	}

	/**
	 * {@inheritDoc} 
	 */

	@Override
	protected void initObject1() 
	{		
		object1.setLocusMutationProb(0.9);
	}

	@Override
	protected void setConfigurationFilename() 
	{
		configurationFilename = "src/test/resources/net/sf/jclec/intarray/mut/UniformMutator.cfg";		
	}
}
