//Jonathan Hummel
import java.io.Serializable;

public class Equipment extends Item implements Serializable {

    private int equipmentSlot;
    private int damageReduction;

    public Equipment(String name, int itemID, int roomID, String description, int equipmentSlot, int damageReduction) {
        super(name, itemID, roomID, description);
        this.equipmentSlot = equipmentSlot;
        this.damageReduction = damageReduction;
    }

    public int getDamageReduction() {
        return damageReduction;
    }
    public int getEquipmentSlot(){
        return equipmentSlot;
    }
}
