package net.sf.jclec.realarray.rec;

import net.sf.jclec.IPopulation;

import net.sf.jclec.realarray.RealArrayIndividual;
import net.sf.jclec.realarray.UniformCrossover;

/**
 * Two parents and two sons crossover.
 *  
 * @author Sebastian Ventura
 *
 * @param <I> Type of individuals to recombine
 */

@SuppressWarnings("serial")
abstract class UniformCrossover2x2 extends UniformCrossover
{
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Empty constructor.
	 */
	
	public UniformCrossover2x2() 
	{
		super();
	}

	/**
	 * Constructor that sets execution context.
	 * 
	 * @param population Execution context
	 */
	
	public UniformCrossover2x2(IPopulation population) 
	{
		super();
	}

	/////////////////////////////////////////////////////////////////
	// -------------------------------------------- Protected methods
	/////////////////////////////////////////////////////////////////
	
	// AbstractRecombinator methods
	
	@Override
	protected void setPpl() 
	{
		ppl = 2;
	}

	@Override
	protected void setSpl() 
	{
		spl = 2;
	}	
	
	@Override
	protected void recombineNext() 
	{
		// Parents genotypes
		double [] p0_genome = 
			((RealArrayIndividual) parentsBuffer.get(parentsCounter)).getGenotype();
		double [] p1_genome = 
			((RealArrayIndividual) parentsBuffer.get(parentsCounter+1)).getGenotype();
		// Creating sons genotypes
		double [] s0_genome = new double[genotypeLength];
		double [] s1_genome = new double[genotypeLength];
		// Do recombination operation
		for (int i=0; i<genotypeLength; i++) {
			if (randgen.coin(locusRecProb)) {
				recombineLocus(p0_genome, p1_genome, s0_genome, s1_genome, i);
			}
			else {
				s0_genome[i] = p0_genome[i];
				s1_genome[i] = p1_genome[i];
			}
		}
		// Create sons, putting them in s
		sonsBuffer.add(species.createIndividual(s0_genome));
		sonsBuffer.add(species.createIndividual(s1_genome));
	}
	
	// Recombination method

	/**
	 * Genotype level recombination.
	 * 
	 * @param p0_genome First  parent chromosome
	 * @param p1_genome Second parent chromosome
	 * @param s0_genome First  son chromosome
	 * @param s1_genome Second son chromosome
	 */
	
	protected abstract void recombineLocus(double[] p0_genome, double[] p1_genome, 
										   double[] s0_genome, double[] s1_genome,
										   int locusIndex);
}
