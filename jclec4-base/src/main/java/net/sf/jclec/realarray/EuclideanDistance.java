package net.sf.jclec.realarray;

import net.sf.jclec.IDistance;
import net.sf.jclec.IIndividual;

/**
 * Euclidean distance
 * 
 * @author Sebastian Ventura
 */

public class EuclideanDistance implements IDistance 
{
	/**
	 * Empty (default) constructor
	 */
	
	public EuclideanDistance() 
	{
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public double distance(IIndividual one, IIndividual other) 
	{
		// Check RealArrayIndividuals
		if ( (one instanceof RealArrayIndividual) && (other instanceof RealArrayIndividual))
		{
			double [] gone = ( (RealArrayIndividual) one).getGenotype();
			double [] gother = ( (RealArrayIndividual) other).getGenotype();
			
			double distance = 0.0;
			
			for (int i=0; i<gone.length; i++){
				double diff = gone[i] - gother[i];
				distance += diff * diff;
			}
			return Math.sqrt(distance);
			
		}
		throw new IllegalArgumentException("RealIndividual required");
	}

}
