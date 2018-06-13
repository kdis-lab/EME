package net.sf.jclec.intarray;

import java.util.ArrayList;

import net.sf.jclec.IIndividual;
import net.sf.jclec.IRecombinatorTest;
import net.sf.jclec.ISystem;
import net.sf.jclec.Population;
import net.sf.jclec.util.intset.Closure;
import net.sf.jclec.util.intset.IIntegerSet;
import net.sf.jclec.util.intset.Interval;
import net.sf.jclec.util.random.DummyRandGenFactory;

public abstract class IntArrayRecombinatorTest<R extends IntArrayRecombinator> extends IRecombinatorTest<R> 
{
	public IntArrayRecombinatorTest(Class<R> toolClass, String testName) 
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
		IntArrayIndividualSpecies species = 
			new IntArrayIndividualSpecies
				(new IIntegerSet[] {
						new Interval(0, 10, Closure.ClosedClosed),
						new Interval(0, 10, Closure.ClosedClosed),
						new Interval(0, 10, Closure.ClosedClosed),
						new Interval(0, 10, Closure.ClosedClosed),
						new Interval(0, 10, Closure.ClosedClosed),
						new Interval(0, 10, Closure.ClosedClosed)
					}
				);
		// Assign species to this system
		pop.setSpecies(species);
		// Sets "mutationContext" property
		return pop;
	}

	@Override
	protected void createParents() 
	{
		parents = new ArrayList<IIndividual> ();
		parents.add(new IntArrayIndividual(new int [] {0,0,0,0,0,0}));
		parents.add(new IntArrayIndividual(new int [] {1,1,1,1,1,1}));
	}
}
