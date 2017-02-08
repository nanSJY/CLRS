package graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import graph.Graph.LinkedNode;

/*
 * 广度优先搜索，breadth-first search (邻接表表示)
 * 		白色：未被发现的顶点
 * 		灰色：已发现和未发现顶点之间的界限
 * 		黑色：已经发现的顶点
 * 		队列Q：存储灰色顶点
 * 		数组R：按照顺序存储黑色顶点，存储广度优先树
 */
public class BFS {

	static int INF = Integer.MAX_VALUE;
	enum Color{
		BLACK,WHITE,GRAY;
	}
	class Node{
		char data;
		int d;
		Node p;
		Color color; 
	}

	Queue<Node> Q;
	List<Node> list;
	List<Node> R;
	
	public void search(Graph graph, char start){
		//初始化队列
		Q = new ArrayDeque<>();
		list = new ArrayList<>();
		
		for(int i=0; i<graph.vers_num; i++){
			Node u = new Node();
			u.data = (char)(65+i);
			if(graph.vers[i].data != start){
				u.color = Color.WHITE;
				u.d = INF;
			}else{
				u.color = Color.GRAY;
				u.d = 0;
				Q.add(u);
			}
			u.p = null;
			list.add(u);
		}
		//遍历
		System.out.println("队列Q：");
		while( !Q.isEmpty() ){
			
			for(Node n:Q){
				System.out.print(n.data);
			}
			System.out.print("\t");
			
			Node u = Q.remove();
			for(Graph.LinkedNode node:graph.adj(u.data)){
				Node v = convert(node);
				if( v.color == Color.WHITE ){
					v.color = Color.GRAY;
					v.d = u.d + 1;
					v.p = u;
					Q.add(v);
				}
			}
			u.color = Color.BLACK;
		}
	}

	private Node convert(LinkedNode node) {
		for(Node n : list){
			if(n.data == node.data){
				return n;
			}
		}
		return null;
	}
	
	public void printTree(char start){
		System.out.println("\n"+"广度优先树（前驱子图）：");
		for(Node n:list){
			if(n.data == start){
				System.out.print(n.data+"-"+n.p+"\t");
			}else{
				System.out.print(n.data+"-"+n.p.data+"\t");
			}
		}
	}
	
	public static void main(String[] args) {
		BFS bfs = new BFS();
//		int[][] matrix = {{0,2,INF,INF,1},{2,0,2,1,9},{INF,2,0,10,INF},{INF,1,10,0,7},{1,9,INF,7,0}};
		int[][] matrix = {  {0,1,1,INF,INF,INF,INF,INF},
							{1,0,INF,INF,INF,INF,INF,INF},
							{1,INF,0,1,INF,INF,INF,INF},
							{INF,INF,1,0,1,1,INF,INF},
							{INF,INF,INF,1,0,1,1,INF},
							{INF,INF,INF,1,1,0,1,1},
							{INF,INF,INF,INF,1,1,0,1},
							{INF,INF,INF,INF,INF,1,1,0}};
		Graph graph = new Graph(matrix);
		char s = 'A';
		bfs.search(graph, s);
		bfs.printTree(s);
	}
}








