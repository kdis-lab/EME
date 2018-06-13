package net.sf.jclec.realarray.mut;

import java.util.ArrayList;

import net.sf.jclec.IIndividual;
import net.sf.jclec.realarray.RealArrayIndividual;
import net.sf.jclec.realarray.RealArrayMutatorTest;

public class ModalDiscreteMutatorTest extends RealArrayMutatorTest<ModalDiscreteMutator> 
{

	public ModalDiscreteMutatorTest(String name) 
	{
		super(ModalDiscreteMutator.class, name);
	}

	@Override
	protected void createExpected() 
	{
		expected = new ArrayList<IIndividual> (); 
		expected.add(new RealArrayIndividual(new double [] {-4.5, -3.9, 1.9714375000000002, 4.176}));
	}

	@Override
	protected void initTool() 
	{
		tool.setLocusMutProb(0.6);
		tool.setBm(2.0);
		tool.setMutationRange(0.1);
		tool.setMinimumRange(1E-5);
	}
}
