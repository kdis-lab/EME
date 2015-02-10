package net.sf.jclec.problem.classification.blocks.fuzzy;

import net.sf.jclec.exprtree.fun.AbstractPrimitive;
import net.sf.jclec.exprtree.fun.ExprTreeFunction;

/**
 * Implementation of the T-conorm : Maximum
 * 
 * @author Pilar Caballano
 * @author Alberto Cano
 */

public class Maximum extends AbstractPrimitive
{
	/////////////////////////////////////////////////////////////////
	// ----------------------------------------------- Argument types
	/////////////////////////////////////////////////////////////////

	private static final long serialVersionUID = 8391399971045710669L;
	
	/** Argument types */
	
	private static final Class<?>[] ARG_TYPES = 
		new Class<?> [] {Double.class, Double.class};

	/////////////////////////////////////////////////////////////////
	// -------------------------------------------------- Constructor
	/////////////////////////////////////////////////////////////////

	/**
	 * Empty (default) constructor.
	 */
	
	public Maximum()
	{
		super(ARG_TYPES, Double.class);
	}

	/////////////////////////////////////////////////////////////////
	// ----------------------------------------------- Public methods
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Execute this operation over the stack and context.
	 * 
	 * @param context the ExprTreeFunction context
	 */

	public void evaluate(ExprTreeFunction context) 
	{
		// Lowe interval extremum
		double arg0 =  super.pop(context);
		// Upper interval extremum
		double arg1 =  super.pop(context);
				
		super.push(context, Math.max(arg0, arg1));
	}
	
	/////////////////////////////////////////////////////////////////
	// ------------------------- Overwriting java.lang.Object methods
	/////////////////////////////////////////////////////////////////

	/**
	 * Compare two objects.
	 * 
	 * @param other Object to compare.
	 * 
	 * @return Result of the comparison.
	 * 
	 */
	
	public boolean equals(Object other)
	{
		return other instanceof Maximum;
	}	

	/**
	 * Shows this operation identification. 
	 * 
	 * @return OR
	 */
	
	public String toString()
	{
		return "OR";
	}
}