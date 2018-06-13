package net.sf.jclec.syntaxtree.mut;

import java.util.ArrayList;

import net.sf.jclec.IIndividual;
import net.sf.jclec.exprtree.fun.Mul;
import net.sf.jclec.exprtree.fun.Sub;
import net.sf.jclec.exprtree.fun.X;
import net.sf.jclec.exprtree.fun.Y;
import net.sf.jclec.syntaxtree.NonTerminalNode;
import net.sf.jclec.syntaxtree.SyntaxTree;
import net.sf.jclec.syntaxtree.SyntaxTreeIndividual;
import net.sf.jclec.syntaxtree.SyntaxTreeMutatorTest;
import net.sf.jclec.syntaxtree.TerminalNode;


/**
 * Unit tests for SelectiveMutatorTest.
 * 
 * @author Amelia Zafra 
 * @author Sebastian Ventura 
 */

public class SelectiveMutatorTests extends SyntaxTreeMutatorTest
{
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////

	/**
	 * Default constructor.
	 */
	
	
	public SelectiveMutatorTests(String name) 
	{
		super(name);
	}
	
	/////////////////////////////////////////////////////////////////
	// ----------------------------- Overwriting IMutatorTest methods
	/////////////////////////////////////////////////////////////////

	@Override
	protected void initTool() 
	{
		tool.setBaseOp(new SelectiveMutator());
	}
	
	@Override
	protected void createParents() 
	{
		parents = new ArrayList<IIndividual> (); 
		// Parent genotype
		SyntaxTree parentTree = new SyntaxTree();
		parentTree.addNode(new NonTerminalNode("expr", new String []{"op", "arg", "arg"}));
		parentTree.addNode(new NonTerminalNode("op", new String []{"-"}));
		parentTree.addNode(new TerminalNode("-", new Sub()));		
		parentTree.addNode(new NonTerminalNode("arg", new String []{"X"}));
		parentTree.addNode(new TerminalNode("X", new X()));		
		parentTree.addNode(new NonTerminalNode("arg", new String []{"Y"}));
		parentTree.addNode(new TerminalNode("Y", new Y()));					
		// Add individual to the parents set
		parents.add(new SyntaxTreeIndividual(parentTree));
	}

	@Override
	protected void createExpected() 
	{
		expected = new ArrayList<IIndividual> ();
		// Mutated individual genotype
		SyntaxTree sonTree = new SyntaxTree();
		sonTree.addNode(new NonTerminalNode("expr", new String []{"op", "arg", "arg"}));
		sonTree.addNode(new NonTerminalNode("op", new String []{"*"}));
		sonTree.addNode(new TerminalNode("*", new Mul()));		
		sonTree.addNode(new NonTerminalNode("arg", new String []{"X"}));
		sonTree.addNode(new TerminalNode("X", new X()));		
		sonTree.addNode(new NonTerminalNode("arg", new String []{"Y"}));
		sonTree.addNode(new TerminalNode("Y", new Y()));				
		//Add individual to expected set
		expected.add(new SyntaxTreeIndividual(sonTree));		
	}
}