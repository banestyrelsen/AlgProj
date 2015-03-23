package se.bth.vajo.alg.tarjan;

import java.util.Date;

public final class CONSTANTS {
	public static enum NODE_COLOR {WHITE, BLACK};	
	public static int MAX_TREE_DEPTH = 7;
	public static int MAX_NODES = 100;
	public static int MAX_CHILDRENPERNODE = 6;
	public static String FILENAME = "family-" + new Date().getTime();
	public static String CASE_1000_CHILDREN = "family-1000children";
	public static String CASE_100_CHILDREN = "family-100children";
	public static String NODE_NAME_ATTR = "name";
	public static String NODE_BORN_ATTR = "born";
}
