package decomplexified.util;

import java.util.ArrayList;

/**
 * @author Alan
 *  Class representing a node (no parent access) in a binary tree
 */
//begin{treenode}
public class TreeNode<T> {
    public TreeNode<T> left;
    public TreeNode<T> right;
    public T value;
//end{treenode}
    
    private Integer id;
    
    public TreeNode(T x) { value = x; }
    public TreeNode(T x, int id) { value = x; this.id = id; }
    
    public String toString() {
        if (id == null) {
            return value.toString();
        } else {
            return "[" + id + "]" + value;
        }
    }
    
    public String familyToString() {
        StringBuffer output = new StringBuffer();
        output.append(this);
        if (left != null || right != null) {
            output.append(" ->");
            output.append(left == null ? " null" : " " + left);
            output.append(right == null ? " null" : " " + right);
        }
        return output.toString();
    }

    /**
     * Build a binary tree.
     * @param values values[i] is the value for node with index i
     * @param families families[i] is a triple, where families[i][1]
     *        and families[2] are indices of the left and right children
     *        of the node with index families[i][0].
     * @return an array of nodes
     */
    public static <T> ArrayList<TreeNode<T>> buildTree(T[] values, Integer[][] families) {
        int n = values.length;
        ArrayList<TreeNode<T>> nodes = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            nodes.add(new TreeNode<T>(values[i], i));
        }
        for (Integer[] family : families) {
            TreeNode<T> node = nodes.get(family[0]);
            if (family[1] != null) { node.left = nodes.get(family[1]); }
            if (family[2] != null) { node.right = nodes.get(family[2]); }
        }
        return nodes;
    }
    
    /* pre-order DFS; for debugging purpose */
    public void print(boolean family) {
        if (family) {
            System.out.println(familyToString());
        } else {
            System.out.println(this);
        }
        if (left != null) { left.print(family); }
        if (right != null) { right.print(family); }
    }
    
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
        ArrayList<TreeNode<Integer>> nodes = TreeNode.buildTree(values, family);
        nodes.get(0).print(true);
    }
}
