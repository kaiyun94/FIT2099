package game.grounds;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.capabilities.Ability;
import game.capabilities.Indicator;
import game.actions.PourAction;
import game.actors.Player;
import game.items.Bucket;

/**
 * A fiery ground that does damage to actors.
 */
public class Fire extends Ground {

    /**
     * Constant. Fire self extinguishes after BASE_DURATION number of turns.
     */
    private static final int BASE_DURATION = 3;

    /**
     * Constant. The damage that fire causes an actor that is on this ground.
     */
    private static final int FIRE_DAMAGE = 20;

    /**
     * The previous ground that the fire will revert to after it is extinguished.
     */
    private Ground previousGround;

    /**
     * A timer to keep track of how long left till the fire is extinguished.
     */
    private int timer;

    /**
     * Constructor. Keeps track of the previous ground.
     * @param ground
     */
    public Fire(Ground ground){
        super('v');
        this.previousGround = ground;
        setTimer(BASE_DURATION);
    }

    /**
     * Sets the timer.
     * @param newTime the time to be set
     */
    public void setTimer(int newTime){
        this.timer = newTime;
    }

    /**
     * Adds to the timer.
     * @param time the time to be added
     */
    public void addTimer(int time){
        this.timer += time;
    }

    /**
     * Adds the default base duration to the timer.
     */
    public void addTimer(){
        this.timer += BASE_DURATION;
    }


    /**
     * Gives the actor that has a bucket that contains water a pour action.
     * @param actor the Actor acting
     * @param location the current Location
     * @param direction the direction of the Ground from the Actor
     * @return action list
     */
    @Override
    public ActionList allowableActions(Actor actor, Location location, String direction) {
        ActionList actions = new ActionList();

        // If the actor has a bucket
        for(Item item: actor.getInventory()){
            if(item.hasCapability(Indicator.BUCKET_POINTER)){
                Bucket bucket = (Bucket) item;
                // If the bucket contains water, add a pour action
                if(bucket.containsWater()){
                    actions.add(new PourAction(this, location));
                    bucket.setBucketState(false);
                }
            }
        }
        return actions;
    }

    /**
     * At every turn, fire does fire damage to the actor that stands on it. Actors that are WET are immune to this damage, but they
     * will lose their WET status. After BASE_DURATION number of turns, the fire ground will revert back to its previous ground.
     * @param location The location of the Ground
     */
    @Override
    public void tick(Location location) {
        // First check if there is an actor at the location.
        if (location.containsAnActor()) {
            Actor actor = location.getActor();

            // If the actor has wet status, he does not receive damage but loses his wet status.
            if(actor.hasCapability(Ability.WET)){
                actor.removeCapability(Ability.WET);
                ((Player)actor).getAbilityManager().removeAbility(Ability.WET);
            }
            else if(this.timer > 0) {
                // Actor receives damage from fire ground since he is not wet.
                actor.hurt(FIRE_DAMAGE);
                // If the actor dies, we remove him from the game.
                if (!actor.isConscious()) {
                    location.map().removeActor(actor);
                }
            }
        }
        // When the fire ground has expired, convert fire ground back to it's previous ground.
        if (this.timer == 0) {
            // set ground to previous ground
            location.setGround(this.previousGround);
        }
        // Reduce the timer by 1 so that fire eventually goes away when the timer reaches 0.
        this.timer--;
    }


    /**
     * Checks if an actor can move into this ground type. All actors can enter fire.
     * @param actor the Actor to check
     * @return true
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        // All actors can enter fire.
        return true;
    }
}
