package game.grounds;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.actors.Enemy;

/**
 *  A lava ground that does damage to actors that are on this ground. It appears inside the lava zone world.
 */
public class Lava extends Ground {

    /**
     * Constant. The default damage that lava causes an actor that is on this ground.
     */
    private static final int Damage_Per_Turn = 15;

    /**
     * Constructor for Lava.
     */
    public Lava(){
        super('L');
    }

    /**
     * At every turn, Lava does damage per turn damage to the actor that is on it. If the actor dies, we remove him.
     * @param location The location of the Ground
     */
    @Override
    public void tick(Location location){
        // Check if there is an actor
        if(location.containsAnActor()){
            Actor actor = location.getActor();
            // Hurt the actor
            actor.hurt(Damage_Per_Turn);
            // Remove the actor that has died
            if(!actor.isConscious()){
                location.map().removeActor(actor);
            }
        }
    }

    /**
     * Checks if an actor can move into this ground type. Enemies cannot move into lava.
     * @param actor the Actor to check
     * @return a boolean to indicate if actor can move into this ground type
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        // Enemies cannot move into lava.
        return !(actor instanceof Enemy);
    }
}
