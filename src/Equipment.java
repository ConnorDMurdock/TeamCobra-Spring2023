//Jonathan Hummel
import java.io.Serializable;

public class Equipment extends Item implements Serializable {

    private int equipmentSlot;
    private float damageReduction;

    public Equipment(String name, int itemID, String description, int equipmentSlot, float damageReduction) {
        super(name, itemID, description);
        this.equipmentSlot = equipmentSlot;
        this.damageReduction = damageReduction;
    }

    public float getDamageReduction() {
        return damageReduction;
    }
    public int getEquipmentSlot(){
        return equipmentSlot;
    }
}
