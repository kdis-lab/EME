package net.sf.jclec;

import java.util.List;

/**
 * Individuals selector.
 *  
 * @author Sebastian Ventura
 */

public interface ISelector extends ITool 
{
	/**
	 * Selection method.
	 * 
	 * @param source Source set
	 * 
	 * @return Selected individuals
	 */
	
	public List<IIndividual> select(List<IIndividual> source);

	/**
	 * Alternative selection method.
	 * 
	 * @param source Source set
	 * @param nofsel Number of individuals to select
	 * 
	 * @return Selected individuals
	 */
	
	public List<IIndividual> select(List<IIndividual> source, int nofsel);

	/**
	 * Alternative selection method.
	 * 
	 * @param source Source set
	 * @param nofsel Number of individuals to select
	 * @param repeat Is selection of repeated individuals allowed?
	 * 
	 * @return Selected individuals
	 */
	
	public List<IIndividual> select(List<IIndividual> source, int nofsel, boolean repeat);
	
}
