import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Scanner;

public class Map implements Serializable {
    /* Class created by: Connor Murdock
     * The Map class reads in the text files in the GameDataFiles folder to use for the game.
     * The files include data for the rooms, items, puzzles, and monsters.
     * Additionally, the Map class is responsible for saving and loading the user's progress during gameplay.
     */

    //HashMap holding the RoomID and Room object that belongs to that RoomID
    private HashMap<String, Room> gameMap;

    //Constructor automatically creates the Map Data when this object is created
    public Map() {
        gameMap = createMapData();
    }

    //Creates the HashMap object, populates it by loading each text file, then returns the populated HashMap
    public HashMap<String, Room> createMapData(){
        HashMap<String, Room> gameMap = new HashMap<>();

        //Loads all 4 text files and assigns items, puzzles, and monsters to the appropriate room
        gameMap = loadRooms();
        loadItems(gameMap);
        loadPuzzles(gameMap);
        loadEnemies(gameMap);

        return gameMap;
    }

    //----All puzzles have north direction text
    //Reads the Rooms.txt file to create the Room objects
    //Returns a populated HashMap of RoomID, Room object
    private HashMap<String, Room> loadRooms(){
        HashMap<String, Room> gameMap = new HashMap<>();
        File roomsFile = new File("GameDataFiles/Rooms.txt");
        try {
            Scanner readFile = new Scanner(roomsFile);
            while(readFile.hasNextLine()){
                //Grab every line from the file and create the Room objects
                String[] roomData = new String[10];
                for (int i = 0; i < 10; i++){
                    String line = readFile.nextLine();
                    roomData[i] = line;
                }

                //Split the room connections by the comma between indexes
                String[] roomConnections = roomData[4].split(",");

                //Combine the room directions text into a new Array
                String[] roomDirectionsText = new String[4];
                int roomDataIndex = 5;
                for (int i = 0; i < 4; i++){
                    roomDirectionsText[i] = roomData[roomDataIndex];
                    roomDataIndex++;
                }

                //Create the room objects and populate them into the HashMap
                Room room = new Room(roomData[0], roomData[1], Boolean.parseBoolean(roomData[2]), roomData[3], roomConnections, roomDirectionsText, roomData[6]);
                gameMap.put(roomData[1], room);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return gameMap;
    }

    //Reads the Items.txt file to create the Item Objects
    //Each item has a different type: consumable, use item, or equipment
    //Items are automatically populated to the correct room using the RoomID field in the text file
    private void loadItems(HashMap<String, Room> gameMap){
        File itemsFile = new File("GameDataFiles/Items.txt");
        try {
            Scanner readFile = new Scanner(itemsFile);
            while(readFile.hasNextLine()){
                String line = readFile.nextLine();
                String[] parts = line.split("_");
                if (parts[0].equalsIgnoreCase("Consumable")){
                    Consumable consumable = new Consumable(parts[1], Integer.parseInt(parts[2]), parts[4], Integer.parseInt(parts[5]));
                    gameMap.get(parts[3]).addItemtoRoom(consumable);
                }
                else if (parts[0].equalsIgnoreCase("UseItem")){
                    UseItem useItem = new UseItem(parts[1], Integer.parseInt(parts[2]), parts[4], Integer.parseInt(parts[5]), Integer.parseInt(parts[6]));
                    gameMap.get(parts[3]).addItemtoRoom(useItem);
                }
                else if (parts[0].equalsIgnoreCase("Equipment")){
                    Equipment equipment = new Equipment(parts[1], Integer.parseInt(parts[2]), parts[4], Integer.parseInt(parts[5]), Integer.parseInt(parts[6]));
                    gameMap.get(parts[3]).addItemtoRoom(equipment);

                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //Reads the Items.txt file to create the Puzzle Objects
    //Puzzles are automatically populated to the correct room using the RoomID field in the text file
    private void loadPuzzles(HashMap<String, Room> gameMap){
        File puzzlesFile = new File("GameDataFiles/Puzzles.txt");
        try {
            Scanner readFile = new Scanner(puzzlesFile);
            while(readFile.hasNextLine()){
                String line = readFile.nextLine();
                String[] parts = line.split("~");
                Puzzle puzzle = new Puzzle(parts[0], parts[1], parts[3], parts[4], parts[5], parts[6], parts[7], Integer.parseInt(parts[8]), Boolean.parseBoolean(parts[9]));
                gameMap.get(parts[2]).setRoomPuzzle(puzzle);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //Reads the Enemies.txt file to create the Enemy Objects
    //Enemies are automatically populated to the correct room using the RoomID field in the text file
    private void loadEnemies(HashMap<String, Room> gameMap){
        File enemiesFile = new File("GameDataFiles/Enemies.txt");
        try {
            Scanner readFile = new Scanner(enemiesFile);
            while(readFile.hasNextLine()){
                String line = readFile.nextLine();
                String[] parts = line.split("~");
                Monster monster = new Monster(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), parts[2], gameMap.get(parts[3]), parts[4], parts[5]);
                gameMap.get(parts[3]).addMonster(monster);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //Returns the HashMap from this object
    public HashMap<String, Room> getGameMap() {
        return gameMap;
    }
}
