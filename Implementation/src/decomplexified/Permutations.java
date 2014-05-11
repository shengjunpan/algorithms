package decomplexified;

import java.util.Arrays;

/**
 * @author Alan
 * http://decomplexify.blogspot.com/2014/05/permutations.html
 */
public class Permutations<T extends Comparable<T>> {

    /**
     * Binary search a query in an sub-array consisting of
     * decreasing elements (in natural order)
     * @return the index where the query can be inserted while
     * maintaining the decreasing order. 
     */
    public int decreasingBinarySearch(T[] a, int from, int limit, T query) {        
        if (from >= limit) {
            return from;
        }
        int mid = (from + limit) / 2;
        int compared = query.compareTo(a[mid]);
        if (compared == 0) {
            // we don't really need this case for permutations 
            return mid;
        } else if (compared > 0) {
            // tail recursion
            return decreasingBinarySearch(a, from, mid, query);
        } else {
            // tail recursion
            return decreasingBinarySearch(a, mid + 1, limit, query);
        }
    }
//begin{permutation}    
    /**
     * Update a permutation to its next (in lexicographical order)
     * @param perm
     * @return true if the permutation is advanced
     */
    public boolean advancePermutation(T[] perm) {
        int n = perm.length;
        
        /* find the last index i such that
         * perm[i] < perm[i+1], and
         * perm[i+1] >= perm[i+2] >= ...
         */
        int i = n - 2;
        while (i>=0 && perm[i].compareTo(perm[i+1]) >= 0) { --i; }

        if (i < 0) { return false;}

        /* elements perm[i+1], perm[i+2], ..., perm[n-1] are in
         * decreasing order, so we can use binary search to find
         * the smallest element that is greater than perm[i].
         */
        int j = decreasingBinarySearch(perm, i+1, perm.length, perm[i]);
        --j;
        
        /* swap perm[i] and perm[j], after which perm[i+1], ..., perm[n-1]
         * are still in decreasing order
         */
        T pi = perm[i];
        perm[i] = perm[j];
        perm[j] = pi;

        /* reverse perm[i+1], ..., perm[n-1] so that they are in
         * increasing order.
         */
        for (int k = i+1; k <= (i+n)/2; ++k) {
            T temp = perm[k];
            perm[k] = perm[n+i-k];
            perm[n+i-k]= temp;
        }
        return true;
    }
//end{permutation}    
    
///////////////////////////////////////////////////////////////////
    private static void testBinarySearch() {
        Permutations<Integer> solver = new Permutations<>();
        Integer[] array = {4,1,3,7,6,5,2,0};
        int index = solver.decreasingBinarySearch(array, 3, array.length, 3);
        System.out.println("index (" + index + ") == " + 6 + "?");
    }
    
    private static void testPermutation() {
        Permutations<Integer> solver1 = new Permutations<>(); 
        Integer[] perm1 = {0,1,2};
        int count1 = 0;
        do {
            System.out.println(Arrays.toString(perm1)
                    + " (" + (++count1) + ")");
        } while (solver1.advancePermutation(perm1));        

        System.out.println("----------");
        Permutations<String> solver2 = new Permutations<>(); 
        String[] perm2 = {"please", "take", "an", "apple"};
        int count2 = 0;
        do {
            System.out.println(Arrays.toString(perm2)
                    + " (" + (++count2) + ")");
        } while (solver2.advancePermutation(perm2));        

    }
    public static void main(String[] args) {
        testBinarySearch();
        testPermutation();
    }

}
