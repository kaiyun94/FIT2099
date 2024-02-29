package game.grounds;

import edu.monash.fit2099.engine.positions.Location;
import game.Util;
import game.items.Coin;

/**
 * Sapling is a type of Tree. It is the 2nd stage of a Tree.
 */
public class Sapling extends Tree {

    /**
     * A counter to count the number of ticks that has passed.
     */
    private int amountTimePassed;


    /**
     * Constant related to growing
     */
    private static final int TURNS_TO_GROW = 10;

    /**
     * Constant related to spawning coin
     */
    private static final int CHANCE_TO_SPAWN_COIN = 10;

    /**
     * Constant related to jump success rate
     */
    private static final int JUMP_SUCCESS_RATE = 80;

    /**
     * Constant related to jump fall damage
     */
    private static final int JUMP_FALL_DAMAGE = 20;


    /**
     * Constructor for Sapling. Initialise time passed to 0.
     *
     */
    public Sapling() {
        super('t');
        this.amountTimePassed = 0;
    }

    /**
     * At every turn, the ground ticks. The ground can perform certain things such as spawning or growing.
     * Sapling has 10% chance to spawn a coin ($20) at every turn. After 10 turns, it grows into Mature.
     *
     * @param location location of the ground
     */
    @Override
    public void tick(Location location) {
        amountTimePassed += 1;
        if(Util.isSuccess(CHANCE_TO_SPAWN_COIN)){
            // If there is no actor at this location, spawn a coin
            location.addItem(new Coin(20));
        }
        // After TURNS_TO_GROW number of turns, grow into a Mature
        if (amountTimePassed == TURNS_TO_GROW){
            location.setGround(new Mature());
        }
        reset(location); // Check if the ground needs to be reset
    }

    /**
     * A getter to get jump rate.
     * @return JUMP_SUCCESS_RATE
     */
    @Override
    public int getJumpRate(){
        return JUMP_SUCCESS_RATE;
    }


    /**
     * A getter to get fall damage.
     * @return JUMP_FALL_DAMAGE
     */
    @Override
    public int getFallDamage() {
        return JUMP_FALL_DAMAGE;
    }


}
