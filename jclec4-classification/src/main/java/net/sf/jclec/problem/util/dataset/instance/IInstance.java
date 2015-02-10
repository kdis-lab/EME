package net.sf.jclec.problem.util.dataset.instance;

import java.io.Serializable;

/**
 * Interface definition for an instance.
 * 
 * Defines the main methods to read/store values for the instances.
 * 
 * @author Amelia Zafra
 * @author Sebastian Ventura
 * @author Alberto Cano
 */

public interface IInstance extends Serializable
{
	/**
	 * Returns the weight of this instance.
	 *
	 * @return instance weight
	 */

	public double getWeight();

	/**
	 * Sets the weight of this instance.
	 *
	 * @param weight instance weight
	 */

	public void setWeight(double weight);

	/**
	 * Get value array of the this instance.
	 *
	 * @return value array of the vector
	 */

	public double[] getValues();

	/**
	 * Set value array of the this instance.
	 *
	 * @param values array of the vector
	 */

	public void setValues(double[] values);

	/**
	 * Returns vector's attribute value in internal format.
	 *
	 * @param attributeIndex attribute index for value to read.
	 * 
	 * @return the specified value as a double (If the corresponding
	 * 		   attribute is categorical then it returns the value's 
	 * 		   index as a double).
	 */

	public double getValue( int attributeIndex );

	/**
	 * Copy method
	 * 
	 * @return A copy of this instance
	 */

	public IInstance copy();
}