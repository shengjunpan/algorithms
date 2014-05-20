package decomplexified;

import java.util.Arrays;

/**
 * @author Alan
 *  http://decomplexify.blogspot.com/2014/05/median-of-two-sorted-arrays.html
 */
public class MedianOf2SortedArrays {
//begin{median-of-one-array}
    /**
     * helper function to find the median a sorted array piece,
     * hiding the parity of the array size
     */
    private double median(int[] a, int start, int n) {
        if (n == 0) {
            return 0.0;
        } else if (n%2 == 1) {
            return a[start+n/2];
        } else {
            return (a[start+n/2-1] + a[start+n/2])/2.0;
        }
    }
//end{median-of-one-array}

//begin{merged-arrays}    
    /**
     * helper class representing the union of two sorted array pieces
     * without actually merging them. An array of insertion points
     * is created, where each insertion point corresponds to an index
     * in array 1 such that an element from array 2 can be inserted
     * into array 1 without breaking its order.
     * 
     * This class can be used to find the median of 2 sorted arrays in
     * O(log(n1) * n2) time, where the size of the second array n2
     * is assumed to be a constant. 
     */
    private static class MergedAndSorted {
        int[] a;
        int start1;
        int[] b;
        int start2;
        
        int[] insertionPoints;
        
        // conceptually merge two sorted array pieces by finding
        // the insertion points of elements from the second
        // array into the first array.
        public MergedAndSorted(int[] a, int start1, int n1,
                               int[] b, int start2, int n2) {
            this.a = a;
            this.start1 = start1;
            this.b = b;
            this.start2 = start2;
            
            insertionPoints = new int[n2];

            int searchStart = start1;
            for (int i = 0; i < n2 ; ++i ) {                
                int searchEnd = start1 + n1;
                
                // binary search for b[start2+i] inside a
                while (searchStart < searchEnd) {
                    int searchMid = (searchStart + searchEnd) / 2;
                    if (b[start2 + i] < a[searchMid]) {
                      searchEnd = searchMid;
                    } else if (b[start2 + i] > a[searchMid]) {
                        searchStart = searchMid + 1;
                    } else {
                        searchStart = searchMid;
                        break;
                    }
                }
                insertionPoints[i] = searchStart;
            }            
        }
                
        /**
         * Find the element at given index of the `merged' array
         */
        public int get(int index) {
            index += start1;
            int i = 0;
            while (i < insertionPoints.length && index > insertionPoints[i]+i) { ++i; }
            if (i < insertionPoints.length && index == insertionPoints[i]+i) {
                return b[start2 + i];
            } else {
                return a[index - i];
            }
        }
    }    
//end{merged-arrays}    

//begin{median-of-two-arrays}
    public double findMedianSortedArrays(int A[], int B[]) {
        // for convenience we enforce B to be the shorter array
        if (A.length < B.length) {
            int[] tempArray = A;
            A = B;
            B = tempArray;
        }

        int start1 = 0, n1 = A.length;
        int start2 = 0, n2 = B.length; 

        // carry out the recursion as long as both arrays have more
        // than 2 elements
        while (n2 >= 3) {
            double m1 = median(A, start1, n1);
            double m2 = median(B, start2, n2);      
        
            int delta = n2/2;       

            // avoid removing either of the two middle elements.
            if (n1 % 2 == 0 && n2 % 2 == 0) {
                delta -= 1;
            }

            // remove delta = min(n1/2,n2/2) elements from both arrays
            n1 -= delta;
            n2 -= delta;
            
            if (m1 < m2) {
                // remove delta elements from the LHS of array 1, while
                // remove delta elements from the RHS of array 2
                start1 += delta;
            } else {
                // remove delta elements from the RHS of array 1, while
                // remove delta elements from the LHS of array 2
                start2 += delta;
            }
        }
        
        MergedAndSorted ms = new MergedAndSorted(A, start1, n1, B, start2, n2);
        int N = n1+n2;
        if (N%2 == 0) {                
            return 0.5 * (ms.get(N/2-1) + ms.get(N/2));
        } else {
            return ms.get(N/2);
        }
    }    
//end{median-of-two-arrays}

////////////////////////////////////////////////////////////////////
    private static void testMergedAndSorted() {
        int[][][] arrayPairs = {
                {{3, 5, 7, 9, 11, 13, 15}, {2, 5, 6, 8, 12, 13}},
                {{1, 2, 3, 5, 7, 15, 16}, {9, 11, 13, 15, 17}},
                {{1, 2, 3, 5, 7, 15, 16}, {}}};

        for (int[][] ab : arrayPairs) {
            System.out.println("---------------");
            int[] a = ab[0];
            int[] b = ab[1];
            System.out.println("a: " + Arrays.toString(a));
            System.out.println("b: " + Arrays.toString(b));
        
            MergedAndSorted ms = new MergedAndSorted(a,0,a.length,b,0,b.length);
            System.out.println("Insertion points: " + Arrays.toString(ms.insertionPoints));
        
            int[] ab2 = new int[a.length + b.length];
            for (int i = 0; i < ab2.length; ++i) {
                ab2[i] = ms.get(i);
            }
            System.out.println("Merged = " + Arrays.toString(ab2) + " ?");
        }
    }
    
    private static void testMedian() {
        int[][][] arrayPairs = {
                {{3, 5, 7, 9, 11, 13, 15}, {2, 5, 6, 8, 12, 13}},
                {{1, 2, 3, 5, 7, 15, 16}, {9, 11, 13, 15, 17}}};
        
        MedianOf2SortedArrays solver = new MedianOf2SortedArrays();
        for (int[][] ab : arrayPairs) {
            int[] a = ab[0];
            int[] b = ab[1];
            int[] merged = new int[a.length + b.length];
            System.arraycopy(a, 0, merged, 0, a.length);
            System.arraycopy(b, 0, merged, a.length, b.length);
            Arrays.sort(merged);
       
            System.out.println("-----------");
            System.out.println("a: " + Arrays.toString(a));
            System.out.println("b" + Arrays.toString(b));
            System.out.println("merged: " + Arrays.toString(merged));
            System.out.println("Found " + solver.findMedianSortedArrays(a, b)
                    + " == " + solver.median(merged, 0, merged.length) + "?");
        }
    }
    
    public static void main(String[] args) {
        testMergedAndSorted();
        System.out.println();
        testMedian();
    }

}
