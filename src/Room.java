import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

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

    public Room(String roomID, String roomName, ArrayList<Monster> monstersList, ArrayList<Item> itemsList, Puzzle roomPuzzle, String[] roomConnections, String[] directionText, String puzzleSolvedDirectionText, String roomDescription, boolean isCheckpoint) {
        this.roomID = roomID;
        this.roomName = roomName;
        this.monstersList = monstersList;
        this.itemsList = itemsList;
        this.roomPuzzle = roomPuzzle;
        this.roomConnections = roomConnections;
        this.directionText = directionText;
        this.puzzleSolvedDirectionText = puzzleSolvedDirectionText;
        this.roomDescription = roomDescription;
        this.isCheckpoint = isCheckpoint;
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

    public void setMonstersList(ArrayList<Monster> monstersList) {
        this.monstersList = monstersList;
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

    public Item removeItemFromRoom(Item item){
       itemsList.remove(item);
       return item;
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

        System.out.println("Monsters found in the room:" + rMonsterNames.toString()+
                "\nItems found in the room "+ritemNames.toString()+
                "\npuzzles found in the room "+ roomPuzzle.getPuzzleType());
    }
     public void enterRoomText(){
            System.out.println(roomDescription);
        }

}
