package decomplexified;

import java.util.LinkedList;

import decomplexified.util.TreeNode;

/**
 * @author Alan
 *  http://decomplexify.blogspot.com/2014/03/algorithm-bfs.html
 */
public class BFSTree<T> {
//begin{tree-bfs-process}
    // action to be performed for a visited node. If the returned
    // value is false, terminate the traversal after this node.
    protected void process(TreeNode<T> node) {
//end{tree-bfs-process}        

        // As an example we simply print out the node
        System.out.println(node.familyToString() + 
                "\tvisited as a child of " + parentNode);
    }

//begin{tree-bfs-traverse}    
    // node whose children are currently being visited
    protected TreeNode<T> parentNode;

    // an FIFO queue to remember nodes which have been visited
    // but not their children
    private LinkedList<TreeNode<T>> frontLine = new LinkedList<>();
    
    public void traverse(TreeNode<T> root) {
        // if the tree is empty do nothing
        if (root == null) { return; }

        // initialization
        parentNode = null;
        frontLine.clear();
        
        // visit the root
        process(root);
        frontLine.addFirst(root);

        while (!frontLine.isEmpty()) {
            // take out the oldest node
            parentNode = frontLine.pollLast();
            
            // add children to the queue
            if (parentNode.left != null) {
                process(parentNode.left);
                frontLine.addFirst(parentNode.left);
            }
            if (parentNode.right != null) {
                process(parentNode.right);
                frontLine.addFirst(parentNode.right);
            }
        } // while
    }
//end{tree-bfs-traverse}
    
    public static void main(String[] args) {
        Integer[][] family = {{0,1,2},{1,3,4},{2,5,6},{4,7,8},{6,9,null}};
        Integer[] values = {0,1,2,3,4,5,6,7,8,9};
/*
            0
         /    \
       1        2
     /  \     /   \
    3    4   5     6
        / \       /
      7    8     9
*/
        TreeNode<Integer> root = TreeNode.buildTree(values, family).get(0);

        BFSTree<Integer> bfs = new BFSTree<Integer>();
        bfs.traverse(root);
    }
}
