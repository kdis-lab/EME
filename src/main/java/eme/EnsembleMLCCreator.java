package eme;

import net.sf.jclec.binarray.BinArrayCreator;
import java.util.HashSet;

/**
 * @author Jose M. Moyano <jmoyano@uco.es>
 * 
 * Class implementing an individual generator for EME
 */
public class EnsembleMLCCreator extends BinArrayCreator
{
	/////////////////////////////////////////////////////////////////
	// --------------------------------------- Serialization constant
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Serialization constant
	 */
	private static final long serialVersionUID = -2638928425169895614L;
	
	/////////////////////////////////////////////////////////////////
	// --------------------------------------------------- Properties
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Number of labels */
	private int numberLabels;
	
	/**
	 * Number of classifiers in the ensemble */
	private int numberClassifiers;
	
	/**
	 * Max number of labels in each base classifier (k)
	 * At the moment, the number of labels for each classifier is fixed, but it could be variable */
	private int maxNumberLabelsClassifier;
	
	/** 
	 * Indicates if the number of active labels is variable for each base classifier */
	private boolean variable;

	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Empty constructor
	 */
	public EnsembleMLCCreator() 
	{
		super();
	}

	/////////////////////////////////////////////////////////////////
	// -------------------------------------------- Protected methods
	/////////////////////////////////////////////////////////////////
	
	/**
	 * {@inheritDoc}
	 */	
	@Override
	protected void createNext() 
	{
		createdBuffer.add(species.createIndividual(createGenotype()));
	}
	
	/////////////////////////////////////////////////////////////////
	// ----------------------------------------------- Public methods
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Set the number of labels of the problem
	 * 
	 * @param numberLabels Number of labels
	 */
	public void setNumberLabels(int numberLabels) {
		this.numberLabels = numberLabels;
	}

	/**
	 * Set the number of classifiers in the ensemble
	 * 
	 * @param numberClassifiers Number of classifiers in the ensemble
	 */
	public void setNumberClassifiers(int numberClassifiers) {
		this.numberClassifiers = numberClassifiers;
	}

	/**
	 * Set the max number of labels in each base classifier (k)
	 * At the moment, the number of labels for each classifier is fixed, but it could be variable
	 *
	 * @param maxNumberLabelsClassifier Max number of labels in each base classifier
	 */
	public void setMaxNumberLabelsClassifier(int maxNumberLabelsClassifier) {
		this.maxNumberLabelsClassifier = maxNumberLabelsClassifier;
	}

	/**
	 * Set variable value
	 * 
	 * @param variable FALSE if the number of labels for each base classifier is fixed, and TRUE if it wold be variable
	 */
	public void setVariable(boolean variable)
	{
		this.variable=variable;
	}
	
	/////////////////////////////////////////////////////////////////
	// ---------------------------------------------- Private methods
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Create a int [] genotype
	 */
	private final byte [] createGenotype()
	{
		byte [] result = new byte[numberClassifiers * numberLabels];
		HashSet<String> Combinations = new HashSet<String>();
		
		int numLabelsClassifier;
		boolean [] visited;
		
		//For each classifier in the ensemble		
		for (int model=0; model<numberClassifiers; )
		{
			if (variable)
				numLabelsClassifier = randgen.choose(2, maxNumberLabelsClassifier+1); //At least 2 labels
			else
				numLabelsClassifier = maxNumberLabelsClassifier;	
			
			//Inicializations
			StringBuffer comb2 = new StringBuffer("");			
			visited = new boolean[numberLabels];	
			
			for(int label=0; label<numberLabels; label++)
			{	   
			   visited[label]=false;
			   result[model*numberLabels+label]=0;
			   comb2.append('0');
			}
			   
			for(int label=0; label<numLabelsClassifier; ) 
			{
	           //Random selection of one label		
	           int randomLabel= (int) randgen.choose(0, numberLabels);
	           if(!visited[randomLabel])
	           {	   
				  visited[randomLabel]=true;
				  result[model*numberLabels+randomLabel]=1;
				  comb2.setCharAt(randomLabel, '1');
	          	  label++;
	           }   
			}	
			
			if((checkModel(model, result)==true) && (Combinations.add(comb2.toString())==true))
			{   //Checks if the model is already in the ensemble
	             model++;
			}
		}	
		
		return result;
	}	
	
	/**
	 * Check if the model has been correctly created
	 * 
	 * @param model Number identifying the model in the idnidivual
	 * @param genotype Genotype
	 * 
	 * @return TRUE if it has been correctly created and FALSE otherwise
	 */
	protected boolean checkModel(int model, byte genotype[])
	{
		boolean check = true;
		
		//Check if the model is 0, maxInteger-1 (all 1s), pow of 2 (0 or 1 labels selected)
        int count0s=0, count1s=0;
        for(int label=0; label< numberLabels; label++)
        {
        	if(genotype[model*numberLabels+label]==0)
        		count0s+=1;
        	if(genotype[model*numberLabels+label]==1)
        		count1s+=1;        	
        }
        if ((count0s==numberLabels)||(count1s==numberLabels)||(count1s==1))
        	check=false;
        
        return(check);	
	}
}