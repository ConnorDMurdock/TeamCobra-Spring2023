import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Game {

    public static void main(String[] args) {
        Map map = new Map();
        HashMap<String, Room> gameMap = map.getGameMap();
        Player player = new Player(100 , 10, "name", gameMap.get("F6"));
        Game game = new Game();

        game.enterPuzzleLoop(player);
    }

/*
    // Muhammad Marenah

    private Player player;
    private Map map;
    private boolean isRunning;
    private boolean gameOver;

    public Game(Player player, Map map) {
        this.player = player;
        this.map = map;
        this.isRunning = true;
        this.gameOver = false;
    }


    public void run() {
        System.out.println("Welcome to the game, Madreign!");

                Scanner scanner = new Scanner(System.in);

        while (isRunning) {
            Room currentRoom = player.getCurrentRoom();

            // Print room description
            System.out.println(currentRoom.getRoomDescription());

            // Print available directions
            System.out.println("Available directions: ");
            String[] connections = currentRoom.getRoomConnections();
            String[] directionText = currentRoom.getDirectionText();
            for (int i = 0; i < connections.length; i++) {
                System.out.println(directionText[i] + ": " + connections[i]);
            }

            // Prompt user for input
            System.out.print("Enter direction or command: ");
            String input = scanner.nextLine();

            // Check for commands
            if (input.equalsIgnoreCase("inventory")) {
                player.checkInventory();
            } else if (input.equalsIgnoreCase("quit")) {
                isRunning = false;
            } else {
                // Try to move to new room
                int direction = parseDirection(input);
                if (direction != -1) {
                    String roomID = connections[direction];
                    Room newRoom = map.getRoomByID(roomID);
                    if (newRoom != null) {
                        player.setCurrentRoom(newRoom);
                    } else {
                        System.out.println("There is no room in that direction!");
                    }
                } else {
                    System.out.println("Invalid direction or command!");
                }
            }

            // Check if game over
            if (player.getHitPoints() <= 0 || gameOver) {
                isRunning = false;
                System.out.println("Game over!");
            }
        }

        scanner.close();
    }

    private int parseDirection(String input) {
        int direction = -1;
        switch (input.toLowerCase()) {
            case "north":
                direction = 0;
                break;
            case "east":
                direction = 1;
                break;
            case "south":
                direction = 2;
                break;
            case "west":
                direction = 3;
                break;
        }
        return direction;
    }
    */

    //Puzzle loop - Albert Austin IV
    public void enterPuzzleLoop(Player player) {
        //needed variables
        Controller controller = new Controller();
        View view = new View();
        Boolean puzzleLoop = true;
        Boolean solution;
        Puzzle puzzle = player.getCurrentRoom().getRoomPuzzle();

        while (puzzleLoop){

            String[] input = controller.getUserInput();

            //If user input is "get instructions", begin puzzle loop
            if (input[0].equals("get") && input[1].equals("instructions")) {


                //print puzzle problem
                view.printPuzzleProblem(puzzle.getProblem());

                //determine the reward type from puzzle
                switch (puzzle.getPuzzleReward()) {
                    case "chest":

                        //loop through puzzle attempts and adds armor to inventory when solved
                        for (int i = 1; i <= puzzle.getAttempts(); i++) {

                            String[] solutionInput = controller.getUserInput();
                            solution = puzzle.solve(solutionInput[0]);

                            System.err.println(i);
                            System.err.println(puzzle.getAttempts());


                            if (solution) {
                                view.printPuzzleSolveAttempt(puzzle.getCorrectOutcome());
                                PuzzleReward rewardPuzzle = (PuzzleReward) puzzle;
                                player.addItemToInventory(rewardPuzzle.getPuzzleItemReward());
                                puzzleLoop = true;
                                view.playerCheckInventory(player.checkInventory());
                                break;
                            } else {
                                view.printPuzzleSolveAttempt(puzzle.getFailOutcome());
                            }

                        }

                        break;

                    case "progress":

                        //loop through puzzle attempts and progresses to next room when solved
                        for (int i = 0; i <= puzzle.getAttempts(); i--) {

                            String[] solutionInput = controller.getUserInput();
                            solution = puzzle.solve(solutionInput[0]);

                            if (solution) {
                                view.printPuzzleSolveAttempt(puzzle.getCorrectOutcome());
                                puzzleLoop = true;
                                break;
                            } else {
                                view.printPuzzleSolveAttempt(puzzle.getFailOutcome());
                            }
                        }
                        break;

                    case "heal":

                        //loop through puzzle attempts and gives player potions when solved
                        for (int i = 0; i <= puzzle.getAttempts(); i--) {

                            String[] solutionInput = controller.getUserInput();
                            solution = puzzle.solve(solutionInput[0]);

                            if (solution) {
                                view.printPuzzleSolveAttempt(puzzle.getCorrectOutcome());
                                PuzzleReward rewardPuzzle = (PuzzleReward) puzzle;
                                for (i = 0; i <= 5; i++) {
                                    player.addItemToInventory(rewardPuzzle.getPuzzleItemReward());
                                }
                                puzzleLoop = true;
                                break;
                            } else {
                                view.printPuzzleSolveAttempt(puzzle.getFailOutcome());
                            }
                        }

                    default:
                        System.out.println("Error: No reward type found");
                }


            }

            if (input[0].equals("hint")) {

                view.printPuzzleHint(puzzle.getHint());
            }
        }

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
                //Player uses a consumable to refill their health
                //Only uses the player's turn if they successfully use a potion
                else if (input[0].equalsIgnoreCase("Use")){
                    if (input.length == 3){
                        if (input[1].equalsIgnoreCase("Health") && input[2].equalsIgnoreCase("Potion")){
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
                        view.errorPrint("Unrecognized command");
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
                    view.errorPrint("Unrecognized command");
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