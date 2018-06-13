package net.sf.jclec.selector;

import java.util.ArrayList;

import net.sf.jclec.IIndividual;
import net.sf.jclec.ISelectorTest;

/**
 * Disruptive selector tests.
 * 
 * @author Rafael Aguilera 
 */

public class DisruptiveSelectorTest extends ISelectorTest<DisruptiveSelector> 
{
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////

	/**
	 * Default constructor
	 * 
	 * @param name
	 */
	
	public DisruptiveSelectorTest(String name) 
	{
		super(DisruptiveSelector.class, name);
	}

	/////////////////////////////////////////////////////////////////
	// -------------------------------------------- Protected methods
	/////////////////////////////////////////////////////////////////
	
	@Override
	protected void initTool() 
	{
		// Do nothing
	}
	
	@Override
	protected void createExpected() 
	{
		expected = new ArrayList<IIndividual>();
		expected.add(source.get(2));
	}	
}
