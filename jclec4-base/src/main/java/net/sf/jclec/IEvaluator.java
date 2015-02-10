package net.sf.jclec;

import java.util.Comparator;
import java.util.List;

/**
 * Individuals evaluator.
 * 
 * @author Sebastian Ventura
 * 
 * @see net.sf.jclec.IFitness
 */

public interface IEvaluator extends JCLEC
{
	/**
	 * Evaluation method.
	 * 
	 * @param inds Individuals to evaluate
	 */
	
	public void evaluate(List<IIndividual> inds);
	
	/**
	 * Get the number of individuals evaluated until now.
	 * 
	 * @return Number of evaluations until now
	 */
	
	public int getNumberOfEvaluations();
	
	/**
	 * Access to fitness comparator.
	 * 
	 * @return Actual fitness comparator
	 */
	
	public Comparator<IFitness> getComparator();
}
