package net.sf.jclec.realarray;

import net.sf.jclec.IConfigure;

import org.apache.commons.configuration.Configuration;

/**
 * Uniform crossover.
 * 
 * @author Sebastian Ventura
 */

@SuppressWarnings("serial")
public abstract class UniformCrossover extends RealArrayRecombinator implements IConfigure 
{
	/////////////////////////////////////////////////////////////////
	// --------------------------------------------------- Properties
	/////////////////////////////////////////////////////////////////

	/** Locus crossover probability */
	
	protected double locusRecProb; 
	
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////

	/**
	 * Empty constructor.
	 */
	
	public UniformCrossover() 
	{
		super();
	}

	/////////////////////////////////////////////////////////////////
	// ----------------------------------------------- Public methods
	/////////////////////////////////////////////////////////////////

	// Setting and getting properties

	/**
	 * Access to locus crossover probability.
	 * 
	 * @return Locus crossover probability
	 */
	
	public double getLocusRecProb() 
	{
		return locusRecProb;
	}

	/**
	 * Set locus crossover probability.
	 *  
	 * @param locusRecProb New value of locus crossover probability
	 */
	
	public void setLocusRecProb(double locusRecProb) 
	{
		this.locusRecProb = locusRecProb;
	}
	
	// IConfigure interface

	/**
	 * 
	 */
	
	public void configure(Configuration settings) 
	{
		// Locus recombination probability
		double locusRecProb = settings.getDouble("[@locus-rec-prob]", defaultLocusRecProb());
		setLocusRecProb(locusRecProb);
	}

	/////////////////////////////////////////////////////////////////
	// -------------------------------------------- Protected methods
	/////////////////////////////////////////////////////////////////

	/**
	 * Get default value for this parameter.
	 */
	
	protected abstract double defaultLocusRecProb();	
}
