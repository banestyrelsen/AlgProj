package se.bth.vajo.alg.tarjan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Tree {
	Random rnd;
	ArrayList<String> names;
	Node root;

	public void buildTree(List<String> namesArg) {
		names = (ArrayList<String>) namesArg;
		rnd = new Random();
		root = new Node("EVE", null, 1000, 1);
		generateDescendants(root);
	}

	public Node getRoot() {
		return root;
	}

	
	private void generateDescendants(Node ancestor) {
		// Add children
		for (int i = 0; i < rnd.nextInt(CONSTANTS.MAX_CHILDRENPERNODE); i++) {
			generateDescendant(ancestor);
		}		
		// Recursively add grandchildren
		for (Node n : ancestor.children) {
			generateDescendants(n);
		}
	}

	/**
	 * Adds one descendant to a parent node and recurses. 
	 * @param ancestor
	 */
	private void generateDescendant(Node ancestor) {
		if (ancestor.generation < CONSTANTS.MAX_TREE_DEPTH
				&& Node.NDESCENDANTS <= CONSTANTS.MAX_NODES) {
			ancestor.addChild(generateName(), ancestor,
					ancestor.born + rnd.nextInt(20) + 18);
		}
	}

	private String generateName() {
		return names.get(rnd.nextInt(names.size() - 1));
	}

	public void printTree(Node current, int indentLevel) {
		System.out.println(indent(indentLevel) + current.toString());
		for (Node n : current.children) {
			printTree(n, indentLevel + 1);
		}
		
	}

	private String indent(int indent) {

		String ret = "";

		if (indent > 0) {
			for (int i = 0; i < indent; i++) {
				ret += "\t";
			}
		}

		return ret;
	}
}
