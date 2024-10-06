import java.awt.*;

import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

//implements the ActionListener interface in order for the buttons to trigger actions
public class Panel extends JPanel implements ActionListener {

    GeomBinTree tree;

    //timer for animations
    Timer timer;

    //buttons for traversal control
    Button infixButton;
    Button prefixButton;
    Button postfixButton;
    Button reset;

    //graphics that are updated as tree is traversed
    Point traversalPoint;
    String traversalString;
    
    //variables for animation control
    int index;
    boolean isTraversing;
    boolean infixTrav;
    boolean prefixTrav;
    boolean postfixTrav;

    //points from the tree in infix, postfix, and prefix order so they can be iterated through in actionPerformed
    ArrayList<Point> infixPoints;
    ArrayList<Point> prefixPoints;
    ArrayList<Point> postfixPoints;

    //constructor
    Panel(GeomBinTree tree) {
        this.tree = tree;
        this.setPreferredSize(new Dimension(600, 600));
        setLayout(null);

        // create timer
        timer = new Timer(1000, this);
        timer.setInitialDelay(0);

        // initialize buttons
        infixButton = new Button("Infix Traversal");
        infixButton.setBounds(25, 525, 125, 50);
        add(infixButton);
        infixButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                infixTrav = true;
                index = 0;
                infixPoints = tree.infixTravPW();
                timer.start();
            }
        });

        prefixButton = new Button("Prefix Traversal");
        prefixButton.setBounds(165, 525, 125, 50);
        add(prefixButton);
        prefixButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                prefixTrav = true;
                index = 0;
                prefixPoints = tree.prefixTravPW();
                timer.start();
            }
        });

        postfixButton = new Button("Postfix Traversal");
        postfixButton.setBounds(310, 525, 125, 50);
        add(postfixButton);
        postfixButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                postfixTrav = true;
                index = 0;
                postfixPoints = tree.postfixTravPW();
                timer.start();
            }
        });

        reset = new Button("Reset");
        reset.setBounds(455, 525, 125, 50);
        add(reset);
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                isTraversing = false;
                repaint();
            }
        });

        // initialize bools to false
        prefixTrav = false;
        infixTrav = false;
        postfixTrav = false;
        
        // set traversal point to be at the current node
        traversalPoint = new Point(tree.currentP.xcord, tree.currentP.ycord, "");
        traversalString = "";

    }

    // drawing methods
    public void drawNode(Graphics g2D, Point p, Color color) {
        g2D.setColor(color);
        // label
        g2D.drawString(p.label, p.getXCord() + 5, p.getYCord() + 45);
        g2D.fillOval(p.getXCord(), p.getYCord(), 30, 30);
        g2D.setColor(Color.black);
        g2D.drawOval(p.getXCord(), p.getYCord(), 30, 30);

    }

    public void drawCrt(Graphics2D g2D) {
        g2D.setStroke(new BasicStroke(8));
        g2D.drawOval(tree.currentP.xcord, tree.currentP.ycord, 30, 30);
    }

    public void drawEdge(Graphics2D g2D, int x, int y, int x2, int y2) {
        g2D.setColor(Color.black);
        g2D.drawLine(x + 10, y + 10, x2 + 10, y2 + 10);
    }

    public void draw(Graphics2D g2D) {

        // paint edges
        for (Edge e : tree.edges) {
            drawEdge(g2D, e.x1, e.y1, e.x2, e.y2);
        }

        // paint nodes
        for (Point p : tree.nodesToPoints.values()) {
            drawNode(g2D, p, Color.blue);
        }

        // paint current node
        if (tree.currentP.xcord != 0 && tree.currentP.ycord != 0) {
            drawCrt(g2D);
        }

        // draw traversal node and traversal string
        if (isTraversing) {
            drawNode(g2D, traversalPoint, Color.green);
            g2D.setFont(new Font("Arial Black", Font.BOLD, 25));
            g2D.drawString(traversalString, 225, 475);
        }

    }

    public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.setStroke(new BasicStroke(3));
        draw(g2D);

    }

    //part of the actionListener interface -- is triggered by the timer starting
    public void actionPerformed(ActionEvent e) {

            //triggers the traversal node to be drawn
            isTraversing = true;
            
            //infix traversal
            if (infixTrav == true){
                if (index < infixPoints.size()){
                    //set the traversal point's coordinates equal to the subsequent point in the infix points ArrayList
                    traversalPoint.xcord = infixPoints.get(index).xcord;
                    traversalPoint.ycord = infixPoints.get(index).ycord;
                    traversalString += infixPoints.get(index).label + " ";
                    repaint();
                    index++;
                }
                else{
                    //stop the timer and clear the traversal string when iteration is done
                    infixTrav = false;
                    traversalString = "";
                    timer.stop();
                }
            }
            //prefix traversal
            else if (prefixTrav == true){
                if (index < prefixPoints.size()){
                    //set the traversal point's coordinates equal to the subsequent point in the prefix points ArrayList
                    traversalPoint.xcord = prefixPoints.get(index).xcord;
                    traversalPoint.ycord = prefixPoints.get(index).ycord;
                    traversalString += prefixPoints.get(index).label + " ";
                    repaint();
                    index++;
                }
                else{
                    //stop the timer and clear the traversal string when iteration is done
                    prefixTrav = false;
                    traversalString = "";
                    timer.stop();
                }
            }
            //postfix traversal
            else if (postfixTrav == true){
                if (index < postfixPoints.size()){
                    //set the traversal point's coordinates equal to the subsequent point in the postfix points ArrayList
                    traversalPoint.xcord = postfixPoints.get(index).xcord;
                    traversalPoint.ycord = postfixPoints.get(index).ycord;
                    traversalString += postfixPoints.get(index).label + " ";
                    repaint();
                    index++;
                }
                else{
                    //stop the timer and clear the traversal string when iteration is done
                    postfixTrav = false;
                    traversalString = "";
                    timer.stop();
                }
            }
    }

}
