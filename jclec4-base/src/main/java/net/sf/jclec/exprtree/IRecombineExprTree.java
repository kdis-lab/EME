package net.sf.jclec.exprtree;

import net.sf.jclec.util.random.IRandGen;

/**
 * Recombine an expression tree
 * 
 * @author Sebastian Ventura
 */

public interface IRecombineExprTree 
{
	/**
	 * Recombination at expression tree level.
	 * 
	 * @param ptree0 First parent expression tree 
	 * @param ptree1 Second parent expression tree
	 * @param stree0 First son expression tree
	 * @param stree1 Second son expression tree
	 * @param schema Expression trees schema 
	 * @param randgen Random generator used in operation
	 */
	
	public abstract void recombine(ExprTree ptree0, ExprTree ptree1, ExprTree stree0, ExprTree stree1, ExprTreeSchema schema, IRandGen randgen); 
}
