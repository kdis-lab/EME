package net.sf.jclec;

import java.util.List;

/**
 * Provide new individuals by mutation.
 * 
 * @author Sebastian Ventura
 */

public interface IMutator extends ITool
{
	/**
	 * Mutation method.
	 * 
	 * @param parents Individuals to mutate
	 * 
	 * @return Mutation result
	 */
	
	public List<IIndividual> mutate(List<IIndividual> parents);
}
