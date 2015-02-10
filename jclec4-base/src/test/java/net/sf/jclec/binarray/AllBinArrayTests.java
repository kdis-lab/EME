package net.sf.jclec.binarray;

import junit.framework.Test;
import junit.framework.TestSuite;

import net.sf.jclec.binarray.mut.UniformMutatorTest;
import net.sf.jclec.binarray.mut.OneLocusMutatorTest;
import net.sf.jclec.binarray.mut.SeveralLociMutatorTest;
import net.sf.jclec.binarray.mut.ConfigureUniformMutatorTest;
import net.sf.jclec.binarray.mut.ConfigureSeveralLociMutatorTest;

import net.sf.jclec.binarray.rec.UniformCrossoverTest;
import net.sf.jclec.binarray.rec.OnePointCrossoverTest;
import net.sf.jclec.binarray.rec.TwoPointsCrossoverTest;
import net.sf.jclec.binarray.rec.ConfigureUniformCrossoverTest;

public class AllBinArrayTests 
{
	public static Test suite() 
	{
		TestSuite suite = new TestSuite("Test for net.sf.jclec.binarray");
		//$JUnit-BEGIN$
		
		suite.addTestSuite(BinArrayIndividualSpeciesTest.class);
		
		suite.addTestSuite(BinArrayCreatorTest.class);
		
		suite.addTestSuite(OnePointCrossoverTest.class);
		suite.addTestSuite(TwoPointsCrossoverTest.class);
		suite.addTestSuite(UniformCrossoverTest.class);
		suite.addTestSuite(ConfigureUniformCrossoverTest.class);
		
		suite.addTestSuite(OneLocusMutatorTest.class);
		suite.addTestSuite(UniformMutatorTest.class);
		suite.addTestSuite(ConfigureUniformMutatorTest.class);
		suite.addTestSuite(SeveralLociMutatorTest.class);
		suite.addTestSuite(ConfigureSeveralLociMutatorTest.class);
		
		//$JUnit-END$
		return suite;
	}
}
