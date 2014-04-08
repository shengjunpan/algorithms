package decomplexified;
/**
 * @author Alan
 *  http://decomplexify.blogspot.com/2014/03/algorithm-edit-distance.html
 */
public class EditDistance {
    
    public int editDistance(String s, String t) {
        int m = s.length();
        int n = t.length();
        
        // a moving row staring from the bottom
        int[] H = new int[n+1];
        
        // initialize bottom row
        for (int j=0; j<n+1; ++j) { H[j] = n - j; }
        
        for (int i=m-1; i>=0; --i) {
            // Hj will be the old H[j+1]
            int Hj = H[n];
            // update the right most cell
            H[n]++;
            for (int j=n-1; j>=0; --j) {
                int newHj = Math.min(
                        Hj + (s.charAt(i) == t.charAt(j) ? 0 : 1),
                        Math.min(H[j] + 1, H[j+1] + 1));
                Hj = H[j];
                H[j] = newHj;
            }
        }
        return H[0];
    }

    public static void main(String[] args) {
        String s = "simple";
        String t = "smpel";
        EditDistance solver = new EditDistance();
        
        int d = solver.editDistance(s, t); 
        System.out.println(s + " ~ " + t + " --> " + d);
    }

}
