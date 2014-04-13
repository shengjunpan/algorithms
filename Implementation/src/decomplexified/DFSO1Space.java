package decomplexified;

import java.util.ArrayList;
import decomplexified.util.TreeNode2;

/**
 * @author Alan
 *  http://decomplexify.blogspot.com/2014/03/algorithm-dfs-o1-space.html
 */
public class DFSO1Space<T> {
  //begin{dfs-process}
    // action to be performed for a visited node.
    protected void process(TreeNode2<T> node) {
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
    
    public void traverse(TreeNode2<T> root, DFSOrder order) {
        if (root == null) { return; }
        
        TreeNode2<T> currentNode = root;

        // pretend we came to the root from parent null
        ComeFrom direction = ComeFrom.PARENT;
        
        while (currentNode != null) {
            switch (direction) {
            case PARENT:
                if (order == DFSOrder.PRE_ORDER) { process(currentNode); }
                if (currentNode.left != null) {
                    currentNode = currentNode.left;
                    direction = ComeFrom.PARENT;
                    continue; // while
                }
                /* fall through to next case */
            case LEFT:
                if (order == DFSOrder.IN_ORDER) { process(currentNode); }
                if (currentNode.right != null) {
                    currentNode = currentNode.right;
                    direction = ComeFrom.PARENT;
                    continue; // while
                }
                /* fall through to next case */
            case RIGHT:
                if (order == DFSOrder.POST_ORDER) { process(currentNode); }
                TreeNode2<T> parentNode = currentNode.parent;
                if (parentNode != null) {
                    direction = parentNode.left == currentNode ?
                            ComeFrom.LEFT : ComeFrom.RIGHT;
                }
                currentNode = parentNode;
            }
        }
    }
//end{dfs}
    
    public static void main(String[] args) {
        String[] values = {"A","B","C","D","E","F","G","H","I","J"};
        Integer[][] family = {{0,1,2},{1,3,4},{2,5,6},{4,7,8},{6,9,null}};
        DFSO1Space<String> dfs = new DFSO1Space<>();
        
        ArrayList<TreeNode2<String>> nodes = TreeNode2.buildTree(values, family);
        TreeNode2<String> root = nodes.get(0);
        
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
