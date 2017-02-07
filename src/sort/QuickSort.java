package sort;

import java.util.Arrays;

/*
 * 不稳定，
 */
public class QuickSort {
	
	public int[] quickSort(int[] A, int p, int q){
		if(p<q){
			int r = divide(A,p,q);
			quickSort(A,p,r-1);
			quickSort(A,r+1,q);
		}
		return A;
	}

	public int divide(int[] a, int p, int q) {
		int x = a[q];
		int j = p-1; //小于主元x的元素下标的最大值
		for(int i=p ; i<q; i++){
			if(a[i]<x){
				j++;
				int temp = a[j];
				a[j] = a[i];
				a[i] = temp;
			}
		}
		j++;
		int temp = a[j];
		a[j] = x;
		a[q] = temp;
	
		System.out.println(Arrays.toString(a));
		return j;
	}
	
	
	public static void main(String[] args) {
		QuickSort q = new QuickSort();
		int[] a = {11,3,2,1,50,7,72,9,1,8};
		q.quickSort(a, 0, a.length-1);
	}
}
