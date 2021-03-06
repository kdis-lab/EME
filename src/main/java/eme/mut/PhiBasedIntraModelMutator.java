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

/**
 * @author Jose M. Moyano: jmoyano@uco.es
 *
 * Class implementing the phi-based mutator
 */
public class PhiBasedIntraModelMutator extends IntraModelMutator
{
	/////////////////////////////////////////////////////////////////
	// --------------------------------------- Serialization constant
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Serialization constant
	 */
	private static final long serialVersionUID = 8453875747867690100L;
	
	
	/**
	 *  Matrix with Phi correlations between labels */
	double [][] phiMatrix;
	
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Empty constructor
	 */	
	public PhiBasedIntraModelMutator() 
	{
		super();
	}

	/**
	 * Set the matrix with phi values between pairs of labels
	 * @param matrix Matrix with phi values
	 */
	public void setPhiMatrix(double [][] matrix)
	{
		phiMatrix = matrix;
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
		
		
		/*
		 * The 'mut' array includes the sum of Phi correlation values of each '0' bit with all '1' bits
		 * To that sum, we add 0.1, so the not correlated labels will have a little chance to mutate
		 */
		double [] mut = new double [mgenome.length];
		
		for(int i=0; i<mut.length; i++)
		{
			//If there is no correlation, there is a little probability
			mut[i] = 0.1;
		}
		
		//For each base classifier
		int mp1;
		double [] acc = new double[numberLabels];
		double rand;
		
		for (int i = 0; i < (gl/numberLabels); i++) 
		{
			//Choose a '1'
			do{
				mp1 = randgen.choose(i*numberLabels, (i+1)*numberLabels);
			}while(mgenome[mp1] != 1);
			
			//For each '0', we add phi[0_position][1_position] to mut[0_position]
			for(int j=i*numberLabels; j<(i+1)*numberLabels; j++)
			{
				if(mgenome[j] == 0)
				{
					for(int k=i*numberLabels; k<(i+1)*numberLabels; k++)
					{
						//Obtain Phi value for this '0' bit with the other '1' bits
						if((mgenome[k] == 1) && (k!=mp1))
						{
							if(!Double.isNaN(phiMatrix[j-i*numberLabels][k-i*numberLabels]))
									mut[j] = mut[j] + Math.abs(phiMatrix[j-i*numberLabels][k-i*numberLabels]);
						}
					}
				}
				else
					mut[j] = 0;				
			}	

			//Accumulate values
			acc [0] = mut[i*numberLabels];
			for(int j=1; j<numberLabels; j++)
			{
				acc[j] = acc[j-1] + mut[j+i*numberLabels];
			}
			
			//Select the bit to mutate
			rand = randgen.uniform(0, acc[numberLabels-1]);

			for(int j=0; j<numberLabels; j++)
			{
				if(acc[j] >= rand)
				{
					//Mutate
					mgenome[mp1] = 0;
					mgenome[j + i*numberLabels] = 1;
					break;
				}
			}
			
		}
		
		sonsBuffer.add(species.createIndividual(mgenome));
	}

}
