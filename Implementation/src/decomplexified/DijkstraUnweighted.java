package decomplexified;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import decomplexified.util.GraphNode;

/**
 * @author Alan
 *  http://decomplexify.blogspot.com/2014/03/algorithm-dijkstras-algorithm.html
 */
public class DijkstraUnweighted<T> {
//begin{dijkstra-traverse}    
    // an FIFO queue to remember nodes which have been visited
    // but not their children
    private LinkedList<GraphNode<T>> frontLine = new LinkedList<>();
    
    // mapping from nodes to distances, also used to mark if a node has
    // been visited: a node has been visited if and only if it has been
    // added to the mapping before.
    private HashMap<GraphNode<T>, Integer> distances = new HashMap<>();
    
    public Integer traverse(GraphNode<T> source, GraphNode<T> sink) {
        // if the graph is empty do nothing
        if (source == null) { return null; }
        if (source == sink) { return 0; }
        
        // initialization
        frontLine.clear();
        distances.clear();
        
        // visit the root
        distances.put(source, 0);
        frontLine.addFirst(source);

        while (!frontLine.isEmpty()) {
            // take out the oldest node
            GraphNode<T> parent = frontLine.pollLast();
            int parentDist = distances.get(parent);
            
            // add children to the queue
            for (GraphNode<T> child : parent.neighbors) {
                if (distances.containsKey(child)) { continue; }
                int newDist = parentDist + 1;
                if (child == sink) { return newDist; }
                
                distances.put(child, newDist);
                frontLine.addFirst(child);
            } // for
        } // while
        return null;
    }
//end{dijkstra-traverse}    

    public static void main(String[] args) {
        Integer[][] families = {{0,1,2},{1,0,3,4},{2,0,4,5,6},{3,1},{4,1,2,7,8},
                                {5,2,7},{6,2,8,9},{7,4,5},{8,4,6},{9,6}, {10}};
        Integer[] values = {0,1,2,3,4,5,6,7,8,9,10};
        ArrayList<GraphNode<Integer>> nodes = GraphNode.buildGraph(values, families);
        GraphNode<Integer> s = nodes.get(0);
        DijkstraUnweighted<Integer> bfs = new DijkstraUnweighted<Integer>();
        for (GraphNode<Integer> t : nodes) {
            Integer dist = bfs.traverse(s, t);
            if (dist == null) {
                System.out.println(t + " is not reachable from " + s);
            } else {
                System.out.println("Distance from " + s + " to " + t + " is " + dist);
            }
        }
    }

}
