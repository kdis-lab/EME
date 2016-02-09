package net.sf.jclec.problem.classification.multilabel.mut;

import net.sf.jclec.binarray.BinArrayIndividual;

public class VariablePhiBasedIntraModelMutator extends PhiBasedIntraModelMutator
{
	/////////////////////////////////////////////////////////////////
	// --------------------------------------- Serialization constant
	/////////////////////////////////////////////////////////////////

	private static final long serialVersionUID = 6016659866612917186L;

	
	
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////

	/**
	 * Empty constructor
	 */
	
	public VariablePhiBasedIntraModelMutator() 
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
			byte [] g = new byte[numberLabels];
			System.arraycopy(mgenome, i*numberLabels, g, 0, numberLabels);
			
			double [] mut = new double[numberLabels];
			
			int activeLabels = 0;

			for(int j=0; j<numberLabels; j++)
			{
				if(mgenome[j + i*numberLabels] == 0)
					mut[j] = calculateZeroBitProbability(g, j);
				else //mgenome[j] == 1
				{
					activeLabels++;
					mut[j] = calculateOneBitProbability(g, j);
				}
			}
			
//			for(int j=0; j<probabilities.length; j++)
//				System.out.print(g[j] + "," + probabilities[j] + "  ");
//			System.out.println();
			
			//Accumulate values
			double [] acc = new double[numberLabels];
			acc [0] = mut[0];
			for(int j=1; j<numberLabels; j++)
			{
				acc[j] = acc[j-1] + mut[j];
			}
			
			//Select the bit to mutate
			double rand = randgen.uniform(0, acc[numberLabels-1]);
			
			for(int j=0; j<numberLabels; j++)
			{
				if(acc[j] >= rand)
				{
					//Mutate
					if((mgenome[j + i*numberLabels] == 0) && (activeLabels < Math.ceil(Math.sqrt(numberLabels))))
						mgenome[j + i*numberLabels] = 1;
					else if( (mgenome[j + i*numberLabels] == 1) && (activeLabels > 2) )
						mgenome[j + i*numberLabels] = 0;
					break;
				}
			}
			
		}
		
		sonsBuffer.add(species.createIndividual(mgenome));
	}
	
	protected double calculateZeroBitProbability(byte[] genotype, int pos)
	{
		double probability = 0;
		
		for(int i=0; i<genotype.length; i++)
		{
			if(genotype[i] == 1)
				probability += Math.abs(phiMatrix[pos][i]);
		}
		
		return probability;
	}
	
	protected double calculateOneBitProbability(byte[] genotype, int pos)
	{
		double probability = 0;
		
		for(int i=0; i<genotype.length; i++)
		{
			if((genotype[i] == 1) && (i != pos))
			{
				//Less phi, More probability
				probability += (1 - Math.abs(phiMatrix[pos][i]));
			}
		}
		
		return probability;
	}
	
	public boolean equals(Object other)
	{
		if (other instanceof VariablePhiBasedIntraModelMutator) {
			return true;
		}
		else {
			return false;
		}
	}
}