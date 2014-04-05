import java.util.ArrayList;
import java.util.Arrays;

/**
 * This is LeetCode-ready version, which is essentially identical to
 * _031414_MedianOfTwoSortedArrays, but self-contained.
 * 
 * @author Alan
 *
 */
public class _031414_MedianOfTwoSortedArraysLeetCode {
    private static <T extends Comparable<T>>
    T element1(T[] a, int i, T e, int j) {
        if (i < j) { return a[i]; }
        else if (i == j) { return e; }
        else { return a[i - 1]; }
    }

    public static <T extends Comparable<T>>
    ArrayList<T> middleOfPlus1(T[] a, T e) {
        // first find where to insert the new element
        int j = Arrays.binarySearch(a, e);
        if (j < 0) { j = -(j+1); }
        
        int N = a.length + 1; // new array size
        ArrayList<T> middles = new ArrayList<>();
        if ( N % 2 == 1) {
            // return middle element if array size is odd
            middles.add(element1(a, N/2, e, j));
        } else {
            // return middle two elements if array size is odd
            middles.add(element1(a, N/2 - 1, e, j));
            middles.add(element1(a, N/2, e, j));
        }
        return middles;
    }

    private static <T extends Comparable<T>>
    T element2(T[] a, int i, T e1, int j1, T e2, int j2) {
        // first force j1 <= j2
        if (j1 > j2) {
            // swap e1 and e2
            T tempE = e1; e1 = e2; e2 = tempE;
            // swap j1 and j2
            int tempJ = j1; j1 = j2; j2 = tempJ;
        }

        if (i < j1) { return a[i]; }
        else if (i == j1) { return e1; }
        else if (i <= j2) { return a[i - 1]; }
        else if (i == j2 + 1) { return e2; }
        else { return a[i - 2]; }
    }

    public static <T extends Comparable<T>>
    ArrayList<T> middleOfPlus2(T[] a, T e1, T e2) {
        // first find where to insert the new elements
        int j1 = Arrays.binarySearch(a, e1);
        if (j1 < 0) { j1 = -(j1+1); }
        int j2 = Arrays.binarySearch(a, e2);
        if (j2 < 0) { j2 = -(j2+1); }
        
        int N = a.length + 2; // new array size
        ArrayList<T> middles = new ArrayList<>();
        if ( N % 2 == 1) {
            // return middle element if array size is odd
            middles.add(element2(a, N/2, e1, j1, e2, j2));
        } else {
            // return middle two elements if array size is odd
            middles.add(element2(a, N/2 - 1, e1, j1, e2, j2));
            middles.add(element2(a, N/2, e1, j1, e2, j2));
        }
        return middles;
    }
    
    private static double median(Integer[] a) {
        int n = a.length;
        if (n == 0) { return 0; }
        return (n%2 == 1) ? a[n/2] : (a[n/2-1] + a[n/2])/2.0;
    }
    private static double median(ArrayList<Integer> a) {
        int n = a.size();
        return n == 0 ? 0.0 :
            (n%2 == 1) ? a.get(n/2) : (a.get(n/2-1) + a.get(n/2))/2.0;
    }


    public static double medianOfTwoSortedArrays(Integer[] a1, Integer[] a2) {
        int n1 = a1.length;
        int n2 = a2.length;

        // for convenience we enforce n1 >= n2
        if (n1 < n2) {
            Integer[] tempArray = a1; a1 = a2; a2 = tempArray;
            int temp = n1; n1 = n2; n2 = temp;
        }
        
        // base cases
        switch (n2) {
        case 0:
            return median(a1);
        case 1:
            return median(middleOfPlus1(a1, a2[0]));
        case 2:
            return median(middleOfPlus2(a1, a2[0], a2[1]));
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

    public double findMedianSortedArrays(int A[], int B[]) {
        Integer[] a1 = new Integer[A.length];
        int i = 0;
        for (int e : A) { a1[i++] = e; }

        Integer[] a2 = new Integer[B.length];
        i = 0;
        for (int e : B) { a2[i++] = e; }
        
        return medianOfTwoSortedArrays(a1, a2);
    }
    
    public static void main(String[] args) {
        int[] a1 = {3, 5, 7, 9, 11, 13, 15};
        int[] a2 = {2, 5, 6, 8, 12, 13};

        Integer[] merged = new Integer[a1.length + a2.length];
        int i = 0;
        for (int e : a1) { merged[i++] = e; }
        for (int e : a2) { merged[i++] = e; }
        Arrays.sort(merged);

        _031414_MedianOfTwoSortedArraysLeetCode solver = new _031414_MedianOfTwoSortedArraysLeetCode();
        
        System.out.println("a1: " + Arrays.toString(a1));
        System.out.println("a2: " + Arrays.toString(a2));
        System.out.println("merged: " + Arrays.toString(merged));
        System.out.println("Found " + solver.findMedianSortedArrays(a1, a2)
                + " == " + median(merged) + "?");
    }

}
