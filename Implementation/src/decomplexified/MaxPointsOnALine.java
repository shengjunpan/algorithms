package decomplexified;

import java.util.Arrays;
import java.util.HashMap;

/**
 * @author Alan
 *  http://decomplexify.blogspot.com/2014/05/max-points-on-line.html
 */
public class MaxPointsOnALine {

    private static class Point {
        int x;
        int y;
        Point() { x = 0; y = 0; }
        Point(int a, int b) { x = a; y = b; }
    }
    
//begin{gcd}    
    /** find the greatest common divisor of two integers.
     * if both are 0, then return 0. Otherwise, the returned
     * value is always positive.
     */
    private static int gcd(int x, int y) {
//end{gcd}    

        if (x < 0) { x = -x; }
        if (y < 0) { y = -y; }
        if (x == 0) { return y; }
        if (y == 0) { return x; }        
        if (x < y) { int tmp = x; x = y; y = tmp; }        
        return gcd(y, x % y);        
    }
    
//begin{line}    
    /** represent a line in the 2D plane as a triple of
     * integers [a,b,c], corresponding to ax+by=c. For
     * uniqueness, gcd(a,b,c) is always 1. Furthermore,
     * the first non-zero number is always positive.
     */
    private static class Line {
//end{line}
        
        private int[] coeff = new int[3];
        
        public Line(Point p1, Point p2) {
            coeff[0] = p2.y - p1.y;
            coeff[1] = p1.x - p2.x;
            coeff[2] = p1.x * p2.y - p2.x * p1.y;
            int d = gcd(gcd(coeff[0], coeff[1]), coeff[2]);
            // make sure the first non-zero number is positive
            if (coeff[0] < 0 || coeff[0] == 0 && coeff[1] < 0
                    || coeff[0] == 0 && coeff[1] == 0 && coeff[2] < 0) {
                d = -d;
            }
            // make sure the gcd is 1 (unless all numbers are 0).
            if (d != 0) {
                coeff[0] /= d;
                coeff[1] /= d;
                coeff[2] /= d;
            }
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(coeff);
        }
        
        @Override
        public boolean equals(Object l2) {
            Line line2 = (Line) l2;
            return Arrays.equals(coeff, line2.coeff);
        }
        
        @Override
        public String toString() {
            return Arrays.toString(coeff);
        }        
    }

//begin{max-points}    
    public int maxPoints(Point[] points) {
        int maxNum = 0;
        
        // for each point, check each line that passes through it.
        // for each pair of points points[i] and points[j], we
        // only need to check once the line that passes through them,
        // thus we only need to look at pairs where i < j.
        for (int i = 0; i < points.length; ++i) {            
            // if the number of remaining points is smaller than maxNum,
            // there is no need to continue.
            if (points.length - (i+1) < maxNum) { break; }
            
            // mapping: line (passing through points[i]) --> count
            HashMap<Line, Integer> lines = new HashMap<>();
            
            // number of times points[i] repeats
            int numRepeat = 1;
            
            for (int j = i+1; j < points.length; ++j) {
                
                // repeated point
                if (points[i].x == points[j].x && points[i].y == points[j].y) {
                    numRepeat++;
                    continue;
                }
                
                // create a line and update its count
                Line line = new Line(points[i], points[j]);
                int n = lines.containsKey(line) ? (lines.get(line)+1) : 1;
                lines.put(line, n);
            }
            
            if (lines.isEmpty()) {
                // special case: all remaining points are the same as points[i]
                if (maxNum < numRepeat) {
                    maxNum = numRepeat;
                }
            } else {
                // find the max count
               for (Integer count : lines.values()) {
                   if (maxNum < count + numRepeat) {
                       maxNum = count + numRepeat;
                   }
               }
            }
        }        
        return maxNum;
    }
//end{max-points}    
    
    
/////////////////////////////////////////////////////////////////
    private static void testGcd() {
        int[][] xys = {{0,0}, {0,4}, {3,5}, {6,8}, {8,6}, {-6,8}, {-8,6}, {-6,-8}};
        for (int[] xy : xys) {
            int x = xy[0];
            int y = xy[1];
            System.out.println("gcd(" + x + "," + y + ")=" + gcd(x,y));
        }
    }

    private static void testLine() {
        Point[][] points = new Point[][] {
                new Point[] {new Point(0,0), new Point(0,0)},
                new Point[] {new Point(1,2), new Point(1,2)},                
                new Point[] {new Point(0,0), new Point(0,2)},
                new Point[] {new Point(0,0), new Point(2,0)},
                new Point[] {new Point(2,0), new Point(0,0)},                
                new Point[] {new Point(1,2), new Point(3,4)},
                new Point[] {new Point(3,4), new Point(1,2)},                
                };
        for (Point[] pointPair : points) {
            Line line = new Line(pointPair[0], pointPair[1]);
            System.out.println(pointPair[0] + ", " + pointPair[1] + " --> " + line);
        }                
    }
    
    public static void main(String[] args) {
        testGcd();
        testLine();
    }

}
