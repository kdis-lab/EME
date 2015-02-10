package net.sf.jclec.exprtree;

import net.sf.jclec.exprtree.fun.Add;
import net.sf.jclec.exprtree.fun.Mul;
import net.sf.jclec.exprtree.fun.Sin;
import net.sf.jclec.exprtree.fun.Sub;
import net.sf.jclec.exprtree.fun.X;
import net.sf.jclec.exprtree.fun.Y;
import net.sf.jclec.exprtree.fun.Z;

import net.sf.jclec.util.random.Ranecu;

public class CreateExprTreeExample 
{
	public static void main(String [] args)
	{
		// Create species
		ExprTreeIndividualSpecies species = new ExprTreeIndividualSpecies();
		species.setMinTreeSize(1);
		species.setMaxTreeSize(10);
		species.setRootType(Double.class);
		species.setTerminals(new IPrimitive [] {new X(), new Y(), new Z()});
		species.setFunctions(new IPrimitive [] {new Add(), new Sub(), new Mul(), new Sin()});
		// Create 
		Ranecu randgen = new Ranecu(12345, 67890);
		
		for (int i=0; i<20; i++)
			System.out.println(species.genotypeSchema.createExprTree(50, randgen));
	}
}
