package net.sf.jclec.problem.classification.exprtree;

import java.util.ArrayList;
import java.util.List;

import net.sf.jclec.exprtree.ExprTree;
import net.sf.jclec.exprtree.ExprTreeSchema;
import net.sf.jclec.exprtree.IPrimitive;
import net.sf.jclec.problem.classification.blocks.AttributeValue;
import net.sf.jclec.problem.classification.blocks.RandomConstantOfContinuousValues;
import net.sf.jclec.problem.classification.blocks.RandomConstantOfDiscreteValues;
import net.sf.jclec.problem.util.dataset.attribute.CategoricalAttribute;
import net.sf.jclec.problem.util.dataset.attribute.IAttribute;
import net.sf.jclec.problem.util.dataset.attribute.IntegerAttribute;
import net.sf.jclec.problem.util.dataset.attribute.NumericalAttribute;
import net.sf.jclec.problem.util.dataset.metadata.IMetadata;

/**
 * ExprTreeSpecies for ExprTreeClassificationRules.<p/>
 * 
 * Extends the ExprTreeSpecies for classification problems in order to provide dataset metadata to generate the appropriate rules.
 * 
 * @author Amelia Zafra
 * @author Sebastian Ventura
 * @author Jose M. Luna 
 * @author Alberto Cano 
 * @author Juan Luis Olmo
 */

public abstract class ExprTreeSpecies extends net.sf.jclec.exprtree.ExprTreeSpecies
{
	/////////////////////////////////////////////////////////////////
	// --------------------------------------------------- Properties
	/////////////////////////////////////////////////////////////////
	
	private static final long serialVersionUID = 4684796418699804000L;
	
	/** Data set specification */
	
	protected IMetadata metadata;
	
	/** If numerical attributes exist, this variable is true*/
	
	protected boolean existNumericalAttributes = false;
	
	/** If categorical attributes exist, this variable is true*/
	
	protected boolean existCategoricalAttributes = false;

	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Empty constructor
	 */
	
	public ExprTreeSpecies() 
	{
		super();
	}
	
	/////////////////////////////////////////////////////////////////
	// ------------------------------- Getting and setting properties
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Get the metadata
	 *
	 * @return metadata
	 */
	
	public IMetadata getMetadata() 
	{
		return metadata;
	}

	/**
	 * Set the metadata
	 *
	 * @param metadata the metadata
	 */
	
	public void setMetadata(IMetadata metadata) 
	{
		this.metadata = metadata;
	}
	
	/**
	 * Returns if exists numerical attributes
	 * 
	 * @return whether exits existNumericalAttributes
	 */
	
	public boolean existNumericalAttributes()
	{
		return existNumericalAttributes;
	}
	
	/////////////////////////////////////////////////////////////////
	// ----------------------------------------------- Public methods
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Establishes the terminal symbols
	 * 
	 * @param inputAttributes list of attributes
	 * @return list of terminal symbols
	 */
	protected List<IPrimitive> setTerminalSymbols(List<IAttribute> inputAttributes)
	{
		List<IPrimitive> terminals = new ArrayList<IPrimitive>();

		// Number of input attribute
		int numAttributes = inputAttributes.size();
		
		//Set terminal symbol
		for(int i = 0; i < numAttributes; i++)
		{
			//Creates a new terminal symbol and adds it to the list
			IAttribute attribute = inputAttributes.get(i);
			IPrimitive term = new AttributeValue();
			
			((AttributeValue) term).setAttributeIndex(i);
			terminals.add(term);			
			
			//Checks the attribute type and assign the value type
			switch(attribute.getType())
			{
				case Numerical:
				{
					IPrimitive termValue = new RandomConstantOfContinuousValues();
					((RandomConstantOfContinuousValues) termValue).setInterval(((NumericalAttribute) attribute).intervalValues());
					terminals.add(termValue);
					existNumericalAttributes = true;
					break;
				}
				case Integer:
				{
					IPrimitive termValue = new RandomConstantOfDiscreteValues();
					((RandomConstantOfDiscreteValues) termValue).setInterval(((IntegerAttribute) attribute).intervalValues());
					terminals.add(termValue);
					existNumericalAttributes = true;
					break;
				}
				case Categorical:
				{
					IPrimitive termValue = new RandomConstantOfDiscreteValues();
					((RandomConstantOfDiscreteValues) termValue).setInterval(((CategoricalAttribute) attribute).intervalValues());
					terminals.add(termValue);
					existCategoricalAttributes = true;
					break;
				}
				default:
					System.out.println("Type is not supported");
			}
		}
		
		return terminals;
	}
	
	/**
	 * Set the terminal and function symbols of the grammar
	 */
	public void setSymbols()
	{
		// List of inputs Attributes
		List<IAttribute> inputAttributes = new ArrayList<IAttribute>();

		// Obtains the input attributes
		for(int i = 0; i < metadata.numberOfAttributes(); i++)
			if(i != metadata.getClassIndex())
				inputAttributes.add(metadata.getAttribute(i));

		// Allocate memory fot genotype Schema
		genotypeSchema = new ExprTreeSchema();
		
		List<IPrimitive> terminals = setTerminalSymbols(inputAttributes);
		List<IPrimitive> functions = setFunctionSymbols();
		
		setTerminalNodes(terminals);
		setNonTerminalNodes(functions);	
	}
	
	/**
	 * Set the terminal symbols
	 * 
	 * @param terminals list of terminal symbols
	 */
	
	protected void setTerminalNodes(List<IPrimitive> terminals)
	{
		IPrimitive [] terminalsAux = new IPrimitive[terminals.size()];
		int i = 0;
		
		for(IPrimitive t: terminals)
			terminalsAux[i++] = t.copy();
		
		genotypeSchema.setTerminals(terminalsAux);
	}
	
	/**
	 * Set the non-terminal symbols
	 *  
	 * @param nonTerminals list of non terminal symbols
	 */
	
	protected void setNonTerminalNodes(List<IPrimitive> nonTerminals)
	{
		IPrimitive [] nonTerminalsAux = new IPrimitive[nonTerminals.size()];
		int i = 0;
		
		for(IPrimitive t: nonTerminals)
			nonTerminalsAux[i++] = t.copy();
	
		genotypeSchema.setFunctions(nonTerminalsAux);
	}
	
	/**
	 * Establishes the function symbols
	 * 
	 * @return list of function symbols
	 */
	
	protected abstract List<IPrimitive> setFunctionSymbols();
	
	/**
	 * {@inheritDoc}
	 */
	
	public abstract ExprTreeRuleIndividual createIndividual(ExprTree genotype);
}