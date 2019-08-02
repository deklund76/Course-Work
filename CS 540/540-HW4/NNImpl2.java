/**
 * The main class that handles the entire network
 * Has multiple attributes each with its own use
 * 
 */

import java.util.*;


public class NNImpl2{
	public ArrayList<Node> inputNodes=null;//list of the output layer nodes.
	public ArrayList<Node> hiddenNodes=null;//list of the hidden layer nodes
	public ArrayList<Node> outputNodes=null;// list of the output layer nodes
	
	public ArrayList<Instance> trainingSet=null;//the training set
	
	Double learningRate=1.0; // variable to store the learning rate
	int maxEpoch=1; // variable to store the maximum number of epochs
	
	/**
 	* This constructor creates the nodes necessary for the neural network
 	* Also connects the nodes of different layers
 	* After calling the constructor the last node of both inputNodes and  
 	* hiddenNodes will be bias nodes. 
 	*/
	
	public NNImpl2(ArrayList<Instance> trainingSet, int hiddenNodeCount, Double learningRate, int maxEpoch, Double [][]hiddenWeights, Double[][] outputWeights)
	{
		this.trainingSet=trainingSet;
		this.learningRate=learningRate;
		this.maxEpoch=maxEpoch;
		
		//input layer nodes
		inputNodes=new ArrayList<Node>();
		int inputNodeCount=trainingSet.get(0).attributes.size();
		int outputNodeCount=trainingSet.get(0).classValues.size();
		for(int i=0;i<inputNodeCount;i++)
		{
			Node node=new Node(0);
			inputNodes.add(node);
		}
		
		//bias node from input layer to hidden
		Node biasToHidden=new Node(1);
		inputNodes.add(biasToHidden);
		
		//hidden layer nodes
		hiddenNodes=new ArrayList<Node> ();
		for(int i=0;i<hiddenNodeCount;i++)
		{
			Node node=new Node(2);
			//Connecting hidden layer nodes with input layer nodes
			for(int j=0;j<inputNodes.size();j++)
			{
				NodeWeightPair nwp=new NodeWeightPair(inputNodes.get(j),hiddenWeights[i][j]);
				node.parents.add(nwp);
			}
			hiddenNodes.add(node);
		}
		
		//bias node from hidden layer to output
		Node biasToOutput=new Node(3);
		hiddenNodes.add(biasToOutput);
			
		//Output node layer
		outputNodes=new ArrayList<Node> ();
		for(int i=0;i<outputNodeCount;i++)
		{
			Node node=new Node(4);
			//Connecting output layer nodes with hidden layer nodes
			for(int j=0;j<hiddenNodes.size();j++)
			{
				NodeWeightPair nwp=new NodeWeightPair(hiddenNodes.get(j), outputWeights[i][j]);
				node.parents.add(nwp);
			}	
			outputNodes.add(node);
		}	
	}
	
	/**
	 * Get the output from the neural network for a single instance
	 * Return the idx with highest output values. For example if the outputs
	 * of the outputNodes are [0.1, 0.5, 0.2], it should return 1. If outputs
	 * of the outputNodes are [0.1, 0.5, 0.5], it should return 2. 
	 * The parameter is a single instance. 
	 */

	public int calculateOutputForInstance(Instance inst)
	{
		//Set the input nodes to the instance's inputs
		for(int k = 0; k < inputNodes.size() - 1; k++) {
			inputNodes.get(k).setInput(inst.attributes.get(k));
		}
		
		//Have the hidden nodes calculate their outputs
		for(int k = 0; k < hiddenNodes.size() - 1; k++) {
			hiddenNodes.get(k).calculateOutput();
		}
		
		//Have the output nodes calculate their outputs and find the one with
		//the highest output
		int highestIndex = -1;
		double highestValue = Integer.MIN_VALUE;
		for(int k = 0; k < outputNodes.size(); k++) {
			outputNodes.get(k).calculateOutput();
			
			//Update if new best index
			if(outputNodes.get(k).getOutput() >= highestValue) {
				highestValue = outputNodes.get(k).getOutput();
				highestIndex = k;
			}
		}
		
		//Return the output for the instance
		return highestIndex;
	}
	
	private double gPrime(double d) {
		if(d > 0) return 1;
		else return 0;
	}
	
	/**
	 * Train the neural networks with the given parameters
	 * 
	 * The parameters are stored as attributes of this class
	 */
	
	public void train()
	{
		//For (maxEpoch) number of times...
		for(int i = 0; i < maxEpoch; i++) {
			
			//For every example in the training set...
			for(int j = 0; j < trainingSet.size(); j++) {
				Instance example = trainingSet.get(j);
				
				//Run the example through the network
				calculateOutputForInstance(example);
				
				//Get the weight changes between the hidden nodes and the
				//output nodes
				double[][] deltaHiddenToOutput = new double[outputNodes.size()][hiddenNodes.size()];
				for(int k = 0; k < outputNodes.size(); k++) {
					for(int l = 0; l < hiddenNodes.size(); l++) {
						double error = example.classValues.get(k) - outputNodes.get(k).getOutput();
						deltaHiddenToOutput[k][l] = gPrime(outputNodes.get(k).getSum()) * error;
					}
				}
				
				//Get the weight changes between the hidden nodes and the
				//input nodes
				double[][] deltaInputToHidden = new double[hiddenNodes.size()][inputNodes.size()];
				for(int k = 0; k < hiddenNodes.size(); k++) {
					for(int l = 0; l < inputNodes.size(); l++) {
						double summation = 0.0;
						for(int m = 0; m < outputNodes.size(); m++) {
							summation += outputNodes.get(m).parents.get(k).weight * deltaHiddenToOutput[m][k];
						}
						deltaInputToHidden[k][l] = gPrime(hiddenNodes.get(k).getSum()) * summation;
					}
				}
				
				//Update all the weights between the hidden nodes and the 
				//output nodes
				for(int k = 0; k < outputNodes.size(); k++) {
					for(int l = 0; l < hiddenNodes.size(); l++) {
						outputNodes.get(k).parents.get(l).weight +=
								learningRate * hiddenNodes.get(l).getOutput() *
								deltaHiddenToOutput[k][l];
					}
				}
				
				//Update all the weights between the input nodes and the
				//hidden nodes
				for(int k = 0; k < hiddenNodes.size() - 1; k++) {
					for(int l = 0; l < inputNodes.size(); l++) {
						hiddenNodes.get(k).parents.get(l).weight += 
								learningRate * inputNodes.get(l).getOutput() *
								deltaInputToHidden[k][l];
					}
				}
			}
		}
	}
}
