package decomplexified;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Alan
 * http://decomplexify.blogspot.com/2014/03/algorithm-median-of-2-sorted-arrays.html
 */
public class MedianOfTwoSortedArrays {

    /**
     * helper functions to find the median a sorted array,
     * hiding the parity of the array size
     * @param a a sorted array
     * @return the median of a
     */
    private double median(Integer[] a) {
        int n = a.length;
        if (n == 0) { return 0; }
        return (n%2 == 1) ? a[n/2] : (a[n/2-1] + a[n/2])/2.0;
    }
    private double median(ArrayList<Integer> a) {
        int n = a.size();
        return n == 0 ? 0.0 :
            (n%2 == 1) ? a.get(n/2) : (a.get(n/2-1) + a.get(n/2))/2.0;
    }
        
    public double medianOfTwoSortedArrays(Integer[] a1, Integer[] a2) {
        int n1 = a1.length;
        int n2 = a2.length;

        // for convenience we enforce n1 >= n2
        if (n1 < n2) {
            Integer[] tempArray = a1; a1 = a2; a2 = tempArray;
            int temp = n1; n1 = n2; n2 = temp;
        }
        
        SortedArrayPlus1 solver1 = new SortedArrayPlus1();
        SortedArrayPlus2 solver2 = new SortedArrayPlus2();
        
        // base cases
        switch (n2) {
        case 0:
            return median(a1);
        case 1:
            return median(solver1.middleOfPlus1(a1, a2[0]));
        case 2:
            return median(solver2.middleOfPlus2(a1, a2[0], a2[1]));
        }
        // now n1 >= n2 >= 3
        
        double m1 = median(a1);
        double m2 = median(a2);
       
        int delta = n2/2;
       
        // avoid removing either of the two middle elements.
        if (n1 % 2 == 0 && n2 % 2 == 0) { delta -= 1; }

        Integer[] newA1;
        Integer[] newA2;
        if (m1 < m2) {
            // for clarity only: creating new array is not necessary
            newA1 = Arrays.copyOfRange(a1, delta, n1);
            newA2 = Arrays.copyOfRange(a2, 0, n2-delta);
        } else {
            // for clarity only: creating new array is not necessary
            newA1 = Arrays.copyOfRange(a1, 0, n1-delta);
            newA2 = Arrays.copyOfRange(a2, delta, n2);
        }
        return medianOfTwoSortedArrays(newA1, newA2);
    }

    public static void main(String[] args) {
        Integer[][][] arrayPairs = {
                {{3, 5, 7, 9, 11, 13, 15}, {2, 5, 6, 8, 12, 13}},
                {{1, 2, 3, 5, 7, 15, 16}, {9, 11, 13, 15, 17}}};
        
        MedianOfTwoSortedArrays solver = new MedianOfTwoSortedArrays();
        for (Integer[][] pair : arrayPairs) {
            Integer[] a1 = pair[0];
            Integer[] a2 = pair[1];
            Integer[] merged = new Integer[a1.length + a2.length];
            System.arraycopy(a1, 0, merged, 0, a1.length);
            System.arraycopy(a2, 0, merged, a1.length, a2.length);
            Arrays.sort(merged);
       
            System.out.println("-----------");
            System.out.println("a1: " + Arrays.toString(a1));
            System.out.println("a2: " + Arrays.toString(a2));
            System.out.println("merged: " + Arrays.toString(merged));
            System.out.println("Found " + solver.medianOfTwoSortedArrays(a1, a2)
                    + " == " + solver.median(merged) + "?");
        }
    }

}
