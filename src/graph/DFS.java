package graph;

import java.util.ArrayList;
import java.util.List;

import graph.BFS.Color;
import graph.Graph.ArrayNode;
import graph.Graph.LinkedNode;

/*
 * 深度优先遍历
 */
public class DFS {

	class Node{
		char data;
		Color color;
		//时间戳：顶点第一次被发现由白色置为灰色时，记录d
		//结束检查顶点的邻接点时，记录f
		int d;
		int f;
		Node p;
		public Node(char data, Color color, Node p) {
			this.data = data;
			this.color = color;
			this.p = p;
		}
	}
	
	List<Node> list = new ArrayList<>();
	int time;
	
	public void dfs(Graph g){
		//初始化：每个顶点置为白色，父节点为null
		for(ArrayNode n : g.vers){
			list.add(new Node(n.data, Color.WHITE, null));
		}
		//对于每一个白色顶点进行进行遍历
		for(ArrayNode node: g.vers){
			Node u = get(node);
			if( u.color == Color.WHITE)
				dfs_visit(g,u);
		}
	}
	private void dfs_visit(Graph g, Node u){
		u.color = Color.GRAY;
		time+=1;
		u.d = time;
		for(LinkedNode node: g.adj(u.data)){
			Node v = get(node);
			if(v.color == Color.WHITE){
				v.p = u;
				dfs_visit(g,v);
			}
		}
		u.color = Color.BLACK;
		time+=1;
		u.f = time;
	}
	
	private Node get(LinkedNode node){
		for(Node n : list){
			if(n.data == node.data)
				return n;
		}
		return null;
	}
	private Node get(ArrayNode node) {
		for(Node n : list){
			if(n.data == node.data)
				return n;
		}
		return null;
	}
	
	public void printTree(){
		System.out.println("深度优先树（前驱子图）：");
		for(Node n:list){
			if(n.p == null)
				System.out.print(n.data+"-"+n.p+"\t");
			else
				System.out.print(n.data+"-"+n.p.data+"\t");
		}
	}
	
	static int INF = Integer.MAX_VALUE;
	public static void main(String[] args) {
		//出度邻接表
/*		int[][] m = {   {0,1,1,INF,INF,INF},
						{INF,0,1,INF,INF,INF},
						{INF,INF,0,1,INF,INF},
						{INF,1,INF,0,INF,INF},
						{INF,INF,INF,1,0,1},
						{INF,INF,INF,INF,INF,1}};*/
		int[][] m = {   {0,INF,INF,INF,1,INF,INF,INF},
						{1,0,INF,INF,INF,1,INF,INF},
						{INF,1,0,INF,INF,1,INF,INF},
						{INF,INF,INF,0,INF,INF,1,1},
						{INF,1,INF,INF,0,INF,INF,INF},
						{INF,INF,INF,INF,1,0,INF,INF},
						{INF,INF,1,INF,INF,1,0,INF},
						{INF,INF,INF,1,INF,INF,1,0}};
		Graph g = new Graph(m);
		DFS dfs = new DFS();
		dfs.dfs(g);
		dfs.printTree();
	
	}
}



