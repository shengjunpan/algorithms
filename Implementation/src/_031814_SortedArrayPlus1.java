
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author Alan
 * http://decomplexify.blogspot.com/2014/03/algorithm-median-of-sorted-array-with.html
 */
public class _031814_SortedArrayPlus1 {
    /**
     * helper: indexing an array plus a new element, but without actually
     * inserting it
     * 
     * @param a
     *            a sorted array
     * @param i
     *            index of the new array
     * @param e
     *            new element
     * @param j
     *            insertion index of the original array
     * @return b[i], where b is the new array obtained by inserting element e
     *         right before a[j].
     */
    private static <T extends Comparable<T>>
    T element1(T[] a, int i, T e, int j) {
        if (i < j) { return a[i]; }
        else if (i == j) { return e; }
        else { return a[i - 1]; }
    }
 
    /**
     * Find the middle element(s) of a sorted array plus a new element, but
     * without actually inserting it
     * 
     * @param a  sorted array
     * @param e  new element
     * @return the middle element if new array size is odd, or the middle two
     *         elements if new array size even
     */
    private <T extends Comparable<T>>
    ArrayList<T> middleOfPlus1(T[] a, T e) {
        // first find where to insert the new element
        _030514_BinarySearch bsSolver = new _030514_BinarySearch();
        int j = bsSolver.binarySearch(a, 0, a.length, e);
        
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

////////////////////////////////////////////////////////////////////////////

    // test helper
    private static ArrayList<Integer> mergeAndSort(Integer[] a, Integer e) {
        ArrayList<Integer> newA = new ArrayList<>(Arrays.asList(a));
        newA.add(e);
        Collections.sort(newA);
        return newA;
    }

    private static void element1_test() {
        Integer[] a = {0, 10, 20, 30, 40};
        
        Integer e = 15;
        int j = 2;
        ArrayList<Integer> newA = mergeAndSort(a, e);
        System.out.println(Arrays.toString(a)+" + "+e+" --> "+newA.toString());
        
        for (int i = 0; i < 6; ++i) {
            System.out.println("newA["+i+"]"+newA.get(i)+" == "+element1(a, i, e, j)+"?");
        }        
    }
    
    private static void middleOfPlus1_oddTest() {
        Integer[] a = {0, 10, 20, 30};
        Integer[] eList = {-5, 5, 15, 25, 35};
        _031814_SortedArrayPlus1 solver = new _031814_SortedArrayPlus1();
        
        System.out.println("--------------");
        for (int e : eList) {
            ArrayList<Integer> newA = mergeAndSort(a, e);
            ArrayList<Integer> middles = solver.middleOfPlus1(a, e);
            System.out.println(Arrays.toString(a)+" + "+e+" --> "
                    +newA.toString()+" --> middle " + middles.toString());            
        }        
    }

    private static void middleOfPlus1_evenTest() {
        Integer[] a = {0, 10, 20, 30, 40};
        Integer[] eList = {-5, 5, 15, 25, 35, 45};
        _031814_SortedArrayPlus1 solver = new _031814_SortedArrayPlus1();
        
        System.out.println("--------------");
        for (int e : eList) {
            ArrayList<Integer> newA = mergeAndSort(a, e);
            ArrayList<Integer> middles = solver.middleOfPlus1(a, e);
            System.out.println(Arrays.toString(a)+" + "+e+" --> "
                    +newA.toString()+" --> middle " + middles.toString());            
        }
    }
    public static void main(String[] args) {
        element1_test();
        middleOfPlus1_oddTest();
        middleOfPlus1_evenTest();
    }
}
