package net.sf.jclec.problem.classification.blocks.fuzzy;

import java.util.ArrayList;

import net.sf.jclec.util.random.IRandGen;
import net.sf.jclec.util.range.Interval;

/**
 * Implementation of a triangular membership function.
 * It considers the interval domain, the set of labels available,
 * and the actual set of labels in use for a particular instance of the triangular function.
 * 
 * The main method is compatibilityDegree() which checks the compatibility degree of an attribute value with the membership function.
 * 
 * @author Alberto Cano
 */

public class TriangularMembershipFunction extends MembershipFunction
{
	/** Generated UID */
	
	private static final long serialVersionUID = -1118767880576272109L;
	
	/** Probability to contains a label */
	
	private double labelProb = 0.3;
	
	/////////////////////////////////////////////////////////////////
	// -------------------------------------------------- Constructor
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Default (empty) constructor
	 */
	
	public TriangularMembershipFunction() {
		super();
	}
	
	/**
	 * Constructor
	 * 
	 * @param interval the interval
	 * @param randgen the random generator
	 * @param labelsSet the set of available labels
	 */
	
	public TriangularMembershipFunction(Interval interval, IRandGen randgen, ArrayList<String> labelsSet) {
		super();
		
		this.interval = interval;
		this.randgen = randgen;
		this.labelsSet = labelsSet;
	}
	
	/**
	 * Constructor
	 * 
	 * @param interval the interval
	 * @param randgen the random generator
	 * @param labels set of labels employed
	 * @param labelsSet the set of available labels
	 */
	
	public TriangularMembershipFunction(Interval interval, ArrayList<String> labels, ArrayList<String> labelsSet, IRandGen randgen)
	{
		this(interval, randgen, labelsSet);
		
		this.labels = labels;
	}
	
	/////////////////////////////////////////////////////////////////
	// ----------------------------------------------- Public methods
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Initializes the labels
	 * 
	 * {@inheritDoc}
	 */

	public void initializeLabel()
	{
		for(int i = 0; i < labelsSet.size(); i++)
		{
			if(randgen.coin(labelProb))
			{
				labels.add(labelsSet.get(i));
			}
		}
		
		if(labels.size() == 0)
			labels.add(labelsSet.get(randgen.choose(0,labelsSet.size())));
	}
	
	/**
	 * Check the compatibility degree of an attribute value with the membership function
	 * 
	 * @param value the attribute value
	 * @return the compatibility degree
	 */
	
	public double compatibilityDegree(double value)
	{
		double maxCompatibility = 0.0;
		
		for(int i = 0; i < labels.size(); i++)
		{
			double compatibility = compatibilityDegree(labels.get(i), value);
			
			if(compatibility > maxCompatibility)
				maxCompatibility = compatibility;
		}
		
		return maxCompatibility;
	}
	
	/**
	 * Implementation of copy()
	 * 
	 * {@inheritDoc}
	 */
	
	public TriangularMembershipFunction copy()
	{
		ArrayList<String> labelsCopy = new ArrayList<String>();
		for(String s : labels)
			labelsCopy.add(s);
		
		ArrayList<String> labelsSetCopy = new ArrayList<String>();
		for(String s : labelsSet)
			labelsSetCopy.add(s);
				
		return new TriangularMembershipFunction(new Interval(interval.getLeft(), interval.getRight(), interval.getClosure()), labelsCopy, labelsSetCopy, randgen);
	}
	
	/**
	 * Check the compatibility degree of an attribute value with a certain label over the membership function
	 * 
	 * @param function the label
	 * @param value the attribute value
	 * @return the compatibility degree
	 */
	
	private double compatibilityDegree(String function, double value)
	{
		double stepSize = (interval.getRight() - interval.getLeft()) / (double) (labelsSet.size() - 1);
		
		double left, center, right;

		center = interval.getLeft() + labelsSet.indexOf(function)*stepSize;
		left = center - stepSize;
		right = center + stepSize;
		
		if(value == center)
			return 1.0;
		
		if(value <= left || value >= right)
			return 0.0;
		
		if(left < value && value < center)
			return (value-left) / (center-left);
	
		if(right > value && value > center)
			return (right-value) / (right-center);
		
		return 0.0;
	}
}