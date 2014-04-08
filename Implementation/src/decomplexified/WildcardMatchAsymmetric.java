package decomplexified;
/**
 * @author Alan
 *  http://decomplexify.blogspot.com/2014/04/wildcard-match-star-and-qmark-asymmetric.html
 */
public class WildcardMatchAsymmetric {
    
    /**
     * Helper: matching with only question marks in t. If t has no no question
     * marks, using KMP is optimal.
     * 
     * @param s
     *            a normal string
     * @param t
     *            a string with possible `?', but no `*'
     * @param start
     *            starting index of s
     * @return whether s starting from `start' matches t
     */
    public static boolean matchedWithQMark(String s, String t, int start) {
        if (start + t.length() > s.length()) { return false; }
        
        for (int j = 0; start+j < s.length() && j<t.length(); ++j) {
            if (s.charAt(start+j) != t.charAt(j) && t.charAt(j) != '?') {
                return false;
            }
        }
        return true;
    }
           
    public boolean matched(String s, String t) {
        // remove stars from t, splitting t into pieces
        String[] P = t.split("\\*+",-1);
        
        // special case: t has no star
        if (P.length == 1) {
            return matchedWithQMark(s, t, 0) &&
                    s.length() == t.length();
        }
        
        String head = P[0];
        String tail = P[P.length - 1];
        
        // special case: can't match both first and last
        // pieces without them overlapping
        if (head.length() + tail.length() > s.length() ||
                !matchedWithQMark(s, head, 0) ||
                !matchedWithQMark(s, tail, s.length() - tail.length())) {
            return false;
        }

        int n = P.length;

        // special case: no more pieces; it's a match
        if (n == 2) { return true; }
        
        // do not match the first piece
        int start = head.length();
        // do not match the last piece
        int sLen = s.length() - tail.length();
        
        // index for the next piece
        int i = 1;
        
        for (; i< P.length - 1; ++i) {
            int tLen = P[i].length();
            boolean found = false;
            while (start <= sLen - tLen) {
                if (matchedWithQMark(s, P[i], start)) {
                    found = true;
                    break;
                }
                start++;
            }
            if (!found) { return false; }

            // if current piece matches s starting at `start',
            // the next piece starts matching at start + tLen
            start += tLen;
        }
        // value of i is the number of pieces matched so far
        return i >= P.length - 1;
    }
     
    public static void matchedWithQMarkTest() {
        String s = "abcdefaabcdefaaa";
        String[] ts = {"ab?def", "a?cd?f", "a??de?", "ab?x?f",
                    "aa", "aaa", "aaaa", ""};

        System.out.println("-----------");
        for (String t : ts) {
            System.out.print(s + " ~ " + t + " --> ");
            for (int i = 0; i < s.length(); ++i) {
                System.out.print(matchedWithQMark(s, t, i)?1:0);
            }
            System.out.println();
        }                
    }

    public static void matchedTest() {
        String[][] sts = { {"aaa", "aa"}, {"aaa", "aaa"}, {"aaa", "aaaa"},
                {"aaa", "a?a"}, {"aaa", "*aa*"}, {"aaa", "*aaa*"}, {"aaa", "*aaaa*"},
                {"abc", "ab*bc"}, {"capqrdxy", "?a*dxy"}, {"capqrdxy", "c*q?z*"},
                {"ab", "*?*?*"}, {"b", "*?*?*"}, {"b", "*?*?"},
                {"abbabaaabbabbaababbabbbbb", "abba*aabba?ba*ab?b?**a?b?b*b*" },
                {"abbabaaabbabbaababbabbbbb", "*abb?ba?ab*bbb*baa?abba?bb*bb" },
                };
        WildcardMatchAsymmetric solver = new WildcardMatchAsymmetric();        

        System.out.println("-----------");
        for (String[] st : sts) {
            String s = st[0];
            String t = st[1];
            System.out.println(s + " ~ " + t + " --> " + solver.matched(s,t));            
        }
    }
        
    public static void main(String[] args) {
        // matchedWithQMarkTest();
        matchedTest();
    }
}
