//Jonathan Hummel
import java.io.Serializable;

public class Equipment extends Item implements Serializable {

    private int equipmentSlot;
    private float itemStat;

    public Equipment(String name, int itemID, String description, int equipmentSlot, float itemStat) {
        super(name, itemID, description);
        this.equipmentSlot = equipmentSlot;
        this.itemStat = itemStat;
    }

    public float getItemStat() {
        return itemStat;
    }
    public int getEquipmentSlot(){
        return equipmentSlot;
    }
}
