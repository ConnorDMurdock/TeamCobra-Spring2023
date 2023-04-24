//Jonathan Hummel
import java.io.Serializable;

public class Consumable extends Item implements Serializable {


    private int potionHP;

    private int remainingHP;

    public Consumable(String name, int itemID, String description, int potionHP) {
        super(name, itemID, description);
        this.potionHP = potionHP;
        this.remainingHP = potionHP;
    }

   public int useConsumable(int playerHP){
        int difference = 100 - playerHP;
        if (difference < remainingHP){
            remainingHP = remainingHP - difference;
            playerHP = playerHP + difference;
        }
        else {
            playerHP = playerHP + remainingHP;
            remainingHP = 0;
        }
        return playerHP;
    }

    public int getRemainingHP(){
        return remainingHP;
    }

//potential means of using health potions.
}
