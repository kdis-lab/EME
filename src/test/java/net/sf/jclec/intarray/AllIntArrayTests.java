package net.sf.jclec.intarray;

import junit.framework.Test;
import junit.framework.TestSuite;

import net.sf.jclec.intarray.rec.OnePointCrossoverTest;
import net.sf.jclec.intarray.rec.UniformCrossoverTest;
import net.sf.jclec.binarray.rec.TwoPointsCrossoverTest;
import net.sf.jclec.intarray.rec.UniformCrossoverConfigureTest;

import net.sf.jclec.intarray.mut.UniformMutatorTest;
import net.sf.jclec.intarray.mut.OneLocusMutatorTest;
import net.sf.jclec.intarray.mut.SeveralLociMutatorTest;
import net.sf.jclec.intarray.mut.SeveralLociMutatorConfigureTest;
import net.sf.jclec.intarray.mut.UniformMutatorConfigureTest;

public class AllIntArrayTests 
{
	public static Test suite() 
	{
		TestSuite suite = new TestSuite("Test for net.sf.jclec.intarray");
		//$JUnit-BEGIN$
		suite.addTestSuite(IntArrayIndividualSpeciesTest.class);
		suite.addTestSuite(OnePointCrossoverTest.class);
		suite.addTestSuite(TwoPointsCrossoverTest.class);
		suite.addTestSuite(UniformCrossoverTest.class);
		suite.addTestSuite(UniformCrossoverConfigureTest.class);
		suite.addTestSuite(OneLocusMutatorTest.class);
		suite.addTestSuite(SeveralLociMutatorTest.class);
		suite.addTestSuite(SeveralLociMutatorConfigureTest.class);
		suite.addTestSuite(UniformMutatorTest.class);
		suite.addTestSuite(UniformMutatorConfigureTest.class);
		//$JUnit-END$
		return suite;
	}

}
