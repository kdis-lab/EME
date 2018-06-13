package net.sf.jclec.base;

import java.util.List;
import java.util.ArrayList;


import net.sf.jclec.IIndividual;
import net.sf.jclec.IMutator;
import net.sf.jclec.IPopulation;
import net.sf.jclec.ISystem;
import net.sf.jclec.util.random.IRandGen;

/**
 * IMutator abstract implementation.
 * 
 * @author Sebastian Ventura
 */

public abstract class AbstractMutator implements IMutator 
{
	private static final long serialVersionUID = 3594355978189570706L;
	
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------- Internal variables 
	/////////////////////////////////////////////////////////////////
	
	// Operator state

	/** Execution context */
	
	protected IPopulation context;
		
	/** Random generator used in mutation */
	
	protected IRandGen randgen;

	// Auxiliary variables 
	
	/** Parents buffer. Used by the atomic mutation method */
	
	protected transient List<IIndividual> parentsBuffer;
	
	/** Sons buffer. Used by the atomic mutation method */

	protected transient List<IIndividual> sonsBuffer;
	
	/** Parent counter */
	
	protected transient int parentsCounter;
	
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Empty constructor.
	 */
	
	public AbstractMutator() 
	{
		super();
	}

	/////////////////////////////////////////////////////////////////
	// ------------------------------ Implementing IMutator interface
	/////////////////////////////////////////////////////////////////
		
	/**
	 * {@inheritDoc}
	 */
	
	public final void contextualize(ISystem context)
	{
		if(context instanceof IPopulation) {
			// Contextualize this operator
			this.context = (IPopulation) context;
			// Attach a random generator to this object
			this.randgen = context.createRandGen();
		}
		else {
			throw new IllegalArgumentException("This object uses a population as execution context");
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	
	public List<IIndividual> mutate(List<IIndividual> parents) 
	{
		// Sets p list to actual parents
		parentsBuffer = parents;
		// Prepare recombination process
		prepareMutation();
		// Create a new list to put sons in it
		sonsBuffer = new ArrayList<IIndividual> ();
    	// For all individuals in "parents" ...
    	for (parentsCounter = 0; parentsCounter < parents.size(); parentsCounter ++) {
			mutateNext();
    	}
		// Returns sons list
		return sonsBuffer;
	}

	/* 
	 * La implementacin por defecto de la interfaz IMutator realiza las siguientes
	 * acciones:
	 * 
	 * 1) Asigna a la variable parentsBuffer la lista de padres pasada como argumento
	 * 2) Llama al mtodo prepareMutation(), que prepara la operacin de mutacin
	 * 3) Crea una lista de individuos y la asigna a la variable sonsBuffer
	 * 4) Invoca al mtodo mutateNext() tantas veces como individuos haya en la lista
	 *    de padres. Este mtodo es responsable de crear un nuevo individuo a partir
	 *    del padre actual y de colocarlo en el buffer de hijos
	 * 5) Devolver la lista de hijos resultante  
	 */

	/////////////////////////////////////////////////////////////////
	// -------------------------------------------- Protected methods
	/////////////////////////////////////////////////////////////////

	/**
	 * Prepare mutation process.
	 */
	
	protected abstract void prepareMutation();
	
	/**
	 * Atomic mutation method. This method...
	 */
	
	protected abstract void mutateNext();
}
