package game.grounds;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Location;
import game.capabilities.Indicator;
import game.items.MagicalWater;

/**
 * An abstract class of Fountain
 */
public abstract class Fountain extends WaterSource {

    /**
     * The maximum capacity that the fountain can be refilled
     */
    private static final int REFILL_CAPACITY = 10; // maximum of 10 refill capacity

    /**
     * The time for the fountain to be fully replenished
     */
    private static final int REPLENISH_TIME = 5; //  replenish in 5 turns

    /**
     * The counter to keep track of how many times the fountain is refilled, each refill will decrease the counter by 1
     */
    private int refillCounter = 10;

    /**
     * The counter to keep track of how many turns since recharge time starts
     */
    private int rechargeCounter = 0;

    /**
     * Constructor for Fountain
     * fountain has capability of spurting water
     */
    public Fountain(char displayChar) {
        super(displayChar);
        this.addCapability(Indicator.SPURTABLE);
    }

    /**
     * Get refill counter
     * @return an integer of refill counter
     */
    public int getRefillCounter() {
        return this.refillCounter;
    }

    /**
     * Set the refill counter with new refill counter
     * @param refillCounter a new refill counter to be set
     */
    public void setRefillCounter(int refillCounter) {
        this.refillCounter = refillCounter;
    }

    /**
     * a list of actions that the actor can do to the fountain
     * @param actor the Actor acting
     * @param location the current Location
     * @param direction the direction of the Ground from the Actor
     * @return an action list that the actor can perform towards the fountain
     */
    @Override
    public ActionList allowableActions(Actor actor, Location location, String direction) {
        // if the fountain is fully replenished, set the water inside the fountain to maximum capacity, and recharge counter set back to 0
        if(rechargeCounter == REPLENISH_TIME) {
            this.setRefillCounter(REFILL_CAPACITY);
            rechargeCounter = 0;
        }
        return super.allowableActions(actor, location, direction);
    }

    /**
     * Fountain experiences the passage of time
     * @param location The location of the Ground
     */
    @Override
    public void tick(Location location) {
        // if the fountain has no water and the fountain is not fully recharged OR in the process of recharging,
        // increase the recharge time for every tick
        if(this.getRefillCounter() == 0 && rechargeCounter != REPLENISH_TIME) {
            rechargeCounter += 1;
        }
        super.tick(location);
    }

    /**
     * returns true if an Actor can enter this ground
     * @param actor the Actor to check
     * @return true if actor can actor, false otherwise
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        return true;
    }

    /**
     * get the maximum refill capacity of the fountain
     * @return maximum refill capacity
     */
    public static int getRefillCapacity() {
        return REFILL_CAPACITY;
    }

    /**
     * An abstract method to get a new magical water slot from the fountain
     * @return a new magical water slot
     */
    public abstract MagicalWater getMagicalWater();

}
