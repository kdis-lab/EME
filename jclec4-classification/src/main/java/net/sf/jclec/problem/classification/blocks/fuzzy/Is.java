package net.sf.jclec.problem.classification.blocks.fuzzy;

import net.sf.jclec.exprtree.fun.AbstractPrimitive;
import net.sf.jclec.exprtree.fun.ExprTreeFunction;
import net.sf.jclec.exprtree.IPrimitive;

/**
 * Primitive implementationof the IS fuzzy operator using a membership function.
 * 
 * @author Pilar Caballano
 * @author Alberto Cano
 */

public class Is extends AbstractPrimitive
{
	/////////////////////////////////////////////////////////////////
	// ----------------------------------------------- Argument types
	/////////////////////////////////////////////////////////////////

	private static final long serialVersionUID = -399658639218154396L;

	/** Argument types */
	
	private static final Class<?> [] ARG_TYPES = 
		new Class<?> [] {Double.class, MembershipFunction.class};
	
	/////////////////////////////////////////////////////////////////
	// -------------------------------------------------- Constructor
	/////////////////////////////////////////////////////////////////

	/**
	 * Empty (default) constructor.
	 */
	
	public Is()
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
		Double value = (Double) super.pop(context);
		
		MembershipFunction function = (MembershipFunction) super.pop(context);
		
		super.push(context, function.compatibilityDegree(value));
	}
	
	/**
	 * Default implementation of copy() return this.
	 * 
	 * {@inheritDoc}
	 */
	
	public IPrimitive copy() 
	{
		return this;
	}

	/**
	 * Default implementation of instance() return this.
	 * 
	 * {@inheritDoc}
	 */
	
	public IPrimitive instance() 
	{
		return this;
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
		return other instanceof Is;
	}	

	/**
	 * Shows this operation identification.
	 * 
	 * @return IS
	 */
	
	public String toString()
	{
		return "IS";
	}
}