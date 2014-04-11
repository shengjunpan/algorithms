package decomplexified.util;

import java.util.ArrayList;

/**
 * @author Alan
 * Class representing a node in a graph
 */
//begin{graphnode}
public class GraphNode<T> {
    public ArrayList<GraphNode<T>> neighbors = new ArrayList<>();
    public T value;
//end{graphnode}

    private Integer id;
    
    GraphNode(T x) { value = x; }
    GraphNode(T x, int id) { value = x; this.id = id; }

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
        if (!neighbors.isEmpty()) {
            output.append(" ->");
            for (GraphNode<T> neighbor : neighbors) {
                output.append(" " + neighbor);
            }
        }
        return output.toString();
    }
    
    /**
     * Build a directed graph. Both directions must be specified if
     * an undirected graph is desired
     * @param values values[i] is the value for the node with index i
     * @param families families[i] is an array, where families[i][1..]
     *        are the child indices of node width index families[i][0].
     * @return an array of nodes
     */
    public static <T> ArrayList<GraphNode<T>> buildGraph(T[] values, Integer[][] families) {
        int n = values.length;
        ArrayList<GraphNode<T>> nodes = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            nodes.add(new GraphNode<T>(values[i], i));
        }
        for (Integer[] family : families) {
            GraphNode<T> node = nodes.get(family[0]);
            for (int i = 1; i < family.length; ++i) {
                Integer childIndex = family[i];
                if (childIndex != null) {
                    node.neighbors.add(nodes.get(childIndex));
                }
            }
        }
        return nodes;
    }
    
/////////////////////////////////////////////////////////////////////
    public static void main(String[] args) {
        Integer[][] families = {{0,1,2},{1,0,3,4},{2,0,4,5,6},{3,1},{4,1,2},{5,2}, {6,2}};
        Integer[] values = {0,1,2,3,4,5,6};
        ArrayList<GraphNode<Integer>> nodes = GraphNode.buildGraph(values, families);
        for (GraphNode<Integer> node : nodes) {
            System.out.println(node.familyToString());
        }
    }
}
