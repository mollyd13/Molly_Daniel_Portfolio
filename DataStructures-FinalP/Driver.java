import java.util.Scanner;

public class Driver {

    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {

        GeomBinTree geom = new GeomBinTree();
        Node<String> current = geom.root;
        Frame f = new Frame(geom);
        boolean isRunning = true;

        //main interaction loop
        while (isRunning) {
            System.out.println();
            System.out.println("Please choose an option: ");
            System.out.println("1: add root");
            System.out.println("2: go left");
            System.out.println("3: go right");
            System.out.println("4: add left child");
            System.out.println("5: add right child");
            System.out.println("6: go to root");
            System.out.println("7: quit");
            System.out.println();

            int response = input.nextInt();
            input.nextLine();

            switch (response) {
                //add root
                case 1:
                    if (geom.isEmpty()){
                        System.out.println("What value would you like to assign the root?");
                        current = geom.addRoot(input.nextLine(), current);
                        System.out.println(current.value + " is now the root.");
                    }
                    else{
                        System.out.println("Root already exists.");
                    }
                    break;
                //go left
                case 2:
                    System.out.println("Navigating left...");
                    current = geom.goLeft(current);
                    if (current != null) {
                        System.out.println("Current node: " + current.toString());
                    }
                    break;
                //go right
                case 3:
                    System.out.println("Navigating right...");
                    current = geom.goRight(current);
                    if (current != null) {
                        System.out.println("Current node: " + current.toString());
                    }
                    break;
                //add left child
                case 4:
                    System.out.println("What value would you like to give the left child?");
                    String x = input.nextLine();
                    System.out.println("Adding " + x + " as the left child...");
                    geom.addLeft(x, current);
                    break;
                //add right child
                case 5:
                    System.out.println("What value would you like to give the right child?");
                    String y = input.nextLine();
                    System.out.println("Adding " + y + " as the right child...");
                    geom.addRight(y, current);
                    break;
                //go to root
                case 6:
                    current = geom.goToRoot();
                    System.out.println("Returning to root...");
                    if (current != null) {
                        System.out.println("Current node: " + current.toString());
                    }
                    break;
                //quit
                case 7:
                    System.out.println("Quitting");
                    isRunning = false;
                    f.dispose();
                    break;
                //in case an invalid option is entered
                default:
                    System.out.println("Please enter a valid option");
                    break;
            }
            f.repaint();
        }

    }
}
