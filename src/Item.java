//Jonathan Hummel
import java.io.Serializable;

public class Item implements Serializable {

    private String name;
    private int itemID;
    private int roomID;
    private String description;

    public Item(String name, int itemID, int roomID, String description) {
        this.name = name;
        this.itemID = itemID;
        this.roomID = roomID;
        this.description = description;
    }

    public String getName() {
        return name;
    }


    public int getItemID() {
        return itemID;
    }


    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public String getDescription() {
        return description;
    }

}
