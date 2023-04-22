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
}