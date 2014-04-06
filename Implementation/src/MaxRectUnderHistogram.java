
import java.util.Arrays;
import java.util.Stack;

/**
 * @author Alan
 * http://decomplexify.blogspot.com/2014/03/algorithm-max-rectangle-under-histogram.html
 */
public class MaxRectUnderHistogram {
    
    // helper class: representation of a merged bar
    private static class Bar {
        Bar(int h, int w) { height = h; width = w; }
        public int height = 0;
        public int width = 0;
        public int area() { return height * width; }
    }
    
    /**
     * 
     * @param height  array of bar heights
     * @return        area of the largest rectangle
     */
    public int maxRectUnderHistogram(int[] height) {
        int n = height.length;

        // get special case out of the way
        if (n == 0) {
            return 0;
        }

        // merged bars with non-decreasing heights.
        Stack<Bar> bars = new Stack<>();

        int maxArea = 0;
        for (int i = 0; i < n; ++i) {
            // If the stack is empty or the "next" bar is higher,
            // add the next bar directly to the stack.
            if (bars.empty() || bars.peek().height < height[i]) {
                bars.push(new Bar(height[i], 1));
                continue;
            }

            // Add the "current" bar as a potential candidate,
            // then remove it from the stack; it will be
            // trimmed and merged to either the "previous" bar
            // or the "next" bar.
            Bar currentBar = bars.pop();

            // update max_area
            int newArea = currentBar.area();
            if (newArea > maxArea) {
                maxArea = newArea;
            }

            // If there is no previous bar, or the previous bar
            // is lower than the next bar, merge the current
            // bar with the next bar.
            if (bars.empty() || bars.peek().height < height[i]) {
                bars.push(new Bar(height[i], currentBar.width + 1));
            } else {
                // otherwise merge the "current" bar with the previous
                // bar, and do NOT increment i.
                bars.peek().width += currentBar.width;
                --i;
            }
        }

        // The heights of the merged bars are now in increasing
        // order. Check all possible rectangles.
        while (!bars.empty()) {
            Bar cur_bar = bars.pop();
            int newArea = cur_bar.area();
            if (newArea > maxArea) { maxArea = newArea; }
            if (!bars.empty()) { bars.peek().width += cur_bar.width; }
        }

        return maxArea;
    }

    public static void main(String[] args) {
        MaxRectUnderHistogram solver = new MaxRectUnderHistogram();

        int[] height = {1, 3, 5, 4, 2};
        
        // expecting: max {5, 2*4, 3*3, 4*2, 5*1} = 9
        int maxArea = solver.maxRectUnderHistogram(height);
        System.out.println(Arrays.toString(height) + " --> " + maxArea);
        
        // expecting: 5*2 = 10
        height = new int[] {2, 1, 5, 6, 2, 3};
        maxArea = solver.maxRectUnderHistogram(height);
        System.out.println(Arrays.toString(height) + " --> " + maxArea);
        
    }

}
