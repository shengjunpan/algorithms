package decomplexified.util;
/**
 * @author Alan
 *  http://decomplexify.blogspot.com/2014/03/algorithm-priority-queue.html
 */
//begin{weighted-value}
public class WeightedValue<T, W extends Comparable<W>>
        implements Comparable<WeightedValue<T, W>> {
    public T value;
    public W weight;

    public WeightedValue() { }
    
    public WeightedValue(T v) { value = v; }
    
    public WeightedValue(T v, W w) {
        value = v;
        weight = w;
    }
    
    @Override
    public int compareTo(WeightedValue<T,W> vp2) {
        return weight.compareTo(vp2.weight);
    }
    
    @Override
    public boolean equals(Object vp2) {
        @SuppressWarnings("unchecked")
        WeightedValue<T,W> vp = (WeightedValue<T,W>) vp2;
        return value.equals(vp.value);
    }
//end{weighted-value}
    
    @Override
    public String toString() {
        return value + "{w=" + weight + "}";
    }
}
