package net.sf.jclec.base;

import java.util.List;

import net.sf.jclec.IEvaluator;
import net.sf.jclec.IIndividual;

/**
 * IEvaluator abstract implementation. 
 * 
 * @author Sebastian Ventura 
 */

@SuppressWarnings("serial")
public abstract class AbstractEvaluator implements IEvaluator 
{
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------- Internal variables
	/////////////////////////////////////////////////////////////////
	
	protected int numberOfEvaluations = 0;
	
	public long executionTime = 0;
	
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Empty constructor.
	 */
	
	public AbstractEvaluator() 
	{
		super();
	}

	/////////////////////////////////////////////////////////////////
	// ----------------------------------------------- Public methods
	/////////////////////////////////////////////////////////////////

	// IEvaluator interface
	
	/**
	 * {@inheritDoc}
	 */
	
	public int getNumberOfEvaluations() 
	{
		return numberOfEvaluations;
	}

	/**
	 * For all individuals in "inds" array: if individual fitness  is
	 * null, then evaluate this individual.
	 * 
	 * This method is final. Is anyone wants implement this method in
	 * another way should create a new IEvaluator class.
	 * 
	 * {@inheritDoc}
	 */
	
	public void evaluate(List<IIndividual> inds) 
	{
		long time = System.currentTimeMillis();
		
		for (IIndividual ind : inds) {
			if (ind.getFitness() == null) {
				evaluate(ind);
				numberOfEvaluations++;
			}
		}
		executionTime += System.currentTimeMillis() - time;
	}
	
	/////////////////////////////////////////////////////////////////
	// -------------------------------------------- Protected methods
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Individual evaluation method.
	 * 
	 * @param ind Individual to evaluate
	 */
	
	protected abstract void evaluate(IIndividual ind);
}
  