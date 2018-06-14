# EME: Evolutionary Multi-label Ensemble

EME is an evolutionary approach for the automatic generation of ensembles of diverse and competitive multi-label classifiers. It takes into account characteristics of the multi-label data such as the relationships among the labels, imbalance of the data, and the complexity of the output space. The ensemble is based on projections of the label space, considering in this way the relationships among the labels but also reducing the computational cost in cases where the output space is complex. Further, EME takes into account all the labels approximately the same number of times in the ensemble, regardless of their frequency or its ease to be predicted; so that the imbalance of the data is considered and the infrequent labels are not ignored. For that, the fitness function takes into account both the predictive performance of the model and the number of times that each label is considered in the ensemble. Finally, the diversity of the ensemble is not taken into account explicitly, but the ensembles evolve selecting their classifiers based on their overall performance.

More information about this algorithm can be find in the following article. 
> Jose M. Moyano, Eva L. Gibaja, Krzysztof J. Cios, Sebastián Ventura. "An Evolutionary Approach to Build Ensembles of Multi-Label Classifiers". Submitted to Information Fusion. (2018).

If you use EME, please cite this article. Further, a bibtex cite [] is provided at the end of the description.

In this repository we provide the code of EME, distributed under the GPLv3 License. EME has been implemented using JCLEC[], Mulan[], and Weka[] libraries. Besides, the last release (v 1.2) [] provides the executable jar to execute EME and also the javadoc.

To execute EME, only have to execute the following command:
```sh
java -jar EME.jar configFile.cfg
```

The configuration file works as shown in the following with an example file. Further, in the repository two configuration files with their respective data are provided.
```xml
<experiment>
  <process algorithm-type="eme.EnsembleAlgorithm">
    <rand-gen-factory type="net.sf.jclec.util.random.RanecuFactory" seed="10"/>
	<species type="net.sf.jclec.binarray.BinArrayIndividualSpecies" genotype-length="1"/>
	<provider type="eme.EnsembleMLCCreator"/>
	<evaluator type="eme.EnsembleMLCEvaluator"/>
	<population-size>50</population-size>
    <max-of-generations>25</max-of-generations>
	<parents-selector type="net.sf.jclec.selector.TournamentSelector">
	  <tournament-size>2</tournament-size>
	</parents-selector>
	<recombinator type="eme.rec.UniformModelCrossover" rec-prob="0.8" />
	<mutator type="eme.mut.PhiBasedIntraModelMutator" mut-prob="0.2" />
	<number-classifiers>12</number-classifiers>
	<number-labels-classifier>3</number-labels-classifier>
	<prediction-threshold>0.5</prediction-threshold>
	<variable>false</variable>
	<validation-set>false</validation-set>
	<use-coverage>true</use-coverage>
	<dataset>
	  <train-dataset>data/emotions/emotions_train_1.arff</train-dataset>
	  <test-dataset>data/emotions/emotions_test_1.arff</test-dataset>
    <xml>data/emotions/emotions.xml</xml>
    </dataset>
	<listener type="eme.EnsembleListener">
	  <report-dir-name>reports/EnsembleMLC</report-dir-name>
	  <global-report-name>summaryEnsembleMLC</global-report-name>
	  <report-frequency>1</report-frequency>	
    </listener>
    </process>
</experiment>
```

