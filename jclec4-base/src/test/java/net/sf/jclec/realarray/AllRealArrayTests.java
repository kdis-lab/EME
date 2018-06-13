package net.sf.jclec.realarray;

import junit.framework.Test;
import junit.framework.TestSuite;

import net.sf.jclec.realarray.rec.FlatCrossoverTest;
import net.sf.jclec.realarray.rec.LinearCrossoverTest;
import net.sf.jclec.realarray.rec.WrightCrossoverTest;
import net.sf.jclec.realarray.rec.BLXAlphaCrossoverTest;
import net.sf.jclec.realarray.rec.DiscreteCrossoverTest;
import net.sf.jclec.realarray.rec.ArithmeticCrossoverTest;

import net.sf.jclec.realarray.mut.RandomMutatorTest;
import net.sf.jclec.realarray.mut.MuhlehbeinMutatorTest;
import net.sf.jclec.realarray.mut.NonUniformMutatorTest;
import net.sf.jclec.realarray.mut.RandomRangeMutatorTest;
import net.sf.jclec.realarray.mut.ModalDiscreteMutatorTest;
import net.sf.jclec.realarray.mut.ModalContinuousMutatorTest;

public class AllRealArrayTests 
{
	public static Test suite() {
		TestSuite suite = new TestSuite("Test for net.sf.jclec.realarray");
		//$JUnit-BEGIN$
		suite.addTestSuite(RealArrayIndividualSpeciesTest.class);
		
		suite.addTestSuite(FlatCrossoverTest.class);
		suite.addTestSuite(WrightCrossoverTest.class);
		suite.addTestSuite(LinearCrossoverTest.class);
		suite.addTestSuite(DiscreteCrossoverTest.class);
		suite.addTestSuite(BLXAlphaCrossoverTest.class);
		suite.addTestSuite(ArithmeticCrossoverTest.class);

		suite.addTestSuite(RandomMutatorTest.class);
		suite.addTestSuite(MuhlehbeinMutatorTest.class);
		suite.addTestSuite(NonUniformMutatorTest.class);
		suite.addTestSuite(RandomRangeMutatorTest.class);
		suite.addTestSuite(ModalDiscreteMutatorTest.class);
		suite.addTestSuite(ModalContinuousMutatorTest.class);

		//$JUnit-END$
		return suite;
	}

}
