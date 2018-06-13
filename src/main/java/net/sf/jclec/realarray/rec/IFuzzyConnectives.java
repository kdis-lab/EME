package net.sf.jclec.realarray.rec;

import net.sf.jclec.IConfigure;

/**
 * Fuzzy connectives 
 * 
 * @author Alberto Lamarca-Rosales 
 * @author Sebastian Ventura
 */

public interface IFuzzyConnectives extends IConfigure
{
	/////////////////////////////////////////////////////////////////
	// ------------------------------- Setting and getting properties
	/////////////////////////////////////////////////////////////////
	
	/** 
	 * @return Actual alpha value
	 */
	
	public double getAlpha();
	
	/** 
	 * Sets the alpha value, used in ...
	 * 
	 * @param alpha New alpha value
	 */

	public void setAlpha(double alpha);
	
	/////////////////////////////////////////////////////////////////
	// ----------------------------------------------- Public methods
	/////////////////////////////////////////////////////////////////
	
	/**
	 * m function
	 */

	public abstract double m(double x, double y);
	
	/**
	 * s function
	 */

	public abstract double s(double x, double y);
	
	/**
	 * f function
	 */

	public abstract double f(double x, double y);
	
	/**
	 * l function
	 */

	public abstract double l(double x, double y);
}
