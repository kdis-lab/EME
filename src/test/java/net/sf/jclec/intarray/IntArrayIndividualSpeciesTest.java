package net.sf.jclec.intarray;

import net.sf.jclec.IConfigureTest;

import net.sf.jclec.util.intset.Closure;
import net.sf.jclec.util.intset.Interval;
import net.sf.jclec.util.intset.IIntegerSet;

/**
 * Configuration test for IntArrayIndividualSpecies class.
 * 
 * @author Sebastian Ventura
 */

public class IntArrayIndividualSpeciesTest extends IConfigureTest<IntArrayIndividualSpecies> 
{
	public IntArrayIndividualSpeciesTest(String name) 
	{
		super(IntArrayIndividualSpecies.class, name);
	}

	@Override
	protected void setConfigurationFilename() 
	{
		configurationFilename = "src/test/resources/net/sf/jclec/intarray/IntArrayIndividualSpecies.cfg";
	}

	@Override
	protected void initObject1() 
	{		
		object1.genotypeSchema = 
			new IIntegerSet [] {
				new Interval(-5, 5, Closure.ClosedClosed),
				new Interval(-1, 1, Closure.ClosedClosed),
				new Interval(-5, 5, Closure.OpenOpen),
				new Interval(-4, 4, Closure.OpenOpen),
				new Interval(-3, 3, Closure.OpenOpen)
			};
	}
}
