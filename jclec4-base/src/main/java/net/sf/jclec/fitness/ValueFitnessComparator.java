package net.sf.jclec.fitness;

import java.util.Comparator;

import net.sf.jclec.IFitness;

/**
 * Compare two IValueFitness objects according their associated fitness value.
 * 
 * @author Sebastian Ventura
 */

public class ValueFitnessComparator implements Comparator<IFitness> 
{
	/////////////////////////////////////////////////////////////////
	// --------------------------------------------------- Properties
	/////////////////////////////////////////////////////////////////
	
	/** Use inverse order? */
	
	protected boolean inverse = false;
	
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Empty constructor.
	 */
	
	public ValueFitnessComparator() 
	{
		super();
	}

	/**
	 * Default constructor. Sets the ascendent flag.
	 */
	
	public ValueFitnessComparator(boolean inverse) 
	{
		super();
		setInverse(inverse);
	}

	/////////////////////////////////////////////////////////////////
	// ------------------------------- Setting and getting properties
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Access to 'inverse' flag
	 * 
	 * @return Inverse flag value
	 */
	
	public boolean isInverse()
	{
		return inverse;
	}

	/**
	 * Sets the invert flag
	 *  
	 * @param inverse Inver flag
	 */
	
	public void setInverse(boolean inverse) 
	{
		this.inverse = inverse;
	}

	/////////////////////////////////////////////////////////////////
	// ------------------ Implementing Comparator<IFitness> interface
	/////////////////////////////////////////////////////////////////

	/**
	 * {@inheritDoc}
	 */
	
	public int compare(IFitness fitness1, IFitness fitness2) 
	{
		double f1value, f2value;  
        try {
        	f1value = ((IValueFitness) fitness1).getValue();
        }
        catch(ClassCastException e) {
            throw new IllegalArgumentException
            	("IValueFitness expected in fitness1");
        }
        try {
            f2value = ((IValueFitness) fitness2).getValue();
        }
        catch(ClassCastException e) {
            throw new IllegalArgumentException
            	("IValueFitness expected in fitness2");
        }
        if (f1value > f2value) {
            return inverse ? -1 : 1;
        }
        else if(f1value < f2value) {
            return inverse ? 1 : -1;
        }
        else {
            return 0;
        }
	}
}
