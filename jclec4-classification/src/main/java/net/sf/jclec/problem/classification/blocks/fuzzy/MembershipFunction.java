package net.sf.jclec.problem.classification.blocks.fuzzy;

import java.util.ArrayList;

import net.sf.jclec.exprtree.fun.AbstractPrimitive;
import net.sf.jclec.exprtree.fun.ExprTreeFunction;
import net.sf.jclec.util.random.IRandGen;
import net.sf.jclec.util.range.Interval;

/**
 * Abstract class of a membership function.
 * 
 * Comprises the interval domain, the complete set of labels available and the current label subset in use.
 *  
 * @author Alberto Cano
 */

public abstract class MembershipFunction extends AbstractPrimitive
{
	/////////////////////////////////////////////////////////////////
	// ----------------------------------------------- Argument types
	/////////////////////////////////////////////////////////////////

	private static final long serialVersionUID = -1118767880576272109L;
	
	/** Argument types */
	
	private static final Class<?>[] ARG_TYPES = null;
	
	/** Domain interval */
	
	protected Interval interval;
	
	/** Set of current labels */
	
	protected ArrayList<String> labels;
	
	/** Set of all available labels */
	
	protected ArrayList<String> labelsSet;
	
	/** Random generator */
	
	protected IRandGen randgen;
	
	/////////////////////////////////////////////////////////////////
	// -------------------------------------------------- Constructor
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Empty (default) constructor.
	 */
	
	public MembershipFunction() {
		super(ARG_TYPES,Boolean.class);
		
		labels = new ArrayList<String>();
		labelsSet = new ArrayList<String>();
	}
	
	/////////////////////////////////////////////////////////////////
	// ----------------------------------------------- Public methods
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Gets the interval of the membership function
	 * 
	 * @return the interval
	 */
	public Interval getInterval() {
		return interval;
	}

	/**
	 * Sets the interval of the membership function
	 * @param interval the interval to set
	 */
	public void setInterval(Interval interval) {
		this.interval = interval;
	}

	/**
	 * Get the labels used in the membership function
	 * @return the labels
	 */
	public ArrayList<String> getLabels() {
		return labels;
	}

	/**
	 * Set the labels used in the membership function
	 * @param labels the labels to set
	 */
	public void setLabels(ArrayList<String> labels) {
		this.labels = labels;
	}

	/**
	 * Gets the set of labels available in the membership function
	 * @return the labelsSet
	 */
	public ArrayList<String> getLabelsSet() {
		return labelsSet;
	}

	/**
	 * Sets the set of labels available in the membership function
	 * @param labelsSet the labelsSet to set
	 */
	public void setLabelsSet(ArrayList<String> labelsSet) {
		this.labelsSet = labelsSet;
	}
	
	/**
	 * Gets the random generator
	 * 
	 * @return the randgen
	 */
	public IRandGen getRandgen() {
		return randgen;
	}

	/**
	 * Sets the random generator
	 * 
	 * @param randgen the randgen to set
	 */
	public void setRandgen(IRandGen randgen) {
		this.randgen = randgen;
	}

	public abstract double compatibilityDegree(double value);
	
	public abstract void initializeLabel();
	
	/**
	 * Execute this operation over the stack and context
	 * 
	 * @param context the ExprTreeFunction context
	 */

	public void evaluate(ExprTreeFunction context) 
	{
		super.push(context, this);
	}
	
	/**
	 * Shows this operation identification
	 * 
	 * @return the labels employed
	 */
	
	public String toString()
	{
		String str = "";
		
		for(String s : labels)
			str += s + " | ";
		
		return str.substring(0,str.length()-3);
	}
}