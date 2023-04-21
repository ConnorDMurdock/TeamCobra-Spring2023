//Jonathan Hummel
import java.io.Serializable;

public class Item implements Serializable {

    private String name;
    private int itemID;
    private String roomID;
    private String description;

    public Item(String name, int itemID, String description) {
        this.name = name;
        this.itemID = itemID;
        this.description = description;
    }

    public String getName() {
        return name;
    }


    public int getItemID() {
        return itemID;
    }


    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getDescription() {
        return description;
    }

}

