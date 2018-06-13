package net.sf.jclec.problem.classification.multilabel.mut;

import net.sf.jclec.binarray.BinArrayIndividual;

public class VariableIntraModelMutator extends IntraModelMutator
{
	/////////////////////////////////////////////////////////////////
	// --------------------------------------- Serialization constant
	/////////////////////////////////////////////////////////////////
	
	private static final long serialVersionUID = -3558101125336263973L;
	
	
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Empty constructor
	 */
	
	public VariableIntraModelMutator() 
	{
		super();
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
		
		for (int i = 0; i < (gl/numberLabels); i++) 
		{
			int mp1 = randgen.choose(i*numberLabels, (i+1)*numberLabels);
			int mp2 = randgen.choose(i*numberLabels, (i+1)*numberLabels);
			
			if((mgenome[mp1]==0) && (mgenome[mp2]==0))
			{
				int max = (int) Math.ceil(Math.sqrt(numberLabels));
				int active = 0;
				for(int j=(i*numberLabels); j<((i+1)*numberLabels); j++)
				{
					if(mgenome[j]==1)
						active++;
				}
				
				//If active is not max, activates mp2
				if(active < max)
					mgenome[mp2] = 1;
			}
			else if( ((mgenome[mp1]==0) && (mgenome[mp2]==1)) || ((mgenome[mp1]==1) && (mgenome[mp2]==0)) )
			{				
				// Swap
				byte aux = mgenome[mp1];
				mgenome[mp1] = mgenome[mp2];
				mgenome[mp2] = aux;
			}
			else if((mgenome[mp1]==1) && (mgenome[mp2]==1))
			{
				int active = 0;
				for(int j=(i*numberLabels); j<((i+1)*numberLabels); j++)
				{
					if(mgenome[j]==1)
						active++;
				}
				
				//If active is not min(2)
				if(active > 2)
					mgenome[mp2] = 0;
			}
			
		}
		
		sonsBuffer.add(species.createIndividual(mgenome));
	}
	
	public boolean equals(Object other)
	{
		if (other instanceof VariableIntraModelMutator) {
			return true;
		}
		else {
			return false;
		}
	}
}