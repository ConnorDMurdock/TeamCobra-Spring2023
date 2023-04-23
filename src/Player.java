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
    private float percentOfDamageTaken;

    //The player's inventory of items. The player can only carry 20 items at a time
    private ArrayList<Item> playerInventory;

    //The player's equipped items. The player has three spots for equipment: helmet, chest plate, and leggings.
    //index 0 = leggings, index 1 = chest plate, index 2 = helmet, index 3 = weapon, index 4 = shield
    private Equipment[] playerEquipment;

    //The player's equipped weapon. The player can only equip one weapon at a time.
    private Equipment playerWeapon;

    /* Constructor. The default values for the player are:
     * remaining lives = 3
     * damage reduction = 1
     * equipment slots = 5 (index 0 is unused)
     * no weapon equipped
     */
    public Player(int hitPoints, int damageDealt, String name, Room currentRoom) {
        super(hitPoints, damageDealt, name, currentRoom);
        this.remainingLives = 3;
        this.percentOfDamageTaken = 1;
        this.playerInventory = new ArrayList<>();
        this.playerEquipment = new Equipment[6];
        this.playerWeapon = null;
    }

    //Adds a given item to the player's inventory.
    //If the inventory already has 20 items in it, do not add the item because the inventory is full.
    public void addItemToInventory(Item item) {
        playerInventory.add(item);
    }

    //returns true if the player's inventory is full (20 items or more).
    //returns false if the player's inventory is not full (19 or fewer items).
    public boolean isInventoryFull(){
        return playerInventory.size() >= 20;
    }

    //Takes in a String of the item's name given by the user, checks the player's inventory for that item, then returns that item if it exists.
    public Item removeItemFromInventory(String itemName) {
        Item item;
        for (Item value : playerInventory) {
            if (value.getName().equalsIgnoreCase(itemName)) {
                item = value;
                playerInventory.remove(value);
                return item;
            }
        }
        return null;
    }

    //Returns an ArrayList of Strings, where each index is [item name: item description].
    //each index is for one item. The ArrayList size is equal to the # of items in the inventory.
    public ArrayList<String> checkInventory() {
        ArrayList<String> inventoryLines = new ArrayList<>();
        if (playerInventory.size() > 0){
            for (Item item : playerInventory) {
                inventoryLines.add("[" + item.getName() + ": " + item.getDescription() + "]");
            }
        }
        return inventoryLines;
    }

    //Makes the player lose a life. If the player has 0 live remaining, return true signaling a "Game Over".
    public boolean loseLife(){
        boolean gameOver = false;
        this.remainingLives = remainingLives - 1;
        if (remainingLives <= 0) {
            gameOver = true;
        }
        return gameOver;
    }

    //Heals the player using the given consumable
    //If the consumable's remaining HP is 0 after use, remove from inventory
    public void heal(Consumable consumable){
        hitPoints = consumable.useConsumable(hitPoints);
        if (hitPoints > 100){
            hitPoints = 100;
        }
        if (consumable.getRemainingHP() <= 0){
            removeItemFromInventory(consumable.getName());
        }
    }

    public Consumable getConsumable() {
        Consumable consumable;
        for (Item value : playerInventory) {
            if (value.getName().equalsIgnoreCase("Health Potion")) {
                consumable = (Consumable)value;
                return consumable;
            }
        }
        return null;
    }

    //Equips the item that the user specifies, as long as the player has the item in their inventory and there is not already an item equipped to that slot.
    public void equipItem(String itemName) {
        Item item = null;
        for (int i = 0; i < playerInventory.size(); i++) {
            if (playerInventory.get(i).getName().equalsIgnoreCase(itemName)) {
                item = playerInventory.get(i);
            }
        }
        //check the equipment slot, then equip to the appropriate slot and update the player stats.
        if (item == null) {
            System.err.println("You do not have that item!");
        }
        else {
            try {
                Equipment equipment = (Equipment) item;
                int equipmentSlot = equipment.getEquipmentSlot();
                if (equipmentSlot == 4 && playerEquipment[4] == null){
                    playerEquipment[4] = equipment;
                    damageDealt += equipment.getItemStat();
                    playerInventory.remove(equipment);
                    System.out.println("equipped the " + equipment.getName());
                }
                else if (playerEquipment[equipmentSlot] == null) {
                    playerEquipment[equipmentSlot] = equipment;
                    percentOfDamageTaken -= equipment.getItemStat();
                    playerInventory.remove(equipment);
                    System.out.println("equipped the " + equipment.getName());
                }
                else {
                    System.err.println("You already have equipment in that slot!");
                }
            } catch (ClassCastException cce) {
                System.err.println("You can't equip that item");
            }
        }
    }

    //Unequips the item that the user specifies, as long as the item is equipped and the inventory is not full.
    public void unequipItem(String itemName){
        for (Equipment e : playerEquipment){
            if (e != null){
                if (itemName.equalsIgnoreCase(e.getName())){
                    if (isInventoryFull()){
                        System.err.println("Cannot unequip the " + e.getName() + ". Inventory is full!");
                    }
                    else {
                        addItemToInventory(e);
                        playerEquipment[e.getEquipmentSlot()] = null;
                        if (e.getEquipmentSlot() == 4){
                            damageDealt -= e.getItemStat();
                        }
                        else {
                            percentOfDamageTaken += e.getItemStat();
                        }
                        System.out.println("Unequipped the " + e.getName());
                    }
                }
            }
        }
    }

    //Inspects the given item, if it exists in the player's inventory
    //Format is as follows: ItemName - itemDescription - itemStat (defense, attack, or remainingHP)
    public String inspectItem(String itemName) {
        String itemInfo = "You don't have that item!";
        for (int i = 0; i < playerInventory.size(); i++) {
            if (playerInventory.get(i).getName().equalsIgnoreCase(itemName)) {
                try {
                    Equipment e = (Equipment)playerInventory.get(i);
                    if (e.getEquipmentSlot() == 4) {
                        itemInfo = e.getName() + " - " + e.getDescription() + " - " + "increases damage by " + e.getItemStat();
                    }
                    else {
                        itemInfo = e.getName() + " - " + e.getDescription() + " - reduces damage taken by " + e.getItemStat() * 100 + "%";
                    }
                } catch (Exception e){}
                try {
                    Consumable c = (Consumable) playerInventory.get(i);
                    itemInfo = c.getName() + " - " + c.getDescription() + " - HP remaining: " + c.getRemainingHP();
                } catch (Exception e){}
            }
        }
        return itemInfo;
    }

    //Gets the room information based on the given direction.
    //If connection = 0, no room is in that direction.
    public String[] getConnectionInDirection(String direction){
        String[] connectedRoomInfo = new String[2];
        switch (direction) {
            case "North", "north" -> {
                connectedRoomInfo[0] = currentRoom.getRoomConnections()[0];
                connectedRoomInfo[1] = currentRoom.getDirectionText()[0];
            }
            case "South", "south" -> {
                connectedRoomInfo[0] = currentRoom.getRoomConnections()[1];
                connectedRoomInfo[1] = currentRoom.getDirectionText()[1];
            }
            case "West", "west" -> {
                connectedRoomInfo[0] = currentRoom.getRoomConnections()[2];
                connectedRoomInfo[1] = currentRoom.getDirectionText()[2];
            }
            case "East", "east" -> {
                connectedRoomInfo[0] = currentRoom.getRoomConnections()[3];
                connectedRoomInfo[1] = currentRoom.getDirectionText()[3];
            }
        }
        return connectedRoomInfo;
    }

    /* Moves the player into the given room, by setting their current room to the new room.
     * displays the room enter text.
     * returns a boolean that is equal to the value of the room's "isCheckpoint" boolean.
     * returns true if the new room is a checkpoint, and false if the new room is not a checkpoint.
     */
    public boolean move(Room room){
        boolean checkpoint = room.isCheckpoint;
        currentRoom = room;
        return checkpoint;
    }

    //Combines the player's current status into an Array that can be used later to print to the console
    public String[] checkStatus() {
        String[] status = new String[4];
        status[0] = String.valueOf(hitPoints);
        status[1] = String.valueOf(damageDealt);
        status[2] = String.valueOf(percentOfDamageTaken * 100);
        status[3] = String.valueOf(remainingLives);
        return status;
    }

    /* Sets the player's hit points to a number equal to hit points minus incoming damage. Simulates taking damage during combat.
     * rather than just taking damage, the incoming damage is decreased by the player's current equipment.
     * The amount of damage dealt will be rounded to the nearest integer.
     * e.g. player has 100 HP and 0.5 (50%) damage reduction. Player takes 10 damage, 10 * 0.5 = 5, player only takes 5 damage. New HP is 95.
     * the dodged boolean is true when the player successfully dodges, and false when they do not.
     */
    @Override
    protected int takeDamage(int damage, boolean dodged) {
        int damageTaken = 0;
        if (!dodged) {
            damageTaken = Math.round(damage * percentOfDamageTaken);
            this.hitPoints = hitPoints - damageTaken;
        }
        return damageTaken;
    }
}
