package dynamicProgramming;


/*
 * 最长公共子序列
 * 
 * 1.求解LCS的长度
 * 2.返溯LCS
 */
public class LCS {
	char[] a ,b ;
	Integer[][] c ;
	
	public LCS(char[] a, char[] b) {
		this.a = a;
		this.b = b;
		this.c = new Integer[a.length+1][b.length+1];
		for(int i=0;i<=a.length;i++)
			c[i][0] = 0;
		for(int j=0;j<=b.length;j++)
			c[0][j] = 0;
	}
	
	//循环，构造c[][]
	public void getLength(){
		for(int i=1;i<=a.length;i++){
			for(int j=1;j<=b.length;j++){
				
				if(a[i-1] == b[j-1])
					c[i][j] = c[i-1][j-1] + 1;
				else
					c[i][j] = Math.max( c[i-1][j] , c[i][j-1] );
			}

		}
		
	}
	
	//递归，构造c[][]；备忘法：计算了p个子问题
	static int p = 0;
	public int getLength( int i, int j) {
		if(c[i][j] == null){
			p++;

			if(a[i-1] == b[j-1])
				c[i][j] = getLength(i-1, j-1)+1;
			else
				c[i][j] = Math.max( getLength( i-1, j) , getLength( i, j-1));
		}
		return c[i][j];
	}
	
	//回溯
	public void print(int i, int j){
		if( i<1 || j<1)
			return;
		if(a[i-1] == b[j-1]){
			print(i-1,j-1);  //左上
			System.out.print(a[i-1]);
		}else if(c[i-1][j] > c[i][j-1]){
			print(i-1,j);  //上
		}else{
			print(i,j-1);  //左
		}
		
	}
	
	public static void main(String[] args) {
		char[] a = {'a','b','c','b','d','a','b'}, b = {'b','d','c','a','b','a'};
		LCS l = new LCS(a,b);
		l.getLength(a.length, b.length);
		for(int i=1;i<=a.length;i++){
			for(int j=1;j<=b.length;j++){
				System.out.print(l.c[i][j]+"\t");
			}
			System.out.println();
		}
		System.out.println("递归次数："+p+"\t"+"长度："+l.c[a.length][b.length]);
		l.print(7,6);
	}
}
		