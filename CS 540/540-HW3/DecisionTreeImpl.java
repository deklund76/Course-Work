import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Fill in the implementation details of the class DecisionTree using this file.
 * Any methods or secondary classes that you want are fine but we will only
 * interact with those methods in the DecisionTree framework.
 * 
 * You must add code for the 1 member and 4 methods specified below.
 * 
 * See DecisionTree for a description of default methods.
 */
public class DecisionTreeImpl extends DecisionTree {
	private DecTreeNode root;
	// ordered list of class labels
	private List<String> labels;
	// ordered list of attributes
	private List<String> attributes;
	// map to ordered discrete values taken by attributes
	private Map<String, List<String>> attributeValues;

	/**
	 * Answers static questions about decision trees.
	 */
	DecisionTreeImpl() {
		// no code necessary this is void purposefully
	}

	/**
	 * Build a decision tree given only a training set.
	 * 
	 * @param train
	 *            : the training set
	 */
	DecisionTreeImpl(DataSet train) {

		this.labels = train.labels;
		this.attributes = train.attributes;
		this.attributeValues = train.attributeValues;
		root = buildTree(new ArrayList<Instance>(train.instances),
				new ArrayList<String>(train.attributes), null, null);
	}

	@Override
	public String classify(Instance instance) {
		DecTreeNode current = root;
		while (!current.terminal) {
			current = current.children.get(getAttributeValueIndex(
					current.attribute, instance.attributes
							.get(getAttributeIndex(current.attribute))));
		}
		return current.label;
	}

	@Override
	public void rootInfoGain(DataSet train) {
		this.labels = train.labels;
		this.attributes = train.attributes;
		this.attributeValues = train.attributeValues;

		for (int i = 0; i < train.attributes.size(); i++) {
			double infoGain = infoGain(attributes.get(i), train.instances);
			System.out.print(train.attributes.get(i) + " ");
			System.out.format("%.5f\n", infoGain);
		}
	}

	@Override
	public void printAccuracy(DataSet test) {
		double successes = 0;
		for (int i = 0; i < test.instances.size(); i++) {
			if (test.instances.get(i).label.equals(classify(test.instances
					.get(i)))) {
				successes++;
			}
		}
		double accuracy = successes / test.instances.size();
		System.out.format("%.5f\n", accuracy);
	}

	/**
	 * Build a decision tree given a training set then prune it using a tuning
	 * set. ONLY for extra credits
	 * 
	 * @param train
	 *            : the training set
	 * @param tune
	 *            : the tuning set
	 */
	DecisionTreeImpl(DataSet train, DataSet tune) {

		this.labels = train.labels;
		this.attributes = train.attributes;
		this.attributeValues = train.attributeValues;
		root = buildTree(new ArrayList<Instance>(train.instances),
				new ArrayList<String>(train.attributes), null, null);

		double successes = 0;
		for (int i = 0; i < tune.instances.size(); i++) {
			if (tune.instances.get(i).label.equals(classify(tune.instances
					.get(i)))) {
				successes++;
			}
		}
		double accuracy = successes / tune.instances.size();

		System.out.println("Accuracy on tune before pruning: " + accuracy);

		Queue<DecTreeNode> queue = new LinkedList<DecTreeNode>();

		if (root == null) {
			return;
		}

		queue.add(root);
		while (!queue.isEmpty()) {
			DecTreeNode current = queue.remove();
			
			if (!current.terminal) {
				List<DecTreeNode> removed = new ArrayList<DecTreeNode>();
				for(int i = 0; i < current.children.size(); i++) {
					removed.add(current.children.remove(i));
				}
				current.terminal = true;
				
				successes = 0;
				for (int j = 0; j < tune.instances.size(); j++) {
					if (tune.instances.get(j).label
							.equals(classify(tune.instances.get(j)))) {
						successes++;
					}
				}
				double accuracy2 = successes / tune.instances.size();

				if (accuracy > accuracy2) {
					current.children.addAll(removed);
					current.terminal = false;
				} else {
					accuracy = accuracy2;
				}
			}

			if (current.children != null) {
				queue.addAll(current.children);
			}
		}
		System.out.println("Accuracy on tune after pruning: " + accuracy);
	}

	@Override
	/**
	 * Print the decision tree in the specified format
	 */
	public void print() {

		printTreeNode(root, null, 0);
	}

	/**
	 * Prints the subtree of the node with each line prefixed by 4 * k spaces.
	 */
	public void printTreeNode(DecTreeNode p, DecTreeNode parent, int k) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < k; i++) {
			sb.append("    ");
		}
		String value;
		if (parent == null) {
			value = "ROOT";
		} else {
			int attributeValueIndex = this.getAttributeValueIndex(
					parent.attribute, p.parentAttributeValue);
			value = attributeValues.get(parent.attribute).get(
					attributeValueIndex);
		}
		sb.append(value);
		if (p.terminal) {
			sb.append(" (" + p.label + ")");
			System.out.println(sb.toString());
		} else {
			sb.append(" {" + p.attribute + "?}");
			System.out.println(sb.toString());
			for (DecTreeNode child : p.children) {
				printTreeNode(child, p, k + 1);
			}
		}
	}

	private DecTreeNode buildTree(List<Instance> instances,
			List<String> curr_attributes, String defaultLabel, String parentAttr) {
		if (instances.isEmpty()) {
			return new DecTreeNode(defaultLabel, null, parentAttr, true);
		}

		boolean allSameLabel = true;
		String label = instances.get(0).label;
		for (int i = 0; i < instances.size() && allSameLabel; i++) {
			if (!instances.get(i).label.equals(label)) {
				allSameLabel = false;
			}
		}
		if (allSameLabel) {
			return new DecTreeNode(label, null, parentAttr, true);
		}

		int classCount = 0;
		String class1 = labels.get(0);
		String class2 = labels.get(1);
		String majorityVote = class1;

		for (int i = 0; i < instances.size(); i++) {
			if (instances.get(i).label.equals(class2))
				classCount++;
		}
		if (classCount > instances.size() / 2) {
			majorityVote = class2;
		}

		if (curr_attributes.isEmpty()) {
			return new DecTreeNode(majorityVote, null, parentAttr, true);
		}

		String bestAttr = curr_attributes.get(0);
		double maxGain = 0;

		for (int i = 0; i < curr_attributes.size(); i++) {
			double infoGain = infoGain(curr_attributes.get(i), instances);
			if (infoGain > maxGain) {
				maxGain = infoGain;
				bestAttr = curr_attributes.get(i);
			}
		}

		DecTreeNode tree = new DecTreeNode(majorityVote, bestAttr, parentAttr,
				false);
		curr_attributes.remove(bestAttr);

		for (int i = 0; i < attributeValues.get(bestAttr).size(); i++) {
			String v = attributeValues.get(bestAttr).get(i);
			List<Instance> instances2 = new ArrayList<Instance>(instances);
			List<String> attributes2 = new ArrayList<String>(curr_attributes);
			for (int j = 0; j < instances2.size(); j++) {
				if (!instances2.get(j).attributes.get(
						getAttributeIndex(bestAttr)).equals(v)) {
					instances2.remove(j);
					j--;
				}
			}
			DecTreeNode subTree = buildTree(instances2, attributes2,
					majorityVote, v);
			tree.addChild(subTree);
		}
		return tree;
	}

	/**
	 * Helper Method that returns the information gain for a given attribute.
	 * 
	 * @param attribute
	 * @param set
	 * @return information gain (double)
	 */
	private double infoGain(String attribute, List<Instance> instances) {
		double classEntropy = 0;
		for (int i = 0; i < labels.size(); i++) {
			double labelCount = 0;
			for (int j = 0; j < instances.size(); j++) {
				if (instances.get(j).label.equals(labels.get(i))) {
					labelCount++;
				}
			}
			classEntropy += -labelCount / instances.size()
					* Math.log(labelCount / instances.size()) / Math.log(2);
		}

		double conEntropy = 0;

		for (int i = 0; i < attributeValues.get(attribute).size(); i++) {
			int valCount = 0;
			int classCount = 0;
			for (int j = 0; j < instances.size(); j++) {
				if (instances.get(j).attributes.get(
						getAttributeIndex(attribute)).equals(
						attributeValues.get(attribute).get(i))) {
					valCount++;
					if (instances.get(j).label.equals(labels.get(0))) {
						classCount++;
					}
				}
			}
			double prob = (double) valCount / (double) instances.size();
			double conProb = (double) classCount / (double) valCount;

			if (conProb != 0 && conProb != 1 && valCount != 0) {
				conEntropy += prob
						* (-conProb * Math.log(conProb) / Math.log(2) - (1 - conProb)
								* Math.log(1 - conProb) / Math.log(2));
			}
		}
		return classEntropy - conEntropy;
	}

	/**
	 * Helper function to get the index of the label in labels list
	 */
	private int getLabelIndex(String label) {
		for (int i = 0; i < this.labels.size(); i++) {
			if (label.equals(this.labels.get(i))) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Helper function to get the index of the attribute in attributes list
	 */
	private int getAttributeIndex(String attr) {
		for (int i = 0; i < this.attributes.size(); i++) {
			if (attr.equals(this.attributes.get(i))) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Helper function to get the index of the attributeValue in the list for
	 * the attribute key in the attributeValues map
	 */
	private int getAttributeValueIndex(String attr, String value) {
		for (int i = 0; i < attributeValues.get(attr).size(); i++) {
			if (value.equals(attributeValues.get(attr).get(i))) {
				return i;
			}
		}
		return -1;
	}
}
