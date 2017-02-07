package sort;

/*
 * 堆排序：此处建立一个大根堆
 * 
 * in place ; instability
 */
public class HeapSort {

	int[] array;
	
	/*
	 * 建立大根堆
	 */
	public HeapSort(int[] array){
		this.array = array;
		//从最后一个非叶子结点进行调整，直到根结点
		for(int i=(array.length-2)/2; i>=0; i--)
			Fixup(i,array.length);
	}
	//调整array[0->size-1]为大根堆
	private void Fixup(int index,int size) {
		if(index>(size-2)/2)
			return;
		
		int left = 2*index+1;
		int right = 2*index+2;
		
		int largest_index = index;
		if( left<size && array[left] > array[index])
			largest_index = left;
		if( right<size && array[right] > array[largest_index])
			largest_index = right;

		if(largest_index == index)
			return;
		else{
//			int temp = array[index];
//			array[index] = array[largest_index];
//			array[largest_index] = temp;
			ArrayUtil.exchange(array, index, largest_index);
			Fixup(largest_index,size);
		}
		
	}
	
	/*
	 * 排序:
	 * 1. 堆顶元素（最大的元素）放在最后，后面是有序区。
	 * 2. 将无序区的元素调整为最大堆.
	 */
	public void sort(){
		for(int i=array.length-1;i>=1;i--){
			ArrayUtil.exchange(array, 0, i);
			Fixup(0, i);
			ArrayUtil.print(array);
		}
	}
	
	public static void main(String[] args) {
		int[] array = {16,7,3,20,17,8};
		HeapSort h = new HeapSort(array);
		h.sort();
	}
}

class ArrayUtil{
	static void exchange(int[] array,int i,int j){
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}
	static void print(int[] array){
		for(int i : array)
			System.out.print(i+"\t");
		System.out.println();
	}
}
