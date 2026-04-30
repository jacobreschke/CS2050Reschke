package CS2050Reschke.M04.Labs;

public class BinaryTreeTestRecursive
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

    public boolean insert(int value) {
    if (root == null) {
        root = new TreeNode(value);
        return true;
    } else {
        return insertRecursive(root, value);
    }
}

private boolean insertRecursive(TreeNode current, int value) {
    if (value < current.data) {
        if (current.left == null) {
            current.left = new TreeNode(value);
            return true;
        } else {
            return insertRecursive(current.left, value);
        }
    } else if (value > current.data) {
        if (current.right == null) {
            current.right = new TreeNode(value);
            return true;
        } else {
            return insertRecursive(current.right, value);
        }
    } else {
        System.out.println("Can't insert duplicate");
        return false;
    }
}

} // BinarySearch
