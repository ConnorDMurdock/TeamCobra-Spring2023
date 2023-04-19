//Jonathan Hummel
import java.io.Serializable;

public class UseItem extends Item implements Serializable {

    private int damage;
    private int equipmentSlot;

    public UseItem(String name, int itemID, int roomID, String description, int damage, int equipmentSlot) {
        super(name, itemID, roomID, description);
        this.damage = damage;
        this.equipmentSlot = equipmentSlot;
    }

    public int getDamage() {
        return damage;
    }
}
