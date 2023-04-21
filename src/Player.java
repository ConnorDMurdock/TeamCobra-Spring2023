import java.util.ArrayList;

public class Player extends Entity
{
    /* Class created by: Connor Murdock
     * The Player class extends entity. This class handles all the information regarding the player
     * This class contains information about the remaining lives, damage reduction, and inventory of the player
     */

    //Remaining lives is the number of lives the player has before reaching a game over
    private int remainingLives;

    //Damage reduction is the percent of damage blocked by currently equipped equipment
    //Should never be less than 0 or greater than 1
    private float damageReduction;

    //The player's inventory of items. The player can only carry 20 items at a time
    private ArrayList<Item> playerInventory;

    //The player's equipped items. The player has three spots for equipment: helmet, chest plate, and leggings.
    //index 0 = leggings, index 1 = chest plate, index 2 = helmet, index 3 = shield
    private Equipment[] playerEquipment;

    //The player's equipped weapon. The player can only equip one weapon at a time.
    private UseItem playerWeapon;

    //Constructor. The default values for the player are:
    //remaining lives = 3
    //damage reduction = 0
    //equipment slots = 4
    //no weapon equipped
    public Player(int hitPoints, int damageDealt, String name, Room currentRoom) {
        super(hitPoints, damageDealt, name, currentRoom);
        this.remainingLives = 3;
        this.damageReduction = 0;
        this.playerInventory = new ArrayList<>();
        this.playerEquipment = new Equipment[4];
        this.playerWeapon = null;
    }

    //Adds a given item to the player's inventory
    //If the inventory already has 20 items in it, do not add the item because the inventory is full
    public void addItemToInventory(Item item) {
        if (playerInventory.size() < 20){
            playerInventory.add(item);
        }
        else {
            System.err.println("Your inventory is full!");
        }
    }

    //Takes in a String of the item's name given by the user, checks the player's inventory for that item, then returns that item if it exists.
    public Item removeItemFromInventory(String itemName) {
        Item item = null;
        for (Item value : playerInventory) {
            if (value.getName().equalsIgnoreCase(itemName)) {
                item = value;
                playerInventory.remove(value);
            }
        }
        return item;
    }

    //Returns an ArrayList of Strings, where each index is [item name: item description]
    //each index is for one item. The ArrayList size is equal to the # of items in the inventory
    public ArrayList<String> checkInventory() {
        ArrayList<String> inventoryLines = new ArrayList<>();
        for (Item item : playerInventory) {
            inventoryLines.add("[" + item.getName() + ": " + item.getDescription() + "]");
        }
        return inventoryLines;
    }

    //Makes the player lose a life. If the player has 0 live remaining, return true signaling a "Game Over"
    public boolean loseLife(){
        boolean gameOver = false;
        this.remainingLives = remainingLives - 1;
        if (remainingLives <= 0) {
            gameOver = true;
        }
        return gameOver;
    }

    //----Possibly change this function based on the useConsumable() function in Consumable
    public void heal(int healthRestored){
        int newHitPoints = hitPoints + healthRestored;
        if (newHitPoints > 100){
            newHitPoints = 100;
        }
        this.hitPoints = newHitPoints;
    }

    //----Finish this code once items have been added to the branch
    public void equipItem(String itemName) {
        Item item = null;
        for (int i = 0; i < playerInventory.size(); i++) {
            if (playerInventory.get(i).getName().equalsIgnoreCase(itemName)) {
                item = playerInventory.get(i);
            }
        }
        //check the equipment slot, then equip to the appropriate slot and update the player stats
        if (item == null) {
            System.err.println("You do not have that item!");
        }
    }

    public String[] getConnectionInDirection(String direction){
        //Gets the room information based on the given direction.
        //If connection = 0, no room is in that direction.
        String[] connectedRoomInfo = new String[2];
        switch (direction){
            case "North":
                connectedRoomInfo[0] = currentRoom.getRoomConnections()[0];
                connectedRoomInfo[1] = currentRoom.getDirectionText()[0];
            case "South":
                connectedRoomInfo[0] = currentRoom.getRoomConnections()[1];
                connectedRoomInfo[1] = currentRoom.getDirectionText()[1];
            case "West":
                connectedRoomInfo[0] = currentRoom.getRoomConnections()[2];
                connectedRoomInfo[1] = currentRoom.getDirectionText()[2];
            case "East":
                connectedRoomInfo[0] = currentRoom.getRoomConnections()[3];
                connectedRoomInfo[1] = currentRoom.getDirectionText()[3];
        }
        return connectedRoomInfo;
    }

    //Moves the player into the given room
    //displays the room enter text
    public void move(Room room){
        currentRoom = room;
        room.enterRoomText();
    }

    /* Sets the player's hit points to a number equal to hit points minus incoming damage. Simulates taking damage during combat.
     * rather than just taking damage, the incoming damage is decreased by the player's current equipment
     * The amount of damage dealt will be rounded to the nearest integer
     * e.g. player has 100 HP and 0.5 (50%) damage reduction. Player takes 10 damage, 10 * 0.5 = 5, player only takes 5 damage. New HP is 95
     * the dodged boolean is true when the player successfully dodges, and false when they do not
     */
    @Override
    public int takeDamage(int damage, boolean dodged) {
        int damageTaken = 0;
        if (!dodged) {
            damageTaken = Math.round(damage * damageReduction);
            this.hitPoints = hitPoints - damageTaken;
        }
        return damageTaken;
    }
}
