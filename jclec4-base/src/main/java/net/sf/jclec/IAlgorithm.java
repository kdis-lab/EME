package net.sf.jclec;

public interface IAlgorithm extends JCLEC
{
	// GenLab methods
	
	/**
	 * Execution method
	 */
	
	public void execute();	
	
	/**
	 * Pause this algorithm execution
	 */
	
	public void pause();
	
	/**
	 * Terminate this algorithm execution
	 */
	
	public void terminate();
	
	// Listener registering/unregistering
	
	/**
	 * Adds the specified listener  to receive  algorithm events from 
	 * this algorithm.
	 * 
	 * @param listener The listener to be added
	 */
	
	public void addListener(IAlgorithmListener listener);

	/**
	 * Removes the specified listener so it no longer receives events 
	 * from this algorithm.
	 * 
	 * @param listener The listener to be removed
	 * 
	 * @return true If the listener is correctly removed
	 */
	
	public boolean removeListener(IAlgorithmListener listener);	
}
