import java.util.ArrayList;

public class Pigeon {

    /* pigeon attributes */
    int xpos;
    int ypos;
    ArrayList<String> inventory;
    float money;
    int worms;

    /* pigeon constructor */
    public Pigeon() {
        this.xpos = 0;
        this.ypos = 0;
        inventory = new ArrayList<String>();
        this.money = 0.00f;
        this.worms = 0;
    }

    /* changes the pigeon's coordinates based on the inputted direction
     * @param direction the way the user would like to move
     */
    public void move(String direction) {
        switch (direction) {
            case "n":
                ypos += 1;
                System.out.println("New pos: " + "(" + xpos + ", " + ypos + ")");
                break;
            case "s":
                ypos -= 1;
                System.out.println("New pos: " + "(" + xpos + ", " + ypos + ")");
                break;
            case "e":
                xpos += 1;
                System.out.println("New pos: " + "(" + xpos + ", " + ypos + ")");
                break;
            case "w":
                xpos -= 1;
                System.out.println("New pos: " + "(" + xpos + ", " + ypos + ")");
                break;

        }
    }

    /* adds an item to the inventory
     * @param item the thing you would like to grab
     */
    public void grab(String item) {
        inventory.add(item);
        System.out.println(
                item + " has been added to your inventory. Use drop to get rid of it.");
    }

    /* removes an item from the inventory
     * @param item the thing you would like to drop
     */
    public void drop(String item) {
        if (inventory.contains(item)) {
            inventory.remove(item);
            System.out.println(item + " removed from your inventory.");
        } else {
            System.out.println("Cannot drop " + item + " because it is not in your inventory.");
        }
    }

    /* tells the user whether they can afford an item
     * @param item the thing you want to know if you can afford
     * @return whether or not they have enough money to buy the item
     */
    public boolean canAfford(String item) {
        if (item.equals("Water")) {
            return money >= 2;
        }
        if (item.equals("Bread")) {
            return money >= 4;
        }
        if (item.equals("Milk")) {
            return money >= 3;
        } else {
            return money >= 15;
        }
    }
}
