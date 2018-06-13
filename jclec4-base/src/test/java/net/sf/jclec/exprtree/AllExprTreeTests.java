package net.sf.jclec.exprtree;

import net.sf.jclec.exprtree.mut.AllNodesMutatorTest;
import net.sf.jclec.exprtree.mut.DemoteMutatorTest;
import net.sf.jclec.exprtree.mut.GrowMutatorTest;
import net.sf.jclec.exprtree.mut.OneNodeMutatorTest;
import net.sf.jclec.exprtree.mut.PromoteMutatorTest;
import net.sf.jclec.exprtree.mut.SubTreeMutatorTest;
import net.sf.jclec.exprtree.mut.TruncMutatorTest;
import net.sf.jclec.exprtree.rec.SubtreeCrossoverTest;
import net.sf.jclec.exprtree.rec.TreeCrossoverTest;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * All unit tests for exprtree, expretree.mut and exprtree.rec packages
 * 
 * @author Sebastian Ventura
 *
 */
public class AllExprTreeTests 
{

	public static Test suite() 
	{
		TestSuite suite = new TestSuite("Test for net.sf.jclec.exprtree");
		//$JUnit-BEGIN$
		// Species
		suite.addTestSuite(ConfigureExprTreeIndividualSpeciesTest.class);
		// Creator
		suite.addTestSuite(ExprTreeCreatorTest.class);
		// Recombinators
		suite.addTestSuite(SubtreeCrossoverTest.class);
		suite.addTestSuite(TreeCrossoverTest.class);
		// Mutators
		suite.addTestSuite(AllNodesMutatorTest.class);
		suite.addTestSuite(OneNodeMutatorTest.class);
		suite.addTestSuite(DemoteMutatorTest.class);
		suite.addTestSuite(GrowMutatorTest.class);
		suite.addTestSuite(PromoteMutatorTest.class);
		suite.addTestSuite(SubTreeMutatorTest.class);
		suite.addTestSuite(TruncMutatorTest.class);		
		//$JUnit-END$
		return suite;
	}

}
