package net.sf.jclec.base;

import net.sf.jclec.IConfigureTest;

import net.sf.jclec.binarray.mut.OneLocusMutator;

/**
 * Filtered mutator tests
 * 
 * @author Sebastian Ventura
 */

public class ConfigureFilteredMutatorTest extends IConfigureTest<FilteredMutator> 
{
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Default constructor
	 * 
	 * @param name
	 */
	
	public ConfigureFilteredMutatorTest(String name) 
	{
		super(FilteredMutator.class, name);
	}

	/////////////////////////////////////////////////////////////////
	// -------------------- Overwriting DecoratedMutatorTests methods
	/////////////////////////////////////////////////////////////////
	
	@Override
	protected void initObject1() 
	{
		// Set decorated mutator
		OneLocusMutator decorated = new OneLocusMutator();
		object1.setDecorated(decorated);		
		// Set mutation probability
		object1.setMutProb(0.35);
	}

	@Override
	protected void setConfigurationFilename() 
	{
		configurationFilename = "src/test/resources/net/sf/jclec/base/FilteredMutator.config.xml";		
	}
}
