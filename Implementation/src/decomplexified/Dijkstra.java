package decomplexified;

import java.util.ArrayList;
import java.util.HashSet;

import decomplexified.util.GraphNode2;
import decomplexified.util.WeightedValue;

/**
 * @author Alan
 *  http://decomplexify.blogspot.com/2014/03/algorithm-dijkstras-algorithm.html
 */
public class Dijkstra<T> {
//begin{dijkstra-traverse}
    // a priority queue to remember nodes which have been visited
    // but their distances are tentative
    private BinaryPriorityQueue<GraphNode2<T>, Double> frontLine =
            new BinaryPriorityQueue<>();
    
    // set used to mark if a node which has a finalized distance, that is,
    // a node which entered the queue then out of the queue.has been visited.
    private HashSet<GraphNode2<T>> finalized = new HashSet<>();
    
    public Double traverse(GraphNode2<T> source, GraphNode2<T> sink) {
        // if the graph is empty do nothing
        if (source == null) { return null; }
        if (sink == source) { return 0.0; }
        
        // initialization
        frontLine.clear();
        finalized.clear();        
        
        // visit the root
        frontLine.push(source, 0.0);
        finalized.add(source);
        
        while (!frontLine.isEmpty()) {
            // take out the node with smallest tentative distance
            WeightedValue<GraphNode2<T>, Double> parentAndWeight = frontLine.pop();
            GraphNode2<T> parent = parentAndWeight.value;
            Double parentDist = parentAndWeight.weight;
            if (parent == sink) { return parentDist; }
            finalized.add(parent);
            
            // add children to the priority queue
            for (WeightedValue<GraphNode2<T>,Double> edge : parent.edges) {
                GraphNode2<T> child = edge.value;
                if (finalized.contains(child)) { continue; }
                Double oldDist = frontLine.getPriority(child);
                Double newDist = parentDist + edge.weight;
                // update the distance of current child if either it has never
                // visited or it is not finalized yet (but still in frontLine)
                if (oldDist == null || newDist < oldDist) {
                    frontLine.push(child, newDist);
                }
            } // for
        } // while
        return null;
    }
//end{dijkstra-traverse}    

    public static void main(String[] args) {
        Integer[] values = {0,1,2,3,4,5,6,7,8};
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
        
        System.out.println("--------------");
        for (GraphNode2<Integer> node : nodes) {
            System.out.println(node.familyToString());
        }
        System.out.println("--------------");

        GraphNode2<Integer> s = nodes.get(0);
        Dijkstra<Integer> bfs = new Dijkstra<Integer>();
        for (GraphNode2<Integer> t : nodes) {
            Double dist = bfs.traverse(s, t);
            if (dist == null) {
                System.out.println(t + " is not reachable from " + s);
            } else {
                System.out.println("Distance from " + s + " to " + t + " is " + dist);
            }
        }
    }
}
