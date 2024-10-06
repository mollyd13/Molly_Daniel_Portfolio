
//generic binary tree class that we did for hw
public class BinTree<T> {

    Node<T> root;

    public BinTree() {
        root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public Node<T> addRoot(T value, Node<T> current) {
        root = new Node<T>(value);
        return root;
    }

    public void addLeft(T data, Node<T> current) {
        if (isEmpty()) {
            System.out.println("You must add a root before adding any children.");
            return;
        }

        // don't let the user overwrite the subtree
        if (current.left == null) {
            current.left = new Node<T>(data);
        } else {
            System.out.println("Left child already exists.");
            return;
        }

    }

    public void addRight(T data, Node<T> current) {
        if (isEmpty()) {
            System.out.println("You must add a root before adding any children.");
            return;
        }

        // don't let the user overwrite the subtree
        if (current.right == null) {
            current.right = new Node<T>(data);
        } else {
            System.out.println("Right child already exists. ");
            return;
        }

    }

    public Node<T> goRight(Node<T> current) {
        if (current != null && current.right != null) {
            return current.right;
        } else {
            System.out.println("The right child does not exist. ");
            return current;
        }
    }

    public Node<T> goLeft(Node<T> current) {
        if (current != null && current.left != null) {
            return current.left;
        } else {
            System.out.println("The left child does not exist. ");
            return current;
        }
    }

    public Node<T> goToRoot() {
        if (root != null) {
            return root;
        } else {
            System.out.println("Please create a root first. ");
            return null;
        }
    }

    public String recStringBuilder(Node<T> current, String s) {
        if (current != null) {
            s = recStringBuilder(current.left, s);
            s += current.value + " ";
            s = recStringBuilder(current.right, s);
        }
        return s;
    }

    public String toString() {
        String s = "";
        if (isEmpty()) {
            return s;
        } else {
            s = recStringBuilder(root, s);
            return s;
        }

    }

}