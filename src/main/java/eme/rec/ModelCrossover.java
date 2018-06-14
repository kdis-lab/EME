/*
 * This file is part of EME algorithm.
 *
 * (c)  Jose Maria Moyano Murillo <jmoyano@uco.es>
 *      Eva Lucrecia Gibaja Galindo <egibaja@uco.es>
 *      Sebastian Ventura Soto <sventura@uco.es>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package eme.rec;

import net.sf.jclec.binarray.BinArrayIndividual;
import net.sf.jclec.binarray.BinArrayRecombinator;

/**
 * @author Jose M. Moyano: jmoyano@uco.es
 *
 * Class implementing a crossover operator who exchanges models with one crossover point between parents
 */
public class ModelCrossover extends BinArrayRecombinator
{
	/////////////////////////////////////////////////////////////////
	// --------------------------------------- Serialization constant
	/////////////////////////////////////////////////////////////////

	/**
	 * Serialization constant
	 */
	private static final long serialVersionUID = 3835150645048325173L;	
	
	/////////////////////////////////////////////////////////////////
	// --------------------------------------------------- Properties
	/////////////////////////////////////////////////////////////////

	/**
	 * Number of labels
	 */
	private int numberLabels;
	
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////

	/**
	 * Empty constructor
	 */	
	public ModelCrossover() 
	{
		super();
	}

	/////////////////////////////////////////////////////////////////
	// ----------------------------------------------- Public methods
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Set the number of labels
	 * 
	 * @param numberLabels Number of labels
	 */
	public void setNumberLabels(int numberLabels) {
		this.numberLabels = numberLabels;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object other)
	{
		if (other instanceof ModelCrossover) {
			return true;
		}
		else {
			return false;
		}
	}	

	/////////////////////////////////////////////////////////////////
	// -------------------------------------------- Protected methods
	/////////////////////////////////////////////////////////////////

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void recombineNext() 
	{
		// Parents conversion
		BinArrayIndividual p0 = (BinArrayIndividual) parentsBuffer.get(parentsCounter);
		BinArrayIndividual p1 = (BinArrayIndividual) parentsBuffer.get(parentsCounter+1);
		// Parents genotypes
		byte [] p0_genome = p0.getGenotype();
		byte [] p1_genome = p1.getGenotype();
		int gl = p0_genome.length;
		// Creating sons genotypes
		byte [] s0_genome = new byte[gl];
		byte [] s1_genome = new byte[gl];
		// Taking a crossover point
		int cp1, cp2;
		cp1 = randgen.choose(0, (gl/numberLabels));
		cp2 = randgen.choose(0, (gl/numberLabels));
		
		// First son		
		for(int i = 0; i < (gl/numberLabels); i++)
		{
			if(i == cp1)
				System.arraycopy(p1_genome, cp1*numberLabels,   s0_genome, i*numberLabels,  numberLabels);
			else
				System.arraycopy(p0_genome, i*numberLabels,   s0_genome, i*numberLabels,  numberLabels);
		}
		
		// Second son		
		for(int i = 0; i < (gl/numberLabels); i++)
		{
			if(i == cp2)
				System.arraycopy(p0_genome, cp2*numberLabels,   s1_genome, i*numberLabels,  numberLabels);
			else
				System.arraycopy(p1_genome, i*numberLabels,   s1_genome, i*numberLabels,  numberLabels);
		}
		
		// Put sons in buffer
		sonsBuffer.add(species.createIndividual(s0_genome));
		sonsBuffer.add(species.createIndividual(s1_genome));
	}
}