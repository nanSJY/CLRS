package graph;

import java.util.LinkedList;
import java.util.List;

import graph.Graph.LinkedNode;

/*单源最短路径：
 *		源点s 到其他所有顶点v 的最短路径，delta(s,v)
 * 		假设：不存在负权值，w(u,v) >= 0	
 * 贪心算法：
 * 		维护一个顶点集合S，对于S中的任意顶点v，delta(s,v)是已知的；
 * 					      对于S之外的任意顶点u，delta(s,u)是未知的。
 * 		对于不属于S的顶点，寻找该顶点到S中某个顶点的最小权值边。
 * 		更新S和顶点的d值。
 */
public class Dijkstra {

	public static int INF = Integer.MAX_VALUE;
	
	List<Node> Q = new LinkedList<>();
	List<Node> S = new LinkedList<>();
	
	public void dijkstra(Graph g, char s){
		
		//初始化Q为顶点集合，源点s距离置为0，其他顶点 距离置为INF；S为空集
		for(Graph.ArrayNode node : g.vers){
			if(node.data != s){
				Q.add(new Node(node.data,INF));
				continue;
			}
			Q.add(new Node(s,0));
		}
		
		//更新
		while( !Q.isEmpty() ){
			Node u = extract_min(Q);
			S.add(u);
			for(Graph.LinkedNode node : g.adj(u.data)){
				int weight = node.weight;
				Node v = get(node);
				if( v!= null && v.d >u.d + weight){
					v.d = u.d + weight;
					v.p = u;
				}
			}
		}
	}
	
	
	private Node get(LinkedNode node) {
		for(Node n : Q){
			if(n.data == node.data)
				return n;
		}
		return null;
	}


	private Node extract_min(List<Node> Q) {
		int min = INF;
		Node node = null;
		for(Node n : Q){
			if(n.d < min ){
				min = n.d;
				node = n;
			}
		}
		Q.remove(node);
		return node;
	}


	private class Node{
		//顶点data、源点到该点的估算距离d，该点的父节点p
		char data;
		int d;
		Node p;
		public Node(char data, int d) {
			this.data = data;
			this.d = d;
		}
	}
	
	public static void main(String[] args) {
		Dijkstra d = new Dijkstra();
		int[][] m = { {0,10,INF,5,INF},
						{INF,0,1,2,INF},
						{INF,INF,0,INF,4},
						{INF,3,9,0,2},
						{7,INF,6,INF,0}};
		Graph g = new Graph(m);
		char s = 'A';
		d.dijkstra(g, s);
		for(Node n : d.S){
			if(n.data == s)
				System.out.println(n.data+"-"+n.p+"-"+n.d);
			else
				System.out.println(n.data+"-"+n.p.data+"-"+n.d);
		}
	}

}
