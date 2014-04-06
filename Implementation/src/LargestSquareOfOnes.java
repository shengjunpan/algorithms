
/**
 * @author Alan
 *  http://decomplexify.blogspot.com/2014/03/largest-square-of-ones.html
 */
public class LargestSquareOfOnes {

    /**
     * 
     * @param A
     *            a 2D matrix of '0's and '1's.
     * @return the side of the largest square of '1's.
     */
    public int largestSquareOfOnes(char[][] A) {
        int m = A.length; // rows
        int n = A[0].length; // columns

        // allocate memory for auxiliary matrices
        int[] V = new int[n];
        int[] S = new int[n];

        // side of largest square of ones, to be returned
        int maxSide = 0;

        // Initialize (imaginary) row -1
        for (int j = 0; j < n; ++j) {
            V[j] = 0;
            S[j] = 0;
        }

        for (int i = 0; i < m; ++i) {
            // Initialization
            int H = 0; // matrix notation: H[i][-1] (outside)
            int Sj = 0; // matrix notation: S[i][-1] (outside)

            for (int j = 0; j < n; ++j) {
                // update H
                H = (A[i][j] == '1') ? (H + 1) : 0;

                // update V
                V[j] = (A[i][j] == '1') ? (V[j] + 1) : 0;

                // update S
                int newSj = Math.min(Sj + 1, Math.min(H, V[j]));
                Sj = S[j]; // save old value for next iteration
                S[j] = newSj;

                // update side of max square
                if (maxSide < S[j]) {
                    maxSide = S[j];
                }
            } // for j
        } // for i
        return maxSide;
    }

    public static void main(String[] args) {
        char[][] A = { {'0','1','1','0','1','1','0','0'},
                          {'0','0','1','1','1','0','1','1'},
                          {'1','1','1','1','1','1','1','0'},
                          {'0','0','1','1','1','1','0','0'}};
        LargestSquareOfOnes solver = new LargestSquareOfOnes();
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 8; ++j) {
                System.out.print(A[i][j] + " ");
            }
            System.out.println();
        }
        int max_side = solver.largestSquareOfOnes(A);
        System.out.println("Max square side: " + max_side);
    }
}
