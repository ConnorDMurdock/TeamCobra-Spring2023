import java.io.Serializable;
import java.util.ArrayList;



// Class written by Duaa//
public class Room implements Serializable {
    public String roomID;
    public String roomName;
    public ArrayList<Monster> monstersList;
    public ArrayList<Item> itemsList;
    public Puzzle roomPuzzle;
    public String[] roomConnections;
    public String[] directionText;
    public String puzzleSolvedDirectionText;
    public String roomDescription;
    public boolean isCheckpoint;



    public void setItemsList(ArrayList<Item> itemsList) {
        this.itemsList = itemsList;
    }

    public void setCheckpoint(boolean checkpoint) {
        isCheckpoint = checkpoint;
    }

    public Room(String roomName, String roomID,  boolean isCheckpoint,  String roomDescription, String[] roomConnections, String[] directionText, String puzzleSolvedDirectionText) {
        this.roomID = roomID;
        this.roomName = roomName;
        this.roomConnections = roomConnections;
        this.directionText = directionText;
        this.puzzleSolvedDirectionText = puzzleSolvedDirectionText;
        this.roomDescription = roomDescription;
        this.isCheckpoint = isCheckpoint;
        monstersList = new ArrayList<>();
        itemsList = new ArrayList<>();
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void addMonster(Monster monster) {
        monstersList.add(monster);
    }

    public void removeMonster(Monster monster) {
        monstersList.remove(monster);
    }

    public ArrayList<Monster> getMonstersList() {
        return monstersList;
    }

    public ArrayList<Item> getItemsList() {
        return itemsList;
    }
    public void addItemtoRoom(Item item)
    {
        itemsList.add(item);
    }

    public Item removeItemFromRoom(String  itemName){
       for (Item i : itemsList){
           if (i.getName().equalsIgnoreCase(itemName)){
               Item temp = i;
               itemsList.remove(i);
               return temp;
           }

       }
       return null;

    }
    public Puzzle getRoomPuzzle() {
        return roomPuzzle;
    }

    public void setRoomPuzzle(Puzzle roomPuzzle) {
        this.roomPuzzle = roomPuzzle;
    }

    public String[] getRoomConnections() {
        return roomConnections;
    }

    public void setRoomConnections(String[] roomConnections) {
        this.roomConnections = roomConnections;
    }

    public String[] getDirectionText() {
        return directionText;
    }

    public void setDirectionText(String[] directionText) {
        this.directionText = directionText;
    }

    public String getPuzzleSolvedDirectionText() {
        return puzzleSolvedDirectionText;
    }

    public void setPuzzleSolvedDirectionText(String puzzleSolvedDirectionText) {
        this.puzzleSolvedDirectionText = puzzleSolvedDirectionText;
    }

    public String getRoomDescription() {
        return roomDescription;
    }

    public void setRoomDescription(String roomDescription) {
        this.roomDescription = roomDescription;
    }

    public boolean isCheckpoint() {
        return isCheckpoint;
    }


    public void scanRoom(){
        ArrayList<String> rMonsterNames = new ArrayList<>();
        for(Monster m:monstersList){
            String tempM = m.getName();
            rMonsterNames.add(tempM);


        }
        ArrayList<String> ritemNames = new ArrayList<>();
        for(Item i:itemsList){
            String tempI = i.getName();
            ritemNames.add(tempI);


        }
        if (rMonsterNames.size() > 0){
            System.out.println("Monsters found in the room: " + rMonsterNames.toString());
        }
        else {
            System.out.println("There are no monsters in this room.");
        }
        if (ritemNames.size() > 0){
            System.out.println("Items found in the room: " + ritemNames.toString());
        }
        else {
            System.out.println("There are no items in this room.");
        }
        if (roomPuzzle != null){
            System.out.println("There is a " + roomPuzzle.getPuzzleType() + " puzzle in this room.");
        }
        else {
            System.out.println("There is no puzzle in this room.");
        }
    }
     public void enterRoomText(){
            System.out.println(roomDescription);
        }

}
