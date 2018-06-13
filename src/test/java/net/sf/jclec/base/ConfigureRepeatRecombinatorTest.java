package net.sf.jclec.base;

import net.sf.jclec.IConfigureTest;

import net.sf.jclec.binarray.rec.OnePointCrossover;

/**
 * Filtered recombinator tests
 * 
 * @author Sebastian Ventura
 */

public class ConfigureRepeatRecombinatorTest extends IConfigureTest<RepeatRecombinator> 
{
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Default constructor
	 * 
	 * @param name
	 */
	
	public ConfigureRepeatRecombinatorTest(String name) 
	{
		super(RepeatRecombinator.class, name);
	}

	/////////////////////////////////////////////////////////////////
	// -------------------- Overwriting DecoratedMutatorTests methods
	/////////////////////////////////////////////////////////////////
	
	@Override
	protected void initObject1() 
	{
		// Set decorated
		OnePointCrossover decorated = new OnePointCrossover();
		object1.setDecorated(decorated);
		// Set recombination probability
		object1.setNumberOfRepetitions(2);
	}

	@Override
	protected void setConfigurationFilename() 
	{
		configurationFilename = "src/test/resources/net/sf/jclec/base/RepeatRecombinator.config.xml";
	}
}
