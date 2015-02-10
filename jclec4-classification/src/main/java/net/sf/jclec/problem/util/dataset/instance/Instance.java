package net.sf.jclec.problem.util.dataset.instance;

/**
 * Implementation of the IInstance interface.
 * 
 * The instance values for the attributes are stored in an array.
 * The instance can be weighted to determine its importance within the dataset.
 * 
 * @author Amelia Zafra
 * @author Sebastian Ventura
 * @author Alberto Cano  
 */

public class Instance implements IInstance
{
	/////////////////////////////////////////////////////////////
	// ----------------------------------------------- Properties
	/////////////////////////////////////////////////////////////

	private static final long serialVersionUID = 2141837612603678135L;

	/** Attribute values */

	protected double[] values;

	/** weight of this instance */

	protected double weight;

	/////////////////////////////////////////////////////////////
	// --------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////

	/**
	 * Default constructor
	 * 
	 * @param numberAttributes Number of attributes
	 */

	public Instance(int numberAttributes)
	{
		super();

		this.values = new double[numberAttributes];
	}

	/////////////////////////////////////////////////////////////
	// ------------------------------------------- Public methods
	/////////////////////////////////////////////////////////////

	/**
	 * Set the value of the index-th element of this instance.
	 * 
	 * @param index Index of the element to change
	 * @param value Internal value for this element
	 */

	public final void setValue(int index, double value)
	{
		this.values[index] = value;
	}

	/**
	 * Sets the weigth for this instance
	 * 
	 * @param weight New weigth value
	 */

	public final void setWeight(double weight)
	{
		this.weight = weight;
	}

	/**
	 * {@inheritDoc}
	 */

	public double getWeight() 
	{
		return weight;
	}

	/**
	 * {@inheritDoc}
	 */

	public double getValue(int attributeIndex) 
	{
		return values[attributeIndex];
	}

	/**
	 * {@inheritDoc}
	 */

	public double[] getValues() 
	{
		return values;
	}

	/**
	 * {@inheritDoc}
	 */

	public void setValues(double[] values) {
		this.values = values;
	}	

	/**
	 * {@inheritDoc}
	 */

	public Instance copy() 
	{
		// Copy 
		Instance result = new Instance(values.length);
		// Copy content
		for(int i=0; i<values.length; i++)
			result.setValue(i,values[i]);
		// Return resulting copy
		return result;
	}
}