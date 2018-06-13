package net.sf.jclec.algorithm.niching;

import java.util.List;
import java.util.ArrayList;

import net.sf.jclec.IDistance;
import net.sf.jclec.IConfigure;
import net.sf.jclec.IIndividual;

import net.sf.jclec.algorithm.classic.SG;

import net.sf.jclec.selector.WorsesSelector;
import net.sf.jclec.selector.BettersSelector;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationRuntimeException;

/**
 * Spatial niching algorithm.
 * 
 * @author Sebastian Ventura 
 * @author Amelia Zafra
 */

public abstract class SpatialNiching extends SG 
{
	/////////////////////////////////////////////////////////////////
	// --------------------------------------------------- Properties
	/////////////////////////////////////////////////////////////////

	private static final long serialVersionUID = -5585172410685336757L;

	/** Distance between individuals */
	
	protected IDistance distance;
	
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------- Internal variables
	/////////////////////////////////////////////////////////////////

	/** Niches set */
	
	protected List<IIndividual> nset = new ArrayList<IIndividual> ();
	
	/** Betters selector */
	
	protected BettersSelector bettersSelector = new BettersSelector(this); 

	/** Worses selector */

	protected WorsesSelector worsesSelector = new WorsesSelector(this); 
	
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////

	/**
	 * Empty constructor
	 */
	
	public SpatialNiching() 
	{
		super();
	}

	/////////////////////////////////////////////////////////////////
	// ----------------------------------------------- Public methods
	/////////////////////////////////////////////////////////////////
	
	// Setting and getting properties
	
	public IDistance getDistance()
	{
		return distance;
	}
	
	public void setDistance(IDistance distance)
	{
		this.distance = distance;
	}
	
	// IConfigure interface
	
	@SuppressWarnings("unchecked")
	public void configure(Configuration configuration)
	{
		// Call super.configure() method
		super.configure(configuration);
		// Parents selector
		try {
			// Distance classname
			String distanceClassname = 
				configuration.getString("distance[@type]");
			// Species class
			Class<? extends IDistance> distanceClass = 
				(Class<? extends IDistance>) Class.forName(distanceClassname);
			// Species instance
			IDistance distance = distanceClass.newInstance();
			// Configure species if necessary
			if (distance instanceof IConfigure) {
				// Extract species configuration
				Configuration distanceConfiguration = configuration.subset("distance");
				// Configure species
				((IConfigure) distance).configure(distanceConfiguration);
			}
			// Set species
			setDistance(distance);
		} 
		catch (ClassNotFoundException e) {
			throw new ConfigurationRuntimeException("Illegal distance classname");
		} 
		catch (InstantiationException e) {
			throw new ConfigurationRuntimeException("Problems creating an instance of distance", e);
		} 
		catch (IllegalAccessException e) {
			throw new ConfigurationRuntimeException("Problems creating an instance of distance", e);
		}
	}
	
	/////////////////////////////////////////////////////////////////
	// -------------------------------------------- Protected methods
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Create niches set. Then, do parents selection
	 */
	
	@Override
	protected void doSelection() 
	{
		// Clear nset
		nset.clear();
		// Put "niche individuals" in nset
		createNiches();
		// Select individuals
		pset = parentsSelector.select(nset, bset.size());
	}	
	
	/**
	 * Create new individuals from parents. Also, nullify fitness of
	 * parents to ensure re-evaluation
	 * 
	 */

	@Override
	protected void doGeneration() 
	{
		//
		// TODO Improve this. Re-evaluation is inefficient
		//
		// Nullify the fitness of all parents. This ensures 
		// evaluation of all individuals after breeding
		for (IIndividual ind : pset) {
			ind.setFitness(null);
		}
		// Call super method
		super.doGeneration();
	}

	@Override
	protected void doUpdate() 
	{
		// Check size of cset. If less than bset.size, add better 
		// individuals from bset
		if(cset.size() < bset.size()) {			
			cset.addAll(bettersSelector.select(bset, bset.size()-cset.size()));
		}		
		// Else, do an elitist update
		else
		{
			IIndividual bestInB = bettersSelector.select(bset, 1).get(0);
			IIndividual bestInC = bettersSelector.select(cset, 1).get(0);			
			if (compareIndividuals(bestInB, bestInC) == 1) {
				IIndividual worstInC = worsesSelector.select(cset, 1).get(0);
				// Remove worst individual in C
				cset.remove(worstInC);
				// Add best individual in B
				cset.add(bestInB);				
			}
		}
		// Sets new bset
		bset = cset;
		// Clear pset, rset & cset
		pset = rset = cset = null;	
	}
	
	/**
	 * Assign fitness to all individuals in bset...
	 */
	
	protected abstract void createNiches();	
	
	/////////////////////////////////////////////////////////////////
	// ---------------------------------------------- Private methods
	/////////////////////////////////////////////////////////////////

	/**
	 * Individuals fitness-based comparison method
	 */
	
	private final int compareIndividuals(IIndividual one, IIndividual two)
	{
		return evaluator.getComparator().compare(one.getFitness(), two.getFitness());
	}
}
