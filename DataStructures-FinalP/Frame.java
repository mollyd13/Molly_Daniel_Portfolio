import javax.swing.*;

//frame class from our previous hw -- pretty much unchanged
public class Frame extends JFrame {
    Panel panel;

    Frame(GeomBinTree g) {
        panel = new Panel(g);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.add(panel);
        this.pack();
    }
}
