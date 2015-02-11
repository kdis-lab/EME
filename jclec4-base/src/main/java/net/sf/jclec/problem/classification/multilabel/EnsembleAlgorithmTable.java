package net.sf.jclec.problem.classification.multilabel;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;

import net.sf.jclec.IIndividual;
import net.sf.jclec.binarray.BinArrayIndividual;
import net.sf.jclec.problem.classification.multilabel.mut.PhiBasedIntraModelMutator;

import org.apache.commons.configuration.Configuration;

import weka.classifiers.trees.J48;
import mulan.classifier.MultiLabelLearner;
import mulan.classifier.transformation.LabelPowerset;
import mulan.data.Statistics;


public class EnsembleAlgorithmTable extends EnsembleAlgorithm {

	
	/////////////////////////////////////////////////////////////////
	// --------------------------------------- Serialization constant
	/////////////////////////////////////////////////////////////////
	private static final long serialVersionUID = -8961333992800576707L;
	
	
	/////////////////////////////////////////////////////////////////
	// --------------------------------------------------- Properties
	/////////////////////////////////////////////////////////////////
	
	private EnsembleClassifierTable classifier;
	
	private Hashtable<String, MultiLabelLearner> tableClassifiers;
	
	private Hashtable<String, Double> tableFitness;
	
	private boolean controlPopulationDiversity = false;
	
	
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////
	public EnsembleAlgorithmTable() 
	{
		super();
		tableClassifiers = new Hashtable<String, MultiLabelLearner> ();
		tableFitness = new Hashtable<String, Double> ();
	}
	
	
	/////////////////////////////////////////////////////////////////
	// ----------------------------------------------- Public methods
	/////////////////////////////////////////////////////////////////
	
	public EnsembleClassifierTable getClassifier()
	{
		return classifier;
	}
	
	public boolean getControlPopulationDiversity()
	{
		return controlPopulationDiversity;
	}
	
	
	public void configure(Configuration configuration)
	{
		super.configure(configuration);
		
		try {
			((EnsembleMLCEvaluatorTable) evaluator).setTable(tableClassifiers);
			((EnsembleMLCEvaluatorTable) evaluator).setTableMeasures(tableFitness);
			
			controlPopulationDiversity = configuration.getBoolean("controlPopulationDiversity");
			System.out.println("controlPopulationDiversity: " + controlPopulationDiversity);
			boolean fitnessWithIndividualDiversity = configuration.getBoolean("fitnessWithIndividualDiversity");
			System.out.println("fitnessWithIndividualDiversity: " + fitnessWithIndividualDiversity);
			((EnsembleMLCEvaluatorTable) evaluator).setFitnessWithIndividualDiversity(fitnessWithIndividualDiversity);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/////////////////////////////////////////////////////////////////
	// -------------------------------------------- Protected methods
	/////////////////////////////////////////////////////////////////
	
	protected void doInit()
	{
		Statistics s = new Statistics();

		double [][] phi = new double[getDatasetTrain().getNumLabels()][getDatasetTrain().getNumLabels()];
		
		try {
			phi = s.calculatePhi(getDatasetTrain());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		s.printPhiCorrelations();		
		
		((EnsembleMLCEvaluatorTable) evaluator).setPhiMatrix(phi);
		
		// Send Phi matrix to the mutator if it needs it
		if(mutator.getDecorated().getClass().toString().contains("PhiBasedIntraModelMutator"))
		{
			((PhiBasedIntraModelMutator) mutator.getDecorated()).setPhiMatrix(phi);
		}		
		
		super.doInit();
	}
	
	
	@Override
	protected void doControl()
	{				
		System.out.println("--- Generation " + generation + " ---");
		System.out.println("----------------------");
		
				
		if(controlPopulationDiversity)
		{
			/* Control population diversity */	
			int maxCombinationsBaseClassifiers = 1;
			
			for(int i=0; i<getNumberLabelsClassifier(); i++)
			{
				maxCombinationsBaseClassifiers = maxCombinationsBaseClassifiers * (getDatasetTrain().getNumLabels() - i);
			}
			maxCombinationsBaseClassifiers = maxCombinationsBaseClassifiers / (factorial(getNumberLabelsClassifier()));
			
			int maxNumOfBaseClassifiers = getPopulationSize() * getNumberClassifiers();
			
			
			int min = 0;
			if(maxCombinationsBaseClassifiers < maxNumOfBaseClassifiers)
				min = maxCombinationsBaseClassifiers;
			else
				min = maxNumOfBaseClassifiers;
			
			int numDistinctBaseClassifiers = getNumberOfDistinctBaseClassifiers(bset);
			
			double populationDiversity = (double)numDistinctBaseClassifiers / min;
			
			System.out.println("populationDiversity: " + populationDiversity);
			
			
			if((populationDiversity < 0.5) && (getMutationProb() < 0.4))
			{
				System.out.println("Incrementar probabilidad de mutación");
				setMutationProb(getMutationProb() + 0.05);
			}
			else if((populationDiversity > 0.85) && (getMutationProb() >= 0.2))
			{
				System.out.println("Decrementar probabilidad de mutación");
				setMutationProb(getMutationProb() - 0.05);
			}
		}
		
		
		// If maximum number of generations is exceeded, evolution is finished
		if (generation >= maxOfGenerations)
		{
			byte[] genotype = ((BinArrayIndividual) bset.get(0)).getGenotype();

			classifier = new EnsembleClassifierTable(getNumberLabelsClassifier(), getNumberClassifiers(), getPredictionThreshold(), getVariable(), new LabelPowerset(new J48()), genotype, tableClassifiers);
			
			System.out.println("Final ensemble");
			for(int i=0; i<getNumberClassifiers(); i++)
			{
				for(int j=0; j<getDatasetTrain().getNumLabels(); j++)
				{
					System.out.print(genotype[i*getDatasetTrain().getNumLabels()+j] + " ");
				}
				System.out.println();
			}
			
			try {				
				classifier.build(getDatasetTrain());
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			state = FINISHED;
		}
	}
	
	

	public String getGenotypeFromIIndividual (IIndividual i)
	{		
		String s = i.toString();
		
		s = s.split("=")[1];
		
		s = s.split(",fitness")[0];
		
		s = s.replace("{","");
		s = s.replace("}","");
		
		s = s.replace(",", "");
		
		return s;
	}
	
	public int factorial (int n)
	{
		if(n == 1)
			return 1;
		else
			return n*factorial(n-1);
	}
	
	public int getNumberOfDistinctBaseClassifiers(List<IIndividual> bset)
	{
		int n = 0;
		
		HashSet<String> set = new HashSet<String>();
		
		for(int p=0; p<bset.size(); p++)
		{
			String p1 = getGenotypeFromIIndividual(bset.get(p));
			for(int i=0; i<p1.length()/getDatasetTrain().getNumLabels(); i++)
			{
				set.add(p1.substring(i*getDatasetTrain().getNumLabels(), i*getDatasetTrain().getNumLabels()+getDatasetTrain().getNumLabels()));
			}
			
		}
		
		n = set.size();
		
		return n;
	}
}
