import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Scanner;

public class Map implements Serializable {
    private HashMap<String, Room> gameMap;

    public Map() {
        gameMap = createMapData();
    }

    public HashMap<String, Room> createMapData(){
        HashMap<String, Room> gameMap = new HashMap<>();

        //Loads all 4 text files and assigns items, puzzles, and monsters to the appropriate room
        gameMap = loadRooms();
        loadItems(gameMap);
        //loadPuzzles(gameMap);
        //loadEnemies(gameMap);

        return gameMap;
    }

    //All puzzles have north direction text
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

    private void loadPuzzles(HashMap<String, Room> gameMap){
        File puzzlesFile = new File("GameDataFiles/Puzzles.txt");
        try {
            Scanner readFile = new Scanner(puzzlesFile);
            while(readFile.hasNextLine()){
                String line = readFile.nextLine();
                String[] parts = line.split("~");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void loadEnemies(HashMap<String, Room> gameMap){
        File enemiesFile = new File("GameDataFiles/Enemies.txt");
        try {
            Scanner readFile = new Scanner(enemiesFile);
            while(readFile.hasNextLine()){
                String line = readFile.nextLine();
                String[] parts = line.split("-");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public HashMap<String, Room> getGameMap() {
        return gameMap;
    }
}
