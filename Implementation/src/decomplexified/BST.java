package decomplexified;
import decomplexified.util.TreeNode2;

/**
 * @author Alan
 *  http://decomplexify.blogspot.com/2014/04/bst.html
 */
public class BST<T extends Comparable<T>> {
//begin{search}
    /* find a value in a BST. Return the node that contains
     * the value, null if not found.
     */
    public TreeNode2<T> find(TreeNode2<T> root, T query) {
        TreeNode2<T> node = root;
        while (node != null) {
            int compared = query.compareTo(node.value);
            if (compared == 0) {
                // found
                break;
            } else if (compared < 0) {
                // query might be in the left subtree
                node = node.left;
            } else { //compared > 0
                // query might be in the right subtree            
                node = node.right;
            }
        }
        return node;
    }
//end{search}
    
//begin{insert}    
    /* insert a new value into a BST . Return the new root */
    public TreeNode2<T> insert(TreeNode2<T> root, T value) {
        TreeNode2<T> node = root;
        TreeNode2<T> parent = root.parent;        
        boolean attachAsLeft = true;
        
        // search for attaching point
        while (node != null) {
            parent = node;
            int compared = value.compareTo(node.value);
            if (compared <= 0) {
                // insert new value to the left subtree
                node = node.left;
                attachAsLeft = true;
            } else {
                // insert new value to the right subtree
                node = node.right;
                attachAsLeft = false;
            }
        }
        
        // create new node and attach
        TreeNode2<T> newNode = new TreeNode2<T>(value);
        if (parent == null) {
            // this only happens if root == null (empty tree)
            return newNode;
        }
        if (attachAsLeft) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
        newNode.parent = parent;
        
        // root does not change
        return root;
    }
//end{insert}
    
//begin{delete}    
    /* delete a node and return the new root */
    private TreeNode2<T> delete(TreeNode2<T> root, T value) {
        // first find the node that has the value
        TreeNode2<T> node = find(root, value);

        // not found
        if (node == null) { return null; }

        // search for a leaf to delete; swap values along the way
        while (true) {
            if (node.left != null) {        
                // look for predecessor in left subtree
                TreeNode2<T> predecessor = node.left;
                while (predecessor.right != null) {
                    predecessor = predecessor.right;
                }
                node.value = predecessor.value;
                node = predecessor;
                
            } else if (node.right != null){
                // look for successor in right subtree
                TreeNode2<T> successor = node.right;
                while (successor.left != null) {
                    successor = successor.left;
                }
                node.value = successor.value;
                node = successor;
            } else {
                // it's a leaf
                break;
            }
        }
        
        // delete the leaf
        TreeNode2<T> parent = node.parent;
        if (parent != null) {
            if (parent.left == node) {
                parent.left = null;
            } else {
                parent.right = null;
            }
            // root node does not change (value may change)
            return root;
        } else {
            // the tree is empty
            return null;
        }
    }
//end{delete}
    
///////////////////////////////////////////////////////////////
    private static void testFind(TreeNode2<Integer> root) {
        System.out.println();
        BST<Integer> bst = new BST<>();
        int[] queries = {7, 12};
        for (int query : queries) {
            TreeNode2<Integer> node = bst.find(root, query);
            System.out.println(query + " --> " + node);
        }        
    }
    
    private static void testInsert(TreeNode2<Integer> root) {
        BST<Integer> bst = new BST<>();
        int value = 12;
        TreeNode2<Integer> newRoot = bst.insert(root, value);
/*
                10
             /     \
           2        13
         /  \     /    \
        1    9  11      15
            /     \    /
          4       12  14
        /  \
        3    6
           /  \
          5    8
             /
            7
*/
        System.out.println();
        System.out.println("After inserting " + value);
        newRoot.print(true);
    }

    private static void testDelete(TreeNode2<Integer> root) {
        BST<Integer> bst = new BST<>();
        int value = 10;
        TreeNode2<Integer> newRoot = bst.delete(root, value);
/*
                9
             /     \
           2        13
         /  \     /    \
        1    8  11      15
            /     \    /
          4       12  14
        /  \
       3    6
          /  \
         5    7
*/
        System.out.println();
        System.out.println("After deleting " + value);
        newRoot.print(true);
    }

    public static void main(String[] args) {
        Integer[] values = {10,2,13,1,9,11,15,4,14,3,6,5,8,7};
        Integer[][] family = {{0,1,2},{1,3,4},{2,5,6},{4,7,null},{6,8,null},
                              {7,9,10},{10,11,12},{12,13,null}};
/*
            10
         /     \
       2        13
     /  \     /   \
    1    9   11    15
        /         /
      4          14
    /  \
   3    6
      /  \
     5    8
        /
       7
 */
        TreeNode2<Integer> root = TreeNode2.buildTree(values, family).get(0);
        root.print(true);
        testFind(root);

        root = TreeNode2.buildTree(values, family).get(0);
        testInsert(root);

        root = TreeNode2.buildTree(values, family).get(0);
        testDelete(root);

    }
}
