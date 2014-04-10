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
    
//begin{dijsktra-process}
    // update the distance of current node from the source node
//begin{dijkstra-process-def}
    protected boolean process(GraphNode<T> node, GraphNode<T> parent) {
//end{dijsktra-process}        
        Integer parentDist = distances.get(parent);        
        if (parentDist != null) {
            distances.put(node, parentDist + 1);
        } else if (node == source) {
            distances.put(node, 0);
        }
        return node != sink;
    }
//end{dijkstra-process-def}
    
//begin{dijkstra-traverse}
    // source node and sink node
    private GraphNode<T> source, sink;
    
    // an FIFO queue to remember nodes which have been visited
    // but not their children
    private LinkedList<GraphNode<T>> frontLine = new LinkedList<>();
    
    // a set to mark visited nodes, including the nodes that
    // entered the queue and then out of the queue
    private HashMap<GraphNode<T>, Integer> distances = new HashMap<>();
    
    public Integer traverse(GraphNode<T> source, GraphNode<T> sink) {
        // if the tree is empty do nothing
        if (source == null) { return null; }
        
        // initialization
        frontLine.clear();
        distances.clear();
        this.source = source;
        this.sink = sink;
        
        // visit the root
        if (!process(source, null)) { return 0; }
        frontLine.addFirst(source);

        while (!frontLine.isEmpty()) {
            // take out the oldest node
            GraphNode<T> parent = frontLine.pollLast();

            // add children to the queue
            for (GraphNode<T> child : parent.neighbors) {
                if (distances.containsKey(child)) { continue; }
                if (!process(child, parent)) {
                    return distances.get(child);
                }
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
