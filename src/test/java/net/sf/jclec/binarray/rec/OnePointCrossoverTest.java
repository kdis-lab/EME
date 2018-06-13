package net.sf.jclec.binarray.rec;

import java.util.ArrayList;

import net.sf.jclec.IIndividual;
import net.sf.jclec.binarray.BinArrayIndividual;
import net.sf.jclec.binarray.BinArrayRecombinatorTest;

public class OnePointCrossoverTest extends BinArrayRecombinatorTest<OnePointCrossover> 
{
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////

	/**
	 * Default constructor
	 */
	
	public OnePointCrossoverTest(String name) 
	{
		super(OnePointCrossover.class, name);
	}

	/////////////////////////////////////////////////////////////////
	// --------------------------- Overwriting ICrossoverTest methods
	/////////////////////////////////////////////////////////////////

	/**
	 * Set evaluate flag to false.
	 *
	 * {@inheritDoc}
	 */
	
	@Override
	protected void initTool() 
	{
		// Do nothing
	}

	@Override
	protected void createExpected() 
	{
		expected = new ArrayList<IIndividual> ();
		expected.add(new BinArrayIndividual(new byte [] {0, 1, 1, 1, 1, 1}));
		expected.add(new BinArrayIndividual(new byte [] {1, 0, 0, 0, 0, 0}));
	}
}
