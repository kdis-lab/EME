package net.sf.jclec.problem.util.dataset.metadata;

import java.io.Serializable;

import net.sf.jclec.problem.util.dataset.attribute.IAttribute;

/**
 * Interface for the metadata of a dataset.
 * 
 * It defines the main methods to handle the metadata of a dataset and its properties: attributes, classes, indexes.
 * 
 * @author Amelia Zafra
 * @author Sebastian Ventura
 */

public interface IMetadata extends Serializable
{
    /**
     * Returns number of mining attributes in mining data specification.
     *
     * @return number of mining attributes
     */

    public int numberOfAttributes();
    
    /**
     * Returns number of classes.
     *
     * @return number of classes
     */

    public int numberOfClasses();

    /**
     * Get index of given attribute in this specification.
     * 
     * @param attribute Attribute ... 
     * 
     * @return index of attribute, -1 if attribute is not found
     */
    
    public int getIndex( IAttribute attribute );
    
    /**
     * Get index of given attribute in this specification.
     * 
     * @param attributeName Attribute name 
     * 
     * @return index of attribute, -1 if attribute is not found
     */
    
    public int getIndex(String attributeName );

    /**
     * Get mining attribute by name.
     *
     * @param attributeName name of attribute required
     * @return specified mining attribute, null if not found
     */

    public IAttribute getAttribute( String attributeName );

    /**
     * Get mining attribute by index of the array of attributes of
     * mining data specification.
     *
     * @param attributeIndex index of attribute required
     * @return specified mining attribute, null if not found
     */

    public IAttribute getAttribute( int attributeIndex);
    
    /**
     * Adds an attribute to this metadata.
     * 
     * If the name of the new attribute is empty or there already 
     * exists an attribute with the same name, it is not added to 
     * the name hashtable.
     * This means that it could not be retrieved via its name. It is
     * highly recommended only to use attributes with unique names.
     *
     * @param attribute the attribute to add
     * @return true attribute also added to name hashtable, false if attribute
     * name is null or there already exists an attribute with the same name
     */
	
    public boolean addAttribute( IAttribute attribute );

    /**
     * Get the class index
     *
     * @return specified class index
     */
    
	public int getClassIndex();
	
    /**
     * Set the class index
     *
     * @param attributeClassIndex specified class index
     */

	public void setClassIndex(int attributeClassIndex);
	
	/**
	 * Copy method
	 * 
	 * @return A copy of this metadata
	 */
	
	public IMetadata copy();
}
