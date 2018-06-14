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
 * Class implementing a crossover operator who exchanges n models between parents given one point
 */
public class MultiModelCrossover extends BinArrayRecombinator
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
	public MultiModelCrossover() 
	{
		super();
	}

	/////////////////////////////////////////////////////////////////
	// ----------------------------------------------- Public methods
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Set number of labels
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
		if (other instanceof MultiModelCrossover) {
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
		// Taking a number of models
		int nModels;
		nModels = randgen.choose(0, (gl/numberLabels)-1);
		
		// Taking a origin point
		int pModel;
		do{
			pModel = randgen.choose(0, (gl/numberLabels)-1);
		}while((pModel+nModels)>(gl/numberLabels)-1);
		
		for(int i = 0; i < (gl/numberLabels); i++)
		{
			if(i == pModel)
			{
				System.arraycopy(p1_genome, pModel*numberLabels,   s0_genome, i*numberLabels,  nModels*numberLabels);
				System.arraycopy(p0_genome, pModel*numberLabels,   s1_genome, i*numberLabels,  nModels*numberLabels);
				i=i+nModels-1;
			}
			else
			{
				System.arraycopy(p0_genome, i*numberLabels,   s0_genome, i*numberLabels,  numberLabels);
				System.arraycopy(p1_genome, i*numberLabels,   s1_genome, i*numberLabels,  numberLabels);
			}
		}
		
		// Put sons in buffer
		sonsBuffer.add(species.createIndividual(s0_genome));
		sonsBuffer.add(species.createIndividual(s1_genome));
	}
}