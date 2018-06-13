package net.sf.jclec.exprtree;

import net.sf.jclec.ISystem;
import net.sf.jclec.Population;
import net.sf.jclec.IMutatorTest;

import net.sf.jclec.exprtree.fun.Add;
import net.sf.jclec.exprtree.fun.Mul;
import net.sf.jclec.exprtree.fun.Sub;
import net.sf.jclec.exprtree.fun.X;
import net.sf.jclec.exprtree.fun.Y;
import net.sf.jclec.exprtree.fun.Z;

import net.sf.jclec.util.random.DummyRandGenFactory;

/**
 * ExprTreeMutator tests.
 * 
 * @author Amelia Zafra
 * @author Sebastian Ventura
 */

public abstract class ExprTreeMutatorTest extends IMutatorTest<ExprTreeMutator> 
{
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Default constructor.
	 */
	
	public ExprTreeMutatorTest(String name) 
	{
		super(ExprTreeMutator.class, name);
	}
	
	/////////////////////////////////////////////////////////////////
	// ----------------------------- Overwriting IToolTest methods
	/////////////////////////////////////////////////////////////////

	@Override
	protected ISystem createContext() 
	{
		Population pop = new Population();
		// Random generators factory
		DummyRandGenFactory randGenFactory = new DummyRandGenFactory();
		randGenFactory.setDummySequence
			(new double [] {0.2, 0.4, 0.5, 0.7, 0.9, 0.1});
//		(new double [] {0.1, 0.2, 0.4, 0.5, 0.7, 0.9});
		pop.setRandGenFactory(randGenFactory);
		// Individuals species
		ExprTreeIndividualSpecies species = 
			new ExprTreeIndividualSpecies();
		species.setMinTreeSize(1);
		species.setMaxTreeSize(10);
		species.setRootType(Double.class);
		species.setTerminals(new IPrimitive [] {new X(), new Y(), new Z()});
		species.setFunctions(new IPrimitive [] {new Add(), new Sub(), new Mul()});
		pop.setSpecies(species);
		return pop;
	}	
}
