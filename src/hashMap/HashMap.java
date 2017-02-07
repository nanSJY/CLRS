package hashMap;

public class HashMap<K,V> {
	
	private int capacity;
	private float loadFactor = 0.75f;
	private int threshold;
	private int size;
	private Entry<K,V>[] table;
	
	private final int MAX_CAPACITY = 2<<10;
	
	public HashMap(int initialCapacity, float loadFactor) {
		if(initialCapacity < 0 )
			throw new IllegalArgumentException("Illegal initial capacity:"+initialCapacity);
		if(initialCapacity > MAX_CAPACITY)
			initialCapacity = MAX_CAPACITY;
		if(loadFactor < 0 || Float.isNaN(loadFactor))
			throw new IllegalArgumentException("Illegal loadFactor:"+loadFactor);
		
		//计算出 2^n > initialCapacity的n的最小值
		int capacity = 1;
		while(capacity < initialCapacity){
			capacity = capacity << 1;
		}
		this.capacity = capacity;
		
		//设置HashMap的容量极限，当HashMap的容量达到该极限时就会进行扩容操作，从而保持装载因子在一定水平上
		this.loadFactor = loadFactor;
		this.threshold = (int) (loadFactor*capacity);
		
		//初始化数组，哈希表实际上就是该数组
		this.table = new Entry[capacity];
	}
	
	private class Entry<A,B>{
		final A key;
		B value;
		final int hash;
		Entry<A,B> next;
		
		public Entry(A key, B value, Entry<A, B> next, int hash) {
			this.key = key;
			this.value = value;
			this.next = next;
			this.hash = hash;
		}
	}
	
	/*
	 * 插入元素 key-value
	 */
	public V put(K key,V value){
		
		//参数检查：不允许key或者value的值为null
		if( key == null || value == null)
			throw new IllegalArgumentException("Illegal key or value:"+key+"/"+value);
		
		//第一种：计算key的哈希值hash，在此直接用哈希码表示映射结果，并计算该哈希值在table中的下标
		int hash = key.hashCode();
		int index = this.indexFor(hash,table.length);
		
		//把key和value封装在Entry中，并把该Entry插入table
		Entry<K,V> temp = new Entry<K, V>(key, value, null, hash);
		V orderValue = this.setEntry(temp, index);
		
		return orderValue;
	}
	
	
	/*
	 * 根据哈希值hash确定该元素在table中的下标
	 */
	private int indexFor(int hash, int length) {
		/* 
		 * 按位与，按照hash值的 低位 确定在table中的位置
		 * 
		 * 由于，在构造方法中，保证table[]的长度capacity = 2^n
		 * 所以，第一个好处：length-1 的二进制表示是：（111111） ，n个1，按位与的速度要比取余运算高。
		 * 第二个好处：减少碰撞发生的几率。 假设n=4，    （abcd）&（1111） = abcd 保留hash的所有位。
		 * 							若用1110，（abc0）&（1110） = abc0
		 * 									 （abc1）&（1110） = abc0   -->发生碰撞
		 */
		
		return hash & (length-1);
		
		//取模运算，和上面的方法效果相同
		//return hash % length;
	}
	
	
	/*
	 * 将Entry temp插入table[index]中
	 */
	private V setEntry(Entry<K,V> temp,int index){
		Entry<K,V> entry = this.table[index];
		
		//table[index] 不为空
		if( entry != null){
			while(entry != null){
				// 1.table[index]的链表中，有这个key，覆盖以前的value
				if((temp.key == entry.key || temp.key.equals(entry.key)) && temp.hash == entry.hash){
					V orderValue = entry.value;
					entry.value = temp.value;
					return orderValue;
				}else if(entry.next == null){
					// 2.已经遍历到链表尾部，仍然没有这个key，则插入该entry到表头
					this.insert(temp, index);
					return null;
				}
				entry = entry.next;
			}
		}
		//table[index] 为空
		this.insert(temp, index);
		return null;		
	}
	
	/*
	 * 把节点temp插入到table[index]中，保证temp在 链表头部
	 */
	private void insert(Entry<K,V> temp,int index){
		
		size++;
		//判断当前容量是否超标，若超标则扩容，以保持loadFactory不变
		if(size > threshold){
			this.reSize();
		}
		//若不超标，或者扩容之后，则插入元素，如果已经存在元素，则插在第一个，最先插入的entry在链表最后
		if(table[index] != null){
			temp.next = table[index];
			table[index] = temp;
			return;
		}
		//若table[index]为空，直接赋值
		table[index] = temp;
	}
	
	/*
	 * 当HashMap中的元素越来越多的时候，hash冲突的几率也就越来越高，因为数组的长度是固定的。
	 * 所以为了提高查询的效率，就要对HashMap的数组进行扩容。
	 * 而在HashMap数组扩容之后，最消耗性能的点就出现了：原数组中的数据必须重新计算其在新数组中的位置，并放进去。
	 * 
	 * 注意：在扩容之后，必须重新计算原table中每个Entry的位置，因为length改变了，而index与length有关。
	 */
	private void reSize() {
		capacity = (capacity*2) > MAX_CAPACITY? MAX_CAPACITY : capacity*2;
		threshold = (int) (capacity * loadFactor);
		
		Entry<K,V>[] newTable = new Entry[capacity];
		this.transfer(newTable);
		table = newTable;
	}

	/*
	 * 将原来的table拷贝到新的newTable中。需要重新计算每个元素的下标index，非常消耗性能 !!
	 */
	private void transfer(Entry<K, V>[] newTable) {
		for(int i=0;i<table.length;i++){
			Entry<K, V> e = table[i];
			if(e != null){
				table[i] = null;
				do{
					int index = indexFor(e.hash, capacity);
					//把e插入newTable[index]
					if(newTable[index] != null){
						Entry<K,V> temp = newTable[index];
						while(temp.next != null){
							temp = temp.next;
						}
						temp.next = e;
					}else{
						newTable[index] = e;
					}
					e = e.next;
				}while(e != null);
			}
		}
	}


	/*
	 * 遍历哈希表
	 */
	public void walk(){
		for(int i=0;i<table.length;i++){
			Entry<K,V> node = table[i];
			System.out.print("table["+i+"] ");
			while(node != null){
				System.out.print("["+node.key+"-"+node.value+"] ");
				node = node.next;
			}
			System.out.println("\n");
		}
	}
	
	/*
	 * 查找元素 key
	 * T(n) = O(1+ n/m)，当装载因子较小的时候（n>= km，m至少与n成正比 或者更高阶），可认为查找是
	 */
	public V get(K key){
		int hash = key.hashCode();
		int index = indexFor(hash, table.length);
		Entry<K,V> entry = table[index];
		while(entry != null){
			if(entry.key == key || entry.key.equals(key)){
				return entry.value;
			}
			entry = entry.next;
		}
		return null;
	}
	
	
	public static void main(String[] args) {
		HashMap<Integer, String> map = new HashMap<>( 4, 0.75f);
		for(int i=0;i<15;i++){
			map.put(i, String.valueOf(i));
		}
		map.put(15, "a");
		map.walk();
		System.out.println(map.get(5));
	}
}







