package net.sf.jclec.fitness;

/**
 * Compare two ICompositeFitness objects according the Pareto dominance criterion.
 *
 * @author Sebastian Ventura
 */

public class ParetoComparator extends CompositeFitnessComparator
{
    /////////////////////////////////////////////////////////////////
    // ------------------------------------------------- Constructors
    /////////////////////////////////////////////////////////////////
    
    /**
     * Empty constructor
     */
    
    public ParetoComparator() 
    {
        super();
    }
    
    /////////////////////////////////////////////////////////////////
    // ----------------------- Overwriting AbstractComparator methods
    /////////////////////////////////////////////////////////////////
    
    /**
     * {@inheritDoc}
     */
    
    public int compare(ICompositeFitness fone, ICompositeFitness foth) {
        // Fitness components
        ISimpleFitness [] fonec = fone.getComponents();
        ISimpleFitness [] fothc = foth.getComponents();
        // Number of components
        int nc = fonec.length;
        // Stores result
        int result = 0;
        // Main cycle
        for (int i=0; i<nc; i++) {
        	int cmp = componentComparators[i].compare(fonec[i], fothc[i]);
        	if (result == 0) {
        		result = cmp;
        	}
        	else {
        		if (cmp != 0 & cmp != result) return 0;
        	}	
        }
        // Return result
        return result;
    }
}
