package net.sf.jclec.base;


import java.util.List;
import java.util.ArrayList;

import net.sf.jclec.ISystem;
import net.sf.jclec.IIndividual;
import net.sf.jclec.IPopulation;
import net.sf.jclec.IRecombinator;

import net.sf.jclec.util.random.IRandGen;

/**
 * IRecombinator abstract implementation.
 * 
 * @author Sebastian Ventura
 */

public abstract class AbstractRecombinator implements IRecombinator 
{
	private static final long serialVersionUID = 7099041555394753140L;
	
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------- Internal variables 
	/////////////////////////////////////////////////////////////////
	
	// Operator state

	/** Execution context */
		
	protected IPopulation context;
		
	/** Random generator used in mutation */
	
	protected IRandGen randgen;
	
	/** Parents per litter */
	
	protected transient int ppl;
	
	/** Sons per litter */
	
	protected transient int spl;

	// Auxiliary variables
	
	/** Parents buffer. Used by the litter recombination method */
	
	protected transient List<IIndividual> parentsBuffer;
	
	/** Sons buffer. Used by the litter recombination method */

	protected transient List<IIndividual> sonsBuffer;
	
	/** Parent counter */
	
	protected transient int parentsCounter;
	
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Empty constructor.
	 */
	
	public AbstractRecombinator() 
	{
		super();
		setPpl();
		setSpl();		
	}

	/////////////////////////////////////////////////////////////////
	// ----------------------------------------------- Public methods
	/////////////////////////////////////////////////////////////////

	// IRecombinator interface
	
	/**
	 * {@inheritDoc}
	 */
	
	public final void contextualize(ISystem context)
	{
		if(context instanceof IPopulation) {
			this.context = (IPopulation) context;
		}
		else {
			throw new IllegalArgumentException("This object uses a population as execution context");
		}
		// Attach a random generator to this object
		if (randgen == null) {
			this.randgen = context.createRandGen();
		}
	}
	
	/**
     * @return Number of parents per litter.
     */
	
	public final int getPpl()
	{
		return ppl;
	}
	
    /**
     * @return Number of sons per litter.
     */

	public final int getSpl()
	{
		return spl;
	}

	/**
	 * {@inheritDoc}
	 */
	
	public List<IIndividual> recombine(List<IIndividual> parents) 
	{
		// Sets p list to actual parents
		parentsBuffer = parents;
		// Prepare recombination process
		prepareRecombination();
		// Create a new list to put sons in it
		sonsBuffer = new ArrayList<IIndividual> ();
    	// For all individuals in "parents" ...
    	for (parentsCounter = 0; parentsCounter <= parents.size() - ppl; parentsCounter += ppl) {
			recombineNext();
    	}
		// Returns sons list
		return sonsBuffer;
	}

	/* 
	 * La implementacin por defecto de la interfaz IRecombinator realiza las 
	 * siguientes acciones:
	 * 
	 * 1) Asigna a la variable parentsBuffer la lista de padres pasada como 
	 *    argumento.
	 * 2) Llama al mtodo prepareRecombination(), que prepara la operacin de 
	 *    recombinacin
	 * 3) Crea una lista de individuos y la asigna a la variable sonsBuffer
	 * 4) Invoca al mtodo recombineNext() tantas veces como grupos de cra
	 *    haya en la lista de padres. Este mtodo es responsable de crear un 
	 *    nuevo grupo de individuos a partir de un grupo de padres (camada) 
	 *    y de colocarlo en el buffer de hijos
	 * 5) Devolver la lista de hijos resultante  
	 */

	/////////////////////////////////////////////////////////////////
	// -------------------------------------------- Protected methods
	/////////////////////////////////////////////////////////////////

	/**
     * Sets the ppl parameter (that  represents the number of parents
     * per litter).
     */
	 
	protected abstract void setPpl();
	
    /**
     * Sets the spl parameter (that represents the number of sons per
     * litter).
     */
	
	protected abstract void setSpl();
	
	/**
	 * Prepare recombination process.
	 */
	
	protected abstract void prepareRecombination();
	
	/**
	 * Atomic recombination method. This method ...
	 */
	
	protected abstract void recombineNext();
}
