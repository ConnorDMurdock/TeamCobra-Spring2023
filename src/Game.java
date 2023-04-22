import java.util.ArrayList;
import java.util.HashMap;

public class Game {
    public static void main(String[] args) {

    }


    /* Function by: Connor Murdock
     * Starts combat between two entities (generally the player and a monster)
     * Returns a boolean that is equal to whether the player died in combat or not
     * If the player dies in combat, the game should reload from the last save and subtract 1 from the player's lives
     *
     * The player has many commands they can use only in combat, such as Attack, Run, and Dodge.
     * The player also has access to some commands that can be used both in and out of combat, such as status and help
     * The combat help menu shows a specialized help menu that only displays the commands usable in combat
     *
     * Combat ends if the player dies, the monster dies, or if the player uses the run command
     */
    public boolean initiateCombat(Player player, Monster monster){
        boolean inCombat = true;
        boolean playerDied = false;
        Controller controller = new Controller();
        View view = new View();

        //Print the monster enter combat message to the player
        view.genericPrint(monster.getEnterCombatMessage());

        while (inCombat) {
            //PLAYER'S TURN:
            //check if the player has 0 HP and should be dead before taking the player's turn.
            if (player.getHitPoints() <= 0) {
                view.entityDefeated(player.getName());
                playerDied = true;
                break;
            }

            //Set temporary combat variables back to default state.
            boolean playerDodged = false;
            boolean validInput = false;

            //Show the user that it's the Player's turn
            view.turnStart(player.getName());

            //This loop will continue to run, maintaining the player's turn, until a valid input is reached.
            while (!validInput){
                //Ask the user for their command
                view.playerTurn();

                //Get user input. Valid inputs are Attack, Use [item name], Dodge, Run, Status, and Help.
                String[] input = controller.getUserInput();

                //Player attacks monster
                if (input[0].equalsIgnoreCase("Attack")) {
                    //(dodged is always false because monsters can only attack)
                    view.damageTaken(monster.getName(), player.attack(monster, false));
                    validInput = true;
                }
                //Player dodges the monster's next attack
                else if (input[0].equalsIgnoreCase("Dodge")) {
                    view.playerDodgePrepare();
                    playerDodged = true;
                    validInput = true;
                }
                //Player runs from the encounter
                else if (input[0].equalsIgnoreCase("Run")) {
                    view.playerRun();
                    inCombat = false;
                    validInput = true;
                }
                //Player uses an item
                else if (input[0].equalsIgnoreCase("Use")){
                    if (input[1].equalsIgnoreCase("health") && input[2].equalsIgnoreCase("Potion")){
                        player.heal(player.useItem(input));
                    }
                    validInput = true;
                }
                //Prints the player's status. Does not take the player's turn
                else if (input[0].equalsIgnoreCase("Status")) {
                    view.playerStatus(player.checkStatus());
                }
                //Prints the help menu to the user Does not take the player's turn
                else if (input[0].equalsIgnoreCase("Help")) {
                    view.combatHelp();
                }
            }

            //Only take the monster's turn if the player is still in combat
            if (inCombat){
                //MONSTER'S TURN:
                //check if the monster has 0 HP and should be dead before the monster takes their turn
                //If monster is dead, display exit combat message, remove from room, and end combat
                if (monster.getHitPoints() <= 0){
                    view.entityDefeated(monster.getName());
                    view.genericPrint(monster.getExitCombatMessage());
                    monster.currentRoom.removeMonster(monster);
                    break;
                }

                //Show the user that it's the monster turn
                view.turnStart(monster.getName());

                //Monster attacks the player
                //print a line showing a successful dodge if player dodged the attack
                if (playerDodged) {view.playerDodgeSuccess();}
                view.damageTaken(player.getName(), monster.attack(player, playerDodged));
            }
        }
        return playerDied;
    }
}
