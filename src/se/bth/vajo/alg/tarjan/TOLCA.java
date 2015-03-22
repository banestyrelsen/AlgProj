package se.bth.vajo.alg.tarjan;

import java.io.IOException;

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

//	Tree T;

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
	 */
	private void union(VNode x, VNode y) {
		// Make the root of one set point to the root of the other
		findSet(x).setParent = findSet(y);
		
	}

	
	private void link(VNode x, VNode y) {
//		if (x.representativera)
	}
	
	/**
	 * Returns a pointer to the representative of the unique set containing x.
	 * 
	 * @param u
	 * @return
	 */
	private VNode findSet(VNode x) {
		VNode retVal = null;
		while (x.setParent != null) {
			retVal = findSet(x.setParent);
		}
		return retVal;
	}
	
	
	
	/**
	 * Creates a new set whose only member (and thus representative) is x. Since
	 * the sets are disjoint, we require that x not already be in some other
	 * set.
	 * 
	 * @param u
	 */
	private Set makeSet(VNode u) {
		return new Set(u);
	}



	private void LCA(VNode u) {
		makeSet(u);
		findSet(u).ancestor = u;
		for (VNode v : u.children) {
			LCA(v);
			union(u, v);
			findSet(u).ancestor = u;
		}
	}

	private void run() throws ParserConfigurationException, SAXException, IOException {
		NameFileReader nameReader = new NameFileReader();
		Tree T = new Tree();
		T.buildTree(nameReader.readFile());

		T.printTree(T.getRoot(), 0);

		
		XMLTree xml = new XMLTree();
		xml.generateFile(T.getRoot());

		
		Tree T2 = new Tree();
		T2 = xml.buildTree();
		T2.printTree(T2.getRoot(), 0);
		System.out.println("\nNUMBER OF DESCENDANTS: " + VNode.NDESCENDANTS);
	}

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		TOLCA t = new TOLCA();
		t.run();
	}

}
