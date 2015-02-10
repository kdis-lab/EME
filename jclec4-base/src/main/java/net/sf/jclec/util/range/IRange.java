package net.sf.jclec.util.range;


import net.sf.jclec.JCLEC;
import net.sf.jclec.util.random.IRandGen;


/**
 * Real values range
 *
 * @author Sebastian Ventura Soto
 */

public interface IRange extends JCLEC 
{
	/**
	 * Returns true if value belongs to this range.
	 *
	 * @param value Value that will be tested
	 *
	 * @return true if this value...
	 */
	
	public boolean contains(double value);

	/**
	 * Effective range width. 
	 * 
	 * In intervals, this  value is the  difference between  effective
	 * extrema (right minus left). In the case of an disjoint intervals
	 * set, is the sum of effective intervals widths.  
	 * 
	 * This value is used in ...
	 * 
	 * @return Effective width
	 */
	
	public double efWidth();
	
	/**
	 * Returns a random value contained in this range.
	 *
	 * @param randgen Random generator used.
	 *
	 * @return A random value ... 
	 */
	
	public double getRandom(IRandGen randgen);

	/**
	 * Returns the nearest value, belonging to this range
	 *
	 *
	 * @param value Value ...
	 *
	 * @return Nearest value to "value"
	 */
	
	public double nearestOf(double value);

	/**
	 * Takes a velue in the range [0.0, 1.0] and returns a value "v",
	 * that belongs to the range and ...
	 *
	 * @param value Value ...
	 *
	 * @return Scaled value
	 */
	
	public double scale(double value);

	/**
	 * Receives a value belonging to the interval...
	 *  
	 * @param value Value...
	 *
	 * @return Unscaled value
	 */
	
	public double unscale(double value);
}
