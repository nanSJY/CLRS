package sort;

import java.util.Arrays;

/*
 * in place ; stability
 */
public class InsertSort {
	
	public static void main(String[] args) {
		
		int[] A = {1,5,1,7,3,9,4,2};
		
		int i = 0;
		int j = 0;
		int key = 0;
		for(i=1;i<A.length;i++){
			key = A[i];
			j = i-1;
			while(j>=0 && key < A[j]){
				A[j+1] = A[j];
				j--;
			}
			A[j+1] = key;
			
			System.out.println(Arrays.toString(A));
		}
		
	}
}
