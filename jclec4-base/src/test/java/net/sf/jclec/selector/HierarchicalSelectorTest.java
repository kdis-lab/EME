package net.sf.jclec.selector;

import java.util.ArrayList;

import net.sf.jclec.IIndividual;
import net.sf.jclec.ISelectorTest;

/**
 * Jerarquias selector tests.
 * 
 * @author Rafael Aguilera 
 */

public class HierarchicalSelectorTest extends ISelectorTest<HierarchicalSelector> 
{
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////

	/**
	 * Default constructor
	 * 
	 * @param name
	 */
	
	public HierarchicalSelectorTest(String name) 
	{
		super(HierarchicalSelector.class, name);
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
