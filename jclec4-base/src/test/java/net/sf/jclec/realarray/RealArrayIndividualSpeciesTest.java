package net.sf.jclec.realarray;

import net.sf.jclec.IConfigureTest;

import net.sf.jclec.util.range.IRange;
import net.sf.jclec.util.range.Closure;
import net.sf.jclec.util.range.Interval;

/**
 * Marshall and unmarshal tests for BinArrayIndividualSpecies class.
 * 
 * @author Sebastian Ventura
 */

public class RealArrayIndividualSpeciesTest extends IConfigureTest<RealArrayIndividualSpecies> 
{
	public RealArrayIndividualSpeciesTest(String name) 
	{
		super(RealArrayIndividualSpecies.class, name);
	}

	@Override
	protected void setConfigurationFilename() 
	{
		configurationFilename = "src/test/resources/net/sf/jclec/realarray/RealArrayIndividualSpecies.cfg";
	}

	@Override
	protected void initObject1() 
	{		
		object1.genotypeSchema = 
			new IRange [] {
				new Interval(-5., 5, Closure.ClosedClosed),
				new Interval(-1., 1, Closure.ClosedOpen),
				new Interval(-5., 5, Closure.OpenOpen),
				new Interval(-4., 4, Closure.OpenOpen),
				new Interval(-3., 3, Closure.OpenOpen)
			};
	}
}
