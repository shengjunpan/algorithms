package decomplexified;

import java.util.ArrayList;
import java.util.HashMap;

import decomplexified.util.GraphNode;

/**
 * @author Alan
 *  http://decomplexify.blogspot.com/2014/03/algorithm-dijkstras-algorithm.html
 */
public class UnweightedDijsktra<T> extends GraphBFS<T> {
    
    private GraphNode<T> source;
    private GraphNode<T> sink;
    HashMap<GraphNode<T>, Integer> distances = new HashMap<>();
    
    @Override
    protected boolean process(GraphNode<T> node) {
        if (parentNode == null) {
            distances.put(node, node == source ? 0 : null);
        } else {
            int parentDistance = distances.get(parentNode);
            distances.put(node, parentDistance + 1);
        }
        return node != sink;
    }

    /**
     * @return distance from node s to node t
     */
    public Integer getDistance(GraphNode<T> s, GraphNode<T> t) {
        // set source and sink so that `Process' can access them.
        source = s;
        sink = t;
        
        distances.clear();
        this.traverse(s);
        return distances.get(t);
    }
    
    public static void main(String[] args) {
        Integer[][] families = {{0,1,2},{1,0,3,4},{2,0,4,5,6},{3,1},{4,1,2,7,8},
                                {5,2,7},{6,2,8,9},{7,4,5},{8,4,6},{9,6}, {10}};
        Integer[] values = {0,1,2,3,4,5,6,7,8,9,10};
        ArrayList<GraphNode<Integer>> nodes = GraphNode.buildGraph(values, families);
        GraphNode<Integer> s = nodes.get(0);
        UnweightedDijsktra<Integer> bfs = new UnweightedDijsktra<Integer>();
        for (GraphNode<Integer> t : nodes) {
            Integer dist = bfs.getDistance(s, t);
            if (dist == null) {
                System.out.println(t + " is not reachable from " + s);
            } else {
                System.out.println("Distance from " + s + " to " + t + " is " + dist);
            }
        }
    }

}
