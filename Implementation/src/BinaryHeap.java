import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * @author Alan
 *  http://decomplexify.blogspot.com/2014/03/algorithm-binary-heap.html
 */
public class BinaryHeap<T extends Comparable<T>> {

    BinaryHeap() { comparator = null; }
    BinaryHeap(Comparator<T> c) { comparator = c; }
    
    // simple methods
    public T top() { return nodes.get(0); }
    public int size() { return nodes.size(); }
    public boolean isEmpty() { return nodes.isEmpty(); }
    
    // push a new value
    public void push(T value) {
        nodes.add(value);
        moveUp(nodes.size()-1);
    }
    
    // remove top value
    public T pop() {
        if (nodes.isEmpty()) { return null; }       
        T topValue = nodes.get(0);
        
        int lastIndex = nodes.size() - 1;
        nodes.set(0, nodes.get(lastIndex));
        nodes.remove(lastIndex);
        
        System.out.println("before moved down:");
        System.out.println(this);
        
        moveDown(0);
        
        return topValue;
    }
    
    // iteratively swap nodes[i] with its parent
    // until it's not smaller than its parent
    private void moveUp(int i) {
        while (i > 0) {
            int p = (i-1)/2;
         
            if (compareNodes(i, p) >= 0) { break; }
            Collections.swap(nodes, i, p);
            i = p;
          }
    }
    // iteratively swap nodes[i] with a smaller child
    // until it's not larger than than either
    private void moveDown(int i) {
        while (true) {
            // child indices
            int L = 2*i+1, R = 2*i+2;

            if (L >= nodes.size()) {
                // current node is a leaf
                return;
            }
            
            // find out which child is smaller
            int smallerChild = L;
            if (R < nodes.size() && compareNodes(R, L) < 0) {
                smallerChild = R;
            }
            
            // swap with the smaller child if necessary
            if (compareNodes(smallerChild, i) < 0) {
                Collections.swap(nodes, smallerChild, i);
                i = smallerChild;
            } else {
                return;
            }
        } // while
    }
    
    // comparison method for convenience
    private int compareNodes(int i, int j) {
        if (comparator == null) {
            // use default comparator
            return nodes.get(i).compareTo(nodes.get(j));
        } else {
            // use customized comparator
            return comparator.compare(nodes.get(i), nodes.get(j));
        }
    }
    
    private ArrayList<T> nodes = new ArrayList<>();
    private Comparator<T> comparator = null;
    
    public String toString() {
        StringBuffer result = new StringBuffer();
        int limit = 1;
        int count = 0;
        for (T v : nodes) {
            if (count == limit) {
                result.append("\n");
                limit *= 2;
                count = 0;
            }
            result.append(v + " ");
            count++;
        }
        return result.toString();
    }
    
/////////////////////////////////////////////////////////////////////

    private static void pushTest() {
        BinaryHeap<Integer> heap = new BinaryHeap<>();
        int[] values = {1,5,3,7,7,4,6,8,8,8,9,5,5,8,7,9,8,8};
        for (int v : values) {
            heap.nodes.add(v);
        }
        System.out.println("before:");
        System.out.println(heap);

        int newE = 6;
        heap.push(newE);
        System.out.println("pushed " + newE +":");
        System.out.println(heap);        
    }
    
    private static void popTest() {
        BinaryHeap<Integer> heap = new BinaryHeap<>();
        int[] values = {1,5,3,7,7,4,6,8,8,8,9,9,8,8,7,9,8,8};
        for (int v : values) {
            heap.nodes.add(v);
        }
        System.out.println("before:");
        System.out.println(heap);

        heap.pop();
        System.out.println("popped:");
        System.out.println(heap);        
    }
    public static void main(String[] args) {
        // pushTest();
        popTest();
    }

}
