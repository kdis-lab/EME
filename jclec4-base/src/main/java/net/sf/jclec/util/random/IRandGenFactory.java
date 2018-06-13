package net.sf.jclec.util.random;

import net.sf.jclec.JCLEC;

/**
 * Random generator factory.
 * 
 * @author Sebastian Ventura
 */

public interface IRandGenFactory extends JCLEC 
{
	/**
	 * Factory method.
	 * 
	 * @return A new instance of a random generator
	 */
	
	public IRandGen createRandGen();
}
