package net.sf.jclec.exprtree.mut;

import net.sf.jclec.exprtree.ExprTree;
import net.sf.jclec.exprtree.ExprTreeSchema;
import net.sf.jclec.exprtree.IMutateExprTree;

import net.sf.jclec.util.random.IRandGen;

/**
 * Randomly selected a function node, replacing it with its larger son.
 *  
 * @author Sebastian Ventura
 */

public class DemoteMutator implements IMutateExprTree
{
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////

	public DemoteMutator() 
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
		return (other instanceof DemoteMutator);
	}

	/////////////////////////////////////////////////////////////////
	// -------------------------------------------- Protected methods
	/////////////////////////////////////////////////////////////////
	
	// ExprTreeMutator methods
	
	@Override
	public ExprTree mutateExprTree(ExprTree ptree, ExprTreeSchema schema, IRandGen randgen) 
	{
		// Create son 
		ExprTree son = new ExprTree();
		// Parent size
		int size = ptree.size();
		// Choose the node to mutate (function node)
		int startIndex;
		do {
			startIndex = randgen.choose(1, size);
		}
		while(ptree.getBlock(startIndex).argumentTypes().length == 0);
		
		// Subtree index
		int endIndex = ptree.subTree(startIndex);
		// Copy blocks before subtree
		for (int i=0; i<startIndex; i++) {
			son.addBlock(ptree.getBlock(i).copy());
		}
		// Replace subtree with the largest of its children
		addLargestChildren(ptree, son, startIndex, randgen);		
		// Copy blocks after subtree
		for (int i=endIndex; i<size; i++) {
			son.addBlock(ptree.getBlock(i).copy());			
		}
		// Return result
		return son;
	}
	
	/**
	 * ...
	 * 
	 * @param ptree Tree parent
	 * @param stree Tree son
	 * @param start Start index
	 */

	protected void addLargestChildren (ExprTree ptree, ExprTree stree, int start, IRandGen randgen)
	{	
		// Number of sons
		int nSons = ptree.getBlock(start).argumentTypes().length;
		// Size of son and start of first son
		int sizeSon, startSon = start+1;
		// In the case that more of one son has the same size
		boolean change;
		// Start and end of the largest son in the expression tree. By defect, 
		// they have the same values that the root node.
		int startLargestSons = start, endLargestSons = ptree.subTree(start);
		// Size of the largest son
		int largestSon = -1;
		
		//Type of the node that is replaced by the largests of its sons
		Class<?> rtype = ptree.getBlock(start).returnType();
		//Typoe of the sons
		Class<?> stypes [] = ptree.getBlock(start).argumentTypes();
		
		// Obtain the largest son
		for(int i=0; i<nSons;i++){
			if(stypes[i].equals(rtype))
			{
				sizeSon = ptree.subTree(startSon) - startSon;
				change = false;

				//If this son has same size, randomly choose one
				// < 0.5 the before son is kept
				// >= 0.5 the new son is choosen
				if(sizeSon == largestSon)
					if(randgen.choose(0,1) >= 0.5) change = true;
			
				if(sizeSon > largestSon || change){
					startLargestSons = startSon;
					endLargestSons = sizeSon + startSon;
					largestSon = sizeSon;
				}
			}
		}
		
		// Copy the largest of the children
		for(int i=startLargestSons; i<endLargestSons; i++){
			stree.addBlock(ptree.getBlock(i).copy());
			
		}
	}	
}
