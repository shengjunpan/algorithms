package decomplexified;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * @author Alan
 *  http://decomplexify.blogspot.com/2014/03/algorithm-binary-heap.html
 */
//begin{binary-heap}
public class BinaryHeap<T extends Comparable<T>> {
    protected ArrayList<T> nodes = new ArrayList<>();
    protected Comparator<T> comparator = null;

    BinaryHeap() { comparator = null; }
    BinaryHeap(Comparator<T> c) { comparator = c; }
    
    // simple methods
    public T top() { return nodes.get(0); }
    public int size() { return nodes.size(); }
    public boolean isEmpty() { return nodes.isEmpty(); }
    
    // push a new value
    public void push(T value) {
        nodes.add(value);
        
        // move the last node up its proper position
        Integer i = nodes.size()-1;
        while ( i != null ) {
            i = swapWithParent(i);
        }
    }
    
    // remove top value
    public T pop() {
        if (nodes.isEmpty()) { return null; }       
        T topValue = nodes.get(0);

        // put the value of the last node inside the
        // root node then delete the last node
        int lastIndex = nodes.size() - 1;
        nodes.set(0, nodes.get(lastIndex));
        nodes.remove(lastIndex);
        
        // move the root down to its proper position
        Integer i = 0;
        while ( i != null ) {
            i = swapWithSmallerChild(i);
        }
        
        return topValue;
    }
    
    /**
     * helper: swap a node with its parent if parent is larger
     * @param i index of current node
     * @return index of parent if swapped, null otherwise
     */
    protected Integer swapWithParent(int i) {
        int p = (i-1)/2;
        if (p >=0 && compareNodes(p, i) > 0) {
            Collections.swap(nodes, i, p);
            return p;
        }
        return null;
    }

    /**
     * helper: swap a node with its smaller child.
     * @param i index of current node
     * @return index of smaller child if swapped, -1 otherwise
     */
    protected Integer swapWithSmallerChild(int i) {
        // child indices
        int L = 2*i+1;
        if (L >= nodes.size()) {
            // current node is a leaf
            return null;
        }

        // find out which child is smaller
        int smallerChild = L;        
        int R = L + 1;
        if (R < nodes.size() && compareNodes(L, R) > 0) {
                smallerChild = R;
        }

        // swap with the smaller child if necessary
        if (compareNodes(i, smallerChild) > 0) {
            Collections.swap(nodes, smallerChild, i);
            return  smallerChild;
        }
        return null;
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
//end{binary-heap}
   
    /**
     * print nodes by level, mainly for debugging purpose
     */
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
        pushTest();
        popTest();
    }

}
