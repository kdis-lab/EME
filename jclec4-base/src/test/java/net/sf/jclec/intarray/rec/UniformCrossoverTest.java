package net.sf.jclec.intarray.rec;

import java.util.ArrayList;

import net.sf.jclec.IIndividual;
import net.sf.jclec.intarray.IntArrayIndividual;
import net.sf.jclec.intarray.IntArrayRecombinatorTest;

public class UniformCrossoverTest extends IntArrayRecombinatorTest<UniformCrossover> 
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
		expected.add(new IntArrayIndividual(new int [] {1, 1, 1, 1, 1, 0}));
		expected.add(new IntArrayIndividual(new int [] {0, 0, 0, 0, 0, 1}));
	}
}
