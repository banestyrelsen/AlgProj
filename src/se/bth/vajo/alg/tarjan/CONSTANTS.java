package se.bth.vajo.alg.tarjan;

import java.util.Date;

public final class CONSTANTS {
	public static enum NODE_COLOR {WHITE, BLACK};	
	public static int MAX_TREE_DEPTH = 15;
	public static int MAX_NODES = 10000;
	public static int MAX_CHILDRENPERNODE = 10;
	public static String FILENAME = "family-" + new Date().getTime();
	public static String CASE_1000_CHILDREN = "family-1000children";
	public static String CASE_1000_CHILDREN_B = "family-1000B";
	public static String CASE_10K = "family-10K";
	public static String CASE_100_CHILDREN = "family-100children";
	public static String CASE_20_CHILDREN = "family-20children";
	public static String CASE_ONE_MILLION_CHILDREN = "family-1Mchildren";
	public static String CASE_ONE_MILLION_CHILDREN_1K_GENERATIONS = "family-1M1KGen";
	public static String NODE_NAME_ATTR = "name";
	public static String NODE_BORN_ATTR = "born";
}
