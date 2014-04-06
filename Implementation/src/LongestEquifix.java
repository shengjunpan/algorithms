
/**
 * @author Alan
 *  http://decomplexify.blogspot.com/2014/03/longest-equifix.html
 */
public class LongestEquifix {
    /**
     * 
     * @param s
     *            input string
     * @return an array holding lengths of equifices of all prefices of s.
     */
    public int[] getEquifixLength(String s) {
        if (s.isEmpty()) {
            return null;
        }

        int n = s.length();

        // equifixLength[i] will hold the length of the longest equifix of
        // s[0..i]
        int[] equifixLength = new int[n];

        // initialization: equifix of s[0] is empty, not itself;
        // an equifix can not be the whole string
        equifixLength[0] = 0;

        for (int i = 1; i < n; ++i) {
            // start with the longest equifix of s[0..i-1]
            int nk = equifixLength[i - 1];

            while (true) {
                // found
                if (s.charAt(nk) == s.charAt(i)) {
                    equifixLength[i] = nk + 1;
                    break;
                }

                // not found. Checking if nk == 0 must be after,
                // NOT before, checking s[nk] == si[i]
                if (nk == 0) {
                    equifixLength[i] = 0;
                    break;
                }

                // recurse on equifix of the equifix
                nk = equifixLength[nk - 1];
            } // while
        } // for
        return equifixLength;
    }
 
    public static void main(String[] args) {
        LongestEquifix solver = new LongestEquifix();
        String[] strings = { "abcxyzabc", "abcabca" };
        for (String s : strings) {
            System.out.println("---------");
            int[] lengths = solver.getEquifixLength(s);
            for (int i = 0; i < lengths.length; ++i) {
                System.out.println(s.substring(0, i+1) + " --> "
                        + s.substring(0, lengths[i]));
            } // for i
        } // for s
    }
}
