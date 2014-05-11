package decomplexified;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * @author Alan
 *  http://decomplexify.blogspot.com/2014/03/binary-search-revisited.html
 */
public class BinarySearchPredictable <T extends Comparable<T>> {
    private Comparator<T> comparator;
    public BinarySearchPredictable() { comparator = null; }
    public BinarySearchPredictable(Comparator<T> cmp) { comparator = cmp; }
    
//begin{bstype}
    /* which index to return */
    public enum BSTYPE {
        FAST, // first seen by the algorithm (same as before)
        LEFTMOST, // leftmost possible insertion index
        RIGHTMOST // rightmost possible insertion index
    }
//end{bstype}
    
//begin{bs-predictable}
    /**
     * 
     * @param a
     *            input array
     * @param from
     *            starting index of search range
     * @param limit
     *            ending index of search range (not include)
     * @param query
     *            element to be searched for
     * @param type
     *            specifies which index to return if one or more elements are
     *            found
     * @return (see the comments in BSTYPE)
     */
    public int binarySearchPredictable(T[] a,
            int from, int limit, T query, BSTYPE type) {
        if (from >= limit) {
            return from;
        }

        int mid = (from + limit) / 2;
        int compared = comparator == null ?
                query.compareTo(a[mid]) : comparator.compare(query,  a[mid]);

        if (compared == 0 && type == BSTYPE.LEFTMOST || compared < 0) {
            return binarySearchPredictable(a, from, mid, query, type);
        }

        if (compared == 0 && type == BSTYPE.RIGHTMOST || compared > 0) {
            return binarySearchPredictable(a, mid + 1, limit, query, type);
        }

        // compared == 0 && type == BSTYPE.FAST
        return mid;
    }
//end{bs-predictable}

    public static void main(String[] args) {
        Integer[] a = { 1, 2, 2, 2, 2, 2, 3, 4, 5 };
        System.out.println("a: " + Arrays.toString(a));
        BinarySearchPredictable<Integer> solver = new BinarySearchPredictable<>();

        System.out.println(" ---- natural order ----");
        int query = 2;
        for (BSTYPE type : BSTYPE.values()) {
            int i = solver.binarySearchPredictable(a, 0, a.length, query, type);
            System.out.println(query + " inserted before a[" + i + "]" + a[i]
                    + " " + type);
        }
        
        Comparator<Integer> comparator = Collections.reverseOrder();
        solver = new BinarySearchPredictable<>(comparator);

        System.out.println(" ---- reverse order ----");
        Integer[] b = { 5, 4, 3, 2, 2, 2, 2, 2, 1};
        System.out.println("b: " + Arrays.toString(b));
        for (BSTYPE type : BSTYPE.values()) {
            int i = solver.binarySearchPredictable(b, 0, b.length, query, type);
            System.out.println(query + " inserted before b[" + i + "]" + b[i]
                    + " " + type);
        }
    }

}
