package net.sf.jclec.problem.util.dataset;

import java.util.ArrayList;

import net.sf.jclec.IConfigure;
import net.sf.jclec.JCLEC;
import net.sf.jclec.problem.util.dataset.instance.IInstance;
import net.sf.jclec.problem.util.dataset.metadata.IMetadata;

/**
 * Dataset interface.
 * 
 * Defines the main methods to handle the data instances and metadata information.
 * 
 * @author Amelia Zafra
 * @author Sebastian Ventura
 */

public interface IDataset extends JCLEC, IConfigure 
{
	/**
     * Get name of this dataset. 
     *
     * @return name of this dataset
     */
	
    public String getName();

    /**
     * Access to this dataset specification.
     * 
     * @return Dataset specification
     */
    
    public IMetadata getMetadata();
    
	/**
	 * Get the number of Instances. 
	 *  
	 * @return number of Instances
	 */
	
	public int numberOfInstances();
	
    /**
     * Load all datasets instances with this method.
     */
    
    public void loadInstances();
    
    /**
     * Set the instances located in the ArrayList
     */
    
    public void setInstances(ArrayList<IInstance> array);
    
	/**
     * Add the instances located in the ArrayList
     */
    
    public void addInstances(ArrayList<IInstance> instances);
    
    /**
     * Access to all dataset instances
     * 
     * @return All the instances contained in this dataset
     */
    
    public ArrayList<IInstance> getInstances();
    
   /**
    * Copy method
    * 
    * @return A copy of this dataset
    */
    
    public IDataset copy();	
}