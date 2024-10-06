import java.util.ArrayList;
import java.util.HashMap;

//creates a geometric representaion of a binary tree
public class GeomBinTree extends BinTree<String> {

    // connects nodes to their points
    HashMap<Node<String>, Point> nodesToPoints;
    // array list of edges that connect points
    ArrayList<Edge> edges;
    // represents the current node/point
    Point currentP;
    // used for creating new points -- the xOffset changes as rows increase
    int xOffset;
    int yOffset;

    // constructor
    public GeomBinTree() {
        super();
        nodesToPoints = new HashMap<>();
        edges = new ArrayList<Edge>();
        // set initial x and y offset
        xOffset = 75;
        yOffset = 100;
        currentP = new Point(0, 0, "");
    }

    // adds a point for the root and sets the currentP equal to the new root
    @Override
    public Node<String> addRoot(String value, Node<String> current) {
        Node<String> newRoot = super.addRoot(value, current);
        nodesToPoints.put(newRoot, new Point(250, 50, value));
        currentP = nodesToPoints.get(newRoot);
        return newRoot;
    }

    // adds a corresponding point to the new left node and adds an edge between
    @Override
    public void addLeft(String value, Node<String> current) {
        if (isEmpty()) {
            System.out.println("Add a root before adding any children.");
            return;
        } else if (current.left != null) {
            System.out.println("Left child already exists.");
            return;
        } else {
            super.addLeft(value, current);
            nodesToPoints.put(current.left, new Point(currentP.xcord - xOffset, currentP.ycord + yOffset, value));
            edges.add(new Edge(currentP.xcord, currentP.ycord, nodesToPoints.get(current.left).xcord,
                    nodesToPoints.get(current.left).ycord));
        }
    }

    // adds a corresponding point to the new right node and adds an edge between
    @Override
    public void addRight(String value, Node<String> current) {
        if (isEmpty()) {
            System.out.println("Add a root before adding any children.");
            return;
        } else if (current.right != null) {
            System.out.println("Right child already exists.");
            return;
        } else {
            super.addRight(value, current);
            nodesToPoints.put(current.right, new Point(currentP.xcord + xOffset, currentP.ycord + yOffset, value));
            edges.add(new Edge(currentP.xcord, currentP.ycord, nodesToPoints.get(current.right).xcord,
                    nodesToPoints.get(current.right).ycord));
        }
    }

    // changes the currentP coordinates to the current node's right child and
    // decreases the x offset
    @Override
    public Node<String> goRight(Node<String> current) {
        if (!isEmpty() && current.right != null) {
            currentP = nodesToPoints.get(current.right);
            // decrease the x offset for every row to prevent overlap
            xOffset -= 20;
        }
        return super.goRight(current);
    }

    // changes the currentP coordinates to the current node's left child and
    // decreases the x offset
    @Override
    public Node<String> goLeft(Node<String> current) {
        if (!isEmpty() && current.left != null) {
            currentP = nodesToPoints.get(current.left);
            // decrease the x offset for every row to prevent overlap
            xOffset -= 20;
        }
        return super.goLeft(current);
    }

    // returns the currentP to the root's coordinates
    @Override
    public Node<String> goToRoot() {
        if (!isEmpty()) {
            currentP = nodesToPoints.get(root);
            xOffset = 75;
        }
        return super.goToRoot();
    }

    // traversal: recursively gives an arraylist of points in order determined by
    // type of traversal

    public ArrayList<Point> infixTravP(Node<String> current, ArrayList<Point> list) {
        // base case
        if (nodesToPoints.containsKey(current)) {
            infixTravP(current.left, list);
            list.add(nodesToPoints.get(current));
            infixTravP(current.right, list);
        }
        return list;
    }

    public ArrayList<Point> infixTravPW() {
        ArrayList<Point> list = new ArrayList<>();
        return infixTravP(root, list);
    }

    public ArrayList<Point> prefixTravP(Node<String> current, ArrayList<Point> list) {
        // base case
        if (nodesToPoints.containsKey(current)) {
            list.add(nodesToPoints.get(current));
            prefixTravP(current.left, list);
            prefixTravP(current.right, list);
        }
        return list;
    }

    public ArrayList<Point> prefixTravPW() {
        ArrayList<Point> list = new ArrayList<>();
        return prefixTravP(root, list);
    }

    public ArrayList<Point> postfixTravP(Node<String> current, ArrayList<Point> list) {
        // base case
        if (nodesToPoints.containsKey(current)) {
            postfixTravP(current.left, list);
            postfixTravP(current.right, list);
            list.add(nodesToPoints.get(current));
        }
        return list;
    }

    public ArrayList<Point> postfixTravPW() {
        ArrayList<Point> list = new ArrayList<>();
        return postfixTravP(root, list);
    }

}
