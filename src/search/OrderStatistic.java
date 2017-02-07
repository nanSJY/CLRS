package search;

import java.util.Arrays;

public class OrderStatistic {
	
	//寻找数组A[p,...,q]中第i小的元素
	public int rand_select(int[] A,int p,int q,int i){
		if(p==q) return A[p];
		
		int r = rand_patition(A,p,q);
		int k = r-p+1;
		if(i==k) return A[r];
		else if(i<k) return rand_select(A,p,r-1,i);
		else return rand_select(A, r+1, q, i-k);
	}

	//该方法实际上直接选择第一个元素作为主元x，并没有随机选择，返回主元的下标
	private int rand_patition(int[] a, int p, int q) {
		int x = a[p];
		int i = p;
		for(int j=p+1;j<=q;j++){
			if(a[j]<x){
				i++;
				int temp = a[i];
				a[i] = a[j];
				a[j] = temp;
			}
		}
		a[p] = a[i];
		a[i] = x;
		System.out.println(Arrays.toString(a));
		System.out.println(x+"-"+i);
		return i;
	}
	
	public static void main(String[] args) {
		OrderStatistic o = new OrderStatistic();
		int[] a = {6,10,13,5,8,3,2,11,2};
		int result = o.rand_select(a, 0, a.length-1, 7);
		System.out.println(result);
	}
	
}
