//generic node class from hw
public class Node<T> {

    T value;
    Node<T> left;
    Node<T> right;

    public Node(T value) {
        this.value = value;
        right = null;
        left = null;
    }

    public String toString() {
        return value + "";
    }

}
