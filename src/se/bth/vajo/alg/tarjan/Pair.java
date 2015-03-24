package se.bth.vajo.alg.tarjan;


public class Pair {
	public VNode u;
	public VNode v;
	public Pair(VNode aArg, VNode bArg) {
		u = aArg;
		v = bArg;
	}
	@Override
	public String toString() {
		return "Pair [u=" + u.toString() + ", v=" + v.toString() + "]";
	}
	
	public boolean inPair(VNode n) {
		return n.equals(u) || n.equals(v);
	}
	
	public VNode other(VNode n) {
		if (n.equals(u)) {
			return v;
		} else {
			return u;
		}
	}
	
}
