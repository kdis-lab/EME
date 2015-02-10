package net.sf.jclec.util.intset;

import net.sf.jclec.JCLEC;
import net.sf.jclec.util.random.IRandGen;

/**
 * Integer values range
 *
 * @author Sebastian Ventura
 */

public interface IIntegerSet extends JCLEC 
{
	/**
	 * Returns true if value belongs to this range.
	 *
	 * @param value Value that will be tested
	 *
	 * @return true if this value...
	 */
	
	public boolean contains(int value);

	/**
	 * Number of elements in this range.
	 * 
	 * @return Range size
	 */
	
	public int size();
	
	/**
	 * Returns a random value contained in this range.
	 *
	 * @param randgen Random generator used.
	 *
	 * @return A random value ... 
	 */
	
	public int getRandom(IRandGen randgen);
}
