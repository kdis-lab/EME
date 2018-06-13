package net.sf.jclec.binarray.rec;

import java.util.ArrayList;

import net.sf.jclec.IIndividual;
import net.sf.jclec.binarray.BinArrayIndividual;
import net.sf.jclec.binarray.BinArrayRecombinatorTest;

public class UniformCrossoverTest extends BinArrayRecombinatorTest<UniformCrossover> 
{
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////

	/**
	 * Default constructor
	 */
	
	public UniformCrossoverTest(String name) 
	{
		super(UniformCrossover.class, name);
	}

	/////////////////////////////////////////////////////////////////
	// ------------------------ Overwriting IRecombinatorTest methods
	/////////////////////////////////////////////////////////////////
	
	/**
	 * {@inheritDoc} 
	 */

	@Override
	protected void initTool() 
	{
		tool.setLocusCrossoverProb(0.64);
	}

	@Override
	protected void createExpected() 
	{
		expected = new ArrayList<IIndividual> ();
		expected.add(new BinArrayIndividual(new byte [] {1, 1, 1, 1, 1, 0}));
		expected.add(new BinArrayIndividual(new byte [] {0, 0, 0, 0, 0, 1}));
	}
}
