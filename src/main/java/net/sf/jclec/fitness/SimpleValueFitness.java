package net.sf.jclec.fitness;

import net.sf.jclec.IFitness;

import net.sf.jclec.base.AbstractFitness;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Simple fitness that contains a double value.
 *
 * @author Sebastian Ventura
 */

public class SimpleValueFitness extends AbstractFitness implements ISimpleFitness, IValueFitness
{
    /////////////////////////////////////////////////////////////////
    // --------------------------------------------------- Parameters
    /////////////////////////////////////////////////////////////////

	/** Comment for <code>serialVersionUID</code> */
	
	private static final long serialVersionUID = 3257285829314556465L;
	
    /////////////////////////////////////////////////////////////////
    // --------------------------------------------------- Attributes
    /////////////////////////////////////////////////////////////////
    
	/** Fitness value */
    
    protected double value;
    
    /////////////////////////////////////////////////////////////////
    // ------------------------------------------------- Constructors
    /////////////////////////////////////////////////////////////////
    
    /**
     * Empty constructor.
     */
    
    public SimpleValueFitness()
    {
        super();
    }
    
    /**
     * Parametrized constructor.
     * 
     * Create a SimpleValueFitness intance, and assigns its value.
     * 
     * @param value Fitness value.
     */
    
    public SimpleValueFitness(double value) 
    {
        super();
        this.value = value;
    }
    
    /////////////////////////////////////////////////////////////////
    // ----------------------------------------------- Public methods
    /////////////////////////////////////////////////////////////////
    
    // IValueFitness interface
    
    /**
     * {@inheritDoc}
     */
    
    public final double getValue() 
    {
        return value;
    }
    
    /**
     * Sets this fitness value.
     *
     * @param value New fitness value.
     */
    
    public final void setValue(double value) 
    {
        this.value = value;
    }
    
    // IFitness interface
        
	/**
	 * {@inheritDoc}
	 */
	
	public IFitness copy()
	{
		return new SimpleValueFitness(value);
	}
	
    // java.lang.Object methods
    
    /**
     * {@inheritDoc}
     */
    
	@Override
    public int hashCode() 
    {
        // Hashcode builder
        HashCodeBuilder hcb = new HashCodeBuilder();
        // Append fitness value
        hcb.append(value);
        // Return hashcode
        return hcb.toHashCode();
    }
    
    /**
     * {@inheritDoc}
     */
    
	@Override
    public boolean equals(Object oth) 
    {
        if (oth instanceof SimpleValueFitness) {
            EqualsBuilder eb = new EqualsBuilder();
            eb.append(value, ((SimpleValueFitness) oth).value);
            return eb.isEquals();
        }
        else {
            return false;
        }
    }
    
    /**
     * {@inheritDoc}
     */
    
	@Override
    public String toString() 
    {
        ToStringBuilder tsb = new ToStringBuilder(this);
        tsb.append("value", value);
        return tsb.toString();
    }
}
