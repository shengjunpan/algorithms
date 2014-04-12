package decomplexified;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author Alan
 * http://decomplexify.blogspot.com/2014/03/algorithm-median-of-sorted-array-with.html
 */
public class SortedArrayPlus2 {
//begin{middle2def-helper}        
    /**
     * helper: indexing an array plus 2 new elements, but without actually
     * inserting them
     * 
     * @param a
     *            a sorted array
     * @param i
     *            index of the new array
     * @param e1, e2
     *            new elements
     * @param j1, j2
     *            insertion indices of the original array
     * @return b[i], where b is the new array obtained by inserting element e1
     *         right before a[j1], and inserting e2 right before a[j2].
     */
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
//end{middle2def-helper}        

//begin{middle2declared}
//begin{middle2def}
    /**
     * Find the middle element(s) of a sorted array plus two new elements
     * 
     * @param a  sorted array
     * @param e1, e2  new element
     * @return the middle element if new array size is odd, or the middle two
     *         elements if new array size even
     */
    public <T extends Comparable<T>>
    ArrayList<T> middleOfPlus2(T[] a, T e1, T e2) {
//end{middle2declared}            
        // first find where to insert the new elements
        int j1 = Arrays.binarySearch(a, e1);
        if (j1 < 0) { j1 = -(j1+1); }
        int j2 = Arrays.binarySearch(a, e2);
        if (j2 < 0) { j2 = -(j2+1); }
        /*
        // Alternative use binarySearch defined in this package:
        BinarySearch bsSolver = new BinarySearch();
        int j1 = bsSolver.binarySearch(a, 0, a.length, e1);
        int j2 = bsSolver.binarySearch(a, 0, a.length, e2);
        */
        
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
//end{middle2def}
    
////////////////////////////////////////////////////////////////////////////
    
    // test helper
    private static ArrayList<Integer> mergeAndSort(Integer[] a, Integer e1, Integer e2) {
        ArrayList<Integer> newA = new ArrayList<>(Arrays.asList(a));
        newA.add(e1);
        newA.add(e2);
        Collections.sort(newA);
        return newA;
    }
    
    private static void element2_test() {
        Integer[] a = {0, 10, 20, 30, 40};       
        Integer e1 = 15;
        Integer e2 = 35;
        int j1 = 2;
        int j2 = 4;

        ArrayList<Integer> newA = mergeAndSort(a, e1, e2);
        System.out.println(Arrays.toString(a)+" + {"+e1+","+e2+"} --> "+newA.toString());
        
        for (int i = 0; i < a.length+2; ++i) {
            System.out.println("newA["+i+"]"+newA.get(i)+" == "+element2(a,i,e1,j1,e2,j2)+"?");
        }        
    }
    
    private static void middleOfPlus2_oddTest() {
        Integer[] a = {0, 10, 20};
        Integer[] eList = {-5, 5, 15, 25};
        SortedArrayPlus2 solver = new SortedArrayPlus2();

        System.out.println("--------------");
        for (int i1 = 0; i1 < 4; ++i1) {
            Integer e1 = eList[i1];
            for (int i2 = i1; i2 < 4; ++i2) {
                Integer e2 = eList[i2];
                ArrayList<Integer> newA = mergeAndSort(a, e1, e2);
                ArrayList<Integer> middles = solver.middleOfPlus2(a, e1, e2);
                System.out.println(Arrays.toString(a)+" + {"+e1+","+e2+"} --> "
                        +newA.toString()+" --> middle "+middles.toString());
            }
        }    
    }

    private static void middleOfPlus2_evenTest() {
        Integer[] a = {0, 10};
        Integer[] eList = {-5, 5, 15, 25};
        
        SortedArrayPlus2 solver = new SortedArrayPlus2();
        
        System.out.println("--------------");
        for (int i1 = 0; i1 < eList.length; ++i1) {
            Integer e1 = eList[i1];
            for (int i2 = i1; i2 < eList.length; ++i2) {
                Integer e2 = eList[i2];
                ArrayList<Integer> newA = mergeAndSort(a, e1, e2);
                ArrayList<Integer> middles = solver.middleOfPlus2(a, e1, e2);
                System.out.println(Arrays.toString(a)+" + {"+e1+","+e2+"} --> "
                        +newA.toString()+" --> middle "+middles.toString());
            }
        }    
    }

    public static void main(String[] args) {
        element2_test();
        middleOfPlus2_oddTest();
        middleOfPlus2_evenTest();
    }
}
