package graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import graph.AdjList.LinkedNode;

/*
 * 最小生成树
 */
public class Prim {
	
	public static int INF = Integer.MAX_VALUE;
	public static List<Node> result = new ArrayList<>();
	public static List<Node> Q;
	// Prim's algorithms
	public static void prim(AdjList graph, char start){
		//初始化
		Q = new LinkedList<>();
		for(int i=0; i<graph.vers_num; i++){
			Q.add( new Node( (char)(65+i), INF) );
		}
		for(Node n:Q){
			if( n.data == start)
				n.key = 0;
		}
		//生成MST
		while( !Q.isEmpty()){
			char u = extractMin(Q);
			for( AdjList.LinkedNode node : graph.adj(u) ){
				Node v = contains(Q,node);
				if( v != null && v.key > node.weight){
					v.key = node.weight;
					v.p = u;
				}
			}
		}
	}
	
	// 判断u的邻接点v是否在Q中，若在，返回该Node,否则返回null
	private static Node contains(List<Node> Q, LinkedNode node) {
		for(Node n : Q){
			if(  node.data == n.data){
				return n;
			}
		}
		return null;
	}
	
	// 优先级队列Q中存储的顶点
	private static class Node{
		char data;
		int key;
		char p;
		public Node(char data, int key) {
			this.data = data;
			this.key = key;
		}
	}

	// 查找Q中key值最小的顶点，把该顶点从Q中放入 最小生成树 中
	private static char extractMin(List<Node> Q){
		char r = 0;
		int min = INF;
		Node node = null;
		for(Node n:Q){
			if(n.key < min){
				min = n.key;
				r = n.data;
				node = n;
			}
		}
		result.add(node);
		Q.remove(node);
		return r;
	}
	
	public static void printMST(){
		for(Node n : result){
			System.out.println(n.data+"-"+n.p);
		}
	}
	
	public static void main(String[] args) {
		int[][] matrix = {{0,2,INF,INF,1},{2,0,2,1,9},{INF,2,0,10,INF},{INF,1,10,0,7},{1,9,INF,7,0}};
//		int[][] matrix = {};
		AdjList g = new AdjList(matrix);
		Prim.prim(g, 'E');
		Prim.printMST();
	}
}
