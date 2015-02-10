package net.sf.jclec.exprtree;

import java.util.ArrayList;

import net.sf.jclec.IIndividual;
import net.sf.jclec.IProviderTest;
import net.sf.jclec.ISystem;
import net.sf.jclec.Population;
import net.sf.jclec.exprtree.fun.Add;
import net.sf.jclec.exprtree.fun.Mul;
import net.sf.jclec.exprtree.fun.Sub;
import net.sf.jclec.exprtree.fun.X;
import net.sf.jclec.exprtree.fun.Y;
import net.sf.jclec.exprtree.fun.Z;
import net.sf.jclec.util.random.DummyRandGenFactory;

/**
 * ExprTreeCreator tests.
 * 
 * @author Sebastian Ventura
 */

public class ExprTreeCreatorTest extends IProviderTest<ExprTreeCreator> 
{
	public ExprTreeCreatorTest(String testName) 
	{
		super(ExprTreeCreator.class, testName);
	}

	@Override
	protected void createExpected() 
	{
		expected = new ArrayList<IIndividual> ();
		// TODO Finish implementation
	}

	@Override
	protected ISystem createContext() 
	{
		Population pop = new Population();
		// Random generators factory
		DummyRandGenFactory randGenFactory = new DummyRandGenFactory();
		randGenFactory.setDummySequence
			(new double [] {0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9});
		pop.setRandGenFactory(randGenFactory);
		// Individuals species		
		ExprTreeIndividualSpecies species =	new ExprTreeIndividualSpecies();
		species.setMinTreeSize(1);
		species.setMaxTreeSize(30);
		species.setRootType(Double.class);
		species.setFunctions(new IPrimitive [] {new Add(), new Mul(), new Sub()});
		species.setTerminals(new IPrimitive [] {new X(), new Y(), new Z()});
		pop.setSpecies(species);
		// Return population
		return pop;
	}

	@Override
	protected void initTool() 
	{
		// Do nothing
	}

/*
 	public void testTonto()
	{
		List<IIndividual> inds = object1.provide(50);
		for (IIndividual ind : inds) {
			System.out.println(ind);
		}
	}
*/
}
