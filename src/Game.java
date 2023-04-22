import java.util.Scanner;

public class Game {

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
        System.out.println("Welcome to the game, Madreign!”);

                Scanner scanner = new Scanner(System.in);

        while (isRunning) {
            Room currentRoom = player.getCurrentRoom();

            // Print room description
            System.out.println(currentRoom.getRoomDescription());

            // Print available directions
            System.out.println("Available directions: ");
            String[] connections = currentRoom.getConnections();
            String[] directionText = currentRoom.getDirectionText();
            for (int i = 0; i < connections.length; i++) {
                System.out.println(directionText[i] + ": " + connections[i]);
            }

            // Prompt user for input
            System.out.print("Enter direction or command: ");
            String input = scanner.nextLine();

            // Check for commands
            if (input.equalsIgnoreCase("inventory")) {
                player.printInventory();
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

    //Puzzle loop - Albert Austin IV
    public void enterPuzzleLoop() {
        //needed variables
        Boolean puzzleLoop;
        Boolean solution;

        //If user input is "get instructions", begin puzzle loop
        if (Controller.getUserInput().toString().equals("get instructions")) {
            puzzleLoop = true;

            //print puzzle problem
            View.printPuzzleProblem(Room.getRoomPuzzle().getProblem());

            //determine the reward type from puzzle
            switch (Room.getRoomPuzzle().getPuzzleReward()) {
                case "chest":

                    //loop through puzzle attempts and adds armor to inventory when solved
                    for (int i = 0; i == Room.getRoomPuzzle().getAttempts(); i--) {

                        solution = Room.getRoomPuzzle().solve(Controller.getUserInput().toString());

                        if (solution == true) {
                            View.printPuzzleSolveAttempt(Room.getRoomPuzzle().getCorrectOutcome());
                            //Legendary armor needs to be added to the player's inventory
                            puzzleLoop = false;
                            break;
                        }
                        else {
                            View.printPuzzleSolveAttempt(Room.getRoomPuzzle().getFailOutcome());
                        }
                    }

                    break;

                case "progress":

                    //loop through puzzle attempts and progresses to next room when solved
                    for (int i = 0; i == Room.getRoomPuzzle().getAttempts(); i--) {

                        solution = Room.getRoomPuzzle().solve(Controller.getUserInput().toString());

                        if (solution == true) {
                            View.printPuzzleSolveAttempt(Room.getRoomPuzzle().getCorrectOutcome());
                            //Progress to next room
                            puzzleLoop = false;
                            break;
                        }
                        else {
                            View.printPuzzleSolveAttempt(Room.getRoomPuzzle().getFailOutcome());
                        }
                    }
                    break;

                case "heal":

                    //loop through puzzle attempts and gives player potions when solved
                    for (int i = 0; i == Room.getRoomPuzzle().getAttempts(); i--) {

                        solution = Room.getRoomPuzzle().solve(Controller.getUserInput().toString());

                        if (solution == true) {
                            View.printPuzzleSolveAttempt(Room.getRoomPuzzle().getCorrectOutcome());
                            //Give player potions
                            puzzleLoop = false;
                            break;
                        }
                        else {
                            View.printPuzzleSolveAttempt(Room.getRoomPuzzle().getFailOutcome());
                        }
                    }

                default:
                    System.out.println("Error: No reward type found");
            }



        }

        if (Controller.getUserInput().toString().equals("Hint for")) {

            View.printPuzzleHint(Room.getRoomPuzzle().getHint());
        }


    }
}