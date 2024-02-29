package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.capabilities.Indicator;
import game.grounds.Fountain;
import game.items.Bottle;
import game.items.Bucket;
import game.grounds.WaterSource;

/**
 * A class that executes the action of filling the water
 */
public class FillAction extends Action {

    /**
     * water source instance
     */
    private WaterSource waterSource;

    /**
     * Constructor
     * @param waterSource water source that is where the water is obtained from
     */
    public FillAction(WaterSource waterSource) {
        this.waterSource = waterSource;
    }

    /**
     * Perform fill water action to fill water into a refill capable item
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return a string that shows the output of the action
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        // check for the actor's inventory
        for (Item item : actor.getInventory()) {
            // if the water source can spurt water (e.g. fountain)
            if (this.waterSource.hasCapability(Indicator.SPURTABLE)) {
                Bottle bottle = null;
                // and the player has a bottle in his/her inventory, fill the magical water into the bottle
                if (item.hasCapability(Indicator.MAGICAL_CONTAINER)) {
                    bottle = (Bottle) item;
                    return bottle.fill(bottle, actor, waterSource);
                }
            }
            // else if the water source is a body of water (e.g. river)
            else if(this.waterSource.hasCapability(Indicator.BODY_OF_WATER)){
                Bucket bucket = null;
                // if the player has a bucket in his/her inventory, fill the bucket with river water
                if (item.hasCapability(Indicator.BUCKET_POINTER)) {
                    bucket = (Bucket) item;
                    return bucket.fill(bucket, actor, waterSource);
                }
            }
        }
        return null;
    }

    /**
     * a description of the fill action
     * @param actor The actor performing the action.
     * @return a string description shown on the console menu
     */
    @Override
    public String menuDescription(Actor actor) {
        String result = "";
        // if the water source can spurt water(e.g. fountain), print this statement
        if(waterSource.hasCapability(Indicator.SPURTABLE)) {
            result += actor + " refills " + "from " + waterSource + " (" + ((Fountain)waterSource).getRefillCounter() + "/" + Fountain.getRefillCapacity() + ")";
        }
        // else if the water source is a body of water(e.g. river), print this statement
        else if(waterSource.hasCapability(Indicator.BODY_OF_WATER)){
            result += actor + " refills " + "from " + waterSource;
        }
        return result;
    }
}
