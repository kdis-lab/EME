package net.sf.jclec.base;

import java.util.ArrayList;
import java.util.List;

import net.sf.jclec.IIndividual;
import net.sf.jclec.ISystem;
import net.sf.jclec.IToolTest;
import net.sf.jclec.Population;
import net.sf.jclec.binarray.BinArrayIndividual;
import net.sf.jclec.binarray.BinArrayIndividualSpecies;
import net.sf.jclec.binarray.mut.OneLocusMutator;
import net.sf.jclec.fitness.SimpleValueFitness;
import net.sf.jclec.util.random.DummyRandGenFactory;

public class FilteredMutatorTest extends IToolTest<FilteredMutator> 
{
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////

	public FilteredMutatorTest(String testName) 
	{
		super(FilteredMutator.class, testName);
	}

	/////////////////////////////////////////////////////////////////
	// ----------------------------------------------- Public methods
	/////////////////////////////////////////////////////////////////

	// FilteredMutator tests
	
	/**
	 * Unit test for mutate() method
	 */
	
	public void testMutate()
	{
		// Parents used in test
		ArrayList<IIndividual> parents = new ArrayList<IIndividual> ();
		parents.add(new BinArrayIndividual(new byte[] {1, 1, 1, 1, 1, 1}, new SimpleValueFitness(0.25)));
		parents.add(new BinArrayIndividual(new byte[] {1, 0, 0, 0, 0, 0}, new SimpleValueFitness(0.05)));
		parents.add(new BinArrayIndividual(new byte[] {1, 0, 0, 0, 0, 1}, new SimpleValueFitness(0.75)));
		parents.add(new BinArrayIndividual(new byte[] {1, 0, 0, 0, 1, 0}, new SimpleValueFitness(0.85)));
		parents.add(new BinArrayIndividual(new byte[] {1, 0, 0, 1, 1, 0}, new SimpleValueFitness(0.65)));
		parents.add(new BinArrayIndividual(new byte[] {1, 0, 1, 1, 0, 0}, new SimpleValueFitness(0.15)));
		parents.add(new BinArrayIndividual(new byte[] {1, 1, 1, 0, 0, 0}, new SimpleValueFitness(0.45)));
		parents.add(new BinArrayIndividual(new byte[] {1, 1, 0, 1, 1, 1}, new SimpleValueFitness(0.55)));
		parents.add(new BinArrayIndividual(new byte[] {1, 0, 0, 0, 1, 1}, new SimpleValueFitness(0.65)));
		parents.add(new BinArrayIndividual(new byte[] {1, 0, 1, 1, 0, 0}, new SimpleValueFitness(0.95)));
		// Call mutate method 
		tool.mutate(parents);
		// Check elements of fertile set
		assertEquals(tool.getFertile().size(), 4);
		List<IIndividual> fertile = tool.getFertile();
		assertEquals(fertile.get(0), parents.get(0));
		assertEquals(fertile.get(1), parents.get(1));
		assertEquals(fertile.get(2), parents.get(8));
		assertEquals(fertile.get(3), parents.get(9));
		// Check elements of sterile set
		assertEquals(tool.getSterile().size(), 6);
		List<IIndividual> sterile = tool.getSterile();
		assertEquals(sterile.get(0), parents.get(2));
		assertEquals(sterile.get(1), parents.get(3));
		assertEquals(sterile.get(2), parents.get(4));
		assertEquals(sterile.get(3), parents.get(5));
		assertEquals(sterile.get(4), parents.get(6));
		assertEquals(sterile.get(5), parents.get(7));
	}	
	
	/////////////////////////////////////////////////////////////////
	// -------------------------------------------- Protected methods
	/////////////////////////////////////////////////////////////////

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
			new BinArrayIndividualSpecies(new byte[] {1, -1, -1, -1, -1, -1});
		// Assign species to this system
		pop.setSpecies(species);
		// Return population
		return pop;
	}

	@Override
	protected void initTool() 
	{
		// Set decorated mutator
		OneLocusMutator decorated = new OneLocusMutator();
		tool.setDecorated(decorated);		
		// Set mutation probability
		tool.setMutProb(0.35);
	}

}
