package decomplexified.util;

import java.util.ArrayList;

/**
 * @author Alan
 * Class representing a node in a graph with weighted edges
 */
//begin{graphnode}
public class GraphNode2<T> {
    public ArrayList<WeightedValue<GraphNode2<T>, Double>> edges = new ArrayList<>();
    public T value;
//end{graphnode}

    private Integer id;
    
    GraphNode2(T x) { value = x; }
    GraphNode2(T x, int id) { value = x; this.id = id; }

    public String toString() {
        if (id == null) {
            return value.toString();
        } else {
            return "[" + id + "]" + value;
        }
    }
    
    public String familyToString() {
        StringBuffer output = new StringBuffer();
        output.append(this);
        if (!edges.isEmpty()) {
            output.append(" ->");
            for (WeightedValue<GraphNode2<T>, Double> edge : edges) {
                output.append(" " + edge);
            }
        }
        return output.toString();
    }
    
    /**
     * Build a directed graph. Both directions must be specified if
     * an undirected graph is desired
     * @param values values[i] is the value for the node with index i
     * @param edgeLists edgeLists[i] specifies neighbors of node of index i
     *        and edgeLists[i][j] specifies the j-th neighbor, such that
     *        edgeLists[i][j][0] is the index of the j-th neighbor, and
     *        edgeLists[i][j][1] is the weight on the edge from i to j.
     * @return an array of nodes
     */
    public static <T> ArrayList<GraphNode2<T>> buildGraph(T[] values, Double[][][] edgeLists) {
        int n = values.length;
        ArrayList<GraphNode2<T>> nodes = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            nodes.add(new GraphNode2<T>(values[i], i));
        }
        for (int i = 0; i < n && i < edgeLists.length; ++i) {
            GraphNode2<T> node = nodes.get(i);
            Double[][] edgeList = edgeLists[i];
            for (Double[] edge :edgeList) {
                int id = (int) edge[0].doubleValue();
                GraphNode2<T> neighbor = nodes.get(id);
                Double weight = edge[1];
                node.edges.add(new WeightedValue<GraphNode2<T>,Double>(neighbor, weight));
            }
        }
        return nodes;
    }
    
/////////////////////////////////////////////////////////////////////
    public static void main(String[] args) {

        Integer[] values = {0,1,2,3,4,5,6,7};
        Double[][][] edgeLists = {
                {{1.0,2.0},{2.0,1.0}},
                {{0.0,2.0},{3.0,2.0},{4.0,1.0}},
                {{0.0,1.0},{4.0,3.0},{5.0,4.0},{6.0,3.0}},
                {{1.0,2.0}},
                {{1.0,1.0},{2.0,3.0},{5.0,3.0},{7.0,3.0}},
                {{2.0,4.0},{4.0,3.0}},
                {{2.0,3.0}},
                {{4.0,3.0}}};
        ArrayList<GraphNode2<Integer>> nodes = GraphNode2.buildGraph(values, edgeLists);
        for (GraphNode2<Integer> node : nodes) {
            System.out.println(node.familyToString());
        }
    }
}
