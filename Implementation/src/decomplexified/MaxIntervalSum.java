package decomplexified;
import java.util.Arrays;

/**
 * @author Alan
 *  http://decomplexify.blogspot.com/2014/03/algorithm-max-interval-sum.html
 */
public class MaxIntervalSum {
    /**
     * helper class representing an interval
     * @author Alan
     *
     */
//begin{max-interval-helper}
    public static class Interval {
        Interval(int f, int t, int s) {
            from = f;
            to = t;
            sum = s;
        }

        public int from;
        public int to; // exclusive
        public int sum;

        public void copyFrom(Interval interval2) {
            from = interval2.from;
            to = interval2.to;
            sum = interval2.sum;
        }
        
        public String toString() { return "["+from+","+to+") "+sum; }
    }
//end{max-interval-helper}
    
//begin{max-interval}
    public Interval maxIntervalSum(int[] a, boolean allowEmpty) {
        // global max interval
        Interval result;
        
        // max interval ending at current index
        Interval current;
        
        if (allowEmpty) {
            result = new Interval(0,0,0);
            current = new Interval(0,0,0);
        } else {
            result = new Interval(0,0,Integer.MIN_VALUE);
            current = new Interval(0,0,Integer.MIN_VALUE);
        }
        
        for (int i=0; i<a.length; ++i) {
            if (current.sum < 0) {
                // if the previous sum is negative, it's better to use
                // a[i] itself as the interval ending at a[i].
                current.sum = a[i];
                current.from = i;
            } else {
                // Otherwise extend the interval ending at a[i-1]
                // to include a[i], but starting index remains
                current.sum += a[i];
            }
            current.to = i+1;
            // update the global max
            if (result.sum < current.sum) {
                result.copyFrom(current);
            }
        } // for
        return result;
    }
//end{max-interval}
    
    public static void main(String[] args) {
        int[][] arrays = {{}, {0}, {1}, {-1}, {-2,1,-3,4,-1,2,1,-5,4}};
        MaxIntervalSum solver = new MaxIntervalSum();
        for (int[] a : arrays) {
            Interval result = solver.maxIntervalSum(a, false);
            System.out.println(Arrays.toString(a) + " --> " + result);
        }

    }

}
