package net.sf.jclec.binarray;

import java.util.ArrayList;

import net.sf.jclec.ISystem;
import net.sf.jclec.Population;
import net.sf.jclec.IIndividual;
import net.sf.jclec.IMutatorTest;

import net.sf.jclec.util.random.DummyRandGenFactory;

/**
 * Unitary test for class that extends BinArrayMutator.
 * 
 * @author Sebastian Ventura
 *
 * @param <M> Type of Mutator
 */

public abstract class BinArrayMutatorTest<M extends BinArrayMutator> extends IMutatorTest<M> 
{
	public BinArrayMutatorTest(Class<M> toolClass, String testName) 
	{
		super(toolClass, testName);
	}

	@Override
	protected ISystem createContext() 
	{
		// System that act as context
		Population pop = new Population();
		// Dummy random generators factory
		DummyRandGenFactory randGenFactory = new DummyRandGenFactory();
		randGenFactory.setDummySequence(new double [] {0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9});
		// Assign random generators factory to this system		
		pop.setRandGenFactory(randGenFactory);
		// System species
		BinArrayIndividualSpecies species = 
			new BinArrayIndividualSpecies(new byte[] {-1, -1, -1, -1, -1, -1});
		// Assign species to this system
		pop.setSpecies(species);
		// return pop
		return pop;
	}

	/**
	 * Mutant is (1,1,1,1,1,1)
	 * 
	 * {@inheritDoc}
	 */
	
	@Override
	protected void createParents() 
	{
		parents = new ArrayList<IIndividual> ();
		parents.add(new BinArrayIndividual(new byte [] {1, 1, 1, 1, 1, 1}));
	}
}
