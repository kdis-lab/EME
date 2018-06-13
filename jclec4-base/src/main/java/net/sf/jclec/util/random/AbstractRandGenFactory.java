package net.sf.jclec.util.random;


import net.sf.jclec.IConfigure;

import org.apache.commons.configuration.Configuration;

/**
 * IRandGenFactory abstract implementation...
 *  
 * @author Sebastian Ventura
 */

@SuppressWarnings("serial")
public abstract class AbstractRandGenFactory implements IRandGenFactory, IConfigure 
{
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------- Internal variables
	/////////////////////////////////////////////////////////////////
	
	/** Seeds generator */
	
	protected SeedGenerator seedGenerator = new SeedGenerator();
	
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Empty (default) constructor
	 */
	
	public AbstractRandGenFactory() 
	{
		super();
	}

	/////////////////////////////////////////////////////////////////
	// ------------------------------- Setting and getting properties
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Access to actual generators seed.
	 * 
	 * @return Actual generators seed 
	 */
	
	public int getSeed()
	{
		return seedGenerator.getRow();
	}
	
	/**
	 * Sets actual generators seed.
	 * 
	 * @param seed New generators seed
	 */
	
	public void setSeed(int seed)
	{
		seedGenerator.setRow(seed);
	}

	/////////////////////////////////////////////////////////////////
	// ---------------------------- Implementing IConfigure interface
	/////////////////////////////////////////////////////////////////

	/**
	 * Configuration method.
	 * 
	 * Configuration parameters for this object are...
	 * 
	 * @param settings Configuration settings
	 */
	
	public void configure(Configuration settings) 
	{
		// Getting seed parameter
		int seed = settings.getInt("[@seed]", 1234567890);
		// Setting seed
		setSeed(seed);
	}
}
