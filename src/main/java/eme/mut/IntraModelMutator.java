package eme.mut;

import net.sf.jclec.binarray.BinArrayIndividual;
import net.sf.jclec.binarray.BinArrayMutator;

public class IntraModelMutator extends BinArrayMutator
{
	/////////////////////////////////////////////////////////////////
	// --------------------------------------- Serialization constant
	/////////////////////////////////////////////////////////////////
	
	/** Generated by Eclipse */
	
	private static final long serialVersionUID = 4051327838076744754L;
	
	/////////////////////////////////////////////////////////////////
	// --------------------------------------------------- Properties
	/////////////////////////////////////////////////////////////////

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