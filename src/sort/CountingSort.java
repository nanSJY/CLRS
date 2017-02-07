package sort;

import java.util.Arrays;

/*
 *  Input : A[i] , i:1->n , A[i]-->{1,2,3,...,k}
 *  输入的元素从 1~k中取值（或者0~k-1），只能取整数。
 *  
 *  四个for循环的时间复杂度分别为 0(k),0(n),0(k),0(n)，
 *  T(n) = 0(n+k)
 */

public class CountingSort {
	
	public static void main(String[] args) {
		int[] A = {1,5,1,7,3,9,4,2};
		
		int n=A.length;
		int k = 10;  //取值 0~k-1
		int[] B = new int[n];
		int[] C = new int[k];
		
		for(int i=0;i<k;i++){
			C[i] = 0;
		}
		for(int i=0;i<n;i++){
			C[A[i]] += 1;
		}
		for(int i=1;i<k;i++){
			C[i] = C[i] + C[i-1];
		}
		for(int i=n-1;i>=0;i--){
			B[C[A[i]]-1] = A[i];
			C[A[i]]--;
			System.out.println(Arrays.toString(B));
		}
	}
}
