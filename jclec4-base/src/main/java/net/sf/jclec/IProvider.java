package net.sf.jclec;

import java.util.List;

/**
 * Individuals provider.
 * 
 * These objects provide  new individuals generating them at random or by other 
 * procedure. 
 * 
 * @author Sebastian Ventura
 */

public interface IProvider extends ITool
{
	/**
	 * Provision method.
	 * 
	 * @param numberOfIndividuals Number of individuals to provide
	 * 
	 * @return List of provided individuals
	 */
	
	public List<IIndividual> provide(int numberOfIndividuals);
}
