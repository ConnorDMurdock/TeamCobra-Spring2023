public class View {

/**
* displays items, puzzles and monsters in the room
*/

//Duaa Fatima
    public void printScanroom(Room room){
        room.scanRoom();
    }
    public void printRoomScan(Room room){
        room.scanRoom();
    }

    public void printRoomDesc(Room room){
        room.enterRoomText();
    }


 //Jonathan Hummel
    public void introductionPart1() {
        System.out.println("Oh champion, what is thou name?");
    }

    public void introductionPart2() {
        System.out.println(" Well " + String name() + " are you prepared to save the people who have known nothing but fear?\n" +
                " Are you brave enough to stand up to a god?\n" +
                " Or will you fall like the one's before you? End his mad reign and become the beacon of hope...");
    }
    //These functions display in the introduction of the game.


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
        System.out.println("-------");
        System.out.println("Player Lives:");
        System.out.println("The player has 3 lives for each play through of the game.");
        System.out.println("If the player's HP reaches 0 and they have remaining lives, they will respawn at their last checkpoint room.");
        System.out.println("------");
        System.out.println("To navigate through the game, select the direction you'd like to move in by entering one of the navigation commands listed above.");
    }

}
