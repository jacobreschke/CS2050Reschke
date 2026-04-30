/*
package CS2050Reschke.M04.Labs;

public class BinaryTreeTestNonRecursive
{
    public static void main(String[] args)
    {
        BinarySearchTree tree = new BinarySearchTree();

        tree.insert(4);
        tree.insert(2);
        tree.insert(6);
        tree.insert(4);
    }

}
class TreeNode{
    int data;
    TreeNode left;
    TreeNode right;

    public TreeNode(int data){
        this.data = data;
        left = null;
        right = null;
    }    
} // TreeNode

class BinarySearchTree
{
    private TreeNode root;
    
    public BinarySearchTree()
    {
        root = null;
    }

    public boolean insert(int value)
    {

        // If first node in the tree - then create root
        if (root == null)
        {
            root = new TreeNode(value);
        } else
        {
            // Locate the parent for the new node
            // Need a couple of references to help
            TreeNode parent = null;
            TreeNode current = root;

            // While haven't reached bottom of tree
            while (current != null)
            {
                // Determine if value will be inserted to left or right of current
                if (value < current.data)
                {
                    parent = current;
                    current = current.left;
                } else if (value > current.data)
                {
                    parent = current;
                    current = current.right;
                } else
                {
                    // Found a duplicate node - do not insert
                    // this is a case for using a return in while loop
                    return false;
                }
            } // while

            if (value < parent.data)
            {
                parent.left = new TreeNode(value);
            } else
            {
                parent.right = new TreeNode(value);
            }
        } // else not root node

        return true;
    } // insert

} // BinarySearch

*/
