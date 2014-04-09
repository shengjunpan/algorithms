package decomplexified.util;

import java.util.ArrayList;
import decomplexified.GraphBFS;

/**
 * @author Alan
 * Class representing a node in a graph
 */
public class GraphNode<T> {
    public ArrayList<GraphNode<T>> neighbors = new ArrayList<>();
    public T value;

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
        Integer[][] family = {{0,1,2},{1,0,3,4},{2,0,4,5,6},{3,1},{4,1,2},{5,2}, {6,2}};
        Integer[] values = {0,1,2,3,4,5,6};
        GraphNode<Integer> root = GraphNode.buildGraph(values, family).get(0);

        // Create a BFS object where `process' means `print'
        GraphBFS<Integer> bfs = new GraphBFS<Integer>() {
            @Override
            public boolean process(GraphNode<Integer> node) {
                // print a node and its children
                System.out.println(node.familyToString());
                return true;
            }
        };
        bfs.traverse(root);
    }
}
