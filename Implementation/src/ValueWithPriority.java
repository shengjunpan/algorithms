/**
 * @author Alan
 *  http://decomplexify.blogspot.com/2014/03/algorithm-priority-queue.html
 */
public class ValueWithPriority<T, P extends Comparable<P>>
        implements Comparable<ValueWithPriority<T, P>> {
    public T value;
    public P priority;

    public ValueWithPriority() { }
    
    public ValueWithPriority(T v) { value = v; }
    
    public ValueWithPriority(T v, P p) {
        value = v;
        priority = p;
    }
    
    @Override
    public int compareTo(ValueWithPriority<T,P> vp2) {
        return priority.compareTo(vp2.priority);
    }
    
    @Override
    public boolean equals(Object vp2) {
        @SuppressWarnings("unchecked")
        ValueWithPriority<T,P> vp = (ValueWithPriority<T,P>) vp2;
        return value.equals(vp.value);
    }
    
    @Override
    public String toString() {
        return "[" + value + "]" + priority;
    }
}
