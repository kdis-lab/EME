package net.sf.jclec.binarray.rec;

import java.util.ArrayList;

import net.sf.jclec.binarray.BinArrayIndividual;
import net.sf.jclec.binarray.BinArrayRecombinator;

/**
 * HUX Crossover. 
 * 
 * This is the crossover defined for binary individuals in the CHC algorithm.
 * 
 * @author Sebastian Ventura
 */

public class HUXCrossover extends BinArrayRecombinator 
{
	private static final long serialVersionUID = -8111605981236251894L;
	
	//////////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Private variables
	//////////////////////////////////////////////////////////////////////
	
	/* Stores crossable loci in recombineNext() method */
	
	private transient ArrayList<Integer> xloci = new ArrayList<Integer>();
	
	//////////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Protected methods
	//////////////////////////////////////////////////////////////////////

	// IRecombine interface
	
	@Override
	protected void recombineNext() 
	{
		// Genotype length
		int gl = species.getGenotypeLength();
		// Parents conversion
		BinArrayIndividual p0 = 
			(BinArrayIndividual) parentsBuffer.get(parentsCounter);
		BinArrayIndividual p1 = 
			(BinArrayIndividual) parentsBuffer.get(parentsCounter+1);
		// First parent's genotype
		byte [] p0_genotype = p0.getGenotype();
		// Second parent's genotype
		byte [] p1_genotype = p1.getGenotype();
		// First son's genotype
		byte [] s0_genotype = new byte[gl];
		// Second son's genotype
		byte [] s1_genotype = new byte[gl];
		// Set the list of genes that differs in parents
		xloci.clear();
		for (int i=0; i<gl; i++) {
			if (p0_genotype[i] != p1_genotype[i]) {
				xloci.add(i);
			}
			else {
				s0_genotype[i] = p0_genotype[i];
				s1_genotype[i] = p1_genotype[i];       					
			}
		}
		// Do HUX crossover
		for (int i : xloci) {
			if (randgen.coin()) {
				s0_genotype[i] = p1_genotype[i];
				s1_genotype[i] = p0_genotype[i];       					       					
			}
			else {
				s0_genotype[i] = p0_genotype[i];
				s1_genotype[i] = p1_genotype[i];       					
			}       				
		}
		sonsBuffer.add(species.createIndividual(s0_genotype));
		sonsBuffer.add(species.createIndividual(s1_genotype));	
	}

}
