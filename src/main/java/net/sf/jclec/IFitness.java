package net.sf.jclec;

/**
 * Individual fitness.
 * 
 * @author Sebastian Ventura
 * 
 * @see net.sf.jclec.IEvaluator
 */

public interface IFitness extends JCLEC
{
	/**
	 * Informs if this is acceptable in terms of...
	 * 
	 * @return <code>true</code> if this fitness is acceptable, 
	 * 		   <code>false</code> otherwise
	 */
	
	public boolean isAcceptable();
	
	/**
	 * Copy method. 
	 * 
	 * @return a copy of this fitness.
	 */
	
	public IFitness copy();	
}
