package net.sf.jclec.problem.classification.multilabel.rec;

import net.sf.jclec.IConfigure;

import net.sf.jclec.binarray.BinArrayIndividual;
import net.sf.jclec.binarray.BinArrayRecombinator;

import org.apache.commons.configuration.Configuration;

import org.apache.commons.lang.builder.EqualsBuilder;


public class UniformModelCrossover extends BinArrayRecombinator implements IConfigure
{
	/////////////////////////////////////////////////////////////////
	// --------------------------------------- Serialization constant
	/////////////////////////////////////////////////////////////////

	/**	Generated by eclipse */
	
	private static final long serialVersionUID = 3258697598016829492L;

	/////////////////////////////////////////////////////////////////
	// --------------------------------------------------- Properties
	/////////////////////////////////////////////////////////////////
	
	private double locusCrossoverProb;
	
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
	
	public void setNumberLabels(int numberLabels) {
		this.numberLabels = numberLabels;
	}

	/**
	 * @return Returns the locus crossover probability
	 */
	
	public final double getLocusCrossoverProb() 
	{
		return locusCrossoverProb;
	}
	
	/**
	 * @param crossProb New locus crossover probability
	 */
	
	public final void setLocusCrossoverProb(double crossProb) 
	{
		this.locusCrossoverProb = crossProb;
	}
	
	// IConfigure interface

	/**
	 * Configuration method.
	 * 
	 * Configuration parameters for UniformCrossover are:
	 * 
	 * <ul>
	 * <li>
	 * <code>[@evaluate]: boolean (default = true)</code></p> 
	 * If this parameter is set to <code>true</true> individuals will
	 * be evaluated after its creation. 
	 * </li>
	 * <li>
	 * <code>[@locus-crossover-prob]: double (default = 0.5)</code></p>
	 * Locus crossover probability.  
	 * </li>
	 * <li>
	 * <code>random-generator: complex</code></p>
	 * Random generator used in individuals mutation.  
	 * </li>
	 * </ul>
	 */

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