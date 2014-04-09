package decomplexified;

import java.util.LinkedList;
import decomplexified.util.TreeNode;

/**
 * @author Alan
 *  http://decomplexify.blogspot.com/2014/03/algorithm-bfs.html
 */
public abstract class TreeBFS<T> {

    // action to be defined for BFS traversal. If the returned
    // value is false, terminate the traversal
    public abstract boolean Process(TreeNode<T> node);
    
    public void traverse(TreeNode<T> root) {
        // if the tree is empty do nothing
        if (root == null) { return; }
        
        // an FIFO queue to remember visited nodes
        LinkedList<TreeNode<T>> visited = new LinkedList<>();
        visited.addFirst(root);
        if (!Process(root)) { return; }

        while (!visited.isEmpty()) {
            // take out the oldest node
            TreeNode<T> node = visited.pollLast();
            
            // add children to the queue
            if (node.left != null) {
                visited.addFirst(node.left);
                if (!Process(node.left)) { return; }
            }
            if (node.right != null) {
                visited.addFirst(node.right);
                if (!Process(node.right)) { return; }
            }
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
            public boolean Process(TreeNode<Integer> node) {
                // print a node and its children
                System.out.println(node.familyToString());
                return true;
            }
        };
        bfs.traverse(root);
    }

}
