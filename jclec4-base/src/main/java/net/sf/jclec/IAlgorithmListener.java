package net.sf.jclec;

/**
 * Algorithm listener.
 * 
 * @author Sebastian Ventura
 */

public interface IAlgorithmListener
{
    /** 
     * This event is fired when the algorithm has completed normally.
     * 
	 * @param event An event containing a reference to the source algorithm.
     */ 
	
	public void algorithmStarted(AlgorithmEvent event);
	
    /**
     * This event is fired when the algorithm is terminated abnormally.
     * 
	 * @param event An event containing a reference to the source algorithm.
     */ 

	public void iterationCompleted(AlgorithmEvent event);

	/**
	 * This event is fired when the algorithm has completed normally. 
	 * 
	 * @param event An event containing a reference to the source algorithm.
	 */
	
	public void algorithmFinished(AlgorithmEvent event);


    /**
     * This event is fired when the algorithm is terminated abnormally.
     * 
     * @param e an event containing a reference to the source algorithm.
     */
	
    public void algorithmTerminated(AlgorithmEvent e);
}
