package net.sf.jclec.problem.util.dataset;

import java.io.Reader;

import org.apache.commons.configuration.Configuration;

/**
 * Extension of the abstract dataset to handle file-based datasets.
 * 
 * @author Amelia Zafra
 * @author Sebastian Ventura
 */

public abstract class FileDataset extends AbstractDataset
{
	/////////////////////////////////////////////////////////////////
	// --------------------------------------------------- Properties
	/////////////////////////////////////////////////////////////////
	
	private static final long serialVersionUID = 4789507783062217436L;

	/** Data file name */
	
	protected String fileName;

	/////////////////////////////////////////////////////////////////
	// ------------------------------------------- Internal variables
	/////////////////////////////////////////////////////////////////
	
	/** Data file reader */

	protected Reader fileReader;
	
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////

	/**
	 * Empty (default) constructor
	 */
	
	public FileDataset() 
	{
		super();
	}

	/**
	 * Constructor that sets the name of the data set file
	 * 
	 * @param fileName Name of the data set file
	 */
	
	public FileDataset(String fileName) 
	{
		super();
		setFileName(fileName);
	}

	/////////////////////////////////////////////////////////////////
	// ----------------------------------------------- Public methods
	/////////////////////////////////////////////////////////////////

	/**
	 * Access to current name
	 * 
	 * @return Current filename
	 */
	
	public String getFileName() 
	{
		return fileName;
	}

	/**
	 * Set filename
	 * 
	 * @param fileName New filename
	 */
	
	public void setFileName(String fileName) 
	{
		this.fileName = fileName;
	}

	/**
	 * Configuration method. 
	 * 
	 * Configuration parameters for FileDataset are:
	 * 
	 */
	
	public void configure(Configuration settings)
	{
		// Set file name
		setFileName(settings.getString(""));
	}
}