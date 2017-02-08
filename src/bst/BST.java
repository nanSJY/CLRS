package bst;

/*
 * ���� ��n����㣬�߶�Ϊh��BST��
 * 		������O(n)
 * 		��ѯ�����ҡ���ֵ��ǰ����̣���O(h)
 * 		һ�β��룺ƽ�������ţ�O(h) = O(log n)
 * 		n�β��룺  �����˳�������룩��O( n * n )
 * 				��á������� O( n * log n)
 * 		ɾ����O(h)
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
	 * �������
	 * ����x�ǰ�����n���������ĸ����������ʱ��ΪO(n)
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
	 * ����
	 * ���ڸ߶�Ϊh��������ѯ������O(h)ʱ�������
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
	 * ���ؼ���Ԫ��
	 * ���ڸ߶�Ϊh����������ʱ��ΪO(h)
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
	 * ��СkeyԪ��
	 * ���ڸ߶�Ϊh����������ʱ��ΪO(h)
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
	 * ��̣�ʱ�临�Ӷ���O(h)
	 * 
	 * 1. x���������ǿգ����Ϊ����������Сֵ��
	 * 2. x��������Ϊ�գ������Ϊy����y��x��������Ƚ�㣬��y���������x�����ȡ�
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
	 * ����
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
	 * ɾ�������ر�ɾ���Ľ�㣬O(h)
	 * 
	 * 1.  zû����Ů��ֱ��ɾ��z��
	 * 2.  zֻ��һ����Ů��ɾ��z����z�ĸ������ӽ�㽨��һ�����ӡ�
	 * 3.  z��������Ů��ɾ��z�ĺ��y������y�����ݴ���z�����ݡ�
	 */
	public Node<K,V> treeDelete(K key){
		Node<K,V> z = treeSearch(key);
		if(z == null)
			return null;
		
		//ȷ��Ҫɾ���Ľ��y
		Node<K,V> y;
		if(z.left == null || z.right == null)
			 y = z;
		else
			y = treeSuccessor(z.key);
		
		//x����Ϊy�ķǿ���Ů������Ϊnull
		Node<K,V> x;
		if(y.left != null)
			x = y.left;
		else
			x = y.right;
		
		//ͨ���޸�y.p��x�е�ָ��ɾ��y
		if(x != null)
			x.p = y.p;
		if(y.p == null)
			root = x;
		else if(y == y.p.left)
			y.p.left = x;
		else
			y.p.right = x;
		
		//����������� ��������
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