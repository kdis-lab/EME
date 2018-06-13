package net.sf.jclec.multiexprtree;

import java.util.ArrayList;

import net.sf.jclec.IIndividual;
import net.sf.jclec.IProviderTest;
import net.sf.jclec.ISystem;
import net.sf.jclec.Population;
import net.sf.jclec.exprtree.IPrimitive;
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

public class MultiExprTreeCreatorTest extends IProviderTest<MultiExprTreeCreator> 
{
	public MultiExprTreeCreatorTest(String testName) 
	{
		super(MultiExprTreeCreator.class, testName);
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
		MultiExprTreeIndividualSpecies species =	
			new MultiExprTreeIndividualSpecies();
		species.setNumberOfExprTrees(2);
		species.setMinTreeSize(0, 1);
		species.setMaxTreeSize(0, 30);
		species.setRootType(0, Double.class);
		species.setFunctions(0, new IPrimitive [] {new Add(), new Mul(), new Sub()});
		species.setTerminals(0, new IPrimitive [] {new X(), new Y(), new Z()});
		species.setMinTreeSize(1, 1);
		species.setMaxTreeSize(1, 10);
		species.setRootType(1, Double.class);
		species.setFunctions(1, new IPrimitive [] {new Add(), new Sub()});
		species.setTerminals(1, new IPrimitive [] {new X()});
		pop.setSpecies(species);
		// Return population
		return pop;
	}

	@Override
	protected void initTool() 
	{
		// Do nothing
	}
}