package decomplexified;

import java.util.Arrays;

/**
 * @author Alan
 *  http://decomplexify.blogspot.com/2014/05/valid-number.html
 */
public class ValidNumber {

//begin{real}    
    private static class Real {
        private String sign = "";
        private String integerPart = "";
        private String decimalPoint = "";
        private String fractionPart = "";
        
        public int read(String s, int i) {
            sign = integerPart = decimalPoint = fractionPart = "";
            int n = s.length();
            
            // scan for sign
            if (i < n && (s.charAt(i) == '+' || s.charAt(i) == '-')) {
                sign = Character.toString(s.charAt(i++));         
            }
            if (i >= n) { return i; }

            // scan for integral part
            StringBuilder integral = new StringBuilder();
            while (i < n && s.charAt(i) >= '0' && s.charAt(i) <= '9') {
                integral.append(s.charAt(i++));
            }
            integerPart = integral.toString();
            
            // scan for decimal point
            if (i < n && s.charAt(i) == '.') {
                decimalPoint = ".";
                i++;
            }

            // scan for decimal part
            StringBuilder fraction = new StringBuilder();
            while (i < n && s.charAt(i) >= '0' && s.charAt(i) <= '9') {
                fraction.append(s.charAt(i++));
            }
            fractionPart = fraction.toString();
            
            return i;            
        }

        public String toString() {
            String[] allParts = {sign, integerPart, decimalPoint, fractionPart};
            return Arrays.toString(allParts);
        }
     
        public boolean isEmpty() {
            return sign.isEmpty() && integerPart.isEmpty() &&
                    decimalPoint.isEmpty() && fractionPart.isEmpty();
        }
        
        /*
         * a "number" is valid only when
         * (1) if there is no decimal point, then there must
         *     be either an integer part or fraction part,
         * (2) if there is a decimal point, then the integer
         *     part must exist and fraction part must not.
         */
        public boolean isValid() {
         return !decimalPoint.isEmpty() && (!integerPart.isEmpty() || !fractionPart.isEmpty()) ||
                 decimalPoint.isEmpty() && !integerPart.isEmpty() && fractionPart.isEmpty(); 
        }

        /*
         * a "number" is an integer if it's valid and there is
         * no decimal point or fraction part
         */
        public boolean isInteger() {
            return isValid() && decimalPoint.isEmpty() && fractionPart.isEmpty();
       }        
    }
//end{real}    

//begin{is-number}    
    public boolean isNumber(String s) {
        int n = s.length();
        Real significant = new Real();
        String e = "";
        Real exponent = new Real();

        // skip leading spaces
        int i = 0;
        while (i < n && s.charAt(i) == ' ') {++i;}

        // read significant part
        i = significant.read(s, i);
        
        // read symbol e
        if (i < n && (s.charAt(i) == 'e' || s.charAt(i) == 'E')) {
            e = "e";
            ++i;
        }
        
        // read exponent
        i = exponent.read(s, i);
        while (i < n && s.charAt(i) == ' ') {++i;}
        
        return i >= n && significant.isValid() &&
                (e.isEmpty() && exponent.isEmpty() ||
                !e.isEmpty() && exponent.isInteger());
    }
//end{is-number}
    
///////////////////////////////////////////////////////////////
    private static void testReal() {
        String[] strs = {"", "3", "x", "1.", ".1", "1x", "1.23x", "-1x", "+1.23.", "+.", "2e10"};
        for (String str : strs) {
            Real r= new Real();
            int i = r.read(str, 0);
            System.out.println(str + " --> " + r + ", " + str.substring(i)
                    + " --> " + r.isValid());
        }
    }
    
    private static void testIsNumber() {
        ValidNumber solver = new ValidNumber();
        String[] inputs = {"0", " 0.1 ", "abc", "1 a", "0e3", " 4e3.", "2e10"};
        for (String input : inputs) {
            System.out.println(input + " --> " + solver.isNumber(input));
        }
    }
    
    public static void main(String[] args) {
        testReal();
        testIsNumber();
    }

}
