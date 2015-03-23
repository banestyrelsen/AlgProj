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
	private void union(VNode x, VNode y) throws InterruptedException {
		// Make the root of one set point to the root of the other
		 findSet(y).setAncestor(x); 
//		VNode xRoot = findSet(x);
//		VNode yRoot = findSet(y);
//		if (xRoot.getRank() < yRoot.getRank()) {
//			yRoot.setSetParent(xRoot);
//		} else if (xRoot.getRank() > yRoot.getRank()) {
//			xRoot.setSetParent(yRoot);
//		} else if (!xRoot.equals(yRoot)) {
//			yRoot.setSetParent(xRoot);
//			xRoot.setRank(xRoot.getRank() + 1);
//		}
//		link(findSet(x), findSet(y));
	}

	/**
	 * Returns a pointer to the representative of the unique set containing x.
	 * 
	 * @param u
	 * @return
	 * @throws InterruptedException 
	 */
	private VNode findSet(VNode x) throws InterruptedException {
//		System.out.println("findSet");
//		System.out.println("x = " + x.toString());
//		System.out.println("x.ancestor = " + x.getAncestor().toString());
		Thread.sleep(10);
		if (!x.equals(x.getAncestor())){
			x.setAncestor(findSet(x.getAncestor()));
		}
		return x.getAncestor();
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
//		Thread.sleep(1000);
	}

	private void LCA(VNode u) throws InterruptedException {
		makeSet(u);
		findSet(u).setAncestor(u);
//		Thread.sleep(1000);
		for (VNode v : u.getChildren()) {
			LCA(v);
			union(u, v);
//			System.out.println(" findSet from loop ");
			findSet(u).setAncestor(u);
		}
		u.setColor(CONSTANTS.NODE_COLOR.BLACK);
		if (testNodes.get(0).getColor() == CONSTANTS.NODE_COLOR.BLACK && testNodes.get(1).getColor() == CONSTANTS.NODE_COLOR.BLACK) {
			System.out.println("LCA of " + testNodes.get(0).toString() + " and " + testNodes.get(1).toString() + " is " + findSet(testNodes.get(1)).getSetParent());
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
		T2 = xml.buildTree(CONSTANTS.CASE_100_CHILDREN);
		T2.printTree(T2.getRoot(), 0);
		System.out.println("\nNUMBER OF DESCENDANTS: "
				+ VNode.getNDESCENDANTS());

		testNodes = xml.getTestNodes();
		System.out.println("Test nodes:");
		for (VNode t : testNodes) {
			System.out.println(t.toString() + " " + t.printAncestry());
		}

		LCA(T2.getRoot());
		// if (testNodes.get(1).color == CONSTANTS.NODE_COLOR.BLACK) {
		// System.out.println("The least common ancestor of "
		// + testNodes.get(0).toString() + " and "
		// + testNodes.get(1).toString() + " is "
		// + findSet(testNodes.get(0)).ancestor);
		// }

	}

	public static void main(String[] args) throws ParserConfigurationException,
			SAXException, IOException, InterruptedException {
		TOLCA t = new TOLCA();
		t.run();
	}

}
