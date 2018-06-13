package net.sf.jclec.intarray.rec;

import java.util.ArrayList;

import net.sf.jclec.IIndividual;
import net.sf.jclec.intarray.IntArrayIndividual;
import net.sf.jclec.intarray.IntArrayRecombinatorTest;

public class OnePointCrossoverTest extends IntArrayRecombinatorTest<OnePointCrossover> 
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
		expected.add(new IntArrayIndividual(new int [] {0, 1, 1, 1, 1, 1}));
		expected.add(new IntArrayIndividual(new int [] {1, 0, 0, 0, 0, 0}));
	}
}
