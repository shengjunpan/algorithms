package decomplexified.util;

import java.util.ArrayList;
import decomplexified.TreeBFS;

/**
 * @author Alan
 *  Class representing a node (no parent access) in a binary tree
 */
public class TreeNode<T> {
    public TreeNode<T> left;
    public TreeNode<T> right;
    public T value;

    private Integer id;
    
    TreeNode(T x) { value = x; }
    TreeNode(T x, int id) { value = x; this.id = id; }
    
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
    
/////////////////////////////////////////////////////////////////////
    public static void main(String[] args) {
        Integer[][] families = {{0,1,2},{1,3,4},{2,5,null},{4,6,7}};
        Integer[] values = {0,1,2,3,4,5,6,7};
        TreeNode<Integer> root = TreeNode.buildTree(values, families).get(0);

        // Create a BFS object where `process' means `print'
        TreeBFS<Integer> bfs = new TreeBFS<Integer>() {
            @Override
            public boolean process(TreeNode<Integer> node) {
                System.out.println(node.familyToString());
                return true;
            }
        };
        bfs.traverse(root);
    }
}
