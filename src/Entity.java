public class Entity
{
    /* Class created by: Connor Murdock
     * The Entity class is the parent class of anything that exists in the game and participates in combat
     * This class contains information about the health, dealt damage, name, and current room of the entity
     */

    //Hit points is how much health the entity has. Also referred to as HP
    protected int hitPoints;

    //Damage dealt is the attack stat of the entity. This is how much damage they will deal to other entities in combat
    protected int damageDealt;

    //Name is the name of the entity
    protected String name;

    //Current room ID is the numerical ID of the room this entity is currently inside
    protected int currentRoomID;

    //Constructor
    public Entity(int hitPoints, int damageDealt, String name, int currentRoomID) {
        this.hitPoints = hitPoints;
        this.damageDealt = damageDealt;
        this.name = name;
        this.currentRoomID = currentRoomID;
    }

    //Returns the hit points of the entity
    public int getHitPoints() {
        return hitPoints;
    }

    //Sets this entity's hit points to the given number
    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    //Returns the amount of damage this entity will deal in combat
    public int getDamageDealt() {
        return damageDealt;
    }

    //Sets the amount of damage this entity will deal in combat
    public void setDamageDealt(int damageDealt) {
        this.damageDealt = damageDealt;
    }

    //Returns the name of this entity
    public String getName() {
        return name;
    }

    //Sets this entity's hit points to a number equal to hit points minus incoming damage. Simulates taking damage during combat.
    //If the entity has 100 hit points and takes 10 damage, this function will subtract 10 and make hit points equal 90
    public void takeDamage(int damage) {
        this.hitPoints = hitPoints - damage;
    }
}
