package net.sf.jclec.realarray.rec;

import net.sf.jclec.JCLEC;

import org.apache.commons.configuration.Configuration;

/**
 * Connectives abstract implementation
 * 
 * @author Alberto Lamarca-Rosales 
 * @author Sebastian Ventura
 */

@SuppressWarnings("serial")
public abstract class AbstractFuzzyConnectives implements JCLEC, IFuzzyConnectives 
{
	/////////////////////////////////////////////////////////////////
	// --------------------------------------------------- Properties
	/////////////////////////////////////////////////////////////////
	
	protected double alpha = 0.5;
	
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Empty constructor
	 */
	
	public AbstractFuzzyConnectives() 
	{
		super();
	}
	
	/////////////////////////////////////////////////////////////////
	// ----------------------------------------------- Public methods
	/////////////////////////////////////////////////////////////////
	
	// Getting and setting properties
	
	public double getAlpha() 
	{
		return alpha;
	}

	public void setAlpha(double alpha) 
	{
		this.alpha = alpha;
	}

	// IConfigure interface
	
	public void configure(Configuration settings)
	{
		double alpha = settings.getDouble("[@alpha]", 0.5);
		setAlpha(alpha);
	}

	/////////////////////////////////////////////////////////////////
	// -------------------------------------------- Fuzzy connectives
	/////////////////////////////////////////////////////////////////
	
	// Implemented in subclasses
}
