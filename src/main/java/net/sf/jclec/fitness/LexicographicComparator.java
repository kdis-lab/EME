package net.sf.jclec.fitness;

/**
 * Compare two ICompositeFitness objects according in a lexicographic manner, 
 * that is, ...
 *
 * @author Sebastian Ventura
 */

public class LexicographicComparator extends CompositeFitnessComparator
{
    /////////////////////////////////////////////////////////////////
    // ------------------------------------------------- Constructors
    /////////////////////////////////////////////////////////////////
    
    /**
     * Empty constructor
     */
    
    public LexicographicComparator()
    {
        super();
    }
    
    /////////////////////////////////////////////////////////////////
    // ----------------------- Overwriting AbstractComparator methods
    /////////////////////////////////////////////////////////////////
    
    /**
     * {@inheritDoc}
     */
    
    public int compare(ICompositeFitness fone, ICompositeFitness foth)
    {
        // Fitness components
        ISimpleFitness [] fonec = fone.getComponents();
        ISimpleFitness [] fothc = foth.getComponents();
        int nc = fonec.length;
        // Compares lexicographically
        for (int i=0; i<nc; i++) {
            int cmp = componentComparators[i].compare(fonec[i] , fothc[i]);
            if (cmp != 0)
                return cmp;
        }
        // All fitness components are equivalent
        return 0;
    }
}
