package decomplexified;

public class PermutationsLeetCode {
//begin{permutation-leetcode}
    public void nextPermutation(int[] perm) {
        int n = perm.length;
        int i = n - 2;
        while (i>=0 && perm[i] >= perm[i+1]) { --i; }
        if (i >= 0) {
            int j = n-1;
            while (j>=0 && perm[j] <= perm[i]) { --j; }
            int pi = perm[i]; perm[i] = perm[j]; perm[j] = pi;
        }
        for (int k = i+1; k <= (i+n)/2; ++k) {
            int temp = perm[k];
            perm[k] = perm[n+i-k];
            perm[n+i-k]= temp;
        }
    }
//end{permutation-leetcode}
}
