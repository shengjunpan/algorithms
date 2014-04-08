package util;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Class representing a node (no parent access) in a binary tree
 * @author Alan
 *
 */
public class TreeNode<T> {
    TreeNode(T x) { value = x; }
    TreeNode(T x, int id) { value = x; this.id = id; }
    
    public TreeNode<T> left;
    public TreeNode<T> right;
    public T value;

    private Integer id;
    
    public String toString() {
        if (id == null) {
            return value.toString();
        } else {
            return "[" + id + "]" + value;
        }
    }
    
    public String printFamily() {
        StringBuffer output = new StringBuffer();
        output.append(this);
        if (left != null || right != null) {
            output.append(" ->");
            output.append(left == null ? " null" : " " + left);
            output.append(right == null ? " null" : " " + right);
        }
        return output.toString();
    }

    public String printTree() {
        // print nodes in BFS order
        LinkedList<TreeNode<T>> visited = new LinkedList<>();
        visited.addFirst(this);
        StringBuffer output = new StringBuffer();
        while (!visited.isEmpty()) {
            TreeNode<T> node = visited.pollLast();
            output.append(node.printFamily());
            output.append("\n");
            if (node.left != null) { visited.addFirst(node.left); }
            if (node.right != null) { visited.addFirst(node.right); }
        }
        return output.toString();
    }

    public static <T> TreeNode<T> buildTree(T[] values, Integer[][] descendants) {
        int n = values.length;
        ArrayList<TreeNode<T>> nodes = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            nodes.add(new TreeNode<T>(values[i], i));
        }
        for (Integer[] triple : descendants) {
            TreeNode<T> node = nodes.get(triple[0]);
            if (triple[1] != null) { node.left = nodes.get(triple[1]); }
            if (triple[2] != null) { node.right = nodes.get(triple[2]); }
        }
        return nodes.get(0);
    }
    
/////////////////////////////////////////////////////////////////////
    public static void main(String[] args) {
        Integer[][] family = {{0,1,2},{1,3,4},{2,5,null},{4,6,7}};
        Integer[] values = {0,1,2,3,4,5,6,7};
        TreeNode<Integer> root = TreeNode.buildTree(values, family);
        System.out.println(root.printTree());
    }
}
