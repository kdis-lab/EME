package net.sf.jclec.exprtree.mut;

import net.sf.jclec.exprtree.ExprTree;
import net.sf.jclec.exprtree.ExprTreeSchema;
import net.sf.jclec.exprtree.IPrimitive;
import net.sf.jclec.exprtree.IMutateExprTree;
import net.sf.jclec.util.random.IRandGen;

/**
 * A random  subtree, T', is  selected and replaced  by a random function node. 
 * T' becomes  a child of  the new function  node, rest of  children are set to 
 * random terminals.
 *  
 * @author Sebastian Ventura
 */

public class PromoteMutator implements IMutateExprTree
{
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////

	public PromoteMutator() 
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
		return (other instanceof PromoteMutator);
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
		ExprTree stree = new ExprTree();
		// Parent size
		int size = ptree.size();
		// Choose the node to mutate
		int startIndex;
		// Maximum size let 
		int maxSize = schema.getMaxTreeSize() -size;
		// Arity maximum in the new function
		int maxArity = maxSize - 1;
		
		// The maximum size must be one number mayor o igual a 1
		if(maxSize >= 1)
		{
			// Choose a subtree
			startIndex = randgen.choose(1, size);
			// Subtree index
			int endIndex = ptree.subTree(startIndex);
			// Copy blocks before subtree
			for (int i=0; i<startIndex; i++) {
				stree.addBlock(ptree.getBlock(i).copy());
			}
		
			// Replace terminal with subtree
			Class<?> rtype = ptree.getBlock(startIndex).returnType();
			
			// Choose a suitable function
			IPrimitive result = functionBlockSuitable(maxArity,rtype, schema, randgen);
			
			//if the mutation has been carried out
			if(result != null)
			{
				stree.addBlock(result);
		
				// One children is the subtree selected
				for(int i=startIndex; i<ptree.subTree(startIndex); i++)
					stree.addBlock(ptree.getBlock(i));
				// Other children are initialized to random terminals
				for(int i=1; i<result.argumentTypes().length; i++)
					stree.addBlock(schema.getTerminalBlock(rtype,randgen));
			
			}
			//If not, the subtree of source individual is copied
			else
			{
				for (int i=startIndex; i<endIndex; i++) 
					stree.addBlock(ptree.getBlock(i).copy());			
			}
		
			// Copy blocks after subtree
			for (int i=endIndex; i<size; i++) {
				stree.addBlock(ptree.getBlock(i).copy());			
			}
		}
		else
			stree = ptree.copy();
		
		
		// Return result
		return stree;
	}
	
	protected IPrimitive functionBlockSuitable(int maxArity, Class<?> rtype, ExprTreeSchema schema, IRandGen randgen)
	{
		
		// Block obtained
		IPrimitive result = null;
		// The maximum number of attempt to find a suitable function will
		// be equal to the number the possible functions
		int maxAttempt = schema.getNumFunctionBlocks(rtype);
		// Check the number of attempts done
		int attempt = 0;
		// Valid Element 
		boolean noValid=true;
		
		do
		{
			// Obtain a appropriate function
			result = schema.getFunctionBlockBetweenMinMaxArity(rtype, randgen, 1, maxArity);
			
			// If result == null, there isn't a function with the 
			// necessary characteristics
			if(result == null)
				attempt = maxAttempt;
			else
			{
				// Type of the children
				Class<?> []sonstype = result.argumentTypes();
	
				// Check the type
				for(int i=0; i<sonstype.length && noValid; i++)
					if(sonstype[i].equals(rtype))
						noValid = false;
			}
			// One attempt more
			attempt ++;
		} 
		while(noValid && (attempt < maxAttempt));
		
		return result.instance();
	}
}
