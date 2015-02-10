package net.sf.jclec.problem.util.dataset.metadata;

import java.util.HashMap;
import java.util.ArrayList;

import net.sf.jclec.problem.util.dataset.attribute.CategoricalAttribute;
import net.sf.jclec.problem.util.dataset.attribute.IAttribute;

/**
 * Implementation of IMetadata interface.
 * 
 * The set of attributes are stored in an attributes list as well as its correspondence between indexes and attribute names.
 * The classIndex controls the index of the attribute which keeps the data class in classification problems.
 *  
 * @author Amelia Zafra
 * @author Sebastian Ventura
 */

public class ClassificationMetadata implements IMetadata 
{
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------- Internal variables
	/////////////////////////////////////////////////////////////////
	
	private static final long serialVersionUID = 7370514914850394015L;

	/** Array list containing all attributes of this meta data */
	
    protected ArrayList<IAttribute> attributesList = new ArrayList<IAttribute>();

    /** Mapping of attribute names to attributes */
    
    protected HashMap<String, IAttribute> attributesMap = new HashMap<String, IAttribute>();
    
	/** Index of the class attribute */
	
	private int classIndex;    

	/////////////////////////////////////////////////////////////////
	// ----------------------------------------------- Public methods
	/////////////////////////////////////////////////////////////////

    /**
     * Empty constructor
     */
    
	public ClassificationMetadata() 
	{
		super();
	}

	/////////////////////////////////////////////////////////////////
	// ----------------------------------------------- Public methods
	/////////////////////////////////////////////////////////////////

	/**
	 * {@inheritDoc}
	 */
	
	public int numberOfAttributes() 
	{
		return attributesList.size();
	}
	
	/**
	 * {@inheritDoc}
	 */
	
	public int numberOfClasses() 
	{
		CategoricalAttribute catAttr = (CategoricalAttribute) getAttribute(classIndex);
		return catAttr.getCategories().size();
	}

	/**
	 * {@inheritDoc}
	 */
	
	public IAttribute getAttribute(String attributeName) 
	{		
		return attributesMap.get(attributeName);
	}

	/**
	 * {@inheritDoc}
	 */
	
	public IAttribute getAttribute(int attributeIndex) 
	{
		return attributesList.get(attributeIndex);
	}

	/**
	 * {@inheritDoc}
	 */
	
	public int getIndex(IAttribute attribute) 
	{
		return attributesList.indexOf(attribute);
	}

	/**
	 * {@inheritDoc}
	 */
	
	public int getIndex(String attributeName) 
	{
		IAttribute attribute = attributesMap.get(attributeName);
		if (attribute == null) {
			return -1;
		}
		else {
			return attributesList.indexOf(attribute);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	
    public boolean addAttribute( IAttribute attribute )
    {
   		String attributeName = attribute.getName();
   		if ( attributeName == null || attributesMap.get(attributeName) != null ) {
   			return false;    			
   		}
   		else {
       		attributesList.add( attribute );
       		attributesMap.put(attributeName, attribute);
       		return true;
    	}
    }
	
    /**
	 * Gets the index of the attribute class
	 * 
	 * @return index of class attribute
	 */
	
	public int getClassIndex()
	{
		return classIndex;
	}
	
	/**
	 * Sets the index of the attribute class
	 * 
	 * @param classIndex index of class attribute
	 */
	
	public void setClassIndex(int classIndex)
	{
		this.classIndex = classIndex;
	}
	
	/**
	 * Copy method
	 * 
	 * @return A copy of this metadata
	 */
	
	public ClassificationMetadata copy()
	{
		ClassificationMetadata metadata = new ClassificationMetadata();
		
		metadata.classIndex = this.classIndex;
		metadata.attributesList = this.attributesList;
		metadata.attributesMap = this.attributesMap;
		
		return metadata;
	}
}