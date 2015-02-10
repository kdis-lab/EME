package net.sf.jclec.binarray;

import java.util.ArrayList;

import net.sf.jclec.IIndividual;
import net.sf.jclec.IProviderTest;
import net.sf.jclec.ISystem;
import net.sf.jclec.Population;
import net.sf.jclec.util.random.DummyRandGenFactory;

public class BinArrayCreatorTest extends IProviderTest<BinArrayCreator> 
{
	public BinArrayCreatorTest(String testName)
	{
		super(BinArrayCreator.class, testName);
	}

	@Override
	protected ISystem createContext() 
	{
		// System that act as context
		Population pop =  new Population();
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

	@Override
	protected void initTool() 
	{
		// Do nothing
	}

	
	@Override
	protected void createExpected() 
	{
		expected = new ArrayList<IIndividual> ();
		expected.add(new BinArrayIndividual(new byte [] {0, 0, 0, 1, 1, 1}));
		expected.add(new BinArrayIndividual(new byte [] {1, 1, 0, 0, 0, 0}));
		expected.add(new BinArrayIndividual(new byte [] {1, 1, 1, 1, 1, 0}));
		expected.add(new BinArrayIndividual(new byte [] {0, 0, 0, 1, 1, 1}));
		expected.add(new BinArrayIndividual(new byte [] {1, 1, 0, 0, 0, 0}));
	}
}
