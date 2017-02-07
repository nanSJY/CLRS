package graph;

import java.util.ArrayList;
import java.util.List;

public class AdjList {
	
	public static int INF = Integer.MAX_VALUE;
	
	// 数组中的顶点
	class ArrayNode {
		char data;
		LinkedNode firstEdge;
		public ArrayNode(char data, LinkedNode firstEdge) {
			this.data = data;
			this.firstEdge = firstEdge;
		}
		
	}
	// 链表中的顶点
	class LinkedNode {
		char data;
		int weight;
		LinkedNode nextEdge;
		public LinkedNode(char data, int weight, LinkedNode nextEdge) {
			this.data = data;
			this.weight = weight;
			this.nextEdge = nextEdge;
		}
		
	}
	//顶点数组
	ArrayNode[] vers;
	int vers_num;
	
	// 邻接矩阵 创建 邻接表
	public AdjList(int[][] matrix){
	vers_num = matrix.length;
		vers = new ArrayNode[vers_num];
		
		for(int i=0;i<vers_num;i++){
			vers[i] = new ArrayNode( (char)(65+i), null);
		
			for(int j=0;j<vers_num;j++){
				int weight = matrix[i][j];
				if( weight != 0  && weight != INF){
					LinkedNode node = new LinkedNode( (char)(65+j), weight, null);
					this.appened(vers[i], node);
				}
			}
			
		}
	}
	
	//把 LinkedNode 放在链表的末尾
	private void appened(ArrayNode head, LinkedNode node) {
		if(head.firstEdge == null)
			head.firstEdge = node;
		else{
			LinkedNode nextNode = head.firstEdge;
			while(nextNode.nextEdge != null){
				nextNode = nextNode.nextEdge;
			}
			nextNode.nextEdge = node;
		}
	}

	//输出邻接表
	private void printAdjList(){
		for(int i=0;i<vers.length;i++){
			System.out.print(vers[i].data+"\t");
			if(vers[i].firstEdge == null)
				break;
			else{
				LinkedNode nextNode = vers[i].firstEdge;
				while(nextNode != null){
					System.out.print(nextNode.data+"-"+nextNode.weight+"\t");
					nextNode = nextNode.nextEdge;
				}
			}
			System.out.println();
		}
	}
	
	// 查找某个顶点的邻接点
	public List<LinkedNode> adj(char ver){
		List<LinkedNode> adjNode = new ArrayList<>();
		
		for(int i=0; i<vers_num; i++){
			if(ver == vers[i].data ){
				if(vers[i].firstEdge != null){
					LinkedNode nextNode = vers[i].firstEdge;
					adjNode.add(nextNode);
					while(nextNode.nextEdge != null){
						nextNode = nextNode.nextEdge;
						adjNode.add(nextNode);
					}
				}
				break;
			}
		}
		return adjNode;
	}
	
	public static void main(String[] args) {

//		int[][] matrix = {{0,1,INF,INF,1},{1,0,1,1,1},{INF,1,0,1,INF},{INF,1,1,0,1},{1,1,INF,1,0}};
		int[][] matrix = {{0,2,INF,INF,1},{2,0,2,1,9},{INF,2,0,10,INF},{INF,1,10,0,7},{1,9,INF,7,0}};
		AdjList g = new AdjList(matrix);
		g.printAdjList();
		
		for(LinkedNode n: g.adj('E')){
			System.out.print(n.data+"-"+n.weight+"\t");
		}
	}
}

