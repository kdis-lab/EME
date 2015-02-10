package net.sf.jclec.exprtree;

import net.sf.jclec.util.random.IRandGen;

/**
 * A single mutation operator for expression tree individuals
 * 
 * @author Sebastian Ventura
 */

public interface IMutateExprTree 
{
	/**
	 * 
	 * @param tree
	 * @param treeSchema
	 * @param randgen
	 * 
	 * @return A new expression tree
	 */
	
	public abstract ExprTree mutateExprTree(ExprTree tree, ExprTreeSchema treeSchema, IRandGen randgen);
}
