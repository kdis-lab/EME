package net.sf.jclec.intarray.rec;

import java.util.ArrayList;

import net.sf.jclec.IIndividual;

import net.sf.jclec.intarray.IntArrayIndividual;
import net.sf.jclec.intarray.IntArrayRecombinatorTest;

public class TwoPointsCrossoverTest extends IntArrayRecombinatorTest<TwoPointsCrossover> 
{
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////

	/**
	 * Default constructor
	 */
	
	public TwoPointsCrossoverTest(String name) 
	{
		super(TwoPointsCrossover.class, name);
	}

	/////////////////////////////////////////////////////////////////
	// ------------------------ Overwriting IRecombinatorTest methods
	/////////////////////////////////////////////////////////////////
	
	@Override
	protected void initTool() 
	{
		// Do nothing
	}
	
	@Override
	protected void createExpected() 
	{
		expected = new ArrayList<IIndividual> ();
		expected.add(new IntArrayIndividual(new int [] {0, 1, 0, 0, 0, 0}));
		expected.add(new IntArrayIndividual(new int [] {1, 0, 1, 1, 1, 1}));
	}
}
