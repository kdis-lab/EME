package net.sf.jclec.realarray.mut;

import java.util.ArrayList;

import net.sf.jclec.IIndividual;
import net.sf.jclec.realarray.RealArrayIndividual;
import net.sf.jclec.realarray.RealArrayMutatorTest;


public class ModalContinuousMutatorTest extends RealArrayMutatorTest<ModalContinuousMutator> 
{

	public ModalContinuousMutatorTest(String name) 
	{
		super(ModalContinuousMutator.class, name);
	}

	@Override
	protected void createExpected() 
	{
		expected = new ArrayList<IIndividual> (); 
		expected.add(new RealArrayIndividual(new double [] {-4.5, -3.9, 2.0759996280670165, 4.3979765625}));
	}

	@Override
	protected void initTool() 
	{
		tool.setBm(2.0);
		tool.setLocusMutProb(0.6);
		tool.setMutationRange(0.1);
		tool.setMinimumRange(1E-5);
	}
}
