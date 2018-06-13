package net.sf.jclec.algorithm;

import java.util.List;

import net.sf.jclec.ISpecies;
import net.sf.jclec.IProvider;
import net.sf.jclec.IEvaluator;
import net.sf.jclec.IConfigure;
import net.sf.jclec.IIndividual;
import net.sf.jclec.IPopulation;

import net.sf.jclec.util.random.IRandGen;
import net.sf.jclec.util.random.IRandGenFactory;


import org.apache.commons.lang.builder.EqualsBuilder;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationRuntimeException;

/**
 * Algorithm that operates over a population. 
 * 
 * @author Sebastian Ventura
 */

@SuppressWarnings("serial")
public abstract class PopulationAlgorithm extends AbstractAlgorithm implements IPopulation 
{
	/////////////////////////////////////////////////////////////////
	// --------------------------------------------------- Properties
	/////////////////////////////////////////////////////////////////
	
	/** Random generators factory */
	
	protected IRandGenFactory randGenFactory;
	
	/** Individual species */
	
	protected ISpecies species;
	
	/** Individuals evaluator */
	
	protected IEvaluator evaluator;

	/** Individuals provider */
	
	protected IProvider provider;
	
	/** Population size */
	
	protected int populationSize;
	
	/** Maximum of generations (stop criterion) */
	
	protected int maxOfGenerations;
	
	/** Maximum of evaluations (stop criterion) */
	
	protected int maxOfEvaluations;
	
	/////////////////////////////////////////////////////////////////
	// ---------------------------- Internal variables (System state)
	/////////////////////////////////////////////////////////////////
	
	/** Actual generation */
	
	protected int generation;
	
	/** Actual individuals set */
	
	protected List<IIndividual> bset;
	
	/** Individuals selected as parents */
	
	protected transient List<IIndividual> pset;
	
	/** Individuals generated */
	
	protected transient List<IIndividual> cset;
	
	/** Individuals to replace */
	
	protected transient List<IIndividual> rset;

	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////

	/**
	 * Empty (default) constructor
	 */
	
	public PopulationAlgorithm() 
	{
		super();
	}

	/////////////////////////////////////////////////////////////////
	// ---------------------------------------- IPopulation interface
	/////////////////////////////////////////////////////////////////

	// System state
	
	public final ISpecies getSpecies() 
	{
		return species;
	}

	public final IEvaluator getEvaluator() 
	{
		return evaluator;
	}

	public final int getGeneration() 
	{
		return generation;
	}

	public List<IIndividual> getInhabitants() 
	{
		return bset;
	}

	// Setting and getting properties

	public void setSpecies(ISpecies species) 
	{
		this.species = species;
	}

	public void setEvaluator(IEvaluator evaluator) 
	{
		this.evaluator = evaluator;
	}

	public final void setGeneration(int generation) 
	{
		this.generation = generation;
	}

	public final void setInhabitants(List<IIndividual> inhabitants)
	{
		this.bset = inhabitants;
	}

	public IRandGenFactory getRandGenFactory() 
	{
		return randGenFactory;
	}

	public void setRandGenFactory(IRandGenFactory randGenFactory) 
	{
		this.randGenFactory = randGenFactory;
	}
	
	public final IProvider getProvider() 
	{
		return provider;
	}

	public final void setProvider(IProvider provider) 
	{	
		// Set provider
		this.provider = provider;
		// Contextualize provider
		
		provider.contextualize(this);
	}

	public final int getPopulationSize() 
	{
		return populationSize;
	}

	public final void setPopulationSize(int populationSize) 
	{
		this.populationSize = populationSize;
	}

	public final int getMaxOfGenerations() 
	{
		return maxOfGenerations;
	}

	public final void setMaxOfGenerations(int maxOfGenerations) 
	{
		this.maxOfGenerations = maxOfGenerations;
	}

	public final int getMaxOfEvaluations() 
	{
		return maxOfEvaluations;
	}

	public final void setMaxOfEvaluations(int maxOfEvaluations) 
	{
		this.maxOfEvaluations = maxOfEvaluations;
	}

	// IAlgorithm interface

	/**
	 * {@inheritDoc}
	 */
	
	public IRandGen createRandGen() 
	{
		return randGenFactory.createRandGen();
	}
	
	// IConfigure interface
	
	/**
	 * Configuration parameters for BaseAlgorithm class are:
	 * 
	 * <ul>
	 * <li>
	 * <code>species: ISpecies (complex)</code></p>
	 * Individual species
	 * </li><li>
	 * <code>evaluator IEvaluator (complex)</code></p>
	 * Individuals evaluator
	 * </li><li>
	 * <code>population-size (int)</code></p>
	 * Population size
	 * </li><li>
	 * <code>max-of-generations (int)</code></p>
	 * Maximum number of generations
	 * </li>
	 * <li>
	 * <code>provider: IProvider (complex)</code></p>
	 * Individuals provider
	 * </li>
	 * </ul>
	 */
	
	public void configure(Configuration configuration)
	{
		// Call super.configure() method
		super.configure(configuration);
		
		// Random generators factory
		setRandGenSettings(configuration);
		
		// Individual species
		setSpeciesSettings(configuration);
		
		// Individuals evaluator
		setEvaluatorSettings(configuration);
		
		// Population size
		int populationSize = configuration.getInt("population-size");
		setPopulationSize(populationSize);
		
		// Maximum of generations
		int maxOfGenerations = configuration.getInt("max-of-generations", Integer.MAX_VALUE); 
		setMaxOfGenerations(maxOfGenerations);
		
		// Maximum of generations
		int maxOfEvaluations = configuration.getInt("max-of-evaluations", Integer.MAX_VALUE); 
		setMaxOfEvaluations(maxOfEvaluations);
		
		// Individuals provider
		setProviderSettings(configuration);
	}
	
	@SuppressWarnings("unchecked")
	protected void setRandGenSettings(Configuration configuration)
	{
		// Random generators factory
		try {
			// Species classname
			String randGenFactoryClassname = 
				configuration.getString("rand-gen-factory[@type]");
			// Species class
			Class<? extends IRandGenFactory> randGenFactoryClass = 
				(Class<? extends IRandGenFactory>) Class.forName(randGenFactoryClassname);
			// Species instance
			IRandGenFactory randGenFactory = randGenFactoryClass.newInstance();
			// Configure species
			if (randGenFactory instanceof IConfigure) {
				((IConfigure) randGenFactory).configure
					(configuration.subset("rand-gen-factory"));
			}
			// Set species
			setRandGenFactory(randGenFactory);
		} 
		catch (ClassNotFoundException e) {
			throw new ConfigurationRuntimeException
				("Illegal random generators factory classname");
		} 
		catch (InstantiationException e) {
			throw new ConfigurationRuntimeException
				("Problems creating an instance of random generators factory", e);
		} 
		catch (IllegalAccessException e) {
			throw new ConfigurationRuntimeException
				("Problems creating an instance of random generators factory", e);
		}
	}
	
	@SuppressWarnings("unchecked")
	protected void setSpeciesSettings(Configuration configuration)
	{
		// Individual species
		try {
			// Species classname
			String speciesClassname = 
				configuration.getString("species[@type]");
			// Species class
			Class<? extends ISpecies> speciesClass = 
				(Class<? extends ISpecies>) Class.forName(speciesClassname);
			// Species instance
			ISpecies species = speciesClass.newInstance();
			// Configure species
			if (species instanceof IConfigure) {
				((IConfigure) species).configure(configuration.subset("species"));
			}
			// Set species
			setSpecies(species);
		} 
		catch (ClassNotFoundException e) {
			throw new ConfigurationRuntimeException("Illegal species classname");
		} 
		catch (InstantiationException e) {
			throw new ConfigurationRuntimeException("Problems creating an instance of species", e);
		} 
		catch (IllegalAccessException e) {
			throw new ConfigurationRuntimeException("Problems creating an instance of species", e);
		}
	}
	
	@SuppressWarnings("unchecked")
	protected void setEvaluatorSettings(Configuration configuration)
	{
		// Individuals evaluator
		try {
			// Evaluator classname
			String evaluatorClassname = 
				configuration.getString("evaluator[@type]");
			// Evaluator class
			Class<? extends IEvaluator> evaluatorClass = 
				(Class<? extends IEvaluator>) Class.forName(evaluatorClassname);
			// Evaluator instance
			IEvaluator evaluator = evaluatorClass.newInstance();
			// Configure species
			if (evaluator instanceof IConfigure) {
				((IConfigure) evaluator).configure(configuration.subset("evaluator"));
			}
			// Set species
			setEvaluator(evaluator);
		} 
		catch (ClassNotFoundException e) {
			throw new ConfigurationRuntimeException("Illegal evaluator classname");
		} 
		catch (InstantiationException e) {
			throw new ConfigurationRuntimeException("Problems creating an instance of evaluator", e);
		} 
		catch (IllegalAccessException e) {
			throw new ConfigurationRuntimeException("Problems creating an instance of evaluator", e);
		}
	}
	
	@SuppressWarnings("unchecked")
	protected void setProviderSettings(Configuration configuration)
	{
		try {
			// Provider classname
			String providerClassname = 
				configuration.getString("provider[@type]");
			// Provider class
			Class<? extends IProvider> providerClass = 
				(Class<? extends IProvider>) Class.forName(providerClassname);
			// Provider instance
			IProvider provider = providerClass.newInstance();
			// Configure provider
			if (provider instanceof IConfigure) {
				((IConfigure) provider).configure(configuration.subset("provider"));
			}
			// Set provider
			setProvider(provider);
		} 
		catch (ClassNotFoundException e) {
			throw new ConfigurationRuntimeException("Illegal provider classname");
		} 
		catch (InstantiationException e) {
			throw new ConfigurationRuntimeException("Problems creating an instance of provider", e);
		} 
		catch (IllegalAccessException e) {
			throw new ConfigurationRuntimeException("Problems creating an instance of provider", e);
		}
	}
	
	// java.lang.Object methods

	@Override
	public boolean equals(Object other)
	{
		if (other instanceof PopulationAlgorithm) {
			PopulationAlgorithm cother = (PopulationAlgorithm) other;
			EqualsBuilder eb = new EqualsBuilder();
			
			// Random generators factory
			eb.append(randGenFactory, cother.randGenFactory);
			// Individual species
			eb.append(species, cother.species);
			// Individuals evaluator
			eb.append(evaluator, cother.evaluator);
			// Population size
			eb.append(populationSize, cother.populationSize);
			// Max of generations
			eb.append(maxOfGenerations, cother.maxOfGenerations);
			// Individuals provider
			eb.append(provider, cother.provider);
			// Return test result
			return eb.isEquals();
		}
		else {
			return false;
		}
	}

	/////////////////////////////////////////////////////////////////
	// -------------------------------------------- Protected methods
	/////////////////////////////////////////////////////////////////

	// Execution methods
	
	/**
	 * Create individuals in population, evaluating before start rest
	 * of evolution
	 */
	
	protected void doInit() 
	{
		// Set the init flag to false NO NECESARIO
		// finished = false;
		// Create individuals
		bset = provider.provide(populationSize);
		// Evaluate individuals
		evaluator.evaluate(bset);
		// Do Control
		doControl();
	}

	/**
	 * ... 
	 */
	
	protected void doIterate() 
	{
		generation++;
		// Do selection
		doSelection();
		// Do generation
		doGeneration();
		// Do replacement
		doReplacement();
		// Do update
		doUpdate();
		// Do control
		doControl();
		// Increments generation counter
	}
			
	/**
	 * Select individuals to be breeded. 
	 */
	
	protected abstract void doSelection();
	
	/**
	 * Generate new individuals from parents
	 */
	
	protected abstract void doGeneration();
	
	/**
	 * Select individuals to extinct in this generation.
	 */
	
	protected abstract void doReplacement();
	
	/**
	 * Update population individuals.
	 */
	
	protected abstract void doUpdate();
	
	/**
	 * Check if evolution is finished. Default implementation of this
	 * method performs the operations:
	 * 
	 * <ul>
	 * <li>
	 * If number of generations exceeds the maximum allowed, set the
	 * finished flag to true. Else, the flag remains false
	 * </li>
	 * <li>
	 * If number of evaluations exceeds the maximum allowed, set the
	 * finished flag to true. Else, the flag remains false
	 * </li>
	 * <li>
	 * If one individual has an  acceptable fitness, set the finished
	 * flag to true. Else, the flag remains false. 
	 * </li>
	 * </ul>
	 */
	
	protected void doControl()
	{
		// If maximum number of generations is exceeded, evolution is
		// finished
		if (generation >= maxOfGenerations) {
//			finished = true;
			state = FINISHED;
			return;
		}
		// If maximum number of evaluations is exceeded, evolution is
		// finished
		if (evaluator.getNumberOfEvaluations() > maxOfEvaluations) {
//			finished = true;
			state = FINISHED;
			return;
		}
		// If any individual in b has an acceptable fitness evolution
		// is finished
		for (IIndividual individual : bset) {
			if (individual.getFitness().isAcceptable()) {
//				finished = true;
				state = FINISHED;
				return;
			}
		}
	}	
}
