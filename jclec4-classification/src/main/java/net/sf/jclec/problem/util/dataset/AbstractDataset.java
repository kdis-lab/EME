package net.sf.jclec.problem.util.dataset;

import java.util.ArrayList;

import net.sf.jclec.problem.util.dataset.instance.IInstance;
import net.sf.jclec.problem.util.dataset.metadata.IMetadata;

/**
 * Abstract definition of a dataset, its name, instances and metadata.
 * 
 * @author Amelia Zafra
 * @author Sebastian Ventura
 * @author Jose M. Luna 
 * @author Alberto Cano 
 * @author Juan Luis Olmo
 */

public abstract class AbstractDataset implements IDataset 
{
	/////////////////////////////////////////////////////////////////
	// --------------------------------------------------- Properties
	/////////////////////////////////////////////////////////////////

	private static final long serialVersionUID = -5863981824188521799L;

	/** Dataset name */
	
	protected String name;
	
	/** Dataset specification */
	
	protected IMetadata metadata;
	
	/** Dataset instances */
	
	protected ArrayList<IInstance> instances;

	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////

	/**
	 * Empty constructor
	 */
	
	public AbstractDataset() 
	{
		super();
	}

	/////////////////////////////////////////////////////////////////
	// ----------------------------------------------- Public methods
	/////////////////////////////////////////////////////////////////

	/**
	 * Sets the name of this dataset.
	 * 
	 * @param name the name of the dataset 
	 */
	
	public final void setName(String name)
	{
		this.name = name;
	}
	
	/**
	 * {@inheritDoc}
	 */
	
	public String getName() 
	{
		return name;
	}

	/**
	 * {@inheritDoc}
	 */
	
	public IMetadata getMetadata() 
	{
		return metadata;
	}
	
	/**
	 * Set the metadata
	 * 
	 * @param metadata the metadata
	 */
	
	public void setMetadata(IMetadata metadata)
	{
		this.metadata = metadata;
	}

	/**
	 * Get the number of instances
	 * 
	 * @return the number of instances
	 */
		
	public int numberOfInstances()
	{
		return instances.size();
	}
}