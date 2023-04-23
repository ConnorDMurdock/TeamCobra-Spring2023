import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Game {

    /* Main Method
     * by: Connor Murdock
     * This method creates necessary objects that the program needs and uses them to run the game
     * The order of things goes as follows:
     *      - introduction showed to the player; get player's name
     *      - create the Map, Player, and Game objects
     *      - Create the initial save game
     *      - Start the game
     * This will continue to loop whenever the player restarts the game after a game over.
     */
    public static void main(String[] args) {
        //Create necessary objects
        boolean donePlaying = false;
        View view = new View();
        Controller controller = new Controller();

        while (!donePlaying) {
            //Display the introduction to the user and get their name
            view.introductionPart1();
            String[] input = controller.getUserInput();
            view.introductionPart2(input[0]);

            //Create the game objects
            Map map = new Map();
            HashMap<String, Room> gameMap = map.getGameMap();
            Player player = new Player(100, 2, input[0], gameMap.get("F1"));
            Game game = new Game();

            //create initial save file
            map.saveGame(map, player);

            //Begin playing
            donePlaying = game.enterExploreLoop(map, player);
        }
        //If player is done playing, exit the program
        System.exit(0);
    }


    //Original Method by Muhammad Marenah
    //Reworked by Connor Murdock

    //Returns a boolean equal to whether the player is done playing or not.
    public boolean enterExploreLoop(Map map, Player player) {
        boolean donePlaying = false;
        HashMap<String, Room> gameMap = map.getGameMap();
        Controller controller = new Controller();
        View view = new View();
        while (true) {
            //Gets the user input while exploring
            String[] input = controller.getUserInput();

            //==MOVE COMMAND==
            //Checks if the input is equal to a cardinal direction (player attempts to move)
            if (input[0].equalsIgnoreCase("Move")) {
                if (input.length == 2){
                    try {
                        String[] connections = player.getConnectionInDirection(input[1]);
                        //If connections[0] (the connected roomID) equals 0, then there is no connection in that direction and display blocked connection text
                        if (connections[0].equalsIgnoreCase("0")) {
                            view.genericPrint(connections[1]);
                        }
                        //Otherwise, move the player into the new room and display the connection text
                        else {
                            if (player.getCurrentRoom().getRoomPuzzle() != null) {
                                if (player.getCurrentRoom().getRoomPuzzle().getPuzzleReward().equals("progression")) {
                                    if (input[1].equalsIgnoreCase("North")) {
                                        view.genericPrint(connections[1]);
                                    }
                                }
                            }
                            else {
                                player.move(gameMap.get(connections[0]));
                                view.genericPrint(connections[1]);
                                //save the game if moving to a checkpoint room with no monsters
                                if (gameMap.get(connections[0]).isCheckpoint()) {
                                    if (gameMap.get(connections[0]).getMonstersList().size() > 0) {
                                        view.errorPrint("This room is a checkpoint, but cannot be activated until there are no monsters in the room...");
                                    } else {
                                        view.genericPrint("Checkpoint reached!");
                                        map.saveGame(map, player);
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        view.commandError();
                    }
                }
                else {
                    view.commandError();
                }
            }

            //==STATUS COMMAND==
            //Shows the user the player's current status
            else if (input[0].equalsIgnoreCase("Status")) {
                view.playerStatus(player.checkStatus());
            }

            //==ADD ITEM COMMAND==
            //Allows the player to pickup items from the room if they exist
            else if (input[0].equalsIgnoreCase("Add")) {
                String itemName = recombineName(input);

                //Make sure the player's inventory is not full first
                if (!player.isInventoryFull()) {
                    //Try to get the item from the room, and add it to the player's inventory
                    try{
                        Item item = player.getCurrentRoom().removeItemFromRoom(itemName);
                        if (item != null) {
                            player.addItemToInventory(item);
                            view.playerItemPickup(itemName);
                        }
                    } catch (Exception e) {
                        view.errorPrint("That item doesn't exist in this room!");
                    }
                }
                else {
                    view.errorPrint("Your inventory is full!");
                }
            }

            //==REMOVE ITEM COMMAND==
            //Drops the item from the player's inventory into the room inventory
            else if (input[0].equalsIgnoreCase("Remove")) {
                String itemName = recombineName(input);

                //Try to get the item from the player, and add it to the room's inventory
                try{
                    Item item = player.removeItemFromInventory(itemName);
                    if (item != null) {
                        player.getCurrentRoom().addItemtoRoom(item);
                        view.playerItemDrop(itemName);
                    }
                } catch (Exception e) {
                    view.errorPrint("You don't have that item!");
                }
            }

            //==EQUIP ITEM COMMAND==
            //Equips the item to the player from their inventory
            else if (input[0].equalsIgnoreCase("Equip")) {
                String itemName = recombineName(input);
                player.equipItem(itemName);
            }

            //==UNEQUIP ITEM COMMAND==
            //Unequips the item from the player and places it in their inventory
            else if (input[0].equalsIgnoreCase("Unequip")) {
                String itemName = recombineName(input);
                player.unequipItem(itemName);
            }

            //==INSPECT ITEM COMMAND==
            //Inspects the item if the player has it in their inventory
            else if (input[0].equalsIgnoreCase("Inspect")) {
                String itemName = recombineName(input);
                view.genericPrint(player.inspectItem(itemName));
            }

            //==SCAN ROOM COMMAND==
            //Allows the user to scan the room and see any items, puzzles, or monsters in this room.
            else if (input[0].equalsIgnoreCase("Scan")) {
                if (input.length > 1) {
                    if (input[1].equalsIgnoreCase("Room")) {
                        player.getCurrentRoom().scanRoom();
                    }
                }
                else {
                    view.commandError();
                }
            }

            //==CHECK INVENTORY COMMAND==
            //Allows the user to view the player's inventory
            else if (input[0].equalsIgnoreCase("Inventory")) {
                view.playerCheckInventory(player.checkInventory());
            }

            //==FIGHT MONSTER COMMAND==
            //Starts combat between the player and the specified monster
            else if (input[0].equalsIgnoreCase("Fight")) {
                String monsterName = recombineName(input);
                try {
                    //Start combat with the specified monster if it exists in this room
                    Monster monster = player.getCurrentRoom().getMonsterByName(monsterName);
                    boolean playerDied = initiateCombat(player, monster);
                    if (playerDied) {
                        //Try to load the last save. If no save found, restart the game
                        try {
                            ArrayList<Object> loadData = map.loadGame();
                            player = (Player)loadData.get(0);
                            map = (Map)loadData.get(1);
                        } catch (Exception e) {
                            //user is not done playing, restart
                            //return false;
                        }
                        //If save file found, make the player lose a life.
                        //If remaining lives is 0, give the user a game over.

                        boolean gameOver = player.loseLife();
                        if (gameOver) {
                            view.gameOver();
                            //Ask the user if they'd like to restart or quit the game
                            while (true) {
                                String[] gameOverInput = controller.getUserInput();
                                if (gameOverInput[0].equalsIgnoreCase("Quit")) {
                                    //user is done playing, quit game
                                    return true;
                                }
                                else if (gameOverInput[0].equalsIgnoreCase("Restart")) {
                                    map.deleteSave();
                                    //user is not done playing, restart
                                    return false;
                                }
                                else {
                                    view.commandError();
                                }
                            }
                        } else {
                            //Save the game with the new life count of the player
                            map.saveGame(map, player);
                        }
                    }
                    //If the player just fought the king and won
                    else {
                        if (monsterName.equals("THE KING") && player.getCurrentRoom().getMonstersList().size() == 0) {
                            System.exit(0);
                        }
                    }
                } catch (Exception e) {
                    view.errorPrint("That monster is not in this room.");
                }
            }

            //==START PUZZLE COMMAND==
            //Starts the puzzle loop when the user interacts with the puzzle
            else if (input[0].equalsIgnoreCase("Get")) {
                if (input.length == 2) {
                    if (input[1].equalsIgnoreCase("Instructions")) {
                        if (player.getCurrentRoom().getRoomPuzzle() != null) {
                            //Only let the player attempt a chest puzzle if there are no enemies in the room (the boss has died)
                            if (player.getCurrentRoom().getRoomPuzzle().getPuzzleReward().equalsIgnoreCase("chest")) {
                                if (player.getCurrentRoom().getMonstersList().size() == 0) {
                                    boolean removePuzzle = enterPuzzleLoop(player);
                                    //Remove the puzzle if successfully solved or attempts remaining = 0
                                    if (removePuzzle) {
                                        player.getCurrentRoom().setRoomPuzzle(null);
                                    }
                                } else {
                                    view.errorPrint("You must defeat the boss of this room first");
                                }
                            } else {
                                boolean removePuzzle = enterPuzzleLoop(player);
                                //Remove the puzzle if successfully solved or attempts remaining = 0
                                if (removePuzzle) {
                                    player.getCurrentRoom().setRoomPuzzle(null);
                                }
                            }
                        } else {
                            view.errorPrint("There is no puzzle in this room");
                        }
                    }
                }
            }

            //==CONSUME POTION COMMAND==
            //Uses a potion from the player's inventory to heal them, if they have any potions
            else if (input[0].equalsIgnoreCase("Consume")){
                if (input.length == 2){
                    if (input[1].equalsIgnoreCase("Potion")){
                        Consumable potion = player.getConsumable();
                        if (potion != null){
                            player.heal(potion);
                        }
                        else {
                            view.errorPrint("You don't have any potions!");
                        }
                    }
                }
                else {
                    view.commandError();
                }
            }

            //==HELP MENU==
            //Displays the help menu to the user
            else if (input[0].equalsIgnoreCase("Help")) {
                view.displayHelpMenu();
            }

            //==COMMAND NOT RECOGNIZED==
            //Print an error to the user if the command is unrecognized
            else {
                view.commandError();
            }
        }
    }

    private String recombineName(String[] input) {
        String itemName = "";
        //Recombines the input back into one string, placing a space after each index
        for (int i = 1; i < input.length; i++) {
            itemName = itemName + input[i] + " ";
        }
        //removes the extra space from the end of the itemName
        itemName = itemName.strip();
        return itemName;
    }

    //Puzzle loop - Albert Austin IV
    //Modified by Connor Murdock

    //Returns whether this puzzle should be removed from the room or not
    public boolean enterPuzzleLoop(Player player) {
        //needed variables
        Controller controller = new Controller();
        View view = new View();
        Boolean solution;
        Puzzle puzzle = player.getCurrentRoom().getRoomPuzzle();

        //print puzzle problem
        view.printPuzzleProblem(puzzle.getProblem());

        //User can attempt to solve the puzzle until they run out of attempts
        for (int i = 1; i <= puzzle.getAttempts(); i++) {
            String[] solutionInput = controller.getUserInput();
            solution = puzzle.solve(solutionInput[0]);

            if (solutionInput[0].equalsIgnoreCase("Hint")) {
                view.printPuzzleHint(puzzle.getHint());
                i--;
            } else {
                switch (puzzle.getPuzzleReward()) {
                    case "chest":
                        if (solution) {
                            view.printPuzzleSolveAttempt(puzzle.getCorrectOutcome());
                            PuzzleReward reward = (PuzzleReward) puzzle;
                            if (!player.isInventoryFull()) {
                                player.addItemToInventory(reward.getPuzzleItemReward());
                                view.playerItemPickup(reward.getPuzzleItemReward().getName());
                            } else {
                                player.getCurrentRoom().addItemtoRoom(reward.getPuzzleItemReward());
                                view.errorPrint("Inventory full! You leave the item in the room for now");
                            }
                            return true;
                        }
                    case "progression":
                        if (solution) {
                            view.printPuzzleSolveAttempt(puzzle.getCorrectOutcome());
                            String[] connections = player.getCurrentRoom().getDirectionText();
                            connections[0] = player.getCurrentRoom().getPuzzleSolvedDirectionText();
                            player.getCurrentRoom().setDirectionText(connections);
                            return true;
                        } else {
                            view.printPuzzleSolveAttempt(puzzle.getFailOutcome());
                        }
                    case "heal":
                        if (solution) {
                            view.printPuzzleSolveAttempt(puzzle.getCorrectOutcome());
                            PuzzleReward reward = (PuzzleReward) puzzle;
                            for (i = 1; i <= 5; i++) {
                                if (!player.isInventoryFull()) {
                                    player.addItemToInventory(reward.getPuzzleItemReward());
                                    view.playerItemPickup(reward.getPuzzleItemReward().getName());
                                } else {
                                    player.getCurrentRoom().addItemtoRoom(reward.getPuzzleItemReward());
                                    view.errorPrint("Inventory full! You leave the item in the room for now");
                                }
                            }
                            return true;
                        }
                }
            }
        }
        //User runs out of attempts. Reward from puzzle is lost for this save
        view.errorPrint("You have no more attempts at this puzzle");
        return true;
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
                    //25% chance to dodge the attack successfully
                    Random random = new Random();
                    int num = random.nextInt(4);
                    System.out.println(num);
                    if (num == 1) {
                        playerDodged = true;
                    }
                    validInput = true;
                }
                //Player runs from the encounter
                else if (input[0].equalsIgnoreCase("Run")) {
                    view.playerRun();
                    inCombat = false;
                    validInput = true;
                }
                //Player uses a consumable to refill their health
                //Only uses the player's turn if they successfully use a potion
                else if (input[0].equalsIgnoreCase("Consume")){
                    if (input.length == 2){
                        if (input[1].equalsIgnoreCase("Potion")){
                            Consumable potion = player.getConsumable();
                            if (potion != null){
                                player.heal(potion);
                                validInput = true;
                            }
                            else {
                                view.errorPrint("You don't have any potions!");
                            }
                        }
                    }
                    else {
                        view.commandError();
                    }
                }
                //Prints the player's status. Does not take the player's turn
                else if (input[0].equalsIgnoreCase("Status")) {
                    view.playerStatus(player.checkStatus());
                }
                //Prints the help menu to the user Does not take the player's turn
                else if (input[0].equalsIgnoreCase("Help")) {
                    view.combatHelp();
                }
                else {
                    view.commandError();
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