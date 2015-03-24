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
	private void union(VNode u, VNode v) throws InterruptedException {
		// Make the root of one set point to the root of the other
	     VNode uRoot = findSet(u);
	     VNode vRoot = findSet(v);
	     
	     if (uRoot.getRank() > vRoot.getRank())
	         vRoot.setAncestor(uRoot);
	     else if (uRoot.getRank() < vRoot.getRank())
	         uRoot.setAncestor(vRoot);
	     else if (uRoot != vRoot) {
	         vRoot.setAncestor(uRoot);
	         uRoot.setRank(uRoot.getRank() + 1);
	     }
		
		
//		Thread.sleep(500);
//		System.out.println("union of sets(" + u.getName() + "," + v.getName()
//				+ ")\n\t" + u.printSet() + "\n\t" + v.printSet());
//		System.out.println("\t: " + u.getName() + "'s ancestor "
//				+ u.getAncestor());
//		System.out.println("\t: " + v.getName() + "'s ancestor "
//				+ v.getAncestor());
//		
//		if (v.getRank() == u.getRank()) {
//			if (findSet(v).getRank() > findSet(v).getGeneration()) {
//				
//			}
//			
//		}
//		
//		else if (v.getGeneration() > u.getGeneration()) {
//			v.setAncestor(u);
//			System.out.println("\t\t: " + v.getName() + "'s new set: "
//					+ v.printSet());
//			System.out.println("\t\t: " + v.getName() + "'s ancestor "
//					+ v.getAncestor());
//			printChildSets(v, "\t\t\t");
//		}
//		System.out.println();

	}

	/**
	 * Returns a pointer to the representative of the unique set containing x.
	 * 
	 * @param u
	 * @return
	 * @throws InterruptedException
	 */
	private VNode findSet(VNode x) throws InterruptedException {
		// System.out.println("findSet");
		// System.out.println("x = " + x.toString());
		// System.out.println("x.ancestor = " + x.getAncestor().toString());
		Thread.sleep(10);
		// System.out.println("findSet");
		if (!x.equals(x.getAncestor())) {
			return findSet(x.getAncestor());
		} else {
			return x.getAncestor();
		}
	}

	private void link(VNode x, VNode y) {
		System.out.println("link");
		if (x.getRank() > y.getRank()) {
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
		System.out.println("makeSet " + u.toString());
		u.setAncestor(u);
		u.setRank(0);
		// Thread.sleep(1000);
	}

	private void LCA(VNode u) throws InterruptedException {
		System.out.println("\nLCA " + u.toString());
		makeSet(u);
		findSet(u).setAncestor(u);
		// Thread.sleep(1000);
		for (VNode v : u.getChildren()) {
			LCA(v);
			union(u, v);
			// System.out.println(" findSet from loop ");
			u.setAncestor(u);
			System.out.println("after union, " + v.getName() + "'s set: \t"
					+ v.printSet());
			printChildSets(v, "\t\t\t");
		}
		u.setColor(CONSTANTS.NODE_COLOR.BLACK);
		System.out.println(u.getName() + ".nodeCOlor: " + u.getColor());
		for (Pair p : P) {
			if (p.inPair(u) && p.other(u).getColor() == CONSTANTS.NODE_COLOR.BLACK) {
				System.out.println(p.other(u).getName() + ".nodeCOlor: " + p.other(u).getColor());
				System.out.println("p.u.printSet(): " + p.u.printSet());
				System.out.println("p.v.printSet(): " + p.v.printSet());
				union(p.u, p.v);
				System.out.println("\nLCA of " + p.u.toString() + " and "
				+ p.v.toString() + " is:\t "
				+ findSet(p.v).getAncestor() + " <- ??? \n");
				Thread.sleep(10000000);
			}
//			if (u.equals(p.u)) { 
//				// This node is a test node
//				if (p.v.getColor() == CONSTANTS.NODE_COLOR.BLACK) {
//					// Its partner has been visited.
//					System.out.println("YES");
//					Thread.sleep(1000);
//					System.out.println("\nLCA of " + p.u.toString() + " and "
//							+ p.v.toString() + " is "
//							+ findSet(p.v).getAncestor() + "\n");
//					System.out.println("p.u: " + p.u.printSet());
//					System.out.println("p.v: " + p.v.printSet());
//					Thread.sleep(1000);
//				} else {
//					System.out.println("NOT BLACK, p.v.getColor() = " + p.v.getColor());
//					Thread.sleep(1000);
//				}
//			} else {
//				System.out.println("PARTNER NOT VISITED, u != p.u");
//				Thread.sleep(1000);
//			}
		}


	}

	ArrayList<VNode> testNodes; // Node pairs, test cases

	private void run() throws ParserConfigurationException, SAXException,
			IOException, InterruptedException {

		XMLTree xml = new XMLTree();

		// NameFileReader nameReader = new NameFileReader();
		// Tree T = new Tree();
		// T.buildTree(nameReader.readFile());
		// T.printTree(T.getRoot(), 0);
		// xml.generateFile(T.getRoot());

		Tree T2 = new Tree();
		T2 = xml.buildTree(CONSTANTS.CASE_20_CHILDREN);
		T2.printTree(T2.getRoot(), 0);
		System.out.println("\nNUMBER OF DESCENDANTS: "
				+ VNode.getNDESCENDANTS());

		testNodes = xml.getTestNodes();
		P = new ArrayList<Pair>();
		addPairs(testNodes);
		System.out.println("Test nodes:");
		for (Pair p : P) {
			System.out.println(p.toString());
		}

		LCA(T2.getRoot());
		System.out.println(testNodes.get(0).getName());
		System.out.println(testNodes.get(1).getName());
		// System.out.println(testNodes.get(0).getName() + " rep: " +
		// findSet(testNodes.get(0)).toString());
		// System.out.println("\t" + testNodes.get(0).getName() + " set: " +
		// testNodes.get(0).printSet());
		//
		// System.out.println(testNodes.get(1).getName() + " rep: " +
		// findSet(testNodes.get(1)).toString());
		// System.out.println("\t" + testNodes.get(1).getName() + " set: " +
		// testNodes.get(1).printSet());
		System.out.println("\n***************************\nFINAL SETS");
		printChildSets(T2.getRoot(), "\t");
		if (testNodes.get(1).getColor() == CONSTANTS.NODE_COLOR.BLACK) {
			System.out.println("The least common ancestor of "
					+ testNodes.get(0).toString() + " and "
					+ testNodes.get(1).toString() + " is "
					+ findSet(testNodes.get(0)).getAncestor());
		}

	}

	private ArrayList<Pair> P;

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

	private void printChildSets(VNode v, String indent) {
		for (VNode n : v.getChildren()) {
			System.out.println(indent + v.getName() + "'s child " + n.getName()
					+ "'s set: " + n.printSet());
			printChildSets(n, indent + "\t");
		}
	}

}
