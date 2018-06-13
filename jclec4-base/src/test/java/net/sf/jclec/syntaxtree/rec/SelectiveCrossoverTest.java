package net.sf.jclec.syntaxtree.rec;

import java.util.ArrayList;

import net.sf.jclec.IIndividual;

import net.sf.jclec.exprtree.fun.Add;
import net.sf.jclec.exprtree.fun.Mul;
import net.sf.jclec.exprtree.fun.Sub;
import net.sf.jclec.exprtree.fun.X;
import net.sf.jclec.exprtree.fun.Y;
import net.sf.jclec.exprtree.fun.Z;

import net.sf.jclec.syntaxtree.SyntaxTree;
import net.sf.jclec.syntaxtree.TerminalNode;
import net.sf.jclec.syntaxtree.NonTerminalNode;
import net.sf.jclec.syntaxtree.SyntaxTreeIndividual;
import net.sf.jclec.syntaxtree.SyntaxTreeRecombinatorTest;

/**
 * Unit tests for SelectiveCrossoverTest.
 * 
 * @author Amelia Zafra 
 * @author Sebastian Ventura 
 */

public class SelectiveCrossoverTest extends SyntaxTreeRecombinatorTest
{
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////

	/**
	 * Default constructor.
	 */
	
	public SelectiveCrossoverTest(String name) 
	{
		super(name);
	}

	/////////////////////////////////////////////////////////////////
	// ----------------------------- Overwriting IMutatorTest methods
	/////////////////////////////////////////////////////////////////

	@Override
	protected void initTool() 
	{
		tool.setBaseOp(new SelectiveCrossover());
	}
	
	@Override
	protected void createParents() 
	{
		parents = new ArrayList<IIndividual> (); 		
		// First parent genotype		
		SyntaxTree parent1Tree = new SyntaxTree();
		parent1Tree.addNode(new NonTerminalNode("expr", new String []{"op", "arg", "arg"}));
		parent1Tree.addNode(new NonTerminalNode("op", new String []{"*"}));
		parent1Tree.addNode(new TerminalNode("*", new Mul()));		
		parent1Tree.addNode(new NonTerminalNode("arg", new String []{"X"}));
		parent1Tree.addNode(new TerminalNode("X", new X()));		
		parent1Tree.addNode(new NonTerminalNode("arg", new String []{"Z"}));
		parent1Tree.addNode(new TerminalNode("Z", new Z()));		
		// Second parent genotype	
		SyntaxTree parent2Tree = new SyntaxTree();
		parent2Tree.addNode(new NonTerminalNode("expr", new String []{"op", "arg", "expr"}));
		parent2Tree.addNode(new NonTerminalNode("op", new String []{"+"}));
		parent2Tree.addNode(new TerminalNode("+", new Add()));		
		parent2Tree.addNode(new NonTerminalNode("arg", new String []{"Y"}));
		parent2Tree.addNode(new TerminalNode("Y", new Y()));
		parent2Tree.addNode(new NonTerminalNode("expr", new String []{"op", "arg", "arg"}));
		parent2Tree.addNode(new NonTerminalNode("op", new String []{"-"}));
		parent2Tree.addNode(new TerminalNode("-", new Sub()));
		parent2Tree.addNode(new NonTerminalNode("arg", new String []{"Z"}));
		parent2Tree.addNode(new TerminalNode("Z", new Z()));		
		parent2Tree.addNode(new NonTerminalNode("arg", new String []{"Y"}));
		parent2Tree.addNode(new TerminalNode("Y", new Y()));			
		// Add individuals to the parents set
		parents.add(new SyntaxTreeIndividual(parent1Tree));
		parents.add(new SyntaxTreeIndividual(parent2Tree));		
	}

	@Override
	protected void createExpected() 
	{
		expected = new ArrayList<IIndividual> ();
		
		// Crossed individuals genotype
		// First son genotype		
		SyntaxTree son1Tree = new SyntaxTree();
		son1Tree.addNode(new NonTerminalNode("expr", new String []{"op", "arg", "arg"}));
		son1Tree.addNode(new NonTerminalNode("op", new String []{"+"}));
		son1Tree.addNode(new TerminalNode("+", new Add()));		
		son1Tree.addNode(new NonTerminalNode("arg", new String []{"X"}));
		son1Tree.addNode(new TerminalNode("X", new X()));		
		son1Tree.addNode(new NonTerminalNode("arg", new String []{"Z"}));
		son1Tree.addNode(new TerminalNode("Z", new Z()));		
		// Second son genotype
		SyntaxTree son2Tree = new SyntaxTree();		
		son2Tree.addNode(new NonTerminalNode("expr", new String []{"op", "arg", "expr"}));
		son2Tree.addNode(new NonTerminalNode("op", new String []{"*"}));
		son2Tree.addNode(new TerminalNode("*", new Mul()));		
		son2Tree.addNode(new NonTerminalNode("arg", new String []{"Y"}));
		son2Tree.addNode(new TerminalNode("Y", new Y()));
		son2Tree.addNode(new NonTerminalNode("expr", new String []{"op", "arg", "arg"}));
		son2Tree.addNode(new NonTerminalNode("op", new String []{"-"}));
		son2Tree.addNode(new TerminalNode("-", new Sub()));
		son2Tree.addNode(new NonTerminalNode("arg", new String []{"Z"}));
		son2Tree.addNode(new TerminalNode("Z", new Z()));		
		son2Tree.addNode(new NonTerminalNode("arg", new String []{"Y"}));
		son2Tree.addNode(new TerminalNode("Y", new Y()));	
		// Add individuals to expected set
		expected.add(new SyntaxTreeIndividual(son1Tree));
		expected.add(new SyntaxTreeIndividual(son2Tree));			
	}	
}