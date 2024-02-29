package game.grounds;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.FillAction;
import game.actors.Enemy;
import game.capabilities.Ability;
import game.capabilities.Indicator;
import game.capabilities.Status;
import game.Util;
import game.actors.Player;
import game.items.Bucket;

/**
 * A river ground. Put a bunch of them together to form a lake. A river is a swimmable terrain, thus only actors can only swim or fly
 * to enter this ground. Enemies cannot enter river.
 */

public class River extends WaterSource implements SwimmableTerrain {

    /**
     * Constant related to chance of getting wet when an actor is on a river.
     */
    private static final int CHANCE_TO_GET_WET = 20;

    /**
     * Constant. The default wet status duration.
     */
    private static final int WET_STATUS_DURATION = 20;

    /**
     * Constructor.
     */
    public River(){
        super('~');
        this.addCapability(Indicator.BODY_OF_WATER);
    }


    /**
     * Returns a swim action to the actor if he can swim. If the actor has a bucket, the actor receives a fill action.
     * @param actor the Actor acting
     * @param location the current Location
     * @param direction the direction of the Ground from the Actor
     * @return an action list
     */
    @Override
    public ActionList allowableActions(Actor actor, Location location, String direction) {
        ActionList actions = new ActionList();
        actions.add(allowSwimAction(actor, location, direction));
        for(Item item: actor.getInventory()){
            if(item.hasCapability(Indicator.BUCKET_POINTER)){
                Bucket bucket = (Bucket) item;
                if(!bucket.containsWater()){
                    actions.add(new FillAction(this));
                }
            }
        }
        return actions;
    }

    /**
     * At every turn, the actor has a chance to get WET. Flying actors cannot get wet.
     * @param location The location of the Ground
     */
    @Override
    public void tick(Location location) {
        // First, check if there is an actor at the location. River has a 20% chance to get the actor wet. Flying actors cannot get wet.
        if(location.containsAnActor()){
            Actor actor = location.getActor();
            // If the actor cannot fly, then he has a chance to get wet.
            if(!actor.hasCapability(Status.FLIGHT)){
                if (Util.isSuccess(CHANCE_TO_GET_WET)){
                    actor.addCapability(Ability.WET);
                    ((Player) actor).getAbilityManager().addAbility(Ability.WET, WET_STATUS_DURATION);
                }
            }
        }
    }


    /**
     * Checks if an actor can move into this ground type. All actors cannot move into this ground, except for flying actors.
     * Enemies cannot enter river.
     * To enter this ground, an actor must swim or fly.
     * @param actor the Actor to check
     * @return a boolean to indicate if actor can move into this ground type
     */
    @Override
    public boolean canActorEnter(Actor actor){
        if (actor.hasCapability(Status.FLIGHT) && !(actor instanceof Enemy)){
            return true;
        }
        else{
            return canActorMove(actor);
        }
    }

    /**
     * @return river string
     */
    @Override
    public String toString() {
        return "river";
    }
}
