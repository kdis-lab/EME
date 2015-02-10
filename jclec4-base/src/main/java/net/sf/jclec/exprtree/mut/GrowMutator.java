package net.sf.jclec.exprtree.mut;

import net.sf.jclec.exprtree.ExprTree;
import net.sf.jclec.exprtree.ExprTreeSchema;
import net.sf.jclec.exprtree.IMutateExprTree;

import net.sf.jclec.util.random.IRandGen;

/**
 * Randomly select a terminal node in the expression and replace it with a 
 * newly generated subtree (of the same return type).
 * 
 * @author Sebastian Ventura
 */

public class GrowMutator  implements IMutateExprTree
{
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////

	public GrowMutator() 
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
		return (other instanceof GrowMutator);
	}
	
	/////////////////////////////////////////////////////////////////
	// -------------------------------------------- Protected methods
	/////////////////////////////////////////////////////////////////
	
	// ExprTreeMutator methods
	
	/**
	 * ...
	 * 
	 * {@inheritDoc}
	 */
	
	@Override
	public ExprTree mutateExprTree(ExprTree ptree, ExprTreeSchema schema, IRandGen randgen) 
	{
		// Create son 
		ExprTree son = new ExprTree();
		// Parent size
		int size = ptree.size();
		// Choose the node to mutate
		int startIndex;
		do {
			startIndex = randgen.choose(1, size);
		}
		while(ptree.getBlock(startIndex).argumentTypes().length != 0);		
		// Copy blocks before subtree
		for (int i=0; i<startIndex; i++) {
			son.addBlock(ptree.getBlock(i).copy());
		}
		// Brach size  		
		int branchSize = 
			randgen.choose(0, schema.getMaxTreeSize() - size);
		if (branchSize == 0) branchSize = 1;
		// Replace terminal with subtree
		Class<?> rtype = ptree.getBlock(startIndex).returnType();
		schema.fillExprBranch(son, rtype, branchSize, randgen);
		// Copy blocks after subtree
		for (int i=startIndex+1; i<size; i++) {
			son.addBlock(ptree.getBlock(i).copy());			
		}
		// Return result
		return son;
	}
}
