Program that builds a decision tree for categorical attributes and 2-class classification tasks.

In the DecisionTreeImpl(DataSet train) method, the decision tree is built using the training data provided. To finish this part, there is a recursive function corresponding to the DecisionTreeLearning function.

public String classify(Instance instance) takes an example, or an instance, as its input and computes the classification output (as a string) of the previously-built decision tree.

For the constructor DecisionTreeImpl(DataSet train, DataSet tune), after the treeis built, it is pruned using the tuning dataset provided and an iterative Pruning Algorithm.
