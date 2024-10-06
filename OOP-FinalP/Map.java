import java.util.Random;
import java.util.Scanner;

public class Map {

    /* gets and stores the user's input */
    public static Scanner input = new Scanner(System.in);
    String userResponse;

    /* booleans for the check location method */
    public boolean canMoveNorth = false;
    public boolean canMoveSouth = true;
    public boolean canMoveEast = false;
    public boolean canMoveWest = true;

    /* quest specific booleans */
    public boolean childQuestComplete = false;
    public boolean womanQuestComplete = false;
    public boolean inStore;
    public boolean inFountain;
    public boolean inSewer;
    public boolean isUnlocked = false;
    public boolean talkedToWoman;

    /* creates a new pigeon */
    Pigeon myPigeon = new Pigeon();

    /* empty map constructor */
    public Map() {
    }

    /*
     * method that is called when you enter the nest, asks the user if they would
     * like to end the game and acts accordingly
     */
    public void nest() {
        System.out.println(
                "Would you like to return to the nest? This is an irreversible decision that will end the game.");
        userResponse = input.nextLine();
        if (userResponse.toLowerCase().equals("yes")) {
            if (myPigeon.worms >= 2) {
                System.out.println(
                        "You have returned to the nest with " + myPigeon.worms
                                + " worms. Congratulations, both of your baby pigeons have survived! Enter \"quit\" to exit the game");

            } else if (myPigeon.worms == 1) {
                System.out.println(
                        "You have returned to the nest with 1 worm, meaning one of your baby pigeons will be fed and the other may suffer a grim fate :(  Enter \"quit\" to exit the game");
            } else {
                System.out.println(
                        "You have returned to the nest but unfortunately have no worms to feed your babies. Their fate will remain uncertain... Enter \"quit\" to exit the game");
            }
        } else {
            System.out.println("Okay, please enter go and a direction to continue the game");
        }
    }

    /*
     * method that is called when the player enters the park, starts the child's
     * quest and gives a worm if it is completed
     */
    public void park() {

        if (childQuestComplete) {
            System.out
                    .println("You return to the park and see the child is now smiling and playing with his friends. ");
            return;
        } else {
            System.out.println(
                    "You enter a park and see a child gazing at a tree longingly. It seems as though his soccer ball is stuck in the tree.");
            System.out.println("Maybe if you talk to him you could help him out...");
        }

        while (!childQuestComplete) {
            userResponse = input.nextLine();

            if (userResponse.toLowerCase().contains("talk") || userResponse.toLowerCase().contains("give")) {
                if (myPigeon.inventory.contains("Ball")) {
                    System.out.println("Wow thanks mr. pigeon! Here, to reward you for your efforts take this worm!");
                    myPigeon.inventory.remove("Ball");
                    myPigeon.worms += 1;
                    System.out.println("1 worm has been added to your inventory.");
                    childQuestComplete = true;
                } else {
                    System.out.println(
                            "\"Hi there mr. pigeon! You can fly, could you help me get my ball from the tree please?\"");
                }
            }

            else if (userResponse.toLowerCase().contains("fly") || userResponse.toLowerCase().contains("go to")) {
                System.out.println("You fly up to the top of the tree. The ball is right in front of you...");
            }

            else if (userResponse.toLowerCase().contains("grab") || userResponse.toLowerCase().contains("take")) {
                myPigeon.grab("Ball");
                System.out.println("Got it! You should speak to the boy again to tell him you have his ball. ");
            }

            else if (userResponse.toLowerCase().contains("return") || userResponse.toLowerCase().contains("go down")) {
                System.out.println("You fly back down to the boy. You should give him his ball.");
            }

            else if (userResponse.toLowerCase().contains("leave")) {
                myPigeon.xpos = -2;
                myPigeon.ypos = 0;
                System.out.println("You have left the park and are now at (" + myPigeon.xpos + ", "
                        + myPigeon.ypos + ").");
                return;
            }

            else {
                System.out.println("Hint: The boy wants you to fly up to the tree and grab his ball.");
            }

        }
    }

    /*
     * method that is called when the player enters the river, starts the old
     * woman's quest and gives a worm if it is completed
     */
    public void river() {
        if (womanQuestComplete) {
            System.out.println(
                    "As you return to the river, you see the old woman feeding the ducks with a smile on her face.");
            return;
        } else {
            System.out.println("You waddle on and see a bench on the edge of a river.");
            System.out.println(
                    "On the bench, an old woman is sitting with an empty bread bag. She appears to be sad, maybe you should ask her what's wrong.");
        }
        while (!womanQuestComplete) {
            userResponse = input.nextLine();
            if (userResponse.contains("talk") || userResponse.contains("ask")
                    || userResponse.contains("what's wrong")) {
                if (talkedToWoman) {
                    System.out.println(
                            "Good to see you again friend! Do you have bread to give me?");
                    userResponse = input.nextLine();
                    if (userResponse.contains("give") || (userResponse.contains("yes"))
                            || (userResponse.contains("no"))) {
                        if (myPigeon.inventory.contains("Bread")) {
                            System.out.println(
                                    "Thank you so much friend! The ducks and I are very grateful for your efforts. Here, take this worm as a token of my gratitude!");
                            myPigeon.inventory.remove("Bread");
                            myPigeon.worms += 1;
                            System.out.println("1 worm has been added to your inventory. You now have " + myPigeon.worms
                                    + " worms total.");
                            womanQuestComplete = true;
                            break;
                        } else {
                            System.out.println("You have no bread to give her. ");
                            break;
                        }
                    }
                } else {
                    System.out.println(
                            "\"Good afternoon friend! Do you think you could help an old woman out? I sit here every Sunday to feed the ducks but I seem to have run out of bread.\"");
                    System.out.println(
                            "\"I believe there's a store nearby that has my favorite bread to give them. Would you mind bringing me some?\"");
                    talkedToWoman = true;
                    break;
                }
            } else if (userResponse.contains("leave")) {
                myPigeon.xpos = -2;
                myPigeon.ypos = -1;
                System.out.println("You have left the river and are now at (" + myPigeon.xpos + ", "
                        + myPigeon.ypos + ").");
                return;
            }

            else {
                System.out.println("Hint: talk to the woman and see how you can help her.");
            }
        }
    }

    /*
     * method that is called when the player enters the store, prompts the user with
     * available items to buy
     */
    public void store() {
        System.out.println("You enter the store, dodging shopping carts and eager humans.");
        System.out.println("As you walk through the aisles you see: ");
        System.out.println("- Water $2");
        System.out.println("- Bread $4");
        System.out.println("- Milk $3");
        System.out.println("- A mysterious key to an unknown location? $15");
        System.out.println("Enter \"buy\" + an item or \"leave\" to exit the store");
        System.out.println("Your current balance is: $" + myPigeon.money);
        inStore = true;

        while (inStore) {
            userResponse = input.nextLine();

            switch (userResponse) {
                case "buy water":
                    if (myPigeon.canAfford("Water")) {
                        myPigeon.money -= 2;
                        myPigeon.inventory.add("Water");
                        System.out.println("Water added to your inventory.");
                        System.out.println("Your current balance is: $" + myPigeon.money);
                        break;
                    } else {
                        System.out.println("You cannot afford water.");
                        break;
                    }
                case "buy bread":
                    if (myPigeon.canAfford("Bread")) {
                        myPigeon.money -= 4;
                        myPigeon.inventory.add("Bread");
                        System.out.println("Bread added to your inventory.");
                        System.out.println("Your current balance is: $" + myPigeon.money);
                        break;
                    } else {
                        System.out.println("You cannot afford Bread.");
                        break;
                    }
                case "buy milk":
                    if (myPigeon.canAfford("Milk")) {
                        myPigeon.money -= 3;
                        myPigeon.inventory.add("Milk");
                        System.out.println("Milk added to your inventory.");
                        System.out.println("Your current balance is: $" + myPigeon.money);
                        break;
                    } else {
                        System.out.println("You cannot afford Milk.");
                        break;
                    }
                case "buy key":
                    if (myPigeon.canAfford("Key")) {
                        myPigeon.money -= 15;
                        myPigeon.inventory.add("Key");
                        System.out.println("Key added to your inventory.");
                        System.out.println("Your current balance is: $" + myPigeon.money);
                        break;
                    } else {
                        System.out.println("You cannot afford Key.");
                        break;
                    }
                case "leave":
                    myPigeon.xpos = 1;
                    myPigeon.ypos = -2;
                    System.out.println(
                            "You have left the store and are now in the parking lot at (" + myPigeon.xpos + ", "
                                    + myPigeon.ypos + ").");
                    inStore = false;
                    break;
                default:
                    System.out.println("Enter \"buy\" + an item or \"leave\" to exit the store");
                    break;
            }

        }
    }

    /*
     * method that is called when the player enters the town square, when the
     * fountain is approached the user can grab coins from it
     */
    public void square() {
        System.out.println("You enter into a bustling town square, crowded with children and families.");
        System.out.println(
                "In the center of the square, you see a bubbling fountain. It appears to be filled with coins from people making wishes...");

        while (!inFountain) {
            userResponse = input.nextLine();
            if (userResponse.contains("approach") || userResponse.contains("go to")) {
                System.out.println(
                        "You approach the fountain and use your little legs to propel yourself onto the ledge. You look down and see hundreds of coins within arms reach.");
                inFountain = true;
            } else {
                System.out.println("Hint: Approaching the fountain could prove beneficial.");
            }
        }
        while (inFountain) {
            userResponse = input.nextLine();
            if (userResponse.contains("grab")) {
                float[] amounts = { 0.10f, 0.25f, 0.50f, 1 };
                Random amount = new Random();
                int index = amount.nextInt(amounts.length);
                float randomValue = amounts[index];
                myPigeon.money += randomValue;
                System.out.println(
                        "You have grabbed $" + randomValue + " You now have $" + myPigeon.money + " in total.");
            } else if (userResponse.contains("leave")) {
                inFountain = false;
                myPigeon.xpos = 2;
                myPigeon.ypos = 1;
                System.out.println("You have left the fountain and are now back in the square at (" + myPigeon.xpos
                        + ", " + myPigeon.ypos + ").");
                return;
            } else {
                System.out.println("Hint: grab the coins from the fountain or enter \"leave\" to leave");
            }
        }

    }

    /*
     * method that is called when the player enters the sewer, the manhole can only
     * be opened with a key and then worms can be grabbed from it
     */
    public void sewer() {
        System.out.println(
                "You reach the manhole cover and see that it is locked. Through the cracks, you can see that inside the manhole is hundreds of worms!");
        inSewer = true;
        while (inSewer) {
            userResponse = input.nextLine();
            if (userResponse.contains("open") || userResponse.contains("unlock")) {
                if (myPigeon.inventory.contains("Key")) {
                    isUnlocked = true;
                    System.out.println(
                            "You twist the key in the lock with your beak and push it aside. You stare at the now open manhole and see that all of those worms are now within your reach.");
                    myPigeon.drop("Key");
                } else {
                    System.out.println("You have no way to unlock the manhole.");
                }
            } else if (userResponse.contains("leave")) {
                inSewer = false;
                myPigeon.xpos = 3;
                myPigeon.ypos = -2;
                System.out.println("You have left the manhole and are now back in the alley at (" + myPigeon.xpos
                        + ", " + myPigeon.ypos + ").");

            } else {
                System.out.println("Hint: enter \"leave\" to exit the manhole or \"open\" to open it.");
            }
        }

        while (isUnlocked) {
            userResponse = input.nextLine();
            if (userResponse.contains("grab") || userResponse.contains("take")) {
                myPigeon.worms += 1;
                System.out.println(
                        "1 worm has been added to your inventory. You now have " + myPigeon.worms + " worms total.");
            } else if (userResponse.contains("leave")) {
                myPigeon.xpos = 3;
                myPigeon.ypos = -2;
                System.out.println("You have left the manhole and are now at (" + myPigeon.xpos + ", "
                        + myPigeon.ypos + ").");
                return;
            } else {
                System.out.println("Hint: enter \"grab\" to take a worm from the hole or \"leave\" to leave.");
            }
        }

    }

    /*
     * changes the movement booleans and gives different messages depending on the
     * pigeon's location
     */
    public void checkLocation() {

        if (myPigeon.xpos == 0 && myPigeon.ypos == 0) {
            nest();
            canMoveNorth = false;
            canMoveEast = false;
            canMoveSouth = true;
            canMoveWest = true;
        }

        else if (myPigeon.xpos == -2 && myPigeon.ypos == -2) {
            river();
            canMoveNorth = true;
            canMoveEast = true;
            canMoveSouth = false;
            canMoveWest = false;
        }

        else if (myPigeon.xpos == -2 && myPigeon.ypos == 1) {
            park();
            canMoveNorth = false;
            canMoveEast = true;
            canMoveSouth = true;
            canMoveWest = false;
        }

        else if (myPigeon.xpos == 2 && myPigeon.ypos == 1) {
            square();
            canMoveNorth = false;
            canMoveEast = false;
            canMoveSouth = true;
            canMoveWest = true;
        }

        else if (myPigeon.xpos == 1 && myPigeon.ypos == 1) {
            sewer();
            canMoveNorth = false;
            canMoveEast = true;
            canMoveSouth = false;
            canMoveWest = false;
        }

        else if (myPigeon.xpos == 2 && myPigeon.ypos == -2) {
            store();
            canMoveNorth = true;
            canMoveEast = false;
            canMoveSouth = false;
            canMoveWest = true;
        }

        else if (myPigeon.xpos == 0 && myPigeon.ypos == -1) {
            System.out.println(
                    "You hear the distant sound of running water to your southwest. It may prove beneficial to follow it...");
            canMoveNorth = true;
            canMoveEast = true;
            canMoveSouth = false;
            canMoveWest = true;
        }

        else if (myPigeon.xpos == 1 && myPigeon.ypos == -1) {
            System.out.println(
                    "You waddle along the sidewalk, making sure to avoid the big clumsy feet of humans passing by.");
            canMoveNorth = false;
            canMoveEast = true;
            canMoveSouth = true;
            canMoveWest = true;
        }

        else if (myPigeon.xpos == 1 && myPigeon.ypos == -2) {
            System.out.println(
                    "You enter a parking lot full of shopping carts. You look around and see a store to your east.");
            canMoveNorth = true;
            canMoveEast = true;
            canMoveSouth = false;
            canMoveWest = false;
        }

        else if (myPigeon.xpos == 2 && myPigeon.ypos == -1) {
            System.out.println(
                    "You enter a parking lot full of shopping carts. You look around and see a store to your south.");
            canMoveNorth = true;
            canMoveEast = true;
            canMoveSouth = true;
            canMoveWest = true;
        }

        else if (myPigeon.xpos == 2 && myPigeon.ypos == 0) {
            System.out.println(
                    "You hear the distant sound of a fountain, and the number of people around you seems to increase");
            canMoveNorth = true;
            canMoveEast = false;
            canMoveSouth = true;
            canMoveWest = false;
        }

        else if (myPigeon.xpos == -1 && myPigeon.ypos == -1) {
            System.out.println("The ground seems to be getting more damp. Maybe there's some water around here...");
            canMoveNorth = true;
            canMoveEast = true;
            canMoveSouth = true;
            canMoveWest = true;
        }

        else if (myPigeon.xpos == -1 && myPigeon.ypos == -2) {
            System.out.println("You can hear ducks quacking and water running to your west. ");
            canMoveNorth = true;
            canMoveEast = false;
            canMoveSouth = false;
            canMoveWest = true;
        }

        else if (myPigeon.xpos == -2 && myPigeon.ypos == -1) {
            System.out.println("You can see a river to your south. Maybe you should explore it...");
            canMoveNorth = true;
            canMoveEast = true;
            canMoveSouth = true;
            canMoveWest = false;
        }

        else if (myPigeon.xpos == -2 && myPigeon.ypos == 0) {
            System.out.println(
                    "As you waddle along the tree lined path, you begin to hear children playing to your north.");
            canMoveNorth = true;
            canMoveEast = true;
            canMoveSouth = true;
            canMoveWest = false;
        }

        else if (myPigeon.xpos == -1 && myPigeon.ypos == 0) {
            System.out.println(
                    "To your east you can see your hungry baby pigeons crying. It is essential that you bring them worms before it's too late.");
            canMoveNorth = true;
            canMoveEast = true;
            canMoveSouth = true;
            canMoveWest = true;
        }

        else if (myPigeon.xpos == -1 && myPigeon.ypos == 1) {
            System.out.println(
                    "You see children playing at a park to your west. I wonder if they could be any help on your journey...");
            canMoveNorth = false;
            canMoveEast = false;
            canMoveSouth = true;
            canMoveWest = true;
        }

        else if (myPigeon.xpos == 3 && myPigeon.ypos == -1) {
            System.out.println(
                    "To your southwest you can hear the voices of excited customers and the rolling of shopping carts. You seem to have reached a dead end...");
            canMoveNorth = false;
            canMoveEast = false;
            canMoveSouth = true;
            canMoveWest = true;
        }

        else if (myPigeon.xpos == 3 && myPigeon.ypos == -2) {
            System.out.println(
                    "Oh? Maybe it was not a dead end after all. You have entered a dark alley, illuminated only by a dull glow that seems to be coming from some sort of manhole to your south");
            canMoveNorth = false;
            canMoveEast = false;
            canMoveSouth = true;
            canMoveWest = true;
        }

        else if (myPigeon.xpos == 3 && myPigeon.ypos == -3) {
            sewer();
            canMoveNorth = true;
            canMoveEast = false;
            canMoveSouth = false;
            canMoveWest = false;
        }
    }
}
