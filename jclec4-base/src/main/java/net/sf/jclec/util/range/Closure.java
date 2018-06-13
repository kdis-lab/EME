package net.sf.jclec.util.range;

/**
 * Interval closure.
 * 
 * @author Sebastian Ventura
 */

public enum Closure 
{
	/** Interval left and right open */
	
	OpenOpen,
	
	/** Interval left closed and right open */

	OpenClosed,
	
	/** Interval left open and right closed */
	
	ClosedOpen,
	
	/** Interval left and right closed */
	
	ClosedClosed
}
