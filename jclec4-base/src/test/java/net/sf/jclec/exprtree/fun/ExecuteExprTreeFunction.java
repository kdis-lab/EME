package net.sf.jclec.exprtree.fun;

import net.sf.jclec.exprtree.ExprTree;

/**
 * ExprTreeFunction execution example 
 * 
 * @author Sebastian Ventura
 */

public class ExecuteExprTreeFunction 
{
	/**
	 * This method perform the following steps:
	 * 
	 * <ul>
	 * <li>Create an ExprTree object</li>
	 * <li>Add several nodes (primitives) to this expression tree</li>
	 * <li>Create an ExprTreeFunction object</li>
	 * <li>Assign ExprTree object as funtion code</li>
	 * <li>Execute function</li>
	 * </ul>
	 */
	
	public static void main(String[] args) 
	{
		ExprTree code = new ExprTree();
		code.addBlock(new Add());
		code.addBlock(new X());
		code.addBlock(new Sub());
		code.addBlock(new Mul());
		code.addBlock(new Y());
		code.addBlock(new Z());
		code.addBlock(new X());
		ExprTreeFunction function = new ExprTreeFunction();
		function.setCode(code);
		System.out.println(function.<Double>execute(1.0, 7.0, 2.0));
	}
}
