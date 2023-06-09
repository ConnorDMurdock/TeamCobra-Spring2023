import java.util.ArrayList;

public class View {

    /* Game Functions
     * by Connor Murdock
     * prints various gameplay required functions to the user
     */

    //Prints any text given as a normal message
    public void genericPrint(String text){
        System.out.println(text);
    }

    //Prints any text given with red text
    public void errorPrint(String text){
        System.err.println(text);
    }

    //Prints the statement in red text. Used when an unknown command is passed in from the user
    public void commandError() {
        System.err.println("Unrecognized command");
    }

    public void gameOver() {
        System.err.println("This world will never know salvation...");
        System.out.println("Would you like to restart or quit the game?");
    }

    /**
    * displays items, puzzles and monsters in the room
    */

    //Duaa Fatima
    public void printScanRoom(Room room){
        room.scanRoom();
    }
    public void printRoomScan(Room room){
        room.scanRoom();
    }

    public void printRoomDesc(Room room){
        room.enterRoomText();
    }


    //Connor Murdock
    //Prints information regarding the player to the console

    //==Out-of-combat commands==
    public void playerStatus(String[] stats) {
        System.out.println("HP: " + stats[0]);
        System.out.println("Attack: " + stats[1]);
        System.out.println("Damage Taken: " + stats[2] + "%");
        System.out.println("Lives Remaining: " + stats[3]);
    }

    public void playerCheckInventory(ArrayList<String> inventoryLines) {
        if (inventoryLines.size() == 0){
            System.err.println("Your inventory is empty");
        }
        else {
            System.out.println("Your Inventory:");
            for (String line : inventoryLines){
                System.out.println(line);
            }
        }
    }

    public void playerItemPickup(String itemName) {
        System.out.println(itemName + " has been added to inventory");
    }

    public void playerItemDrop(String itemName) {
        System.out.println(itemName + " has been removed from inventory");
    }

    //==In-combat commands==
    public void damageTaken(String entityName, int damage) {
        System.out.println(entityName + " took " + damage + " damage!");
    }

    public void playerRun() {
        System.out.println("You escape from the combat encounter");
    }

    public void playerDodgePrepare() {
        System.out.println("You prepare to dodge the monster's next attack...");
    }

    public void playerDodgeSuccess() {
        System.out.println("You successfully dodged the attack!");
    }

    public void entityDefeated(String name) {
        System.out.println(name + " has been defeated.");
    }

    public void turnStart(String name) {
        System.out.println("It's now " + name + "'s turn");
    }

    public void playerTurn() {
        System.out.println("What would you like to do?");
    }

    public void combatHelp() {
        System.out.println("==Combat Commands==");
        System.out.println("Attack: attack the monster");
        System.out.println("Consume Potion: Use a health potion to heal");
        System.out.println("Dodge: dodge the monster's next attack, 25% success chance");
        System.out.println("Run: escape from combat");
        System.out.println("Status: see your current status");
        System.out.println("Help: see this menu");
    }

 //Jonathan Hummel
    public void introductionPart1() {
        System.out.println("Oh champion, what is thou name?");
    }

    public void introductionPart2(String name) {
        System.out.println(" Well " + name + " are you prepared to save the people who have known nothing but fear?\n" +
                " Are you brave enough to stand up to a god?\n" +
                " Or will you fall like the one's before you? End his mad reign and become the beacon of hope...");
    }
    //These functions display in the introduction of the game.

    /**
     * Puzzle Section
     */

    //Albert Austin IV

    //Print puzzle problem
    public void printPuzzleProblem(String puzzleProblem) {
        System.out.println(puzzleProblem);
    }

    //Print puzzle hint
    public void printPuzzleHint(String puzzleHint) {
        System.out.println(puzzleHint);
    }

    //Print puzzle solve attempt
    public void printPuzzleSolveAttempt(String puzzleSolveAttempt) {
        System.out.println(puzzleSolveAttempt);
    }


    /**
     * Displays the navigation commands used to traverse the map, such as North, South, East, and West, as well as all the commands the player can use to interact with items, enemies, and the player inventory.
     */

    //Muhammad Marenah
    public void displayHelpMenu() {
        System.out.println("-------- HELP MENU --------");
        System.out.println("Navigation Commands:");
        System.out.println("North - Move to the room to the north");
        System.out.println("South - Move to the room to the south");
        System.out.println("East - Move to the room to the east");
        System.out.println("West - Move to the room to the west");
        System.out.println("-------");
        System.out.println("Interaction Commands:");
        System.out.println("Scan Room - Display the current room's description, a list of any items in the room, a list of any monsters in the room, and/or any puzzles in the room.");
        System.out.println("Add [item name] - Add an item to the player's inventory");
        System.out.println("Remove [item name] - Remove an item from the player's inventory");
        System.out.println("Equip [item name] - Equip the item to the player from the player's inventory");
        System.out.println("Unequip [item name] - Unequip the item from the player and place it into the player's inventory");
        System.out.println("Status - Check player's health points and status");
        System.out.println("Fight [monster name] - Starts combat with the specified monster");
        System.out.println("Get Instructions - Get instructions for the room's puzzle and attempt to solve it");
        System.out.println("-------");
        System.out.println("Player Lives:");
        System.out.println("The player has 3 lives for each play through of the game.");
        System.out.println("If the player's HP reaches 0 and they have remaining lives, they will respawn at their last checkpoint room.");
        System.out.println("------");
        System.out.println("To navigate through the game, select the direction you'd like to move in by entering one of the navigation commands listed above.");
    }

}
