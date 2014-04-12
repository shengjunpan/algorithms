package decomplexified;

import java.util.ArrayList;

import decomplexified.util.TreeNode;

/**
 * @author Alan
 *  http://decomplexify.blogspot.com/2014/03/algorithm-dfs-recursion-on-binary-trees.html
 */
public class DFSRecursion<T> {
  //begin{dfs-process}
    // action to be performed for a visited node.
    protected void process(TreeNode<T> node) {
//end{dfs-process}
        
        // As an example we simply print out the node
        System.out.println(node.familyToString());
    }

//begin{dfs}    
    // Traversal orders
    public static enum DFSOrder {
        PRE_ORDER, IN_ORDER, POST_ORDER
    }

    public void traverse(TreeNode<T> root, DFSOrder order) {
        if (root == null) { return; }
        
        if (order == DFSOrder.PRE_ORDER) { process(root); }
        
        traverse(root.left, order);

        if (order == DFSOrder.IN_ORDER) { process(root); }
        
        traverse(root.right, order);

        if (order == DFSOrder.POST_ORDER) { process(root); }
    }
//end{dfs}
    
    public static void main(String[] args) {
        String[] values = {"A","B","C","D","E","F","G","H","I","J"};
        Integer[][] family = {{0,1,2},{1,3,4},{2,5,6},{4,7,8},{6,9,null}};
        DFSRecursion<String> dfs = new DFSRecursion<>();
        
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
