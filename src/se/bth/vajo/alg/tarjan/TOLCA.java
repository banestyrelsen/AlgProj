package se.bth.vajo.alg.tarjan;


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
	 * Tarjan's off-line least-common-ancestors algorithm he least common
	 * ancestor of two nodes u and v in a rooted tree T is the node w that is an
	 * ancestor fo both u and v and that has the greatest depth in T. In the
	 * off-line least-common ancestors problem, we are given a rooted tree T and
	 * an arbitrary set P = {{u, v}} of unordered pairs of nodes in T, and we
	 * wish to determine the least common ancestor of each pair in P. To solve
	 * the off-line least common ancestors problem, the following procedure
	 * performs a tree walk of T with the intial call LCA(T.root). We assume
	 * that each node is colored WHITE prior to the walk.
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

	Tree T;


	
	private void union(Node u, Node v) {

	}
	private void makeSet(Node u) {

	}

	private Node findSet(Node u) {
		return null;
	}

	private void LCA(Node u) {
		makeSet(u);
		findSet(u).ancestor = u;
		for (Node v : u.children) {
			LCA(v);
			union(u, v);
			findSet(u).ancestor = u;
		}
	}

	private void run() {
		NameFileReader nameReader = new NameFileReader();		
		T = new Tree();
		T.buildTree(nameReader.readFile());
		
		T.printTree(T.getRoot(), 0);
		System.out.println("\nNUMBER OF NODES: " + Node.NDESCENDANTS);
	}

	public static void main(String[] args) {
		TOLCA t = new TOLCA();
		t.run();
	}

}
