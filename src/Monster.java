public class Monster extends Entity
{
    /* Class created by: Connor Murdock
     * The Enemy class extends entity. This class handles all the information regarding the enemies of the game
     * This class contains information about the combat messages displayed to the user when entering and exiting combat
     */

    //Enter combat message will be displayed to the user when combat begins
    private String enterCombatMessage;

    //Enter combat message will be displayed to the user when combat ends
    private String exitCombatMessage;

    //Constructor
    public Monster(int hitPoints, int damageDealt, String name, Room currentRoom, String enterCombatMessage, String exitCombatMessage) {
        super(hitPoints, damageDealt, name, currentRoom);
        this.enterCombatMessage = enterCombatMessage;
        this.exitCombatMessage = exitCombatMessage;
    }

    //Returns enter combat message as a String
    public String getEnterCombatMessage() {
        return enterCombatMessage;
    }

    //Returns exit combat message as a String
    public String getExitCombatMessage() {
        return exitCombatMessage;
    }
}
