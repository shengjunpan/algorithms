package decomplexified;

import java.util.LinkedList;
import decomplexified.util.TreeNode;

/**
 * @author Alan
 *  http://decomplexify.blogspot.com/2014/03/algorithm-bfs.html
 */
public abstract class TreeBFS<T> {

    // action to be defined
    public abstract void Process(TreeNode<T> node);
    
    public void traverse(TreeNode<T> root) {
        // if the tree is empty do nothing
        if (root == null) { return; }
        
        // an FIFO queue to remember visited nodes
        LinkedList<TreeNode<T>> visited = new LinkedList<>();
        visited.addFirst(root);

        while (!visited.isEmpty()) {
            // take out the oldest node
            TreeNode<T> node = visited.pollLast();
            
            // Process a node when it's out of the queue. Alternatively
            // we could process it when adding it to the queue
            Process(node);
            
            // add children to the queue
            if (node.left != null) { visited.addFirst(node.left); }
            if (node.right != null) { visited.addFirst(node.right); }
        } // while
    }

    /**
     * Example showing how to use BFS to print a binary tree
     */
    public static void main(String[] args) {
        Integer[][] family = {{0,1,2},{1,3,4},{2,5,6},{4,7,8},{6,9,null}};
        Integer[] values = {0,1,2,3,4,5,6,7,8,9};
        TreeNode<Integer> root = TreeNode.buildTree(values, family);

        // Create a BFS object where `process' means `print'
        TreeBFS<Integer> bfs = new TreeBFS<Integer>() {
            @Override
            public void Process(TreeNode<Integer> node) {
                System.out.println(node.familyToString());
            }
        };
        bfs.traverse(root);
    }

}
