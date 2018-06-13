package net.sf.jclec.fitness;

import java.util.Comparator;

import net.sf.jclec.IFitness;

/**
 * Compare two ICompositeFitness objects.
 * 
 * @author Sebastian Ventura
 */

public abstract class CompositeFitnessComparator implements Comparator<IFitness> 
{
	/////////////////////////////////////////////////////////////////
	// --------------------------------------------------- Properties
	/////////////////////////////////////////////////////////////////

	/** Component fitness comparators */
	
	protected Comparator<IFitness> [] componentComparators;
	
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////

	/**
	 * Empty constructor.
	 */
	
	public CompositeFitnessComparator() 
	{
		super();
	}

	/**
	 * Constructor that set component fitness comparators.
	 * 
	 * @param componentComparators Component fitness comparators
	 */
	
	public CompositeFitnessComparator(Comparator<IFitness> [] componentComparators) 
	{
		super();
	}

	/////////////////////////////////////////////////////////////////
	// ------------------------------- Setting and getting properties
	/////////////////////////////////////////////////////////////////

	/**
	 * Access to component fitness comparators
	 * 
	 * @return Component fitness comparators
	 */
	
	public Comparator<IFitness>[] getComponentComparators() 
	{
		return componentComparators;
	}

	/**
	 * Set component fitness comparators.
	 * 
	 * @param componentComparators Component fitness comparators
	 */
	
	public void setComponentComparators(Comparator<IFitness>[] componentComparators) 
	{
		this.componentComparators = componentComparators;
	}

	/////////////////////////////////////////////////////////////////
	// ---------------------------- Implementing Comparator interface
	/////////////////////////////////////////////////////////////////

	public int compare(IFitness fitness0, IFitness fitness1) 
	{
		ICompositeFitness cfitness0, cfitness1;
		try {
			cfitness0 = (ICompositeFitness) fitness0;
		}
		catch(ClassCastException e) {
			throw new IllegalArgumentException
				("IComposite fitness expected as first argument");
		}
		try {
			cfitness1 = (ICompositeFitness) fitness1;
		}
		catch(ClassCastException e) {
			throw new IllegalArgumentException
				("IComposite fitness expected as second argument");
		}
		return compare(cfitness0, cfitness1);
	}

	/**
	 * Compare two composite fitnesses.
	 */
	
	protected abstract int compare(ICompositeFitness cfitness0, ICompositeFitness cfitness1);
}
