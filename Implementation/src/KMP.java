
/**
 * @author Alan
 * http://decomplexify.blogspot.com/2014/03/kmp-algorithm.html
 */
public class KMP {

    /**
     * Input parameters and local variables may not conform to Java naming
     * convention, but rather correspond to symbols used in the slides
     * 
     * @param q
     *            query
     * @param D
     *            Document
     * @return the starting index of the matched substring, -1 if not found
     */
    public int IndexOfByKMP(String q, String D) {
        int m = q.length();
        int n = D.length();

        // get special cases out of the way
        if (m == 0) { return 0; }
        if (m > n) { return -1; }

        LongestEquifix equiSolver = new LongestEquifix();
        // E[i] holds the length of the longest equifix of q[0..i].
        int[] E = equiSolver.getEquifixLength(q);

        // q[0] will be aligned with D[k]
        int k = 0;

        // the first comparison after realignment will be q[start] vs.
        // D[k+start].
        int start = 0;

        boolean matched = false;
        while (k <= n - m && !matched) {
            int i = start;

            // find first mismatch
            while (i < m && q.charAt(i) == D.charAt(k + i)) { ++i; }

            // done if q matches D[k..k+m)
            if (i == m) { return k; }

            if (i == 0) {
                // special case: mismatch happens at q[0] != D[k]. Shift q by 1.
                k += 1;
                start = 0;
            } else {
                // normal case: we look at the longest equifix of q[0..i).
                int L = i == 0 ? 0 : E[i - 1];

                // see the slides for explanation
                k += i - L;
                start = L;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        String document = "zzabcxyzabc";
        String[] queries = {"bcx", "xyzc"};
        KMP solver = new KMP();
        
        for (String query : queries) {
            int index = solver.IndexOfByKMP(query, document);
            System.out.println(query + " in " + document + "? " + index);
        }
    }

}
