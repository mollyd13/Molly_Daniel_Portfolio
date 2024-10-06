import java.util.Scanner;
public class PlayGame {

    public static void main(String[] args) {

        /*tells us whether the game is still running*/
        boolean isRunning = false;

        /*gets input from the user*/
        Scanner input = new Scanner(System.in);

        /*Storage for user's responses*/
        String userResponse = "";

        System.out.println("Welcome to Pigeon, enter \"look around\" to begin the game!");

        /*creates new map*/
        Map myMap = new Map();

        /*gets user response*/
        userResponse = input.nextLine();

       /* prompts the user to start the game */
        do {
            if (userResponse.toLowerCase().equals("look around")){
                System.out.println("You are awoken by the sound of your hungry baby pigeons.");
                    System.out.println("You look around your nest, and are faced with the fact that you have no more worms to give them.");
                    System.out.println("It's time for an adventure...");
                    System.out.println("Enter \"go\" plus a direction to begin your journey. If you are stuck at any time, enter \"help\" for a list of commands.");
                    isRunning = true;
            }
            else{
                System.out.println("Please enter \"look around\" to get started.");
                userResponse = input.nextLine();
            }
        } while (!isRunning);

        /* main game loop*/
        do {

            userResponse = input.nextLine();

            /* calls movement function and checks pigeon's location */
            if (userResponse.toLowerCase().contains("go") || userResponse.toLowerCase().contains("walk") || userResponse.toLowerCase().contains("fly")){
                if (userResponse.toLowerCase().contains("north")){
                    if (myMap.canMoveNorth){
                        myMap.myPigeon.move("n");
                        myMap.checkLocation();
                    }
                    else{
                        System.out.println("The path towards your north is obstructed");
                     }
                }
                else if (userResponse.toLowerCase().contains("south")){
                    if (myMap.canMoveSouth){
                        myMap.myPigeon.move("s");
                        myMap.checkLocation();
                    }
                    else{
                        System.out.println("The path towards your south is obstructed");
                     }
                }
                else if (userResponse.toLowerCase().contains("east")){
                    if (myMap.canMoveEast){
                        myMap.myPigeon.move("e");
                        myMap.checkLocation();
                    }
                    else{
                        System.out.println("The path towards your east is obstructed");
                     }
                }
                else if (userResponse.toLowerCase().contains("west")){
                    if (myMap.canMoveWest){
                        myMap.myPigeon.move("w");
                        myMap.checkLocation();
                    }
                    else{
                        System.out.println("The path towards your west is obstructed");
                     }
                }
                else {
                    System.out.println("Please enter go + north, south, east, or west.");
                }
            }

            /* calls the drop function on pigeon */
            else if (userResponse.toLowerCase().contains("drop") || userResponse.toLowerCase().contains("discard")){
                if (userResponse.toLowerCase().contains("worm")){
                    myMap.myPigeon.worms -= 1;
                    System.out.println("You have dropped a worm. You now have " + myMap.myPigeon.worms + " worms.");
                }
                else if (userResponse.toLowerCase().contains("ball")){
                    myMap.myPigeon.drop("ball");
                }
                else if (userResponse.toLowerCase().contains("bread")){
                    myMap.myPigeon.drop("Bread");
                }
                else if (userResponse.toLowerCase().contains("milk")){
                    myMap.myPigeon.drop("Milk");
                }
                else if (userResponse.toLowerCase().contains("key")){
                    myMap.myPigeon.drop("Key");
                }
                else if (userResponse.toLowerCase().contains("water")){
                    myMap.myPigeon.drop("Water");
                }
            }

            /* gives possible commands*/
            else if (userResponse.toLowerCase().contains("help")){
                System.out.println("The possible commands are: ");
                System.out.println("- go + north, south, east, or west");
                System.out.println("- show inventory");
                System.out.println("- drop + an item from your inventory");
                System.out.println("- leave to exit a location");
                System.out.println("- talk to interact with an NPC");
                System.out.println("- grab to take an item");
                System.out.println("- quit to exit the game (your progress will not be saved)");
            }

            /* exits the game*/
            else if (userResponse.toLowerCase().contains("quit") || userResponse.toLowerCase().contains("exit") || userResponse.toLowerCase().contains("stop")){
                System.out.println("exiting the game...");
                isRunning = false;
                input.close();
            }

            /* prints inventory and amount of worms and money */
            else if (userResponse.toLowerCase().equals("show inventory")) {
                System.out.println("inventory: ");
                for (int i=0; i<myMap.myPigeon.inventory.size(); i++){
                    System.out.println(myMap.myPigeon.inventory.get(i));
                }
                System.out.println("$" + myMap.myPigeon.money);
                System.out.println("Worms: " + myMap.myPigeon.worms);
            }

            else{
                System.out.println("\"" + userResponse + "\"" + " is not a valid command. Enter \"help\" for a list of valid commands");
            }

        } while (isRunning);
 }
}