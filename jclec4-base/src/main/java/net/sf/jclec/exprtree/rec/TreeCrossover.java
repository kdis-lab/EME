package net.sf.jclec.exprtree.rec;


import net.sf.jclec.exprtree.ExprTree;
import net.sf.jclec.exprtree.ExprTreeSchema;
import net.sf.jclec.exprtree.IRecombineExprTree;

import net.sf.jclec.util.random.IRandGen;

/**
 * Trees crossover.
 * 
 * This operator swap a complete tree 
 * 
 * @author Sebastian Ventura
 */

public class TreeCrossover implements IRecombineExprTree 
{
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////

	/**
	 * Empty constructor
	 */
	
	public TreeCrossover() 
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
		return (other instanceof TreeCrossover);
	}
	
	/////////////////////////////////////////////////////////////////
	// -------------------------------------------- Protected methods
	/////////////////////////////////////////////////////////////////
	
	// ExprTreeRecombinator methods
	
	/**
	 * In this operation, tree sons are equal to ... 
	 * 
	 * {@inheritDoc}
	 * 
	 * 
	 */
	
	@Override
	public void recombine(ExprTree ptree0, ExprTree ptree1, ExprTree stree0, ExprTree stree1, ExprTreeSchema tschema, IRandGen randgen) 
	{
		int p0size = ptree0.size();
		for (int i=0; i<p0size; i++) {
			stree1.addBlock(ptree0.getBlock(i).copy());
		}
		int p1size = ptree1.size();
		for (int i=0; i<p1size; i++) {
			stree0.addBlock(ptree1.getBlock(i).copy());
		}
	}
}
