package net.sf.jclec;

import org.apache.commons.configuration.Configuration;

/**
 * Configuration method.
 * 
 * @author Sebastian Ventura
 */

public interface IConfigure extends JCLEC
{
	/**
	 * Configuration method.
	 * 
	 * @param settings Configuration settings
	 */
	
	public void configure(Configuration settings);
}
