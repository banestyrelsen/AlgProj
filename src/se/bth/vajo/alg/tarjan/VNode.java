package se.bth.vajo.alg.tarjan;

import java.util.ArrayList;
import java.util.List;

import se.bth.vajo.alg.tarjan.CONSTANTS.NODE_COLOR;


public class VNode {
	public static int NDESCENDANTS = -1;
	public final String name;
	public VNode ancestor;
	public List<VNode> children;
	public NODE_COLOR color;
	public final int born;
	public final int generation;
	
	// Set variables
	public int rank;
	public VNode setParent;
	
	public VNode (String nameArg, VNode ancestorArg, int bornArg, int generationArg) {
		NDESCENDANTS++;
		ancestor = ancestorArg;
		name = nameArg;
		color = NODE_COLOR.WHITE;
		children = new ArrayList<VNode>();
		born = bornArg;
		generation = generationArg;
		
		// Set variables;
		rank = -1;
		setParent = null;
	}
	
	public void addChild(String nameArg, VNode ancestorArg, int bornArg) {
		
		this.children.add(new VNode(nameArg, ancestorArg, bornArg, this.generation + 1));
	}
	
	public void addChild(VNode n) {		
		this.children.add(n);
	}
	
	public String toString() {
		return generation + ". " + name + ", born " + born + "."; 
	}
}
