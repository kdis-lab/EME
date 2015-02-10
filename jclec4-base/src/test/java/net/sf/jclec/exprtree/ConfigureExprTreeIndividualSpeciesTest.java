package net.sf.jclec.exprtree;

import net.sf.jclec.IConfigureTest;

import net.sf.jclec.exprtree.fun.Add;
import net.sf.jclec.exprtree.fun.Mul;
import net.sf.jclec.exprtree.fun.Sub;
import net.sf.jclec.exprtree.fun.X;
import net.sf.jclec.exprtree.fun.Y;
import net.sf.jclec.exprtree.fun.Z;

/**
 * 
 * @author Sebastian Ventura
 */

public class ConfigureExprTreeIndividualSpeciesTest extends IConfigureTest<ExprTreeIndividualSpecies> 
{

	public ConfigureExprTreeIndividualSpeciesTest(String name) 
	{
		super(ExprTreeIndividualSpecies.class, name);
	}

	@Override
	protected void initObject1() 
	{
		object1.setMinTreeSize(1);
		object1.setMaxTreeSize(10);
		object1.setRootType(Double.class);
		object1.setTerminals(new IPrimitive [] {new X(), new Y(), new Z()});
		object1.setFunctions(new IPrimitive [] {new Add(), new Sub(), new Mul()});
	}

	@Override
	protected void setConfigurationFilename() 
	{
		configurationFilename = "src/test/resources/net/sf/jclec/exprtree/ExprTreeIndividualSpecies.config.xml";
	}
}
