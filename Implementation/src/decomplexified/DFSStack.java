package decomplexified;

import java.util.ArrayList;
import java.util.Stack;

import decomplexified.util.TreeNode;

/**
 * @author Alan
 *  http://decomplexify.blogspot.com/2014/03/ill-show-how-to-implement-dfs-on-binary.html
 */
public class DFSStack<T> {
  //begin{dfs-process}
    // action to be performed for a visited node.
    protected void process(TreeNode<T> node) {
//end{dfs-process}
        
        // As an example we simply print out the node
        System.out.println(node.familyToString());
    }

//begin{comefrom}    
    // how did we get to the current node?
    public static enum ComeFrom {
        LEFT, RIGHT, PARENT
    }
//end{comefrom}    

//begin{dfs}    
    // Traversal orders
    public static enum DFSOrder {
        PRE_ORDER, IN_ORDER, POST_ORDER
    }
    
    public void traverse(TreeNode<T> root, DFSOrder order) {
        if (root == null) { return; }
        
        // a stack for saving visited nodes. A node stays until all
        // its descendants have been processed, even if itself has
        // been processed
        Stack<TreeNode<T>> visited = new Stack<>();
        visited.push(root);
        
        // pretend we came to the root from parent null
        ComeFrom direction = ComeFrom.PARENT;
        
        while (!visited.empty()) {
            TreeNode<T> topNode = visited.peek();
            switch (direction) {
            case PARENT:
                if (order == DFSOrder.PRE_ORDER) { process(topNode); }
                if (topNode.left != null) {
                    visited.push(topNode.left);
                    direction = ComeFrom.PARENT;
                    continue; // while
                }
                /* fall through to next case */
            case LEFT:
                if (order == DFSOrder.IN_ORDER) { process(topNode); }
                if (topNode.right != null) {
                    visited.push(topNode.right);
                    direction = ComeFrom.PARENT;
                    continue; // while
                }
                /* fall through to next case */
            case RIGHT:
                if (order == DFSOrder.POST_ORDER) { process(topNode); }
                
                // a node is removed from the stack only if all its descendants
                // have been processed, regardless of the traversal order
                visited.pop();
                
                if (!visited.empty()) {
                    direction = visited.peek().left == topNode ?
                            ComeFrom.LEFT : ComeFrom.RIGHT;
                }
            }
        }
    }
//end{dfs}
    
    public static void main(String[] args) {
        String[] values = {"A","B","C","D","E","F","G","H","I","J"};
        Integer[][] family = {{0,1,2},{1,3,4},{2,5,6},{4,7,8},{6,9,null}};
        DFSStack<String> dfs = new DFSStack<>();
        
        ArrayList<TreeNode<String>> nodes = TreeNode.buildTree(values, family);
        TreeNode<String> root = nodes.get(0);
        
        /*
                   A
                /      \
               B        C
             /   \     /  \
            D     E   F    G
                 / \      /
                H   I    J
        */
        System.out.println("-------------------");
        System.out.println("Pre-roder:");
        dfs.traverse(root, DFSOrder.PRE_ORDER);

        System.out.println("-------------------");
        System.out.println("In-roder:");
        dfs.traverse(root, DFSOrder.IN_ORDER);

        System.out.println("-------------------");
        System.out.println("Post-roder:");
        dfs.traverse(root, DFSOrder.POST_ORDER);

}

}
