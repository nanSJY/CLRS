package sort;

import java.util.Arrays;

/*
 * Divide --> Conquer --> Combine
 * 稳定， 空间复杂度 = O(n)
 */

public class MergeSort {
	
	public int[] mergeSort(int[] A,int p,int q){
		if(p<q){
			int r = (int)Math.floor((p+q)/2);
			mergeSort(A, p, r);
			mergeSort(A, r+1, q);
			combine(A,p,r,q);
		}
		return A;
	}
	
	public void combine(int[] a, int p, int r, int q) {
		int[] b = new int[q-p+1];
		int i = p;
		int j = r+1;
		int min = 0;
		int k = 0;
		while(i<=r && j <= q){
			min = a[i]<=a[j]? a[i++] :a[j++];
			b[k++] = min;
		}
		if(i<=r){
			for(int s=i;s<=r;s++)
				b[k++] = a[s];
		}
		if(j<=q){
			for(int s=j;s<=q;s++)
				b[k++] = a[s];
		}
		for(k=p;k<=q;k++){
			a[k] = b[k-p];
		}
		System.out.println(Arrays.toString(b));
	}



	public static void main(String[] args) {
		MergeSort m = new MergeSort();
		int[] a = {11,3,2,1,50,7,72,9,1,43};
		m.mergeSort(a, 0, a.length-1);
	}
}
