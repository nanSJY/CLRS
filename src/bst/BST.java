package bst;

/*
 * 对于 有n个结点，高度为h的BST：
 * 		遍历：O(n)
 * 		查询（查找、最值、前驱后继）：O(h)
 * 		一次插入：平均、最优：O(h) = O(log n)
 * 		n次插入：  最坏（按顺序或倒序插入）：O( n * n )
 * 				最好、期望： O( n * log n)
 * 		删除：O(h)
 * 
 */
public class BST<K extends Comparable<K>,V> {
	
	public Node<K,V> root;
	
	private class Node<A,B>{
		A key;
		B value;
		Node<A,B> p;
		Node<A,B> left;
		Node<A,B> right;
		
		public Node(A key, B value, Node<A, B> p, Node<A, B> left,
				Node<A, B> right) {
			this.key = key;
			this.value = value;
			this.p = p;
			this.left = left;
			this.right = right;
		}
		
	}
	
	/*
	 * 中序遍历
	 * 假设x是包含了n个结点得树的根，中序遍历时间为O(n)
	 */
	public void inorderTreeWalk(){
		inorderTreeWalk(root);
		System.out.println();
	}
	private void inorderTreeWalk(Node<K,V> x){
		if(x != null){
			inorderTreeWalk(x.left);
//			System.out.print("["+x.key+"-"+x.value+"]  ");
			System.out.print(x.key+"\t");
			inorderTreeWalk(x.right);
		}
	}

	/*
	 * 查找
	 * 对于高度为h的树，查询可以在O(h)时间内完成
	 */
	public Node<K,V> treeSearch(K k){
		return treeSearch(root,k);
	}
	private Node<K,V> treeSearch(Node<K,V> x,K k){
		if(x == null || x.key == k || x.key.equals(k))
			return x;
		int cmp = k.compareTo(x.key);
		if( cmp < 0)
			return treeSearch(x.left, k);
		else
			return treeSearch(x.right, k);
	}
	
	/*
	 * 最大关键字元素
	 * 对于高度为h的树，运行时间为O(h)
	 */
	public Node<K,V> treeMinimum(){
		return treeMinimum(root);
	}
	private Node<K,V> treeMinimum(Node<K,V> x){
		if(x.left != null)
			return treeMinimum(x.left);
		return x;
	}
	
	/*
	 * 最小key元素
	 * 对于高度为h的树，运行时间为O(h)
	 */
	public Node<K,V> treeMaximum(){
		return treeMaximum(root);
	}
	private Node<K,V> treeMaximum(Node<K,V> x){
		if(x.right != null)
			return treeMaximum(x.right);
		return x;
	}
	
	/*
	 * 后继，时间复杂度是O(h)
	 * 
	 * 1. x的右子树非空，后继为右子树的最小值。
	 * 2. x的右子树为空，若后继为y，则y是x的最低祖先结点，且y的左儿子是x的祖先。
	 */
	public Node<K,V> treeSuccessor(K key){
		Node<K,V> x = treeSearch(key);
		if(x == null)
			return null;
		if(x.right != null)
			return treeMinimum(x.right);
		
		Node<K,V> y = x.p;
		while(y != null && y.right == x){
			x = y;
			y = y.p;
		}
		return y;
	}

	/*
	 * 插入
	 */
	public void treeInsert(K key,V value){
		Node<K,V> node = new Node<K, V>(key, value, null, null, null);
		if(root == null){
			root = node;
			return;
		}
		treeInsert(root,node);
	}
	private void treeInsert(Node<K,V> x,Node<K,V> node){
		int cmp = node.key.compareTo(x.key);
		if(cmp < 0){
			if(x.left == null){
				x.left = node;
				node.p = x;
			}
			treeInsert(x.left,  node);
		}
		else if(cmp > 0){
			if(x.right == null){
				x.right = node;
				node.p = x;
			}
			treeInsert(x.right, node);
		}else{
			x.value = node.value;
		}
	}
	
	/*
	 * 删除，返回被删除的结点，O(h)
	 * 
	 * 1.  z没有子女，直接删除z。
	 * 2.  z只有一个子女，删除z，在z的父结点和子结点建立一条链接。
	 * 3.  z有两个子女，删除z的后继y，再用y的内容代替z的内容。
	 */
	public Node<K,V> treeDelete(K key){
		Node<K,V> z = treeSearch(key);
		if(z == null)
			return null;
		
		//确定要删除的结点y
		Node<K,V> y;
		if(z.left == null || z.right == null)
			 y = z;
		else
			y = treeSuccessor(z.key);
		
		//x被置为y的非空子女，或者为null
		Node<K,V> x;
		if(y.left != null)
			x = y.left;
		else
			x = y.right;
		
		//通过修改y.p和x中的指针删除y
		if(x != null)
			x.p = y.p;
		if(y.p == null)
			root = x;
		else if(y == y.p.left)
			y.p.left = x;
		else
			y.p.right = x;
		
		//第三种情况中 复制内容
		if( y != z){
			z.key = y.key;
			z.value = y.value;
		}
		
		return y;
	}
	
	public static void main(String[] args) {
		BST<Integer,String> bst = new BST<>();
/*		bst.treeInsert(5, 1);
		bst.treeInsert(9, 1);
		bst.treeInsert(2, 1);
		bst.treeInsert(0, 1);
		bst.treeInsert(4, 1);
		bst.treeInsert(4, 4);
		bst.treeInsert(-9, 1);
		bst.treeInsert(-11, 1);
		bst.treeInsert(8, 1);
		bst.treeInsert(7, 1);*/
		
		for(int i=0;i<10;i++){
			bst.treeInsert(Integer.valueOf(i), i*i+"");
		}
		bst.inorderTreeWalk();
		bst.treeDelete(5);
		bst.inorderTreeWalk();
	}
	
	
	
}