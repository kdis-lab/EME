package net.sf.jclec.problem.classification.blocks.fuzzy;

import net.sf.jclec.exprtree.fun.AbstractPrimitive;
import net.sf.jclec.exprtree.fun.ExprTreeFunction;

/**
 * Implementation of the T-norm : Minimum
 * 
 * @author Pilar Caballano
 * @author Alberto Cano
 */

public class Minimum extends AbstractPrimitive
{
	/////////////////////////////////////////////////////////////////
	// ----------------------------------------------- Argument types
	/////////////////////////////////////////////////////////////////

	private static final long serialVersionUID = 4812669509414941489L;
	
	/** Argument types */
	
	private static final Class<?>[] ARG_TYPES = 
		new Class<?> [] {Double.class, Double.class};

	/////////////////////////////////////////////////////////////////
	// -------------------------------------------------- Constructor
	/////////////////////////////////////////////////////////////////

	/**
	 * Empty (default) constructor.
	 */
	
	public Minimum()
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
		
		super.push(context, Math.min(arg0, arg1));
	}
	
	// ------------------------- Overwriting java.lang.Object methods
	/////////////////////////////////////////////////////////////////

	/**
	 * Compare two objects.
	 * 
	 * @param other Object to compare.
	 * 
	 * @return Result of the comparison.
	 */
	
	public boolean equals(Object other)
	{
		return other instanceof Minimum;
	}	

	/**
	 * Shows this operation identification.
	 * 
	 * @return AND
	 */
	
	public String toString()
	{
		return "AND";
	}
}