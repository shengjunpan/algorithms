package decomplexified;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import decomplexified.BinarySearchPredictable.BSTYPE;

/**
 * @author Alan
 * http://decomplexify.blogspot.com/2014/05/permutations.html
 */
public class Permutations<T extends Comparable<T>> {    
//begin{permutation}    
    /**
     * Update a permutation to its successor (in lexicographical order)
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
         * the smallest element that is strictly greater than perm[i].
         */
        Comparator<T> comparator = Collections.reverseOrder();
        BinarySearchPredictable<T> searcher = new BinarySearchPredictable<>(comparator);
        int j = searcher.binarySearchPredictable(perm, i+1, n, perm[i], BSTYPE.LEFTMOST);
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
    private static void testPermutation() {
        System.out.println("----------");
        Permutations<Integer> solver1 = new Permutations<>(); 
        Integer[] perm1 = {1,5,1};
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
        testPermutation();
    }

}
