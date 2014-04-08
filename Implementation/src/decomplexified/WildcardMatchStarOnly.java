package decomplexified;
/**
 * @author Alan
 *  http://decomplexify.blogspot.com/2014/03/algorithm-string-matching-with-wild.html
 */
public class WildcardMatchStarOnly {
    
    public boolean matched(String s, String t) {
        int m = s.length();
        int n = t.length();
        
        // a moving row staring from the bottom
         boolean[] H = new boolean[n+1];

         // initialize bottom row
        H[n] = true;
        for (int j=n-1; j>=0; --j) {
            H[j] = t.charAt(j) == '*' && H[j+1];
            if (!H[j]) break;
        }
        
        // fill up the matrix
        for (int i = m - 1; i >= 0; --i) {
            // Hj will be the old H[j+1]
            boolean Hj = H[n];
            // update the right most cell
            H[n] &= s.charAt(i) == '*';
            for (int j = n - 1; j >= 0; --j) {
                char si = s.charAt(i);
                char tj = t.charAt(j);
                boolean newHj = si == tj && si != '*' && Hj ||
                        (si == '*' || tj == '*') && (H[j] || H[j+1]);
                Hj = H[j];
                H[j] = newHj; 
            } // for j
        } // for i
        return H[0];
    }
    
    public static void main(String[] args) {
        String s = "abcd";
        String t1 = "*b*d";
        String t2 = "a*e*";
        WildcardMatchStarOnly solver = new WildcardMatchStarOnly();
        
        System.out.println(s + " ~ " + t1 + " --> " + solver.matched(s,t1));
        System.out.println(s + " ~ " + t2 + " --> " + solver.matched(s,t2));
    }
}
