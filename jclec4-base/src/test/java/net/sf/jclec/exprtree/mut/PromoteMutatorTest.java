package net.sf.jclec.exprtree.mut;

import java.util.ArrayList;

import net.sf.jclec.ISystem;
import net.sf.jclec.Population;
import net.sf.jclec.IIndividual;

import net.sf.jclec.exprtree.fun.Add;
import net.sf.jclec.exprtree.fun.Mul;
import net.sf.jclec.exprtree.fun.Sub;
import net.sf.jclec.exprtree.fun.X;
import net.sf.jclec.exprtree.fun.Y;
import net.sf.jclec.exprtree.fun.Z;

import net.sf.jclec.exprtree.ExprTree;
import net.sf.jclec.exprtree.IPrimitive;
import net.sf.jclec.exprtree.ExprTreeIndividual;
import net.sf.jclec.exprtree.ExprTreeMutatorTest;
import net.sf.jclec.exprtree.ExprTreeIndividualSpecies;

import net.sf.jclec.util.random.DummyRandGenFactory;

/**
 * Unit tests for PromoteMutatorTest.
 * 
 * @author Amelia Zafra - Sebastian Ventura
 */

public class PromoteMutatorTest extends ExprTreeMutatorTest 
{
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////

	/**
	 * Default constructor.
	 */
	
	
	public PromoteMutatorTest(String name) 
	{
		super(name);
	}

	/////////////////////////////////////////////////////////////////
	// ----------------------------- Overwriting IMutatorTest methods
	/////////////////////////////////////////////////////////////////

	@Override
	protected void initTool() 
	{
		tool.setBaseOp(new PromoteMutator());		
	}
		
	@Override
	protected void createExpected() 
	{
		expected = new ArrayList<IIndividual> ();
		
		// Mutated individual genotype
		ExprTree genotype = new ExprTree();
		genotype.addBlock(new Add());
		genotype.addBlock(new X());
		genotype.addBlock(new Sub());
		genotype.addBlock(new Sub());
		genotype.addBlock(new Mul());
		genotype.addBlock(new Add());
		genotype.addBlock(new X());
		genotype.addBlock(new Y());
		genotype.addBlock(new Y());
		genotype.addBlock(new Z());
		genotype.addBlock(new Y());
		
		//Add individual to expected set
		expected.add(new ExprTreeIndividual(genotype));		
	}

		
	@Override
	protected void createParents() 
	{
		parents = new ArrayList<IIndividual> (); 		
		
		// Parent genotype
		ExprTree genotype = new ExprTree();
		genotype.addBlock(new Add());
		genotype.addBlock(new X());
		genotype.addBlock(new Sub());
		genotype.addBlock(new Mul());
		genotype.addBlock(new Add());
		genotype.addBlock(new X());
		genotype.addBlock(new Y());
		genotype.addBlock(new Y());
		genotype.addBlock(new Z());
		
		// Add individual to the parents set
		parents.add(new ExprTreeIndividual(genotype));		
	}
	
	//////////////////////////////////////////////////////////////////
	// --------------------------- Overwriting ExprTreeMutator methods
	//////////////////////////////////////////////////////////////////
	
	/**
	 * Creamos el contexto del sistema, se modifica para anadir un individuo
	 * con mayor longitud maxima
	 */
	
	@Override
	protected ISystem createContext() 
	{
		Population pop = new Population();
		// Random generators factory
		DummyRandGenFactory randGenFactory = new DummyRandGenFactory();
		randGenFactory.setDummySequence
			(new double [] {0.1, 0.2, 0.4, 0.5, 0.7, 0.9});
		pop.setRandGenFactory(randGenFactory);
		// Individual species
		ExprTreeIndividualSpecies species = new ExprTreeIndividualSpecies();
		species.setMinTreeSize(1);
		species.setMaxTreeSize(15);
		species.setRootType(Double.class);
		species.setTerminals(new IPrimitive [] {new X(), new Y(), new Z()});
		species.setFunctions(new IPrimitive [] {new Add(),  new Sub(), new Mul()});
		pop.setSpecies(species);
		return pop;
	}
}
