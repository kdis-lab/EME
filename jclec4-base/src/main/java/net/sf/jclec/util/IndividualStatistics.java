package net.sf.jclec.util;

import java.util.List;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Collections;

import net.sf.jclec.IFitness;
import net.sf.jclec.IIndividual;

import net.sf.jclec.fitness.IValueFitness;

/**
 * This class calculates several statistics over an individual set.
 *  
 * @author Sebastian Ventura
 */

public class IndividualStatistics 
{
	/**
	 * Best individual in a set
	 * 
	 * @param set Individuals set
	 * @param comparator Fitness comparator
	 */
	
	public static final IIndividual bestIndividual(final List<IIndividual> set, final Comparator<IFitness> comparator)
	{
		Comparator<IIndividual> indComparator = new Comparator<IIndividual> () {
			public int compare(IIndividual arg0, IIndividual arg1) 
			{
				return comparator.compare(arg0.getFitness(), arg1.getFitness()); 
			}
		};
		// Set size
		int setSize = set.size();
		// Best individual 
		IIndividual best = set.get(0);
		// Set best individual
		for (int i=1; i<setSize; i++) {
			IIndividual other = set.get(i); 
	        if (indComparator.compare(best, other) == -1) {
	          best = other;
	        }
		}
		return best;	
	}

	/**
	 * Worst individual in a set
	 * 
	 * @param set Individuals set
	 * @param comparator Fitness comparator
	 */
	
	public static final IIndividual worstIndividual(final List<IIndividual> set, final Comparator<IFitness> comparator)
	{
		Comparator<IIndividual> indComparator = new Comparator<IIndividual> () {
			public int compare(IIndividual arg0, IIndividual arg1) 
			{
				return comparator.compare(arg0.getFitness(), arg1.getFitness()); 
			}
		};
		// Set size
		int setSize = set.size();
		// Worst individual 
		IIndividual worst = set.get(0);
		// Set worst individual
		for (int i=1; i<setSize; i++) {
			IIndividual other = set.get(i); 
	        if (indComparator.compare(worst, other) == 1) {
	        	worst = other;
	        }
		}
		return worst;	
	}
	
	/**
	 * Median individual in a set
	 * 
	 * @param set Individuals set
	 * @param comparator Fitness comparator
	 */
	
	public static final IIndividual medianIndividual(final List<IIndividual> set, final Comparator<IFitness> comparator)
	{
		Comparator<IIndividual> indComparator = new Comparator<IIndividual> () {
			public int compare(IIndividual arg0, IIndividual arg1) 
			{
				return comparator.compare(arg1.getFitness(), arg0.getFitness()); 
			}
		};
		// Set size
		int setSize = set.size();
		// Set median individual
		LinkedList<IIndividual> aux = new LinkedList<IIndividual>(set);
		Collections.sort(aux, indComparator);
		// Median individual
		return set.get(setSize/2);
	}
	
	/**
	 * Average fitness of the individuals in a set
	 * 
	 * @param set Individuals set
	 */
	
    public static final double averageFitness(List<IIndividual> set) 
    {
    	double result = 0.0;
    	int nOfInds = set.size();
    	for (IIndividual ind : set) {
    		IFitness fitness = ind.getFitness(); 
    		try {
    			result += ((IValueFitness) fitness).getValue();
    		}
    		catch (ClassCastException e) {
    			throw new IllegalArgumentException("");
    		}
    	}
    	// Fitness average
    	result /= nOfInds;
    	return result;
    }
    
	/**
	 * Fitness variance of the individuals in a set
	 * 
	 * @param set Individuals set
	 */
	
    public static final double fitnessVariance(List<IIndividual> set) 
    {
    	double avg = 0.0, var = 0.0;
    	int nOfInds = set.size();
    	for (IIndividual ind : set) {
    		IFitness fitness = ind.getFitness(); 
    		try {
    			double tmp = ((IValueFitness) fitness).getValue();
    			avg += tmp;
    			var += tmp*tmp;
    		}
    		catch (ClassCastException e) {
    			throw new IllegalArgumentException("");
    		}
    	}
    	// Fitness average
    	avg /= nOfInds;
    	// Variance of fitness
    	var  = (var/nOfInds) - avg*avg;
    	return var;
    }

    /**
	 * Average fitness and fitness variance in a set
	 * 
	 * @param set Individuals set
	 */
	
    public static final double [] averageFitnessAndFitnessVariance(List<IIndividual> set) 
    {
    	double result [] = {0.0, 0.0};
    	int nOfInds = set.size();
    	for (IIndividual ind : set) {
    		IFitness fitness = ind.getFitness(); 
    		try {
    			double tmp = ((IValueFitness) fitness).getValue();
    			result[0] += tmp;
    			result[1] += tmp*tmp;
    		}
    		catch (ClassCastException e) {
    			throw new IllegalArgumentException("");
    		}
    	}
    	// Fitness average
    	result[0] /= nOfInds;
    	// Variance of fitness
    	result[1] = (result[1]/nOfInds) - result[0]*result[0];
    	return result;
    }
}
