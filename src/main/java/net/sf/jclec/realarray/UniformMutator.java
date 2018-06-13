package net.sf.jclec.realarray;


import net.sf.jclec.IConfigure;

import org.apache.commons.configuration.Configuration;

/**
 * Uniform mutation operator.
 * 
 * @author Sebastian Ventura 
 */

@SuppressWarnings("serial")
public abstract class UniformMutator extends RealArrayMutator implements IConfigure
{
	/////////////////////////////////////////////////////////////////
	// --------------------------------------------------- Properties
	/////////////////////////////////////////////////////////////////

	/** Mutation probability for several locus */
	
	protected double locusMutProb;
	
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////

	/**
	 * Empty constructor
	 */
	
	public UniformMutator() 
	{
		super();
	}

	/////////////////////////////////////////////////////////////////
	// ----------------------------------------------- Public methods
	/////////////////////////////////////////////////////////////////

	// Setting and getting properties

	/**
	 * Get locus mutation probability.
	 * 
	 * @return Locus mutation probability
	 */
	
	public double getLocusMutProb() 
	{
		return locusMutProb;
	}

	/**
	 * Set locus mutation probability
	 * 
	 * @param locusMutProb Locus mutation probability 
	 */
	
	public void setLocusMutProb(double locusMutProb) 
	{
		this.locusMutProb = locusMutProb;
	}

	// IConfigure interface
	
	public void configure(Configuration settings) 
	{
		// Locus mutation probability
		double locusMutProb = settings.getDouble("[@locus-mut-prob]", defaultLocusMutProb());
		setLocusMutProb(locusMutProb);
	}

	/////////////////////////////////////////////////////////////////
	// -------------------------------------------- Protected methods
	/////////////////////////////////////////////////////////////////

	// AbstractMutator methods

	/**
	 * {@inheritDoc} 
	 */
	
	@Override
	protected void mutateNext() 
	{
		RealArrayIndividual ind = 
			(RealArrayIndividual) parentsBuffer.get(parentsCounter);
		double [] parentChromosome = ind.getGenotype();
		double [] mutantChromosome = new double[genotypeLength];
		for (int i=0; i<genotypeLength; i++) {
			if (randgen.coin(locusMutProb)) {
				doLocusMutation(parentChromosome, mutantChromosome, i);
			}
			else {
				mutantChromosome[i] = parentChromosome[i];
			}
		}
		sonsBuffer.add(species.createIndividual(mutantChromosome));
	}
	
	// Locus mutation methods
	
	/**
	 * Do mutation operation over an individual locus
	 * 
	 * @param parentChromosome Parent chromosome
	 * @param mutantChromosome Mutant chromosome
	 * @param locusIndex Index of locus to mutate
	 */
	
	protected abstract void doLocusMutation(double [] parentChromosome, double [] mutantChromosome, int locusIndex);

	// Default locus mutation probability
	
	/**
	 * Set default value for this parameter.
	 */
	
	protected abstract double defaultLocusMutProb();
}
