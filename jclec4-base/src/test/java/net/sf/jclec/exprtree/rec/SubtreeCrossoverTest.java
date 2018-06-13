package net.sf.jclec.exprtree.rec;

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
import net.sf.jclec.exprtree.ExprTreeRecombinatorTest;

public class SubtreeCrossoverTest extends ExprTreeRecombinatorTest
{	
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////

	/**
	 * Default constructor.
	 */
	
	public SubtreeCrossoverTest(String name) 
	{
		super(name);
	}

	/////////////////////////////////////////////////////////////////
	// ----------------------------- Overwriting IMutatorTest methods
	/////////////////////////////////////////////////////////////////

	@Override
	protected void initTool() 
	{
		tool.setBaseOp(new SubtreeCrossover());
	}
	
	@Override
	protected void createExpected() 
	{
		expected = new ArrayList<IIndividual> ();
		
		// Crossed individuals genotype
		
		ExprTree tree0 = new ExprTree();
		tree0.addBlock(new Add());
		tree0.addBlock(new X());
		tree0.addBlock(new Sub());
		tree0.addBlock(new Sub());
		tree0.addBlock(new X());
		tree0.addBlock(new Y());
		tree0.addBlock(new X());
				
		
		ExprTree tree1 = new ExprTree();
		tree1.addBlock(new Add());
		tree1.addBlock(new X());
		tree1.addBlock(new Add());
		tree1.addBlock(new Mul());
		tree1.addBlock(new Y());
		tree1.addBlock(new Z());
		tree1.addBlock(new X());	
		

		// Add individuals to expected set
		expected.add(new ExprTreeIndividual(tree0));
		expected.add(new ExprTreeIndividual(tree1));	
	}

	@Override
	protected void createParents() 
	{
		parents = new ArrayList<IIndividual> (); 		
		
		// Parents genotype
		
		ExprTree tree0 = new ExprTree();
		tree0.addBlock(new Add());
		tree0.addBlock(new X());
		tree0.addBlock(new Sub());
		tree0.addBlock(new Mul());
		tree0.addBlock(new Y());
		tree0.addBlock(new Z());
		tree0.addBlock(new X());
		
		ExprTree tree1 = new ExprTree();
		tree1.addBlock(new Add());
		tree1.addBlock(new X());
		tree1.addBlock(new Add());
		tree1.addBlock(new Sub());
		tree1.addBlock(new X());
		tree1.addBlock(new Y());
		tree1.addBlock(new X());
				
		// Add individuals to the parents set
		parents.add(new ExprTreeIndividual(tree0));
		parents.add(new ExprTreeIndividual(tree1));		
	}
}
