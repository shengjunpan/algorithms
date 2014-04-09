package decomplexified;

import java.util.HashSet;
import java.util.LinkedHashSet;

import decomplexified.util.GraphNode;

/**
 * @author Alan
 *  http://decomplexify.blogspot.com/2014/03/algorithm-bfs-graph.html
 */
public abstract class GraphBFS<T> {

    // action to be defined for BFS traversal. If the returned
    // value is false, terminate the traversal
    protected abstract boolean process(GraphNode<T> node);
    
    // node whose children are currently being visited
    protected GraphNode<T> parentNode = null;

    public void traverse(GraphNode<T> root) {
        // if the tree is empty do nothing
        if (root == null) { return; }
        
        parentNode = null;

        // a set to remember current level of nodes, initially only the root
        // we use a LinkedHashSet to produce stable process order, but a
        // HashSet would just be fine.
        HashSet<GraphNode<T>> justVisited = new LinkedHashSet<>();
        justVisited.add(root);
        if (!process(root)) { return; }

        // a set to remember previous level of nodes, initially empty
        // we use a LinkedHashSet to produce stable process order, but a
        // HashSet would just be fine.
        HashSet<GraphNode<T>> previousVisited = new LinkedHashSet<>();
        while (!justVisited.isEmpty()) {
            // next level of nodes: unvisited neighbors of `justVisited'
            HashSet<GraphNode<T>> nextLevel = new HashSet<>();
            for (GraphNode<T> node : justVisited) {
                parentNode = node;
                for (GraphNode<T> neighbor : parentNode.neighbors) {
                    // avoid visiting visited nodes
                    if (!previousVisited.contains(neighbor) &&
                            !justVisited.contains(neighbor) &&
                            !nextLevel.contains(neighbor)) {
                        nextLevel.add(neighbor);
                        if (!process(neighbor)) { return; }
                    } // if
                } // for neighbor
            } // for node
            // move down one level
            previousVisited = justVisited;
            justVisited = nextLevel;
        } // while
    }

    /**
     * Example showing how to use BFS to print a binary tree
     */
    public static void main(String[] args) {
        Integer[][] families = {{0,1,2},{1,0,3,4},{2,0,4,5,6},{3,1},{4,1,2},{5,2}, {6,2}};
        Integer[] values = {0,1,2,3,4,5,6};
        GraphNode<Integer> root = GraphNode.buildGraph(values, families).get(0);

        // Create a BFS object where `process' means `print'
        GraphBFS<Integer> bfs = new GraphBFS<Integer>() {
            @Override
            public boolean process(GraphNode<Integer> node) {
                // print a node and its children
                System.out.println(node.familyToString() + 
                        "\tvisited as a child of " + parentNode);
                return true;
            }
        };
        bfs.traverse(root);

        System.out.println("-----------");
        Integer[][] families2 = {{0,1,2},{1,0,3,4},{2,0,4,5,6},{3,1},{4,1,2,7,8},
                               {5,2,7},{6,2,8,9},{7,4,5},{8,4,6},{9,6}};
        Integer[] values2 = {0,1,2,3,4,5,6,7,8,9};
        GraphNode<Integer> root2 = GraphNode.buildGraph(values2, families2).get(0);
        bfs.traverse(root2);
    }
}
