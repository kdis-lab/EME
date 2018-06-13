package net.sf.jclec.syntaxtree;

import net.sf.jclec.IMutatorTest;
import net.sf.jclec.ISystem;
import net.sf.jclec.Population;
import net.sf.jclec.exprtree.fun.Add;
import net.sf.jclec.exprtree.fun.Mul;
import net.sf.jclec.exprtree.fun.Sub;
import net.sf.jclec.exprtree.fun.X;
import net.sf.jclec.exprtree.fun.Y;
import net.sf.jclec.exprtree.fun.Z;
import net.sf.jclec.util.random.DummyRandGenFactory;


/**
 * Unit tests for all SyntaxTreeMutator subclasses
 *  
 * @author Amelia Zafra
 * @author Sebastian Ventura
 */

public abstract class SyntaxTreeMutatorTest extends IMutatorTest<SyntaxTreeMutator> 
{
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Default constructor.
	 */
	
	public SyntaxTreeMutatorTest(String testName) 
	{
		super(SyntaxTreeMutator.class, testName);
	}

	/////////////////////////////////////////////////////////////////
	// ----------------------------- Overwriting IToolTest methods
	/////////////////////////////////////////////////////////////////

	@Override
	protected ISystem createContext() 
	{
		Population pop =  new Population();
		// Random generators factory
		DummyRandGenFactory randGenFactory = new DummyRandGenFactory();
		randGenFactory.setDummySequence
			(new double [] {0.1, 0.4, 0.4, 0.5, 0.7, 0.9});
		pop.setRandGenFactory(randGenFactory);
		// Individuals species
		SyntaxTreeIndividualSpecies species = 
			new SyntaxTreeIndividualSpecies();
		species.setRootSymbol("expr");
		species.setTerminals(new TerminalNode [] {
				new TerminalNode("X", new X()),
				new TerminalNode("Y", new Y()),
				new TerminalNode("Z", new Z()),
				new TerminalNode("+", new Add()),
				new TerminalNode("-", new Sub()),
				new TerminalNode("*", new Mul()),
		});
		species.setNonTerminals(new NonTerminalNode [] {
				new NonTerminalNode("expr", new String [] {"op", "arg", "arg"}),
				new NonTerminalNode("expr", new String [] {"op", "arg", "expr"}),
				new NonTerminalNode("op", new String [] {"+"}),
				new NonTerminalNode("op", new String [] {"-"}),
				new NonTerminalNode("op", new String [] {"*"}),
				new NonTerminalNode("arg", new String [] {"X"}),
				new NonTerminalNode("arg", new String [] {"Y"}),
				new NonTerminalNode("arg", new String [] {"Z"}),
		});
		species.setMaxDerivSize(20);
		
		pop.setSpecies(species);
		return pop;
	}
}
