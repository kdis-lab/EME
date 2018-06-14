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

import net.sf.jclec.IConfigure;

import net.sf.jclec.binarray.BinArrayIndividual;
import net.sf.jclec.binarray.BinArrayRecombinator;

import org.apache.commons.configuration.Configuration;

import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * @author Jose M. Moyano: jmoyano@uco.es
 *
 * Class implementing a crossover operator who exchanges many models between parents with a probability for each of them
 */
public class UniformModelCrossover extends BinArrayRecombinator implements IConfigure
{
	/////////////////////////////////////////////////////////////////
	// --------------------------------------- Serialization constant
	/////////////////////////////////////////////////////////////////

	/**
	 * Serialization constant
	 */
	private static final long serialVersionUID = 3258697598016829492L;

	/////////////////////////////////////////////////////////////////
	// --------------------------------------------------- Properties
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Crossover probability for each of the models inside the individual
	 */
	private double locusCrossoverProb;
	
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
	public UniformModelCrossover() 
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
	 * Get the probability to crossover each of the models inside and individual
	 * 
	 * @return Probability to crossover each model
	 */
	public final double getLocusCrossoverProb() 
	{
		return locusCrossoverProb;
	}
	
	/**
	 * Set the probability to crossover each of the models inside and individual
	 * 
	 * @param crossProb Probability to crossover each model
	 */
	public final void setLocusCrossoverProb(double crossProb) 
	{
		this.locusCrossoverProb = crossProb;
	}
	
	// IConfigure interface

	@Override
	public void configure(Configuration configuration) 
	{
		// Get the 'locus-crossover-prob' property
		double locusCrossoverProb = configuration.getDouble("[@locus-crossover-prob]", 0.5);
		setLocusCrossoverProb(locusCrossoverProb);
	}
	
	/**
	 * {@inheritDoc}
	 */	
	@Override
	public boolean equals(Object other)
	{
		if (other instanceof UniformModelCrossover) {
			// Type conversion
			UniformModelCrossover cother = (UniformModelCrossover) other;
			// Equals Buildre
			EqualsBuilder eb = new EqualsBuilder();
			eb.append(locusCrossoverProb, cother.locusCrossoverProb);
			// Returns 
			return eb.isEquals();
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
		// Genotype length
		int gl = p0_genome.length;
		// Creating sons genotypes
		byte [] s0_genome = new byte[gl];
		byte [] s1_genome = new byte[gl];
		// Building sons
		for (int i=0; i<(gl/numberLabels); i++) 
		{
			if (randgen.coin(locusCrossoverProb)) {
				System.arraycopy(p1_genome, i*numberLabels,   s0_genome, i*numberLabels,  numberLabels);
				System.arraycopy(p0_genome, i*numberLabels,   s1_genome, i*numberLabels,  numberLabels);
			}
			else {
				System.arraycopy(p0_genome, i*numberLabels,   s0_genome, i*numberLabels,  numberLabels);
				System.arraycopy(p1_genome, i*numberLabels,   s1_genome, i*numberLabels,  numberLabels);
			}
		}
		
		// Put sons in son buffer
		sonsBuffer.add(species.createIndividual(s0_genome));
		sonsBuffer.add(species.createIndividual(s1_genome));	
	}
}