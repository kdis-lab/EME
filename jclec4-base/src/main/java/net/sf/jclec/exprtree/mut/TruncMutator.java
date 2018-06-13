package net.sf.jclec.exprtree.mut;

import net.sf.jclec.exprtree.ExprTree;
import net.sf.jclec.exprtree.ExprTreeSchema;
import net.sf.jclec.exprtree.IMutateExprTree;

import net.sf.jclec.util.random.IRandGen;

/**
 * Randomly select a  function node  in the  expression and  replace it  with a 
 * terminal (of  the same return  type), thus effectively  clipping the tree at 
 * that node.
 * 
 * @author Sebastian Ventura
 */

public class TruncMutator implements IMutateExprTree
{
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////

	public TruncMutator() 
	{
		super();
	}

	/////////////////////////////////////////////////////////////////
	// ----------------------------------------------- Public methods
	/////////////////////////////////////////////////////////////////

	// java.lang.Object methods

	@Override
	public boolean equals(Object other)
	{
		return (other instanceof TruncMutator);
	}
	
	/////////////////////////////////////////////////////////////////
	// -------------------------------------------- Protected methods
	/////////////////////////////////////////////////////////////////
	
	// ExprTreeMutator methods
	
	/**
	 * ...
	 * 
	 * @param ptree Parent tree
	 * @param schema Tree information
	 * @param randgen Random generator
	 * 
	 * @return Son tree
	 */
	
	@Override
	public ExprTree mutateExprTree(ExprTree ptree, ExprTreeSchema schema, IRandGen randgen) 
	{
		// Create son 
		ExprTree stree = new ExprTree();
		// Parent size
		int size = ptree.size();
		// Choose the node to mutate
		int startIndex;
		do {
			startIndex = randgen.choose(1, size);
		}
		while(ptree.getBlock(startIndex).argumentTypes().length == 0);
		// Subtree index
		int endIndex = ptree.subTree(startIndex);
		// Copy blocks before subtree
		for (int i=0; i<startIndex; i++) {
			stree.addBlock(ptree.getBlock(i).copy());
		}
		// Replace subtree with terminal
		Class<?> rtype = ptree.getBlock(startIndex).returnType();
		stree.addBlock(schema.getTerminalBlock(rtype, randgen));
		// Copy blocks after subtree
		for (int i=endIndex; i<size; i++) {
			stree.addBlock(ptree.getBlock(i).copy());			
		}
		// Return result
		return stree;
	}	
}
