package net.sf.jclec.fitness;

import net.sf.jclec.IFitness;

/**
 * Composite fitness.
 * 
 * @author Sebastian Ventura 
 */

public interface ICompositeFitness extends IFitness 
{
    /**
     * Access to all fitness components.
     * 
     * @return This fitness components
     */
    
    public ISimpleFitness[] getComponents();
    
    /**
     * Sets this fitness components.
     *
     * @param components New fitness components.
     */
    
    public void setComponents(ISimpleFitness[] components);
    
    /**
     * Returns a fitness component.
     *
     * @param cidx Index of desired index.
     *
     * @return cidx-throws fitness component
     */
    
    public ISimpleFitness getComponent(int cidx);
    
    /**
     * Sets a fitness component.
     *
     * @param cidx Index of desired component
     * @param cval Desired component value.
     */
    
    public void setComponent(int cidx, ISimpleFitness cval);
}
