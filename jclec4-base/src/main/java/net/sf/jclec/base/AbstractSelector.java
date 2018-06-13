package net.sf.jclec.base;

import java.util.List;
import java.util.ArrayList;

import net.sf.jclec.IIndividual;
import net.sf.jclec.IPopulation;
import net.sf.jclec.ISelector;
import net.sf.jclec.ISystem;

/**
 * ISelector abstract implementation.
 * 
 * @author Sebastian Ventura
 */

public abstract class AbstractSelector implements ISelector 
{
	private static final long serialVersionUID = -3732046358227327268L;
	
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------- Internal variables
	/////////////////////////////////////////////////////////////////

	// Operator state
	
	/** Execution context */
	
	protected IPopulation context;
	
	// Auxiliary variables
	
	/** Source set */
	
	protected transient List<IIndividual> actsrc;
	
	/** Source set size */
	
	protected transient int actsrcsz;
	
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Empty selector.
	 */
	
	public AbstractSelector() 
	{
		super();
	}

	/**
	 * Constructor that contextualizes selector. This method is used
	 * in algorithms internally.
	 */
	
	public AbstractSelector(ISystem context) 
	{
		super();
		contextualize(context);
	}

	/////////////////////////////////////////////////////////////////
	// ----------------------------- Implementing ISelector interface
	/////////////////////////////////////////////////////////////////
		
	/**
	 * {@inheritDoc}
	 */
	
	public void contextualize(ISystem context)
	{
		if(context instanceof IPopulation) {
			this.context = (IPopulation) context;
		}
		else {
			throw new IllegalArgumentException
				("This object uses a population as execution context");
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	
	public List<IIndividual> select(List<IIndividual> src) 
	{
		return select(src, src.size());
	}

	/**
	 * {@inheritDoc}
	 */
	
	public List<IIndividual> select(List<IIndividual> src, int nofsel) 
	{
		return select(src, nofsel, true);
	}
	
	public List<IIndividual> select(List<IIndividual> src, int nofsel, boolean repeat) 
	{
		// Sets source set and actsrcsz
		actsrc = src; actsrcsz = src.size();
		// Prepare selection process
		prepareSelection();
		// Performs selection of n individuals
		ArrayList<IIndividual> result = new ArrayList<IIndividual>();
		for (int i=0; i<nofsel; i++) {
			IIndividual selected = selectNext(); 
			if (!repeat) {
				while (result.contains(selected)) {
					selected = selectNext();
				}
			}
			result.add(selected);		
		}		
		// Returns selection
		return result;
	}

	/* 
	 * La implementacin por defecto de la interfaz IRecombinator realiza las 
	 * siguientes acciones:
	 * 
	 * 1) Asigna a la variable actualSource el conjunto fuente pasado como 
	 *    argumento.
	 * 2) Llama al mtodo prepareSelection(), que prepara la operacin de 
	 *    seleccin
	 * 3) Invoca al mtodo selectNext() tantas veces como individuos haya que
	 *    seleccionar. Este mtodo es responsable de seleccionar un nuevo 
	 *    individuo y de colocarlo en el buffer de seleccionados
	 * 4) Devolver la lista resultante  
	 */

	/////////////////////////////////////////////////////////////////
	// -------------------------------------------- Protected methods
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Prepare the selection process.
	 */
	
	protected abstract void prepareSelection();
	
	/**
	 * Selects an individual (in the context of a selection process).
	 * 
	 * @return Selected individual
	 */
	
	protected abstract IIndividual selectNext();
}
