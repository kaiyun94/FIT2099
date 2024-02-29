package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actors.Player;
import game.capabilities.Ability;
import game.items.Bucket;

/**
 * Actions which allows an actor to pour water on themselves
 */
public class SelfPourAction extends Action {

    /**
     * to keep track of each bucket
     */
    private Bucket bucket;

    /**
     * Constant. The default wet status duration.
     */
    private static final int WET_STATUS_DURATION = 20;

    /**
     * Constructor
     * @param bucket the specific bucket used in the action
     */
    public SelfPourAction(Bucket bucket){
        this.bucket = bucket;
    }

    /**
     * If the bucket contains water, apply the wet enum on the actor and empty out the bucket (set bucket state false)
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return different strings based on the bucket state
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        if(this.bucket.containsWater()) {
            actor.addCapability(Ability.WET);
            ((Player) actor).getAbilityManager().addAbility(Ability.WET, WET_STATUS_DURATION);
            this.bucket.setBucketState(false);
            return actor + " has poured the bucket's contents on themselves!";
        }
        return "The bucket is empty!";
    }

    /**
     * @param actor The actor performing the action.
     * @return the option for the player to perform the action
     */
    @Override
    public String menuDescription(Actor actor) {
        String output = actor + " uses bucket to pour water on themselves.";
        if(this.bucket.containsWater()){
            output += " (bucket is filled)";
        }else {
            output += " (bucket is empty)";
        }
        return output;
    }
}
