package net.sf.jclec;

import java.util.List;

import net.sf.jclec.IEvaluator;
import net.sf.jclec.IIndividual;
import net.sf.jclec.ISpecies;

/**
 * Generic population.
 *  
 * @author Sebastian Ventura
 */

public interface IPopulation extends ISystem
{
	// System species
	
	/**
	 * Access to system species.
	 * 
	 * @return System species
	 */
	
	public ISpecies getSpecies();
	
	// System evaluator
	
	/**
	 * Access to system evaluator.
	 */
	
	public IEvaluator getEvaluator();
	
	// Generation counter
	
	/**
	 * Access to actual generation.
	 *  
	 * @return Actual generation
	 */
	
	public int getGeneration();
		
	// Population individuals
	
	/**
	 * Access to population inhabitants.
	 * 
	 * @return Population inhabitants
	 */
	
	public List<IIndividual> getInhabitants();
}
