package net.sf.jclec.exprtree.mut;

import java.util.ArrayList;

import net.sf.jclec.IIndividual;

import net.sf.jclec.exprtree.fun.Add;
import net.sf.jclec.exprtree.fun.Mul;
import net.sf.jclec.exprtree.fun.Sub;
import net.sf.jclec.exprtree.fun.X;
import net.sf.jclec.exprtree.fun.Y;
import net.sf.jclec.exprtree.fun.Z;

import net.sf.jclec.exprtree.ExprTree;
import net.sf.jclec.exprtree.ExprTreeIndividual;
import net.sf.jclec.exprtree.ExprTreeMutatorTest;

/**
 * Unit tests for SubTreeMutatorTest.
 * 
 * @author Amelia Zafra - Sebastian Ventura
 */

public class SubTreeMutatorTest extends ExprTreeMutatorTest
{
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////

	/**
	 * Default constructor.
	 */
	
	
	public SubTreeMutatorTest(String name) 
	{
		super(name);
	}

	/////////////////////////////////////////////////////////////////
	// ----------------------------- Overwriting IMutatorTest methods
	/////////////////////////////////////////////////////////////////

	@Override
	protected void initTool() 
	{
		tool.setBaseOp(new SubtreeMutator());
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
		genotype.addBlock(new Mul());
		genotype.addBlock(new Z());
		genotype.addBlock(new X());
		genotype.addBlock(new X());
		
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
		genotype.addBlock(new Y());
		genotype.addBlock(new Z());
		genotype.addBlock(new X());
		
		// Add individual to the parents set
		parents.add(new ExprTreeIndividual(genotype));		
	}
	
}
