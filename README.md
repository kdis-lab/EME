# EME: Evolutionary Multi-label Ensemble

EME is an evolutionary approach for the automatic generation of ensembles of diverse and competitive multi-label classifiers. It takes into account characteristics of the multi-label data such as the relationships among the labels, imbalance of the data, and the complexity of the output space. The ensemble is based on small projections of the label space, considering in this way the relationships among the labels but also reducing the computational cost in cases where the output space is complex. Further, EME takes into account all the labels approximately the same number of times in the ensemble, regardless of their frequency or its ease to be predicted; so that the imbalance of the data is considered and the infrequent labels are not ignored. For that, the fitness function takes into account both the predictive performance of the model and the number of times that each label is considered in the ensemble.

More information about this algorithm can be find in the following article:
> Jose M. Moyano, Eva L. Gibaja, Krzysztof J. Cios, Sebastián Ventura. "An Evolutionary Approach to Build Ensembles of Multi-Label Classifiers". Submitted to Information Fusion. (2018).

If you use EME, please cite the paper. Further, a [bibtex citation file](https://github.com/i02momuj/EvolutionaryMultiLabelEnsemble/blob/master/citation.bib) is also provided.

In this repository we provide the code of EME, distributed under the GPLv3 License. EME has been implemented using JCLEC [[Ven08]](#Ven08), Mulan [[Tso11]](#Tso11), and Weka [[Hal09]](#Hal09)  libraries. Besides, the latest release [(v 1.2)](https://github.com/i02momuj/EvolutionaryMultiLabelEnsemble/releases/tag/v1.2) provides the executable jar to execute EME and also the javadoc.

To execute EME, the following command have to be executed:
```sh
java -jar EME.jar configFile.cfg
```

The configuration file is a xml file including the parameters of the evolutionary algorithm. In this case, there are some parameters that are mandatory, which are presented in the following example.

```xml
<experiment>
  <process algorithm-type="eme.EnsembleAlgorithm">
    <rand-gen-factory seed="10"/>
		 
    <parents-selector type="net.sf.jclec.selector.TournamentSelector">
      <tournament-size>2</tournament-size>
    </parents-selector>
		 
    <population-size>50</population-size>
    <max-of-generations>25</max-of-generations>		 
		 
    <recombinator type="eme.rec.UniformModelCrossover" rec-prob="0.8" />
    <mutator type="eme.mut.PhiBasedIntraModelMutator" mut-prob="0.2" />
		 
    <number-classifiers>12</number-classifiers>
    <number-labels-classifier>3</number-labels-classifier>
    <prediction-threshold>0.5</prediction-threshold>
    <use-coverage>true</use-coverage>
		 
    <dataset>
      <train-dataset>data/emotions_train1.arff</train-dataset>
      <test-dataset>data/emotions_test1.arff</test-dataset>
      <xml>data/emotions.xml</xml>
    </dataset>
		
    <listener type="eme.EnsembleListener">
      <report-dir-name>reports/EnsembleMLC</report-dir-name>
      <global-report-name>summaryEnsembleMLC</global-report-name>
      <report-frequency>10</report-frequency>	
    </listener>
  </process>
</experiment>
```

* The configuration file must start with the ```<experiment>``` tag and then the ```<process>``` tag, the last indicating the class with the evolutionary algorithm, in our case ```eme.EnsembleAlgorithm```.
* The ```<rand-gen-factory>``` must determine the seed for random numbers with the ```seed``` attribute. Further, it may indicate the type of the rand-gen-factory, which by default is ```net.sf.jclec.util.random.RanecuFactory```. If several seeds are going to be used, the tag ```<rand-gen-factory multi="true">``` may be used, including inside the different seeds, as follows:
  ```xml
    <rand-gen-factory multi="true">
	  <rand-gen-factory seed="10"/>
	  <rand-gen-factory seed="20"/>
	  <rand-gen-factory seed="30"/>
	    ...
    </rand-gen-factory>
  ```
* The parents selector is determined with the ```<parents-selector>``` tag. If, for example, the tournament selector is selected, its size is determined with the sub-tag ```<tournament-size>```.
* The size of the population is determined with the ```<population-size>``` tag.
* The number of generations of the evolutionary algorithm is determined with the ```<max-of-generations>``` tag.
* The ```<recombinator>``` tag determines the type of recombinator or crossover operator. In EME, three crossover operators are implemented: ```ModelCrossover```, ```MultiModelCrossover```, and ```UniformModelCrossover```. Further, the probability to apply this operator to each individual is determined with the ```rec-prob``` attribute.
* The ```<mutator>``` tag determines the type of mutation operator. In EME, two crossover operators are implemented: the basic ```IntraModelMutator```, and ```PhiBasedIntraModelMutator```. Further, the probability to apply this operator to each individual is determined with the ```mut-prob``` attribute.
* The number of classifiers in each ensemble is determined by the ```<number-classifiers>``` tag.
* The number of labels of each classifier, or size of the *k*-labelset, is determined by the ```<number-labels-classifier>``` tag.
* The threshold used for the final prediction of the ensemble is determined with the ```<prediction-threshold>``` tag.
* The ```<use-coverage>``` tag determines if the coverage ratio measure is included in the fitness of the individuals. The coverage ratio takes into account the number of times that each label appears in the ensemble.
* With the ```<dataset>``` tag, the datasets used for training (for the evolutionary algorithm) and testing (for testing the final ensemble obtained by EME) are determined with the tags ```<train-dataset>``` and ```<test-dataset>``` respectively. The ```<xml>``` tag indicates the xml file of the dataset (Mulan format, [see more](http://www.uco.es/kdis/mllresources/)).  Several datasets, or several partitions of the same dataset may be used, including the tag ```<dataset multi="true">```, and the different datasets inside, as follows:
  ```xml
    <dataset multi="true">
      <dataset>
        <train-dataset>data/emotions_train1.arff</train-dataset>
        <test-dataset>data/emotions_test1.arff</test-dataset>
        <xml>data/emotions.xml</xml>
      </dataset>
      <dataset>
        <train-dataset>data/emotions_train2.arff</train-dataset>
        <test-dataset>data/emotions_test2.arff</test-dataset>
        <xml>data/emotions.xml</xml>
      </dataset>
      <dataset>
        <train-dataset>data/emotions_train3.arff</train-dataset>
        <test-dataset>data/emotions_test3.arff</test-dataset>
        <xml>data/emotions.xml</xml>
      </dataset>
        ...
    </dataset>
  ```
* The ```<listener>``` tag determines the class used as listener; it is the responsible of creating the different reports during and at the end of the evolutionary process. By default, the listener used is the one of the ```eme.EnsembleListener``` class. The ```<report-dir-name>``` tag determines the directory where the reports of the different executions are stored. The ```<global-report-name>``` tag indicates the filename of the global report file. Finally, the ```<report-frequency>``` tag indicates the frequency with which the reports for the iterations are created.

Then, several more characteristics of the evolutionary algorithm could be modified in the configuration file, but they are optional and default values for them are given if they are not included in this file:
* The ```<validation-set>``` tag  indicates if the training set is divided into training and validation, in order to evaluate the individuals with a different dataset to which was used to train them. By default, its value is ```false```.
* The ```<evaluator>``` tag determines the class of the evaluator used for evaluating the individuals. Since only one evaluator has been implemented in EME, its default value is ```eme.EnsembleMLCEvaluator```.
* The ```<provider>``` tag determines the class that generates the initial population of individuals. By default, the ```eme.EnsembleMLCCreator``` class is used.

Two multi-label datasets (*emotions* and *yeast*) have been included in the repository as example; however, a wide variety of dataset are available at the [KDIS Research Group Repository](http://www.uco.es/kdis/mllresources/). Further, two example configuration files (*Experiment_emotions.cfg* and *Experiment_yeast.cfg*) are also provided.

### References

<a name="Hal09"></a>**[Hal09]** M. Hall, E. Frank, G. Holmes, B. Pfahringer, P. Reutemann, and I. H. Witten. (2009). The WEKA data mining software: an update. ACM SIGKDD explorations newsletter, 11(1), 10-18.

<a name="Tso11"></a>**[Tso11]** G. Tsoumakas, E. Spyromitros-Xioufis, J. Vilcek, and I. Vlahavas. (2011). Mulan: A java library for multi-label learning. Journal of Machine Learning Research, 12, 2411-2414.

<a name="Ven08"></a>**[Ven08]** S. Ventura, C. Romero, A. Zafra, J. A. Delgado, and C. Hervás. (2008). JCLEC: a Java framework for evolutionary computation. Soft Computing, 12(4), 381-392.