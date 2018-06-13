package net.sf.jclec.intarray.mut;

import net.sf.jclec.IConfigureTest;

/**
 * Unit test for SeveralLociMutator.
 * 
 * @author Sebasitan Ventura
 */

public class SeveralLociMutatorConfigureTest extends IConfigureTest<SeveralLociMutator> 
{
	public SeveralLociMutatorConfigureTest(String name) 
	{
		super(SeveralLociMutator.class, name);
	}

	/**
	 * {@inheritDoc} 
	 */
	
	@Override
	protected void initObject1() 
	{
		object1.setNumberOfMutationPoints(3);
	}

	@Override
	protected void setConfigurationFilename() 
	{
		configurationFilename = "src/test/resources/net/sf/jclec/intarray/mut/SeveralLociMutator.cfg";
	}
}
