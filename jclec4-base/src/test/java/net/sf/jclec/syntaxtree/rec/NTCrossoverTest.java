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
 * Unit tests for NTCrossoverTest.
 * 
 * @author Amelia Zafra
 * @author Sebastian Ventura 
 */

public class NTCrossoverTest extends SyntaxTreeRecombinatorTest
{
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////

	/**
	 * Default constructor.
	 */
	
	public NTCrossoverTest(String name) 
	{
		super(name);
	}

	/////////////////////////////////////////////////////////////////
	// ----------------------------- Overwriting IMutatorTest methods
	/////////////////////////////////////////////////////////////////

	@Override
	protected void initTool() 
	{
		tool.setBaseOp(new NTCrossover());
	}
	
	@Override
	protected void createParents() 
	{
		parents = new ArrayList<IIndividual> (); 				
		// First parent genotype		
		SyntaxTree treeParent1 = new SyntaxTree();
		treeParent1.addNode(new NonTerminalNode("expr", new String []{"op", "arg", "arg"}));
		treeParent1.addNode(new NonTerminalNode("op", new String []{"*"}));
		treeParent1.addNode(new TerminalNode("*", new Mul()));		
		treeParent1.addNode(new NonTerminalNode("arg", new String []{"X"}));
		treeParent1.addNode(new TerminalNode("X", new X()));		
		treeParent1.addNode(new NonTerminalNode("arg", new String []{"Z"}));
		treeParent1.addNode(new TerminalNode("Z", new Z()));		
		// Second parent genotype				
		SyntaxTree treeParent2 = new SyntaxTree();
		treeParent2.addNode(new NonTerminalNode("expr", new String []{"op", "arg", "expr"}));
		treeParent2.addNode(new NonTerminalNode("op", new String []{"+"}));
		treeParent2.addNode(new TerminalNode("+", new Add()));		
		treeParent2.addNode(new NonTerminalNode("arg", new String []{"Y"}));
		treeParent2.addNode(new TerminalNode("Y", new Y()));
		treeParent2.addNode(new NonTerminalNode("expr", new String []{"op", "arg", "arg"}));
		treeParent2.addNode(new NonTerminalNode("op", new String []{"-"}));
		treeParent2.addNode(new TerminalNode("-", new Sub()));
		treeParent2.addNode(new NonTerminalNode("arg", new String []{"Z"}));
		treeParent2.addNode(new TerminalNode("Z", new Z()));		
		treeParent2.addNode(new NonTerminalNode("arg", new String []{"Y"}));
		treeParent2.addNode(new TerminalNode("Y", new Y()));			
		// Add individuals to the parents set
		parents.add(new SyntaxTreeIndividual(treeParent1));
		parents.add(new SyntaxTreeIndividual(treeParent2));		
	}

	@Override
	protected void createExpected() 
	{
		// Create expected set
		expected = new ArrayList<IIndividual> ();		
		// First son genotype		
		SyntaxTree son1Tree = new SyntaxTree();
		son1Tree.addNode(new NonTerminalNode("expr", new String []{"op", "arg", "arg"}));
		son1Tree.addNode(new NonTerminalNode("op", new String []{"*"}));
		son1Tree.addNode(new TerminalNode("*", new Mul()));		
		son1Tree.addNode(new NonTerminalNode("arg", new String []{"Z"}));
		son1Tree.addNode(new TerminalNode("Z", new Z()));		
		son1Tree.addNode(new NonTerminalNode("arg", new String []{"Z"}));
		son1Tree.addNode(new TerminalNode("Z", new Z()));		
		// Second son genotype				
		SyntaxTree son2Tree = new SyntaxTree();
		son2Tree.addNode(new NonTerminalNode("expr", new String []{"op", "arg", "expr"}));
		son2Tree.addNode(new NonTerminalNode("op", new String []{"+"}));
		son2Tree.addNode(new TerminalNode("+", new Add()));		
		son2Tree.addNode(new NonTerminalNode("arg", new String []{"Y"}));
		son2Tree.addNode(new TerminalNode("Y", new Y()));
		son2Tree.addNode(new NonTerminalNode("expr", new String []{"op", "arg", "arg"}));
		son2Tree.addNode(new NonTerminalNode("op", new String []{"-"}));
		son2Tree.addNode(new TerminalNode("-", new Sub()));
		son2Tree.addNode(new NonTerminalNode("arg", new String []{"X"}));
		son2Tree.addNode(new TerminalNode("X", new X()));		
		son2Tree.addNode(new NonTerminalNode("arg", new String []{"Y"}));
		son2Tree.addNode(new TerminalNode("Y", new Y()));	
		// Add individuals to expected set
		expected.add(new SyntaxTreeIndividual(son1Tree));
		expected.add(new SyntaxTreeIndividual(son2Tree));		
	}
}