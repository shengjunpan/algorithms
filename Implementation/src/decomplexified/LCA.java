/**
 * 
 */
package decomplexified;

import java.util.ArrayList;

import decomplexified.util.TreeNode;

/**
 * @author Alan
 *  http://decomplexify.blogspot.com/2014/03/algorithm-lowest-common-ancestor.html
 */
public class LCA<T> {
//begin{lca-helper}
    // helper class to encapsulate the LCA as well as if the current
    // tree has the input nodes (a and b)
    public static class LCAInfo<T> {
        public TreeNode<T> lca = null;
        public boolean hasA = false;
        public boolean hasB = false;
    }
//end{lca-helper}

//begin{lca}
    /**
     * @param a  first input node
     * @param b  second input node
     * @param root  root of the tree defining the search range
     * @return the LCA of a and b; null if not found
     */
    public LCAInfo<T> lca(TreeNode<T> a, TreeNode<T> b, TreeNode<T> root) {
        LCAInfo<T> info = new LCAInfo<>();
        if (a == null || b== null || root == null) {
            return info;
        }
        
        // search for LCA in the subtrees
        LCAInfo<T> infoLeft = lca(a,b,root.left);
        LCAInfo<T> infoRight = lca(a,b,root.right);
        
        info.hasA = infoLeft.hasA || infoRight.hasA || root == a;
        info.hasB = infoLeft.hasB || infoRight.hasB || root == b;
        if (infoLeft.lca != null) {
            // found LCA in left subtree
            info.lca = infoLeft.lca;
        } else if (infoRight.lca != null) {
            // found LCA in right subtree
            info.lca = infoRight.lca;
        } else if (info.hasA && info.hasB) {
            // current root is LCA
            info.lca = root;
        }
        
        return info;
    }
//end{lca}
    
    public static void main(String[] args) {
        Integer[][] family = {{0,1,2},{1,3,4},{2,5,6},{4,7,8},{6,9,null}};
        Integer[] values = {0,1,2,3,4,5,6,7,8,9};
        ArrayList<TreeNode<Integer>> nodes = TreeNode.buildTree(values, family);
        for (TreeNode<Integer> node : nodes) {
            System.out.println(node.familyToString());
        }
        System.out.println("-----------");
        TreeNode<Integer> root = nodes.get(0);
        LCA<Integer> solver = new LCA<>();
        for (int i = 0; i<nodes.size(); ++i) {
            TreeNode<Integer> a = nodes.get(i);
            for (int j= i; j<nodes.size(); ++j) {
                TreeNode<Integer> b = nodes.get(j);
                System.out.println("LCA of " + a + " and " + b
                        + " is " + solver.lca(a, b, root).lca);
            }
            System.out.println();
        }
    }

}
