package net.sf.jclec.exprtree.rec;

import java.util.List;
import java.util.ArrayList;

import net.sf.jclec.exprtree.ExprTree;
import net.sf.jclec.exprtree.ExprTreeSchema;
import net.sf.jclec.exprtree.IRecombineExprTree;
import net.sf.jclec.util.random.IRandGen;

/**
 * Subtrees crossover.
 * 
 * This operator selects randomly two subtrees (one on each parent) and swaps them.
 * 
 * @author Sebastian Ventura
 *
 */

public class SubtreeCrossover implements IRecombineExprTree 
{
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////

	/**
	 * Empty constructor
	 */
	
	public SubtreeCrossover() 
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
		return (other instanceof SubtreeCrossover);
	}
	
	/////////////////////////////////////////////////////////////////
	// -------------------------------------------- Protected methods
	/////////////////////////////////////////////////////////////////

	// ExprTreeRecombinator methods
	
	/**
	 * {@inheritDoc}
	 */
	
	@Override
	
	public void recombine(ExprTree ptree0, ExprTree ptree1, ExprTree stree0, ExprTree stree1, ExprTreeSchema schema, IRandGen randgen) 
	{
		// First parent size
		int treeP0Size = ptree0.size();
		// Second parent size
		int treeP1Size = ptree1.size();
		
		// Root index for first crossover subtree 
		int cp1Start = randgen.choose(1, treeP0Size);
		// One node after first crossover subtree 
		int cp1EndP1 = ptree0.subTree(cp1Start);
		// Size of first crossover subtree 
		int cp1Size = cp1EndP1 - cp1Start;
		// Return type for first crossover subtree
		Class<?> cp1Type = ptree0.getBlock(cp1Start).returnType();
		// First crossover step : Locate valid root nodes for the  
		// second subtree (same return type). Starts in i=1 for
		// excluding root node
		List<Integer> cp2List1 = new ArrayList<Integer> ();		
		for (int i=1; i<treeP1Size; i++) {
			if (ptree1.getBlock(i).returnType().equals(cp1Type)) {
				cp2List1.add(i);
			}
		}
		// Maximum tree size
		int maxTreeSize = schema.getMaxTreeSize();
		// Second crossover step: Remove root nodes that result 
		// invalid sons (son size greater that maximum tree size) 
		// Starts in i=1 for excluding root node
		List<Integer> cp2List2 = new ArrayList<Integer> ();
		for (int i=1; i<cp2List1.size(); i++) {
			// Second crossover point
			int cp2i = cp2List1.get(i);
			// Size of second crossover subtree
			int cp2iSize = ptree1.subTree(cp2i) - cp2i;
			// First condition: 
			boolean cond1 = (treeP0Size - cp1Size + cp2iSize) <= maxTreeSize ;
			// Second condition
			boolean cond2 = (treeP1Size - cp2iSize + cp1Size) <= maxTreeSize;
			// If both conditions are satisfied...
			if (cond1 && cond2) {
				cp2List2.add(i);
			}
		}		
		
		// If there are valid crossover points, perform crossover 
		if (cp2List2.size() != 0) {
			// Second crossover point
			int cp2Start = cp2List2.get(randgen.choose(0, cp2List2.size()));
			// One node after second crossover subtree  
			int cp2EndP1 = ptree1.subTree(cp2Start);
			// First son
			for (int i=0; i<cp1Start; i++) 
				stree0.addBlock(ptree0.getBlock(i).copy()); 
			for (int i=cp2Start; i<cp2EndP1; i++)
				stree0.addBlock(ptree1.getBlock(i).copy()); 
			for (int i= cp1EndP1; i<treeP0Size; i++)
				stree0.addBlock(ptree0.getBlock(i).copy()); 
			// Second son
			for (int i=0; i<cp2Start; i++) 
				stree1.addBlock(ptree1.getBlock(i).copy()); 
			for (int i=cp1Start; i<cp1EndP1; i++)
				stree1.addBlock(ptree0.getBlock(i).copy()); 
			for (int i= cp2EndP1; i<treeP1Size; i++)
				stree1.addBlock(ptree1.getBlock(i).copy()); 
		}
		// Else sons are copies of parents
		else {
			for (int i=0; i<treeP0Size; i++) {
				stree0.addBlock(ptree0.getBlock(i).copy());
			}
			for (int i=0; i<treeP1Size; i++) {
				stree1.addBlock(ptree1.getBlock(i).copy());
			}
		}
	}
}
