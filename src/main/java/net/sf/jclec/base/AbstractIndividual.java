package net.sf.jclec.base;

import net.sf.jclec.IFitness;
import net.sf.jclec.IIndividual;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * IIndividual abstract implementation.
 *  
 * @author Sebastian Ventura
 *
 * @param <G> Object that plays the role of genotype
 */

public abstract class AbstractIndividual<G> implements IIndividual 
{
	/////////////////////////////////////////////////////////////////
	// --------------------------------------------------- Attributes
	/////////////////////////////////////////////////////////////////

	private static final long serialVersionUID = -7488967006695821449L;

	/** Individual genotype */
	
	protected G genotype;
	
	/** Individual fitness */
	
	protected IFitness fitness;
	
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////

	/**
	 * Empty constructor 
	 */
	
	protected AbstractIndividual() 
	{
		super();
	}

	/**
	 * Constructor that sets individual genotype.
	 * 
	 * @param genotype Individual genotype
	 */
	
	protected AbstractIndividual(G genotype) 
	{
		super();
		setGenotype(genotype);
	}

	/**
	 * Constructor that sets individual genotype and fitness. 
	 * 
	 * @param genotype Individual genotype
	 * @param fitness  Individual fitness
	 */
	
	protected AbstractIndividual(G genotype, IFitness fitness) 
	{
		super();
		setGenotype(genotype);
		setFitness(fitness);
	}

	/////////////////////////////////////////////////////////////////
	// ----------------------------------------------- Public methods
	/////////////////////////////////////////////////////////////////

	/**
	 * Sets individual genotype
	 * 
	 * @param genotype Individual genotype
	 */
	
	public final void setGenotype(G genotype)
	{
		this.genotype = genotype;
	}

	/**
	 * Access to individual genotype
	 * 
	 * @return Individual genotype 
	 */
	
	public final G getGenotype() 
	{
		return genotype;
	}

	// IIndividual interface

	/**
	 * {@inheritDoc}
	 */
	
	public final IFitness getFitness() 
	{
		return fitness;
	}

	/**
	 * {@inheritDoc}
	 */
	
	public final void setFitness(IFitness fitness) 
	{
		this.fitness = fitness;
	}
	
	// java.lang.Object methods
	
	
	/**
	 * Return an string that represent this fitness object legibly.
	 * 
	 * @return String that represents this individual.
	 */
	
	@Override
	public String toString()
	{
		ToStringBuilder tsb = new ToStringBuilder(this);
		tsb.append("genotype", genotype);
		tsb.append("fitness", fitness);
		return tsb.toString();
	}
}
