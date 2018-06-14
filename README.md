# EME: Evolutionary Multi-label Ensemble

EME is an evolutionary approach for the automatic generation of ensembles of diverse and competitive multi-label classifiers. It takes into account characteristics of the multi-label data such as the relationships among the labels, imbalance of the data, and the complexity of the output space. The ensemble is based on projections of the label space, considering in this way the relationships among the labels but also reducing the computational cost in cases where the output space is complex. Further, EME takes into account all the labels approximately the same number of times in the ensemble, regardless of their frequency or its ease to be predicted; so that the imbalance of the data is considered and the infrequent labels are not ignored. For that, the fitness function takes into account both the predictive performance of the model and the number of times that each label is considered in the ensemble. Finally, the diversity of the ensemble is not taken into account explicitly, but the ensembles evolve selecting their classifiers based on their overall performance.

More information about this algorithm can be find in the following article:
> Jose M. Moyano, Eva L. Gibaja, Krzysztof J. Cios, Sebastián Ventura. "An Evolutionary Approach to Build Ensembles of Multi-Label Classifiers". Submitted to Information Fusion. (2018).

If you use EME, please cite the paper. Further, a bibtex cite [] is provided at the end of the description.

In this repository we provide the code of EME, distributed under the GPLv3 License. EME has been implemented using JCLEC[], Mulan[], and Weka[] libraries. Besides, the last release (v 1.2) [] provides the executable jar to execute EME and also the javadoc.

To execute EME, only have to execute the following command:
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

* The configuration file must start with the ```<experiment>``` tag and then the ```<process>``` tag, which indicates the class with the evolutionary algorithm, in our case ```eme.EnsembleAlgorithm```.
* The ```<rand-gen-factory>``` must determine the seed for random numbers with the ```seed``` attribute. Further, it may indicate the type of the rand-gen-factory, which by default is ```net.sf.jclec.util.random.RanecuFactory```. If several seeds are going to be used, the tag ```<rand-gen-factory multi="true">``` is used, including inside the different seeds, as follows:
  ```xml
    <rand-gen-factory multi="true">
	  <rand-gen-factory seed="10"/>
	  <rand-gen-factory seed="20"/>
	  <rand-gen-factory seed="30"/>
	    ...
    </rand-gen-factory>
  ```
* The parents selector is determined with the tag ```<parents-selector>```. If for example the tournament selector is selected, its size is determined with the sub-tag ```<tournament-size>```.
* The size of the population is determined with the ```<population-size>``` tag.
* The number of generations of the evolutionary algorithm is determined with the ```<max-of-generations>``` tag.
* The ```<recombinator>``` tag determines the type of recombinator or crossover operator. In EME, three crossover operators are implemented: ```ModelCrossover```, ```MultiModelCrossover```, and ```UniformModelCrossover```. Further, the probability to apply this operator to each individual is determined with the ```rec-prob``` attribute.
* The ```<mutator>``` tag determines the type of mutation operator. In EME, two crossover operators are implemented: the basic ```IntraModelMutator```, and ```PhiBasedIntraModelMutator```.. Further, the probability to apply this operator to each individual is determined with the ```mut-prob``` attribute.
* The number of classifiers in each ensemble is determined with the ```<number-classifiers>``` tag.
* The number of labels of each classifier, or size of the *k*-labelset, is determined with the ```<number-labels-classifier>``` tag.
* The threshold used for the final prediction of the ensemble is determined with the ```<prediction-threshold>``` tag.
* The tag ```<use-coverage>``` determines if the coverage ratio measure is included in the fitness of the individuals. The coverage ratio takes into account the number of times that each label appears in the ensemble.
* With the ```<dataset>``` tag, the datasets used for training (for the evolutionary algorithm) and testing (for testint the final obtained ensemble by EME) are determined with the tags ```<train-dataset>``` and ```<test-dataset>``` respectively. The ```<xml>``` tag indicates the xml file of the dataset.  Several datasets, or several partitions of the same dataset may be used, including the tag ```<dataset multi="true">```, including inside the different datasets, as follows:
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
* The ```<listener>``` tag determines the class used as listener; it is the responsible of creating the different reports during and at the end of the evolutionary process. The ```<report-dir-name>``` tag determines the directory where the reports of the different executions are stored. The ```<global-report-name>``` tag indicates the filename of the global report file. Finally, the ```<report-frequency>``` tag indicates the frequency with which the reports for the iterations are created.

Then, several more characteristics of the evolutionary algorithm could be modified in the configuration file, but they are optional and default values for them are given if they are not included in this file:
* The ```<validation-set>``` tag  indicates if the training set is divided into training and validation, in order to evaluate the individuals with a different dataset to which was used to train them. By default, its value is ```false```.
* The ```<evaluator>``` tag determines the class of the evaluator used for evaluating the individuals. Since only one evaluator has been implemented in EME, its default value is ```eme.EnsembleMLCEvaluator```.
* The ```<provider>``` tag determines the class that generates the initial population of individuals. By default, the ```eme.EnsembleMLCCreator``` class is used.

