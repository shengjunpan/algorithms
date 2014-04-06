/**
 * @author Alan
 *  http://decomplexify.blogspot.com/2014/03/largest-square-of-ones.html
 */
public class LargestRectOfOnes {
    /* Auxiliary class representing a rectangle */
    private static class Rectangle {
        Rectangle(int w, int h) {
            width = w;
            height = h;
        }
        public int width = 0;
        public int height = 0;
        public int area() { return width * height; }
    }
    
    /**
     * 
     * @param A
     *            a 2D matrix of '0's and '1's.
     * @return the area of the largest square of '1's.
     */
    public int largestRectOfOnes(char[][] A) {
        int m = A.length; // rows
        if (m == 0) { return 0; }

        int n = A[0].length; // columns
        if (n == 0) { return 0; }
        
        // allocate memory for auxiliary matrices
        int[] V = new int[n];
        Rectangle[] S = new Rectangle[n];

        // area of largest rectangle of ones, to be returned
        int maxArea = 0;

        // Initialize (imaginary) row -1
        for (int j = 0; j < n; ++j) {
            V[j] = 0;
            S[j] = new Rectangle(0,0);
        }

        for (int i = 0; i < m; ++i) {
            // Initialization
            int H = 0; // i.e., H[i][-1] (outside)
            Rectangle Sj = new Rectangle(0,0); // i.e., S[i][-1] (outside)

            for (int j = 0; j < n; ++j) {
                // update H
                H = (A[i][j] == '1') ? (H + 1) : 0;

                // update V
                V[j] = (A[i][j] == '1') ? (V[j] + 1) : 0;

                // update S
                int newWidth = Math.min(Sj.width + 1, H);
                int newHeight = Math.min(Sj.height + 1, V[j]);
                Rectangle newSj = new Rectangle(newWidth, newHeight);
                
                // the horizontal bar by itself may be larger
                if (newSj.area() < H) { newSj = new Rectangle(H, 1); }
                
                // the vertical bar by itself may be larger
                if (newSj.area() < V[j]) { newSj = new Rectangle(1, V[j]); }

                Sj = S[j]; // save old value for next iteration
                S[j] = newSj;

                // update side of max square
                int currentArea = S[j].area();
                if (maxArea < currentArea) {
                    maxArea = currentArea;
                }
            } // for j
        } // for i
        return maxArea;
    }

    public static void main(String[] args) {
        char[][] A = { {'0','1','1','0','1','1','0','0'},
                          {'0','0','1','1','1','0','1','1'},
                          {'1','1','1','1','1','1','1','0'},
                          {'0','0','1','1','1','1','1','0'}};
        LargestRectOfOnes solver = new LargestRectOfOnes();
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 8; ++j) {
                System.out.print(A[i][j] + " ");
            }
            System.out.println();
        }
        int maxArea = solver.largestRectOfOnes(A);
        System.out.println("Max rectangle area: " + maxArea);
    }
}
