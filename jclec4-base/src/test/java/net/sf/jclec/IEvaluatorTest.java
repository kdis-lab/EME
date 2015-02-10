package net.sf.jclec;

import java.util.List;

import junit.framework.TestCase;

/**
 * Evaluator tests.
 * 
 * @author Sebastian Ventura
 *
 * @param <E> Type of evaluator to test
 */

public abstract class IEvaluatorTest<E extends IEvaluator> extends TestCase 
{
	/////////////////////////////////////////////////////////////////
	// --------------------------------------------------- Attributes
	/////////////////////////////////////////////////////////////////
	
	/** Object class */
	
	protected final Class<E> evaluatorClass;
	
	/** Object to marshall */
	
	protected E evaluator; 

	/** Individual recombined in testRecombine() */
	
	protected List<IIndividual> individuals;

	/** Individual expected in testRecombine() */

	protected List<IFitness> expectedFitnesses;

	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////
		
	public IEvaluatorTest(Class<E> evaluatorClass, String name) 
	{
		super(name);
		this.evaluatorClass = evaluatorClass;
	}

	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------ Unitary tests
	/////////////////////////////////////////////////////////////////

	/**
	 * Unit test for the evaluate() method
	 */
	
	public void testEvaluate()
	{
		// Create individuals
		createIndividuals();
		// Create expected fitnesses
		createExpectedFitnesses();
		// Evaluate individuals
		evaluator.evaluate(individuals);
		// Compare fitness produced and expected
		int nofinds = individuals.size();
		for (int i=0; i<nofinds; i++) {
			assertEquals(expectedFitnesses.get(i), individuals.get(i).getFitness());
		}
	}
	
	/////////////////////////////////////////////////////////////////
	// --------------------------------- Protected (abstract methods)
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Create individuals to evaluate
	 */
	
	protected abstract void createIndividuals();
	
	/**
	 * Create expected fitnesses
	 */
	
	protected abstract void createExpectedFitnesses();
}
