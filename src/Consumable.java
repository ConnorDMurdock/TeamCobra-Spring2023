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

   /* public void useConsumable(playerHP){
        int h = playerHP;
        if(h < 10){
            playerHP = playerHP + h;
            return playerHP;
        }
        else{
            int partialHealthPotion;
            int ht = (100 - playerHP);
            partialHealthPotion = 10-ht;
            playerHP = 100;
            return playerHP;
        }
    }*/

//potential means of using health potions.
}
