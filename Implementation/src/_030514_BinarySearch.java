
import java.util.Arrays;

/**
 * @author Alan
 * http://decomplexify.blogspot.com/2014/03/algorithm-binary-search.html
 */
public class _030514_BinarySearch {
    /**
     * 
     * @param a
     *            input array
     * @param from
     *            starting index
     * @param limit
     *            ending index (not included)
     * @param query
     *            element to be searched for
     * @return index where query can be inserted in order
     */
    public <T extends Comparable<T>>
    int binarySearch(T[] a, int from, int limit, T query) {
        if (from >= limit) {
            return from;
        }

        int mid = (from + limit) / 2;
        int compared = query.compareTo(a[mid]);

        if (compared == 0) {
            // return the index once it's found
            return mid;
        } else if (compared < 0) {
            // recursively search the left half
            return binarySearch(a, from, mid, query);
        } else {
            // recursively search the right half
            return binarySearch(a, mid + 1, limit, query);
        }
    }

    public static void main(String[] args) {
        _030514_BinarySearch solver = new _030514_BinarySearch();
        
        Integer[] a = { 0, 2, 4, 6, 8 };
        System.out.println("a: " + Arrays.toString(a));

        int query = 2;
        int index = solver.binarySearch(a, 0, 5, query);
        System.out.println(query + " would be inserted at index " + index);

        query = 7;
        index = solver.binarySearch(a, 0, 5, query);
        System.out.println(query + " would be inserted at index " + index);
    }
}
