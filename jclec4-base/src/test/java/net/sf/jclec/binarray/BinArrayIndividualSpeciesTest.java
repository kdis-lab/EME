package net.sf.jclec.binarray;

import net.sf.jclec.IConfigureTest;

/**
 * Marshall and unmarshal tests for BinArrayIndividualSpecies class.
 * 
 * @author Sebastian Ventura
 */

public class BinArrayIndividualSpeciesTest extends IConfigureTest<BinArrayIndividualSpecies> 
{
	public BinArrayIndividualSpeciesTest(String name) 
	{
		super(BinArrayIndividualSpecies.class, name);
	}

	@Override
	protected void setConfigurationFilename() 
	{
		configurationFilename = "src/test/resources/net/sf/jclec/binarray/BinArrayIndividualSpecies.config.xml";
	}

	@Override
	protected void initObject1() 
	{
		object1.genotypeSchema = new byte [] {-1, -1, -1, -1, -1, -1, -1, -1, -1};
	}
}
