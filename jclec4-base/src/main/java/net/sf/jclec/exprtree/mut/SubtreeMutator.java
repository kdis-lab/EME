package net.sf.jclec.exprtree.mut;

import net.sf.jclec.exprtree.ExprTree;
import net.sf.jclec.exprtree.ExprTreeSchema;
import net.sf.jclec.exprtree.IMutateExprTree;
import net.sf.jclec.util.random.IRandGen;

/**
 * Subtree mutator.
 * 
 * @author Sebastian Ventura
 */

public class SubtreeMutator implements IMutateExprTree
{
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////

	public SubtreeMutator() 
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
		return (other instanceof SubtreeMutator);
	}
	
	/////////////////////////////////////////////////////////////////
	// -------------------------------------------- Protected methods
	/////////////////////////////////////////////////////////////////

	// ExprTreeMutator methods
	
	@Override
	public ExprTree mutateExprTree(ExprTree ptree, ExprTreeSchema schema, IRandGen randgen) 
	{
		// Create son 
		ExprTree stree = new ExprTree();
		// Parent size
		int size = ptree.size();
		// Choose the node to mutate
		int startIndex = randgen.choose(1, size);
		// End of subtree (plus one) index
		int endIndex = ptree.subTree(startIndex);
		// Copy blocks before subtree
		for (int i=0; i<startIndex; i++) {
			stree.addBlock(ptree.getBlock(i).copy());
		}
		// Replace old branch with a new one
		int new_size = size - (endIndex - startIndex);
		int branchSize = 
			randgen.choose(0, schema.getMaxTreeSize() - new_size);
		if (branchSize == 0) branchSize = 1;
		Class<?> rtype = ptree.getBlock(startIndex).returnType();
		schema.fillExprBranch(stree, rtype, branchSize, randgen);
		// Copy blocks after subtree
		for (int i=endIndex; i<size; i++) {
			stree.addBlock(ptree.getBlock(i).copy());			
		}
		// Return result
		return stree;
	}	
}
