package net.sf.jclec.fitness;

import net.sf.jclec.IFitness;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Composite fitness that implements IValueFitness interface.
 *
 * @author Sebastian Ventura
 */

public class CompositeValueFitness extends CompositeFitness implements IValueFitness
{
    /////////////////////////////////////////////////////////////////
    // --------------------------------------- Serialization constant
    /////////////////////////////////////////////////////////////////

	/** Comment for <code>serialVersionUID</code> */
	
	private static final long serialVersionUID = 3258690996551104821L;
	
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
    
    public CompositeValueFitness() 
    {
        super();
    }

    /**
     * Constructor that sets components and value.
     */
    
    public CompositeValueFitness(ISimpleFitness [] components) 
    {
        super(components);
    }

    /**
     * Constructor that sets components and value.
     */
    
    public CompositeValueFitness(ISimpleFitness [] components, double value) 
    {
        super(components);
        setValue(value);
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
     * @param value New value.
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
		// Copy components
		int cl = components.length;
		ISimpleFitness [] comps = new ISimpleFitness[cl];
		for (int i=0; i<cl; i++)
			comps[i] = (ISimpleFitness) components[i].copy();
		// Sets the value
		double val = value;
		// Returns result
		return new CompositeValueFitness(comps, val);
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
        // Append super-hashCode
        hcb.appendSuper(super.hashCode());
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
        if (oth instanceof CompositeValueFitness) {
            // Type conversion
            CompositeValueFitness coth = (CompositeValueFitness) oth;
            // equallity test
            EqualsBuilder eb = new EqualsBuilder();
            eb.appendSuper(super.equals(coth));
            eb.append(value, coth.value);
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
        tsb.appendSuper(super.toString());
        tsb.append("value", value);
        return tsb.toString();
    }
}
