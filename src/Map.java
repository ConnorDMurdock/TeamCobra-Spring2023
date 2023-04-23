import java.io.*;
import java.util.ArrayList;
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
                Room room = new Room(roomData[0], roomData[1], Boolean.parseBoolean(roomData[2]), roomData[3], roomConnections, roomDirectionsText, roomData[9]);
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
                else if (parts[0].equalsIgnoreCase("Equipment")){
                    Equipment equipment = new Equipment(parts[1], Integer.parseInt(parts[2]), parts[4], Integer.parseInt(parts[5]), Float.parseFloat(parts[6]));
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
                if (parts[0].equalsIgnoreCase("item")) {
                    PuzzleReward puzzleReward = new PuzzleReward(parts[1], parts[2], parts[4], parts[5], parts[6], parts[7], parts[8], Integer.parseInt(parts[9]), Boolean.parseBoolean(parts[10]), parts[11]);
                    String roomID = parts[3];
                    String itemLine = readFile.nextLine();
                    String[] itemParts = itemLine.split("~");
                    Item item = null;
                    if (itemParts[0].equalsIgnoreCase("Equipment")){
                        item = new Equipment(itemParts[1], Integer.parseInt(itemParts[2]), itemParts[3], Integer.parseInt(itemParts[4]), Float.parseFloat(itemParts[5]));
                    }
                    else if (itemParts[0].equalsIgnoreCase("Consumable")){
                        item = new Consumable(itemParts[1], Integer.parseInt(itemParts[2]), itemParts[3], Integer.parseInt(itemParts[4]));
                    }
                    puzzleReward.setPuzzleItemReward(item);
                    gameMap.get(roomID).setRoomPuzzle(puzzleReward);
                }
                else if (parts[0].equalsIgnoreCase("path")){
                    Puzzle puzzle = new Puzzle(parts[1], parts[2], parts[4], parts[5], parts[6], parts[7], parts[8], Integer.parseInt(parts[9]), Boolean.parseBoolean(parts[10]), parts[11]);
                    gameMap.get(parts[3]).setRoomPuzzle(puzzle);
                }
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

    //Saves the player's progress by saving the Player object and Map object to a file
    //Saves as objects, text illegible to humans providing a simple form of encryption
    //File will be saved to the folder GameDataFiles as Saves.dat
    //File always saved in the order of player then map
    public void saveGame(Map gameMap, Player player) {
        try {
            ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream("GameDataFiles/Save.dat"));
            writer.writeObject(player);
            writer.writeObject(gameMap);
            writer.close();
        } catch (IOException ioe){
            System.err.println("Game could not be saved");
            ioe.printStackTrace();
        }
    }

    //Reads the Saves.dat file in the GameDataFiles folder
    //Returns a list of objects, where index 0 is the Player from the save, and index 1 is the Map from the save
    public ArrayList<Object> loadGame() throws Exception {
        ArrayList<Object> objectsFromFile = new ArrayList<>();
        try {
            ObjectInputStream reader = new ObjectInputStream(new FileInputStream("GameDataFiles/Save.dat"));
            while (true){
                Object obj = reader.readObject();
                objectsFromFile.add(obj);
            }
        } catch (EOFException eofe){
            //File loaded successfully
        } catch (Exception e){
            throw new Exception("Game could not be loaded");
            //System.err.println("Game could not be loaded");
            //e.printStackTrace();
        }
        return objectsFromFile;
    }

    //Deletes the user's save file in the case of a game over
    public void deleteSave() {
        try {
            File file = new File("GameDataFiles/Save.dat");
            file.delete();
        } catch (Exception e) {
            System.err.println("File could not be deleted");
        }
    }

    //Returns the HashMap from this object
    public HashMap<String, Room> getGameMap() {
        return gameMap;
    }
}
