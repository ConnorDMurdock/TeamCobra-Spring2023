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
    //index 0 = helmet, index 1 = chest plate, index 2 = leggings
    private Equipment[] playerEquipment;

    //Constructor. The default values for the player are:
    //remaining lives = 3
    //damage reduction = 0
    //current room ID = 1
    public Player(int hitPoints, int damageDealt, String name, int currentRoomID) {
        super(hitPoints, damageDealt, name, 1);
        this.remainingLives = 3;
        this.damageReduction = 0;
        this.playerInventory = new ArrayList<>();
        this.playerEquipment = new Equipment[3];
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

    public Item removeItemFromInventory(String itemName) {
        Item item = null;
        for (int i = 0; i < playerInventory.size(); i++){
            if (playerInventory.get(i).getItemName){
                item = playerInventory.get(i);
            }
        }
        return item;
    }

    public ArrayList<String> checkInventory() {
        ArrayList<String> inventoryLines = new ArrayList<>();
        for (int i = 0; i < playerInventory.size(); i++){
            Item item = playerInventory.get(i);
            inventoryLines.add(item.getItemName + ": " + item.getItemDescription);
        }
        return inventoryLines;
    }

    public int attack(){
        return damageDealt;
    }

    public void loseLife(){
        this.remainingLives = remainingLives - 1;
    }

    public void heal(int healthRestored){
        int newHitPoints = hitPoints + healthRestored;
        if (newHitPoints > 100){
            newHitPoints = 100;
        }
        this.hitPoints = newHitPoints;
    }


    /*Sets the player's hit points to a number equal to hit points minus incoming damage. Simulates taking damage during combat.
     * rather than just taking damage, the incoming damage is decreased by the player's current equipment
     * The amount of damage dealt will be rounded to the nearest integer
     * e.g. player has 100 HP and 0.5 (50%) damage reduction. Player takes 10 damage, 10 * 0.5 = 5, player only takes 5 damage. New HP is 95
     */
    @Override public void takeDamage(int damage) {
        this.hitPoints = hitPoints - Math.round(damage * damageReduction);
    }
}
