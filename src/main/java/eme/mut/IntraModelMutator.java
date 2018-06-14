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

package eme.mut;

import net.sf.jclec.binarray.BinArrayIndividual;
import net.sf.jclec.binarray.BinArrayMutator;

/**
 * @author Jose M. Moyano: jmoyano@uco.es
 *
 * Class implementing a basic mutator
 */
public class IntraModelMutator extends BinArrayMutator
{
	/////////////////////////////////////////////////////////////////
	// --------------------------------------- Serialization constant
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Serialization constant
	 */
	private static final long serialVersionUID = 4051327838076744754L;
	
	/////////////////////////////////////////////////////////////////
	// --------------------------------------------------- Properties
	/////////////////////////////////////////////////////////////////

	/**
	 * Number of labels
	 */
	protected int numberLabels;
	
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Empty constructor
	 */	
	public IntraModelMutator() 
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
	protected void mutateNext() 
	{
		// Individual to be mutated
		BinArrayIndividual mutant = (BinArrayIndividual) parentsBuffer.get(parentsCounter);
		// Genome length
		int gl = mutant.getGenotype().length;
		// Creates mutant genotype
		byte [] mgenome = new byte[gl];
		System.arraycopy(mutant.getGenotype(), 0, mgenome, 0, gl);
		
		int mp1, mp2;
		for (int i = 0; i < (gl/numberLabels); i++) 
		{
			mp1 = randgen.choose(i*numberLabels, (i+1)*numberLabels);
			do{
				mp2 = randgen.choose(i*numberLabels, (i+1)*numberLabels);
			}while(mp1==mp2);
			
			// Swap
			byte aux = mgenome[mp1];
			mgenome[mp1] = mgenome[mp2];
			mgenome[mp2] = aux;
		}
		
		sonsBuffer.add(species.createIndividual(mgenome));
	}
	
	/**
	 * Check if two mutators are of the same type
	 */
	public boolean equals(Object other)
	{
		if (other instanceof IntraModelMutator) {
			return true;
		}
		else {
			return false;
		}
	}
}