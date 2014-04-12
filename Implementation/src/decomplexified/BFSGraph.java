package decomplexified;

import java.util.HashSet;
import java.util.LinkedList;

import decomplexified.util.GraphNode;

/**
 * @author Alan
 *  http://decomplexify.blogspot.com/2014/03/algorithm-bfs-graph.html
 */
public class BFSGraph<T> {
  //begin{graph-bfs-process}
    // action to be performed for a visited node. If the returned
    // value is false, terminate the traversal after this node.
    protected void process(GraphNode<T> node) {
//end{graph-bfs-process}        

        // As an example we simply print out the node
        System.out.println(node.familyToString() + 
                "\tvisited as a child of " + parentNode);
    }
    
//begin{graph-bfs-traverse}    
    // node whose children are currently being visited
    protected GraphNode<T> parentNode = null;

    // an FIFO queue to remember nodes which have been visited
    // but not their children
    private LinkedList<GraphNode<T>> frontLine = new LinkedList<>();
    
    // a set to mark visited nodes, including the nodes that
    // entered the queue and then out of the queue
    private HashSet<GraphNode<T>> visited = new HashSet<>();
    
    public void traverse(GraphNode<T> root) {
        // if the tree is empty do nothing
        if (root == null) { return; }
        
        // initialization
        parentNode = null;
        frontLine.clear();

        // visit the root
        process(root);
        frontLine.addFirst(root);
        visited.add(root);

        while (!frontLine.isEmpty()) {
            // take out the oldest node
            parentNode = frontLine.pollLast();

            // add children to the queue
            for (GraphNode<T> child : parentNode.neighbors) {
                if (visited.contains(child)) { continue; }
                process(child);
                frontLine.addFirst(child);
                visited.add(child);
            } // for
        } // while
    }
//end{graph-bfs-traverse}    

    /**
     * Example showing how to use BFS to print a binary tree
     */
    public static void main(String[] args) {
        Integer[][] families = {{0,1,2},{1,0,3,4},{2,0,4,5,6},{3,1},{4,1,2},{5,2}, {6,2}};
        Integer[] values = {0,1,2,3,4,5,6};
        GraphNode<Integer> root = GraphNode.buildGraph(values, families).get(0);

        // Create a BFS object where `process' means `print'
        BFSGraph<Integer> bfs = new BFSGraph<Integer>();
        bfs.traverse(root);

        System.out.println("-----------");
        Integer[][] families2 = {{0,1,2},{1,0,3,4},{2,0,4,5,6},{3,1},{4,1,2,7,8},
                               {5,2,7},{6,2,8,9},{7,4,5},{8,4,6},{9,6}};
        Integer[] values2 = {0,1,2,3,4,5,6,7,8,9};
        GraphNode<Integer> root2 = GraphNode.buildGraph(values2, families2).get(0);
        bfs.traverse(root2);
    }
}
