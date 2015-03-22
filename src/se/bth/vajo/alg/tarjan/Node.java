package se.bth.vajo.alg.tarjan;

import java.util.ArrayList;
import java.util.List;

import se.bth.vajo.alg.tarjan.CONSTANTS.NODE_COLOR;


public class Node {
	public static int NDESCENDANTS = -1;
	public final String name;
	public Node ancestor;
	public List<Node> children;
	public NODE_COLOR color;
	public final int born;
	public final int generation;
	
	public Node (String nameArg, Node ancestorArg, int bornArg, int generationArg) {
		NDESCENDANTS++;
		ancestor = ancestorArg;
		name = nameArg;
		color = NODE_COLOR.WHITE;
		children = new ArrayList<Node>();
		born = bornArg;
		generation = generationArg;
	}
	
	public void addChild(String nameArg, Node ancestorArg, int bornArg) {
		
		this.children.add(new Node(nameArg, ancestorArg, bornArg, this.generation + 1));
	}
	
	public String toString() {
		return generation + ". " + name + ", born " + born + "."; 
	}
}
