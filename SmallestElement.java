package hr.grubic.algorithms;


public class SmallestElement {

	public static int kthSmallest(int[]A, int[]B, int k) {
		if (A== null || B== null) {
			return -1;
		}
		int N = A.length;
		int M = B.length;
		if (k>N+M) {
			return -1;
		}
		return kthSmallest(A,0, M-1, B, 0, N-1, k);
	}
	
	private static int kthSmallest(int[]A, int pa, int qa, int[]B, int pb, int qb, int k) {
		if (pa > qa) {
			//we've used up the A array
			return B[pb+k-1];
		}
		if (pb > qb) {
			//we've used up the B array
			return A[pa+k-1];
		}
		int mida = pa+ (qa-pa)/2;
		int midb = pb + (qb-pb)/2;
		
		int p = mida-pa + midb - pb +2;
		if (k<p) {
			//second half of one array can be discarded
			//discard the one with the larger middle element
			if (A[mida] > B[midb]) {
				return kthSmallest(A, pa, mida-1, B, pb, qb, k);
			} else {
				return kthSmallest(A, pa, qa, B, pb, midb-1, k);
			}
		} else {
			//first half of one array can be discarded
			if (A[mida] > B[midb]) {
				return kthSmallest(A, pa, qa, B, midb+1, qb, k-(midb+1-pb));
			} else {
				return kthSmallest(A, mida+1, qa, B, pb, qb, k-(mida+1-pa));
			}
		}
		
	}
	
	private static int kthSmallestAlternative(int[]A, int pa, int m, int[]B, int pb, int n, int k) {
	    int i= (int) Math.ceil((double)m / (m+n) * (k-1));
		//int i = pa + m/2;
		int j = (k-1) - i;
		System.out.println("i:" + i + "j: " +j + "k:" +k);
		int Ai_1;
		int Ai;
		assert(i >= 0); assert(j >= 0); assert(i <= pa+m); assert(j <= pa+n);
			Ai_1 = ((i == pa)  ? Integer.MIN_VALUE : A[pa+i-1]);
			Ai   = ((i+pa==  m) ?  Integer.MAX_VALUE : A[pa+i]);
	
		int Bj_1;
		int Bj;
		
			Bj_1 = ((j == pb) ? Integer.MIN_VALUE : B[pb+j-1]);
			Bj   = ((j+pb == n) ?  Integer.MAX_VALUE : B[pb+j]);
		if (Ai < Bj ) {
			if (Ai > Bj_1) return Ai;
			/*if (Ai == Bj_1) {
				return kthSmallestAlternative(A, pa, m, B, pb, n, k+1);
			}*/
		}
		if (Bj < Ai) {
			if(Bj >= Ai_1) return Bj;
		}
		if (Ai<=Bj) {
			return kthSmallestAlternative(A, i+1, m-i-1, B, pb, j-1, k-(i+1));
		}
		return kthSmallestAlternative(A, pa, i, B, j+1, n-j-1, k-(j+1));
		
	}
	
	public static int kthLargest(int[]A, int[]B, int k) {
		if (A== null || B== null) {
			return -1;
		}
		int N = A.length;
		int M = B.length;
		if (k>N+M) {
			return -1;
		}
		return kthLargest(A,0, N-1, B, 0, M-1, k);
	}
	
	private static int kthLargest(int[]A, int pa, int qa, int[]B, int pb, int qb, int k) {
		if (pa > qa) {
			//we've used up the A array
			return B[qb-k+1];
		}
		if (pb > qb) {
			//we've used up the B array
			return A[qa-k+1];
		}
		int mida = pa+ (qa-pa)/2;
		int midb = pb + (qb-pb)/2;
		
		int p = (qa- mida) + (qb- midb);
		System.out.println(p);
	
		if (p>=k) {
			//first half of one array can be discarded
			//discard the one with the smaller middle element
			if (A[mida] > B[midb]) {
				return kthLargest(A, pa, qa, B, midb+1, qb, k);
			} else {
				return kthLargest(A, mida+1, qa, B, pb, midb-1, k);
			}
		} else {
			//p<k
			//second half of one array can be discarded
			if (A[mida] > B[midb]) {
				return kthLargest(A, pa, mida-1, B, pb, qb, k-(qa-mida+1));
			} else {
				return kthLargest(A, pa, qa, B, pb, midb-1, k-(qb-midb+1));
			}
		}
	}
	
	private static int kthLargestOfEqualSize(int[]A, int[]B, int k) {
		int n = A.length;
		int la = 0;
		int ha = n-1;
		int lb = 0;
		int hb = n-1;
		
		while (true) {
			if (la == ha) {
				return A[la];
			}
			if (la > ha) {
				return B[hb-k+1];
			}
			int mida = la + (ha-la)/2;
			int midb = findSmallestLargerThan(B, A[mida]);
			if (midb == -1) {
				return A[ha-k+1];
			}
			//number of elements larger than A[mida]
			int p = hb - midb + 1 + ha - mida;
			if (p==k) {
				return A[mida+1];
			} 
			if (p>k) {
				la = mida+1;
			} else {
				k = k - (ha-mida);
				ha = mida;
			}
		}
	}
	

	private static int findSmallestLargerThan(int[] B, int value) {
		int lo = 0;
		int hi = B.length-1;
		while (hi>lo) {
			int mid = lo+(hi-lo)/2;
			if (B[mid]<=value) {
				lo = mid+1;
			} else {
				hi = mid;
			}
		}
		if (hi==lo && B[lo] > value) {
			return lo;
		}
		return -1;
	}

	private static void printArray(int[]A, int lo, int hi) {
		for (int i=lo; i<=hi; i++) {
			System.out.print(A[i] + " ");
		}
		System.out.println();
	}
	
	
	public static void main(String[] args) {
		int[] A = new int[] {
				2,3};
		int[] B = new int [] {
				1,4
		};
		System.out.println(kthLargest(A, B, 1));
	}
	
}
