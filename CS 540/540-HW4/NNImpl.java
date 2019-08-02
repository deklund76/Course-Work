/**
 * The main class that handles the entire network
 * Has multiple attributes each with its own use
 * 
 */

import java.util.*;

public class NNImpl {
	public ArrayList<Node> inputNodes = null;// list of the output layer nodes.
	public ArrayList<Node> hiddenNodes = null;// list of the hidden layer nodes
	public ArrayList<Node> outputNodes = null;// list of the output layer nodes

	public ArrayList<Instance> trainingSet = null;// the training set

	Double learningRate = 1.0; // variable to store the learning rate
	int maxEpoch = 1; // variable to store the maximum number of epochs

	/**
	 * This constructor creates the nodes necessary for the neural network Also
	 * connects the nodes of different layers After calling the constructor the
	 * last node of both inputNodes and hiddenNodes will be bias nodes.
	 */

	public NNImpl(ArrayList<Instance> trainingSet, int hiddenNodeCount,
			Double learningRate, int maxEpoch, Double[][] hiddenWeights,
			Double[][] outputWeights) {
		this.trainingSet = trainingSet;
		this.learningRate = learningRate;
		this.maxEpoch = maxEpoch;

		// input layer nodes
		inputNodes = new ArrayList<Node>();
		int inputNodeCount = trainingSet.get(0).attributes.size();
		int outputNodeCount = trainingSet.get(0).classValues.size();
		for (int i = 0; i < inputNodeCount; i++) {
			Node node = new Node(0);
			inputNodes.add(node);
		}

		// bias node from input layer to hidden
		Node biasToHidden = new Node(1);
		inputNodes.add(biasToHidden);

		// hidden layer nodes
		hiddenNodes = new ArrayList<Node>();
		for (int i = 0; i < hiddenNodeCount; i++) {
			Node node = new Node(2);
			// Connecting hidden layer nodes with input layer nodes
			for (int j = 0; j < inputNodes.size(); j++) {
				NodeWeightPair nwp = new NodeWeightPair(inputNodes.get(j),
						hiddenWeights[i][j]);
				node.parents.add(nwp);
			}
			hiddenNodes.add(node);
		}

		// bias node from hidden layer to output
		Node biasToOutput = new Node(3);
		hiddenNodes.add(biasToOutput);

		// Output node layer
		outputNodes = new ArrayList<Node>();
		for (int i = 0; i < outputNodeCount; i++) {
			Node node = new Node(4);
			// Connecting output layer nodes with hidden layer nodes
			for (int j = 0; j < hiddenNodes.size(); j++) {
				NodeWeightPair nwp = new NodeWeightPair(hiddenNodes.get(j),
						outputWeights[i][j]);
				node.parents.add(nwp);
			}
			outputNodes.add(node);
		}
	}

	/**
	 * Get the output from the neural network for a single instance Return the
	 * idx with highest output values. For example if the outputs of the
	 * outputNodes are [0.1, 0.5, 0.2], it should return 1. If outputs of the
	 * outputNodes are [0.1, 0.5, 0.5], it should return 2. The parameter is a
	 * single instance.
	 */

	public int calculateOutputForInstance(Instance inst) {
		for (int i = 0; i < inputNodes.size() - 1; i++) {
			inputNodes.get(i).setInput(inst.attributes.get(i));
		}
		for (int i = 0; i < hiddenNodes.size() - 1; i++) {
			hiddenNodes.get(i).calculateOutput();
		}
		double maxOut = -1;
		int maxIndex = -1;
		for (int i = 0; i < outputNodes.size(); i++) {
			outputNodes.get(i).calculateOutput();

			if (outputNodes.get(i).getOutput() >= maxOut) {
				maxIndex = i;
				maxOut = outputNodes.get(i).getOutput();
			}
		}
		return maxIndex;
	}

	/**
	 * Train the neural networks with the given parameters
	 * 
	 * The parameters are stored as attributes of this class
	 */

	public void train() {
		for (int i = 0; i < maxEpoch; i++) {

			for (int j = 0; j < trainingSet.size(); j++) {
				Instance currExample = trainingSet.get(j);

				calculateOutputForInstance(currExample);

				double[][] deltaJToK = new double[outputNodes.size()][hiddenNodes
						.size()];
				for (int k = 0; k < outputNodes.size(); k++) {
					for (int l = 0; l < hiddenNodes.size(); l++) {
						double error = currExample.classValues.get(k)
								- outputNodes.get(k).getOutput();
						if (outputNodes.get(k).getSum() > 0)
							deltaJToK[k][l] = error;
						else
							deltaJToK[k][l] = 0;
					}
				}

				double[][] deltaIToJ = new double[hiddenNodes.size()][inputNodes
						.size()];
				for (int k = 0; k < hiddenNodes.size(); k++) {
					for (int l = 0; l < inputNodes.size(); l++) {
						double totalDelt = 0.0;
						for (int m = 0; m < outputNodes.size(); m++) {
							totalDelt += outputNodes.get(m).parents.get(k).weight
									* deltaJToK[m][k];
						}
						if (hiddenNodes.get(k).getSum() > 0)
							deltaIToJ[k][l] = totalDelt;
						else
							deltaIToJ[k][l] = 0;
					}
				}

				for (int k = 0; k < outputNodes.size(); k++) {
					for (int l = 0; l < hiddenNodes.size(); l++) {
						outputNodes.get(k).parents.get(l).weight += learningRate
								* hiddenNodes.get(l).getOutput()
								* deltaJToK[k][l];
					}
				}

				for (int k = 0; k < hiddenNodes.size() - 1; k++) {
					for (int l = 0; l < inputNodes.size(); l++) {
						hiddenNodes.get(k).parents.get(l).weight += learningRate
								* inputNodes.get(l).getOutput()
								* deltaIToJ[k][l];
					}
				}
			}
		}
	}
}
