import java.util.HashMap;
import util.WeightedValue;

/**
 * @author Alan
 *  http://decomplexify.blogspot.com/2014/03/algorithm-priority-queue.html
 */
public class BinaryPriorityQueue<T,W extends Comparable<W>>
        extends BinaryHeap<WeightedValue<T,W>> {

    private HashMap<T, Integer> valueLocation = new HashMap<>();
    
    /**
     * Insert a value by an associated priority,
     * or update its priority if it exists already
     * @param value
     * @param priority
     * @return whether the value was inserted or updated 
     */
    public boolean push(T value, W priority) {
        boolean existed = valueLocation.containsKey(value);
        push(new WeightedValue<>(value, priority));
        return existed;
    }
    
    /**
     * Override the parent method `push'
     */
    @Override
    public void push(WeightedValue<T,W> vp) {       
        // find the index of the value to be inserted
        Integer i = valueLocation.get(vp.value);
        
        if (i == null) {
            // if it's a new value add a new node
            nodes.add(vp);
            i = nodes.size() - 1;
            valueLocation.put(vp.value, i);
        } else {
            // if it exists, reset its priority
            nodes.set(i, vp);

            // attempt to move it down first. Note that we don't
            // use i!=null for while-condition so as to keep i
            // in case no swap is done.
            while (true) {
                Integer p = swapWithSmallerChild(i);
                if (p == null) { break; }
                valueLocation.put(nodes.get(p).value, p);
                valueLocation.put(nodes.get(i).value, i);
                i = p;
            }
        }

        // move the node up to its proper location
        while (i != null) {
            Integer p = swapWithParent(i);
            if (p != null) {
                valueLocation.put(nodes.get(p).value, p);
                valueLocation.put(nodes.get(i).value, i);
            }
            i = p;
        }
    }
    
    // remove top value
    public WeightedValue<T,W> pop() {
        if (nodes.isEmpty()) { return null; }       
        WeightedValue<T,W> topValue = nodes.get(0);

        // put the value of the last node inside the
        // root node then delete the last node
        int lastIndex = nodes.size() - 1;
        nodes.set(0, nodes.get(lastIndex));
        nodes.remove(lastIndex);
        
        // move the root down to its proper position
        Integer i = 0;
        while ( i != null) {
            Integer p = swapWithSmallerChild(i);
            if (p != null ) {
                valueLocation.put(nodes.get(p).value, p);
                valueLocation.put(nodes.get(i).value, i);
            }
            i = p;
        }
        
        return topValue;
    }

/////////////////////////////////////////////////////////////////////

    private static void pushNewTest() {
        BinaryPriorityQueue<String, Integer> pQueue = new BinaryPriorityQueue<>();
        String[] values = {"1","5","3","7a","7b","4","6","8a","8b","8c",
                            "9a","5a","5b","8d","7c","9b","8e","8f"};
        Integer[] priorities = {1, 5, 3, 7, 7, 4, 6, 8, 8, 8, 9, 5, 5, 8, 7, 9, 8, 8};
        for (int i =0; i < values.length; ++i) {
            pQueue.nodes.add(new WeightedValue<String, Integer>(values[i], priorities[i]));
            pQueue.valueLocation.put(values[i], i);
        }

        System.out.println("---------------");
        System.out.println("before:");
        System.out.println(pQueue);

        String newValue = "6b";
        int newPriority = 6;
        pQueue.push(newValue, newPriority);
        System.out.println("pushed " + newValue + " with priority " + newPriority);
        System.out.println(pQueue);
    }

    private static void pushExistedTest() {
        BinaryPriorityQueue<String, Integer> pQueue = new BinaryPriorityQueue<>();
        String[] values = {"1","5","3","7a","7b","4","6","8a","8b","8c",
                            "9a","5a","5b","8d","7c","9b","8e","8f"};
        Integer[] priorities = {1, 5, 3, 7, 7, 4, 6, 8, 8, 8, 9, 5, 5, 8, 7, 9, 8, 8};
        for (int i =0; i < values.length; ++i) {
            pQueue.nodes.add(new WeightedValue<String, Integer>(values[i], priorities[i]));
            pQueue.valueLocation.put(values[i], i);
        }
        System.out.println("---------------");
        System.out.println("before:");
        System.out.println(pQueue);

        String newValue = "1"; // actually old
        int newPriority = 7;
        pQueue.push(newValue, newPriority);
        System.out.println("reset " + newValue + " with priority " + newPriority);
        System.out.println(pQueue);

        newPriority = 1;
        pQueue.push(newValue, newPriority);
        System.out.println("reset " + newValue + " with priority " + newPriority);
        System.out.println(pQueue);

    }

    private static void popTest() {
        BinaryPriorityQueue<String, Integer> pQueue = new BinaryPriorityQueue<>();
        String[] values = {"1","5","3","7a","7b","4","6","8a","8b","8c",
                           "9a","9b","8d","8e","7c","9c","8f","8g"};
        Integer[] priorities = { 1, 5, 3, 7, 7, 4, 6, 8, 8, 8, 9, 9, 8, 8, 7, 9, 8, 8 };
        for (int i =0; i < values.length; ++i) {
            pQueue.nodes.add(new WeightedValue<String, Integer>(values[i], priorities[i]));
            pQueue.valueLocation.put(values[i], i);
        }

        System.out.println("---------------");
        System.out.println("before:");
        System.out.println(pQueue);

        pQueue.pop();
        
        System.out.println("popped:");
        System.out.println(pQueue);
    }

    public static void main(String[] args) {
        pushNewTest();
        pushExistedTest();
        popTest();
    }
}
