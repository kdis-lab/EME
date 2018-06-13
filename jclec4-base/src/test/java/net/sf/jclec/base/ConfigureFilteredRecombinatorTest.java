package net.sf.jclec.base;

import net.sf.jclec.IConfigureTest;

import net.sf.jclec.binarray.rec.OnePointCrossover;

/**
 * Filtered recombinator tests
 * 
 * @author Sebastian Ventura
 */

public class ConfigureFilteredRecombinatorTest extends IConfigureTest<FilteredRecombinator> 
{
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Default constructor
	 * 
	 * @param name
	 */
	
	public ConfigureFilteredRecombinatorTest(String name) 
	{
		super(FilteredRecombinator.class, name);
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
		object1.setRecProb(0.75);
	}

	@Override
	protected void setConfigurationFilename() 
	{
		configurationFilename = "src/test/resources/net/sf/jclec/base/FilteredRecombinator.config.xml";
	}
}
