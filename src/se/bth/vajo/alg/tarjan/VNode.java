package se.bth.vajo.alg.tarjan;

import java.util.ArrayList;
import java.util.List;

import se.bth.vajo.alg.tarjan.CONSTANTS.NODE_COLOR;

public class VNode {
	private static int NDESCENDANTS = -1;
	private final String name;
	private VNode ancestor;
	private VNode parent;
	private ArrayList<VNode> children;
	private NODE_COLOR color;
	private final int born;
	private final int generation;



	// Set variables
	private int rank;
	private VNode setParent;

	public VNode getAncestor() {
		return this.ancestor;
	}

	public VNode(String nameArg, VNode parentArg, int bornArg,
			int generationArg) {
		NDESCENDANTS++;
		parent = parentArg;

		name = nameArg;
		color = NODE_COLOR.WHITE;
		children = new ArrayList<VNode>();
		born = bornArg;
		generation = generationArg;

		// Set variables;
		ancestor = this;
		rank = generation;
		setParent = this;
	}

	public VNode getParent() {
		return parent;
	}

	public void setParent(VNode parent) {
		this.parent = parent;
	}

	public void addChild(String nameArg, VNode ancestorArg, int bornArg) {

		this.children.add(new VNode(nameArg, ancestorArg, bornArg,
				this.generation + 1));
	}

	public void addChild(VNode n) {
		this.children.add(n);
	}

	public String toString() {
		return generation + ". " + name + ", born " + born;
	}

	public String printAncestry() {
		String ancestry = "";
		VNode p = this.parent;
		while (p != null) {
			ancestry += " <- " + p.toString();
			p = p.parent;
		}
		return ancestry;
	}

	public String printSet() {
		String set = this.toString();
//		System.out.println(set);
		if (!this.ancestor.equals(this)) {
			set += " <- " + this.ancestor.printSet();
		}
		return set;
	}
	
	
	
	public static int getNDESCENDANTS() {
		return NDESCENDANTS;
	}

	public static void setNDESCENDANTS(int nDESCENDANTS) {
		NDESCENDANTS = nDESCENDANTS;
	}

	public ArrayList<VNode> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<VNode> children) {
		this.children = children;
	}

	public NODE_COLOR getColor() {
		return color;
	}

	public void setColor(NODE_COLOR color) {
		this.color = color;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public VNode getSetParent() {
		return setParent;
	}

	public void setSetParent(VNode setParentArg) {
		this.setParent = setParentArg;
	}



	public void setAncestor(VNode ancestorArg) {
//		System.out.println("\t" + this.name + "'s new ancestor is " + ancestorArg.getName());
		this.ancestor = ancestorArg;
	}
	
	public String getName() {
		return name;
	}

	public int getBorn() {
		return born;
	}

	public int getGeneration() {
		return generation;
	}
}
