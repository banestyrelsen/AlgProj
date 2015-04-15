package se.bth.vajo.alg.tarjan;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class TOLCA {
	/*
	 * OK uppgift. Vi har ju pratat något om den. Det blir en del att koda
	 * (graf, set-union, mm). Tänk i förväg igenom hur du generera testdata.
	 * Tänk igenom implementering av set-union (version två i boken 21.3). Börja
	 * utan eller med högst en av heuristikerna. Sen kan du lägga till
	 * heuristiker när du vet att det fungerar. (Stighalvering, som visat på
	 * föreläsning, i stället för stigkomprimering är nog väl så bra.) Du kan
	 * behöva riktigt stora träd för att kunna märka skillnader mellan olika en
	 * resp båda heuristikerna. Gör en tidsplan och håll koll på hur du använder
	 * tiden så att du kommer i mål.
	 */

	/*
	 * Tarjan's off-line least-common-ancestors algorithm.
	 */
	/*
	 * The least common ancestor of two nodes u and v in a rooted tree T is the
	 * node w that is an ancestor of both u and v and that has the greatest
	 * depth in T. In the off-line least-common ancestors problem, we are given
	 * a rooted tree T and an arbitrary set P = {{u, v}} of unordered pairs of
	 * nodes in T, and we wish to determine the least common ancestor of each
	 * pair in P. To solve the off-line least common ancestors problem, the
	 * following procedure performs a tree walk of T with the initial call
	 * LCA(T.root). We assume that each node is colored WHITE prior to the walk.
	 */

	/* a. Argue that line 10 executes exactly once for each pair {u,v} € P. */
	/*
	 * b. Argue that at the time of the call LCA(u), the number of sets in the
	 * disjoint-set data structure equals the depth of u in T.
	 */
	/*
	 * c. Prove that LCA correctly prints the least common ancestor of u and v
	 * for each pair {u,v} € P.
	 */
	/*
	 * d. Analyze the running time of LCA, assuming that we use the
	 * implementation of the disjoint-set data structure in Section 21.3.
	 */

	// Tree T;

	/**
	 * Unites the dynamic sets that contain x and y, say Sx and Sy, into a new
	 * set that is the union of these two sets. We assume that the two sets are
	 * disjoint prior to the operation. The representative of the resulting set
	 * is any member of Sx U Sy, although many implementations of Union
	 * specifically choose the representative of either Sx or Sy as the new
	 * representative. Since we require the sets in the collection to be
	 * disjoint, conceptually we destroy sets Sx and Sy, removing them from the
	 * colleciton S. In practice, we often absorb the elements of one of the
	 * sets into the other set.
	 * 
	 * @param u
	 * @param v
	 * @throws InterruptedException
	 */

	// Metrics
	private long nMakeSetOperations = 0;
	private long nUnionOperations = 0;
	private long nFindSetOperations = 0;
	private ArrayList<Pair> P;
	private ArrayList<VNode> testNodes; // Node pairs, test cases
	private VNode result;
	
	private void union(VNode u, VNode v) throws InterruptedException {
		link(findSet(u), findSet(v));
		nUnionOperations++;
	}

	/**
	 * Returns a pointer to the representative of the unique set containing x.
	 * 
	 * @param u
	 * @return
	 * @throws InterruptedException
	 */
	private VNode findSet(VNode x) throws InterruptedException {
		nFindSetOperations++;

		// WITHOUT path compression
		if (x.getAncestor() == x) {

			return x;
		} else {
			return findSet(x.getAncestor());
		}

		// WITH path compression
		// if (x.getAncestor() != x) {
		// x.setAncestor(x.getAncestor());
		// }
		// return x.getAncestor();

	}

	private void link(VNode x, VNode y) {
		// System.out.println("link");
		if (x.getRank() < y.getRank()) {
			y.setAncestor(x);
		} else {
			x.setAncestor(y);
			if (x.getRank() == y.getRank()) {
				y.setRank(y.getRank() + 1);
			}
		}
	}

	/**
	 * Creates a new set whose only member (and thus representative) is x. Since
	 * the sets are disjoint, we require that x not already be in some other
	 * set.
	 * 
	 * Removes u to a singleton set,
	 * 
	 * @param u
	 * @throws InterruptedException
	 */
	private void makeSet(VNode u) throws InterruptedException {
		// System.out.println("makeSet " + u.toString());
		nMakeSetOperations++;
		u.setAncestor(u);
		// u.setRank(0);
		// Thread.sleep(1000);
	}


	private void LCA(VNode u) throws InterruptedException {
		makeSet(u);
		findSet(u).setAncestor(u);
		for (VNode v : u.getChildren()) {
			LCA(v);
			union(u, v);
			findSet(u).setAncestor(u);
		}
		u.setColor(CONSTANTS.NODE_COLOR.BLACK);
		for (Pair p : P) {
			if (p.inPair(u)
					&& p.other(u).getColor() == CONSTANTS.NODE_COLOR.BLACK) {
				System.out.println("  PAIR FOUND.");
				System.out
						.println("  "
								+ "Current node ("
								+ u.toString()
								+ ") is in the predefined pair, and the node color of its partner ("
								+ p.other(u).toString() + ") is: "
								+ p.other(u).getColor() + ".");

				System.out.println("  " + p.u.getName() + "'s ancestry: "
						+ p.u.printAncestry());
				System.out.println("  " + p.v.getName() + "'s ancestry: "
						+ p.v.printAncestry());
				System.out.println("  " + p.u.getName() + "'s current set: "
						+ p.u.printSet());
				System.out.println("  " + p.v.getName() + "'s current set: "
						+ p.v.printSet());

				// union(p.u, p.v);
				result = findSet(p.other(u)).getAncestor();
				System.out.println("  " + "LCA of " + p.u.toString() + " and "
						+ p.v.toString() + " is:\t "

						+ result);

			}

		}

	}


	private void run() throws ParserConfigurationException, SAXException,
			IOException, InterruptedException {

		XMLTree xml = new XMLTree();
		Tree T = new Tree();
		boolean generate = false;

		if (generate) {
			System.out.println("Generating new tree with max "
					+ CONSTANTS.MAX_NODES + " nodes and max "
					+ CONSTANTS.MAX_CHILDRENPERNODE + " children per node");
			NameFileReader nameReader = new NameFileReader();
			T.buildTree(nameReader.readFile());
			T.printTree(T.getRoot(), 0);
			System.out.println("\nTree with : " + VNode.getNDESCENDANTS()
					+ " generated.");
			System.out.println("Saving tree to file...");
			xml.generateFile(T.getRoot());

		} else {
			String testCase = CONSTANTS.CASE_100_CHILDREN;
			System.out.println("Reading tree from file: \"" + testCase + "\"");
			T = xml.buildTree(testCase);

			 T.printTree(T.getRoot(), 0);
		}
		// Thread.sleep(2000);
		System.out.println("Tree with " + VNode.getNDESCENDANTS()
				+ " descendants generated. ");
		testNodes = xml.getTestNodes();
		P = new ArrayList<Pair>();
		addPairs(testNodes);

		System.out.println("\nCalculating LCA of nodes " + P.get(0).toString());
		System.out.println("Traversing tree...");
		// System.out.println("Starting timer...");
		// long start = new Date().getTime();
		LCA(T.getRoot());
		// long done = new Date().getTime();
		// long elapsed = done - start;

		System.out.println("\nTotal number of MAKE-SET operations: "
				+ nMakeSetOperations);
		System.out.println("Total number of FIND-SET operations: "
				+ nFindSetOperations);
		System.out.println("Total number of UNION operations: "
				+ nUnionOperations);

		// printChildSets(T.getRoot(), "");

	}



	private void addPairs(ArrayList<VNode> pairs) {
		for (int i = 0; i < pairs.size(); i += 2) {
			Pair p = new Pair(pairs.get(i), pairs.get(i + 1));
			P.add(p);
		}
	}

	public static void main(String[] args) throws ParserConfigurationException,
			SAXException, IOException, InterruptedException {
		TOLCA t = new TOLCA();
		t.run();
	}

	public void printAllSets(VNode root) {
		printChildSet(root);

	}

	private void printChildSet(VNode n) {
		for (VNode c : n.getChildren()) {
			System.out.println(c.printSet());
			printChildSet(c);
		}

	}

	// private void printChildSets(VNode v, String indent) {
	// for (VNode n : v.getChildren()) {
	// System.out.println(indent + v.getName() + "'s child " + n.getName()
	// + "'s set: " + n.printSet());
	// printChildSets(n, indent + "\t");
	// }
	// }

}
