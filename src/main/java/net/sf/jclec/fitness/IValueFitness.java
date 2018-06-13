package net.sf.jclec.fitness;

import net.sf.jclec.IFitness;

/**
 * Fitness that contains a double value. 
 * 
 * This kind of fitness is used in roulette-based selection.
 * 
 * @author Sebastian Ventura
 */

public interface IValueFitness extends IFitness
{
    /**
     * @return Value for this fitness.
     *
     * @throws UnsupportedOperationException The method hasn't been implemented.
     */
    
    public double getValue();
	
    /**
     * Sets fitness value.
     * 
     * @param value New fitness value
     */
    
    public void setValue(double value);
}
