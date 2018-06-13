package net.sf.jclec.exprtree.mut;

import net.sf.jclec.exprtree.ExprTree;
import net.sf.jclec.exprtree.IPrimitive;
import net.sf.jclec.exprtree.ExprTreeSchema;
import net.sf.jclec.exprtree.IMutateExprTree;

import net.sf.jclec.util.random.IRandGen;

/**
 * Took each and every node in the computer program and replaced it with another 
 * randomly chosen block of the same return type and arity.
 * 
 * @author Sebastian Ventura
 */

public class AllNodesMutator implements IMutateExprTree
{
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////

	public AllNodesMutator() 
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
		return (other instanceof AllNodesMutator);
	}

	/////////////////////////////////////////////////////////////////
	// -------------------------------------------- Protected methods
	/////////////////////////////////////////////////////////////////
	
	// PrefExprMutator methods
	
	public ExprTree mutateExprTree(ExprTree ptree, ExprTreeSchema schema, IRandGen randgen) 
	{
		// Copy of expr
		ExprTree son = ptree.copy();
		// One past last node to mutate
		int size = son.size();
		for (int i=0; i<size; i++) {
			// Mutated node
			IPrimitive oldBlock = son.getBlock(i);
			Class<?> rtype = oldBlock.returnType();
			int arity = oldBlock.argumentTypes().length;		
			IPrimitive newBlock = 
				schema.getAnyBlock(rtype, randgen, arity);
			son.setBlock(newBlock, i);
		}
		// Return result
		return son;
	}	
}
