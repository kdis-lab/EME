package net.sf.jclec.selector;

import java.util.ArrayList;

import net.sf.jclec.IIndividual;
import net.sf.jclec.ISelectorTest;

/**
 * JerarquiasNoLineales selector tests.
 * 
 * @author Rafael Aguilera 
 */

public class NonLinearHierarchicalSelectorTest extends ISelectorTest<NonLinearHierarchicalSelector> 
{
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////

	/**
	 * Default constructor
	 * 
	 * @param name
	 */
	
	public NonLinearHierarchicalSelectorTest(String name) 
	{
		super(NonLinearHierarchicalSelector.class, name);
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
