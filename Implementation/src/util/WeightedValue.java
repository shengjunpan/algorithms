package util;
/**
 * @author Alan
 *  http://decomplexify.blogspot.com/2014/03/algorithm-priority-queue.html
 */
public class WeightedValue<T, W extends Comparable<W>>
        implements Comparable<WeightedValue<T, W>> {
    public T value;
    public W priority;

    public WeightedValue() { }
    
    public WeightedValue(T v) { value = v; }
    
    public WeightedValue(T v, W p) {
        value = v;
        priority = p;
    }
    
    @Override
    public int compareTo(WeightedValue<T,W> vp2) {
        return priority.compareTo(vp2.priority);
    }
    
    @Override
    public boolean equals(Object vp2) {
        @SuppressWarnings("unchecked")
        WeightedValue<T,W> vp = (WeightedValue<T,W>) vp2;
        return value.equals(vp.value);
    }
    
    @Override
    public String toString() {
        return "[" + value + "]" + priority;
    }
}
