package game.grounds;

import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Location;
import game.capabilities.Indicator;
import game.Util;
import game.actors.FlyingKoopa;
import game.actors.GroundKoopa;

import java.util.ArrayList;
import java.util.List;

/**
 * Mature is a type of Tree. It is the 3rd and final stage of a Tree.
 */
public class Mature extends Tree {

    /**
     * A counter to count the number of ticks that has passed.
     */
    private int amountTimePassed;

    /**
     * Constant related to growing
     */
    private static final int TURNS_TO_GROW = 5;

    /**
     * Constant related to spawning Koopa
     */
    private static final int CHANCE_TO_SPAWN_A_KOOPA = 15;

    /**
     * Constant related to Ground Koopa
     */
    private static final int CHANCE_TO_SPAWN_GROUND_KOOPA = 50;

    /**
     * Constant related to withering
     */
    private static final int CHANCE_TO_WITHER = 20;

    /**
     * Constant related to jump success rate
     */
    private static final int JUMP_SUCCESS_RATE = 70;

    /**
     * Constant related to jump fall damage
     */
    private static final int JUMP_FALL_DAMAGE = 30;

    /**
     * Constructor for Mature. Initialise time passed to 0.
     *
     */
    public Mature() {
        super('T');
        this.amountTimePassed = 0;
    }

    /**
     * At every turn, the ground ticks. The ground can perform certain things such as spawning or growing.
     * Mature has 15% chance to spawn Koopa at every turn, 20% to wither and become dirt. Every 5 turns, it can grow a sprout
     * randomly at its adjacent location.
     *
     * @param location location of the ground
     */
    @Override
    public void tick(Location location) {
        amountTimePassed += 1;
        if(Util.isSuccess(CHANCE_TO_SPAWN_A_KOOPA)){
            // If there is no actor at this location, spawn a Koopa
            if(!location.containsAnActor()){
                // Either spawn a Ground Koopa
                if(Util.isSuccess(CHANCE_TO_SPAWN_GROUND_KOOPA)){
                    location.addActor(new GroundKoopa());
                }
                else{
                    // Or spawn a Flying Koopa
                    location.addActor(new FlyingKoopa());
                }
            }
        }

        if(amountTimePassed % TURNS_TO_GROW == 0){
            // Grow sprouts around randomly at fertile grounds after TURNS_TO_GROW number of turns

            // Get all the exits at this location
            List<Exit> exits = location.getExits();
            // A list to store exits that allow sprout to spawn
            ArrayList <Exit> sprout_spawn_locations = new ArrayList<>();

            // Loop through exits and filter out non-fertile grounds
            for(Exit exit: exits){
                if (exit.getDestination().getGround().hasCapability(Indicator.IS_FERTILE)){
                    sprout_spawn_locations.add(exit);
                }
            }
            // Randomly select a fertile ground to spawn sprout on
            if (sprout_spawn_locations.size() > 0){
                int index = (int)(Math.random() * sprout_spawn_locations.size());
                Location locationToGrow = sprout_spawn_locations.get(index).getDestination();
                locationToGrow.setGround(new Sprout());
            }
        }

        // Mature has a chance to wither at every turn
        if(Util.isSuccess(CHANCE_TO_WITHER)){
            location.setGround(new Dirt());
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
