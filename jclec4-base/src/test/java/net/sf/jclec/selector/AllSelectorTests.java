package net.sf.jclec.selector;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllSelectorTests 
{
	public static Test suite() {
		TestSuite suite = new TestSuite(AllSelectorTests.class.getName());
		//$JUnit-BEGIN$
		suite.addTestSuite(DisruptiveSelectorTest.class);
		suite.addTestSuite(SigmaScalingSelectorTest.class);
		suite.addTestSuite(HierarchicalSelectorTest.class);
		suite.addTestSuite(NonLinearHierarchicalSelectorTest.class);
		//$JUnit-END$
		return suite;
	}

}
