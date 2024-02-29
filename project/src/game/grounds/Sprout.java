package game.grounds;

import edu.monash.fit2099.engine.positions.Location;
import game.Util;
import game.actors.Goomba;

/**
 * Sprout is a type of Tree. It is the 1st stage of a Tree.
 */
public class Sprout extends Tree {

    /**
     * A counter to count the number of ticks that has passed.
     */
    private int amountTimePassed;

    /**
     * Constant related to growing
     */
    private static final int TURNS_TO_GROW = 10;

    /**
     * Constant related to spawning Goomba
     */
    private static final int CHANCE_TO_SPAWN_GOOMBA = 10;

    /**
     * Constant related to jump success rate
     */
    private static final int JUMP_SUCCESS_RATE = 90;

    /**
     * Constant related to jump fall damage
     */
    private static final int JUMP_FALL_DAMAGE = 10;

    /**
     * Constructor for Sprout. Initialise time passed to 0.
     *
     */
    public Sprout() {
        super('+');
        this.amountTimePassed = 0;
    }

    /**
     * At every turn, the ground ticks. The ground can perform certain things such as spawning or growing.
     * Sprout has 10% chance to spawn Goomba at every turn. After 10 turns, it grows in Sapling.
     *
     * @param location location of the ground
     */
    @Override
    public void tick(Location location) {
        amountTimePassed += 1;
        if(Util.isSuccess(CHANCE_TO_SPAWN_GOOMBA)){
            // If there is no actor at this location, spawn Goomba
            if(!location.containsAnActor()){
                location.addActor(new Goomba());
            }
        }
        // After TURNS_TO_GROW number of turns, grow into a sapling
        if (amountTimePassed == TURNS_TO_GROW){
            location.setGround(new Sapling());
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
