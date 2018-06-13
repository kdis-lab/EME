package net.sf.jclec;

/**
 * Distance between two individuals.
 * 
 * @author Sebastian Ventura
 * @since jclec4
 */
public interface IDistance 
{
	/**
	 * Distance method.
	 * 
	 * @param one   First individual to compare
	 * @param other Second individual to compare
	 * 
	 * @return Distance between individuals <code>one</code> and <code>other</code>
	 */
	
	public abstract double distance(IIndividual one, IIndividual other);
}
