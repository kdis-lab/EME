package net.sf.jclec.binarray;

import net.sf.jclec.ISpecies;

import net.sf.jclec.base.AbstractMutator;

/**
 * BinArrayIndividual (and subclasses) specific mutator.  
 * 
 * @author Sebastian Ventura
 */

public abstract class BinArrayMutator extends AbstractMutator 
{
	private static final long serialVersionUID = -2598591683615873232L;
	
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------ Operation variables
	/////////////////////////////////////////////////////////////////

	/** Individual species (taked from execution context) */
	
	protected transient BinArraySpecies species;
	
	/** Genotype schema */ 
	
	protected transient byte [] schema;
	
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////

	/**
	 * Empty (default) constructor.
	 */
	
	public BinArrayMutator() 
	{
		super();
	}

	/////////////////////////////////////////////////////////////////
	// ----------------------------------------------- Public methods
	/////////////////////////////////////////////////////////////////
	
	// AbstractMutator methods
	
	/**
	 * {@inheritDoc}
	 */
	
	@Override
	protected void prepareMutation() 
	{
		ISpecies species = context.getSpecies();
		if (species instanceof BinArraySpecies) {
			// Set individuals species
			this.species = (BinArraySpecies) species;
			// Sets genotype schema
			this.schema = this.species.getGenotypeSchema();
		}
		else {
			throw new IllegalStateException("Invalid species in context");
		}
	}

	/* 
	 * Este mtodo fija el schema que vamos a utilizar para mutar los genotipos
	 * de los nuevos individuos. Para ello, asegura que el objeto species que
	 * representa a los individuos de la poblacin es de tipo IBinArraySpecies.
	 * En caso negativo, lanza una excepcin.
	 */

	/////////////////////////////////////////////////////////////////
	// -------------------------------------------- Protected methods
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Gets a mutate locus in represented individuals
	 */
	
	protected final int getMutableLocus()
	{
		int gl = schema.length;
		int ml;
		do {
			ml = randgen.choose(0, gl);
		}
		while (schema[ml] != -1);
		// Return mutation point
		return ml;
	}
	
	/**
	 * Flip method.
	 * 
	 * @param chrom Chromosome affected
	 * @param locus Locus affected
	 */
	
	protected final void flip(byte [] chrom, int locus)
	{
		chrom[locus] = (chrom[locus] == 0) ? (byte) 1 : (byte) 0;  
	}
}
