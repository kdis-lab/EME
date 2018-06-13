package net.sf.jclec.exprtree;

import net.sf.jclec.exprtree.fun.Add;
import net.sf.jclec.exprtree.fun.Mul;
import net.sf.jclec.exprtree.fun.Sub;
import net.sf.jclec.exprtree.fun.X;
import net.sf.jclec.exprtree.fun.Y;
import net.sf.jclec.exprtree.fun.Z;

public class PruebaToString 
{
	public static void main(String [] args)
	{
		ExprTree tree = new ExprTree();
		
		tree.addBlock(new Add());
		tree.addBlock(new Sub());
		tree.addBlock(new X());
		tree.addBlock(new Y());
		tree.addBlock(new Add());
		tree.addBlock(new Mul());
		tree.addBlock(new Mul());
		tree.addBlock(new X());
		tree.addBlock(new X());
		tree.addBlock(new Y());
		tree.addBlock(new Z());
		
		System.out.println(tree);

		ExprTree tree2 = new ExprTree();
		
		tree2.addBlock(new X());

		System.out.println(tree2);
		
	}
}
