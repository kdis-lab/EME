package net.sf.jclec.realarray.rec;

import net.sf.jclec.IPopulation;

import net.sf.jclec.realarray.RealArrayIndividual;
import net.sf.jclec.realarray.UniformCrossover;

/**
 * Two parents and one son crossover.
 *  
 * @author Sebastian Ventura
 */

@SuppressWarnings("serial")
abstract class UniformCrossover2x1 extends UniformCrossover
{
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Empty constructor.
	 */
	
	public UniformCrossover2x1() 
	{
		super();
	}

	/**
	 * Constructor that sets execution context.
	 * 
	 * @param population Execution context
	 */
	
	public UniformCrossover2x1(IPopulation population) 
	{
		super();
	}

	/////////////////////////////////////////////////////////////////
	// -------------------------------------------- Protected methods
	/////////////////////////////////////////////////////////////////
	
	// AbstractRecombinator methods
	
	/** Set spl=1 */
	
	@Override
	protected void setPpl()
	{
		ppl = 2;
	}
	
	/** Set ppl = 2 */
	
	@Override
	protected void setSpl()
	{
		spl = 1;
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
		// Do recombination operation
		for (int i=0; i<genotypeLength; i++) {
			if (randgen.coin(locusRecProb)) {
				recombineLocus(p0_genome, p1_genome, s0_genome, i);
			}
			else {
				s0_genome[i] = p0_genome[i];
			}
		}
		// Create sons, putting them in s
		sonsBuffer.add(species.createIndividual(s0_genome));
	}

	// Recombination method
	
	/**
	 * Genotype level recombination.
	 * 
	 * @param p0_genome First  parent chromosome
	 * @param p1_genome Second parent chromosome
	 * @param s0_genome First  son chromosome
	 * @param locusIndex Locus affected by recombination
	 */
	
	protected abstract void recombineLocus(double[] p0_genome, double[] p1_genome, 
										   double[] s0_genome, 
										   int locusIndex);
}
