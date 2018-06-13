package net.sf.jclec.intarray.rec;

import net.sf.jclec.IConfigureTest;

public class UniformCrossoverConfigureTest extends IConfigureTest<UniformCrossover> 
{
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////

	/**
	 * Default constructor
	 */
	
	public UniformCrossoverConfigureTest(String name) 
	{
		super(UniformCrossover.class, name);
	}

	/////////////////////////////////////////////////////////////////
	// ------------------------ Overwriting IRecombinatorTest methods
	/////////////////////////////////////////////////////////////////
	
	/**
	 * {@inheritDoc} 
	 */

	@Override
	protected void initObject1() 
	{
		object1.setLocusCrossoverProb(0.64);
	}

	@Override
	protected void setConfigurationFilename() 
	{
		configurationFilename = "src/test/resources/net/sf/jclec/intarray/rec/UniformCrossover.cfg";
	}

}
