package decomplexified;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * @author Alan
 *  http://decomplexify.blogspot.com/2014/02/algorithm-longest-increasing-subsequence.html
 */
public class LongestIncreasingSequence {
//begin{lis-length}
    public <T extends Comparable<T>> int LISLength(T[] a) {
        if (a.length == 0) { return 0; }
        
        ArrayList<T> savedNodes = new ArrayList<>();
        int n = a.length;
        for (int i=0; i<n; ++i) {
            int j = Collections.binarySearch(savedNodes, a[i]);
            if (j < 0) { j = - (j+1); }
            if (j == savedNodes.size()) {
                // add a new level
                savedNodes.add(a[i]);
            } else {
                // attach a[i] to the node at (j-1)-th level,
                // replacing the node at j-th level.
                savedNodes.set(j,  a[i]);
            }
        }
        return savedNodes.size();
    }
//end{lis-length}
    
//begin{lis}    
    public <T extends Comparable<T>> int[] LIS(final T[] a) { 
        if (a.length == 0) { return new int[0]; }

        // we save the indices instead of the values so that
        // we can do backtracking
        ArrayList<Integer> savedIndices = new ArrayList<>();

        int n = a.length;
        // the savedIndices don't necessary from a path, so we need
        // to remember the parents of ALL nodes for backtracking
        int[] parents = new int[n];

        // helper: compare two indices by their corresponding values in a.
        // If not using a comparator, we could alternatively use two
        // arrays to save both the values and indices of the nodes.
        Comparator<Integer> comparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer i1, Integer i2) {
                return a[i1].compareTo(a[i2]);
            }
        };
        
        for (int i=0; i<n; ++i) {
            int j = Collections.binarySearch(savedIndices, i, comparator);
            if (j < 0) { j = - (j+1); }
            if (j == savedIndices.size()) {
                // add a new level
                savedIndices.add(i);
            } else {
                // attach a[i] to the node at (j-1)-th level,
                // replacing the node at j-th level.
                savedIndices.set(j,  i);
            }
            // set the parent of the new/updated node
            parents[i] = j == 0 ? -1 : savedIndices.get(j-1);
        }
        
        // backtracking from the last saved node
        int m = savedIndices.size();
        int[] pathIndices = new int[m];
        pathIndices[m-1] = savedIndices.get(m-1);
        for (int j=m-2; j>=0; --j) {
            pathIndices[j] = parents[pathIndices[j+1]];
        }
        return pathIndices;
    }
//end{lis}

    public static void main(String[] args) {
        Integer[] a= {2,1,5,6,4,3};
        LongestIncreasingSequence solver = new LongestIncreasingSequence();

        System.out.println("a: " + Arrays.toString(a));
        System.out.println("length of LIS: " + solver.LISLength(a));

        int[] pathIndices = solver.LIS(a);
        int m = pathIndices.length;
        Integer[] path = new Integer[m];
        for (int j=0; j<m; ++j) {
            path[j] = a[pathIndices[j]];
        }

        System.out.println("LIS: " + Arrays.toString(path));

    }

}
