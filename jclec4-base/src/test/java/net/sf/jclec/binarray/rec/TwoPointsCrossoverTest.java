package net.sf.jclec.binarray.rec;

import java.util.ArrayList;

import net.sf.jclec.IIndividual;
import net.sf.jclec.binarray.BinArrayIndividual;
import net.sf.jclec.binarray.BinArrayRecombinatorTest;

public class TwoPointsCrossoverTest extends BinArrayRecombinatorTest<TwoPointsCrossover> 
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
		expected.add(new BinArrayIndividual(new byte [] {0, 1, 0, 0, 0, 0}));
		expected.add(new BinArrayIndividual(new byte [] {1, 0, 1, 1, 1, 1}));
	}
}
