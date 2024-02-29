package game.actors;


import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.capabilities.Status;
import game.items.Key;
import game.reset.Resettable;

/**
 * King of Koopas, the legend itself, Bowser!
 */
public class Bowser extends Enemy implements Resettable {

    /**
     * declare the base damage
     */
    public static final int BASE_DAMAGE = 80;

    /**
     * declare the base hitrate
     */
    private static final int BASE_HITRATE = 50;

    /**
     * Declare the default attack verb
     */
    private static final String ATTACK_VERB = "punches";

    /**
     * stores Bowser's initial location
     */
    private Location initialLocation;

    /**
     * shows if the initial location of bowser has been created
     */
    private boolean isInitialLocationAdded = false;

    /**
     * constructor
     */
    public Bowser(){
        super("Bowser", 'B', 500);
        setDamage(BASE_DAMAGE);
        setHitrate(BASE_HITRATE);
        setAttackVerb(ATTACK_VERB);
        this.addItemToInventory(new Key());
        this.addCapability(Status.ARSONIST);
    }

    /**
     * When the player resets the game, Bowser will return into its original position, heal up and is no longer aggro with Mario (stop following)
     * Bowser can only attack and follow opposing actors and has no wander actions
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return actions based on its current situations
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        if(!isInitialLocationAdded) {
            initialLocation = map.locationOf(this);
            isInitialLocationAdded = true;
        }
        if(this.hasCapability(Status.RESET_NOW)) {
            if(map.locationOf(this) != initialLocation) {
                if (initialLocation.containsAnActor()){
                    map.removeActor(map.getActorAt(initialLocation));
                }
                map.moveActor(this, initialLocation);
            }
            this.heal(this.getMaxHp());
            this.removeCapability(Status.ATTACKED);
            this.removeCapability(Status.RESET_NOW);
            return new DoNothingAction();
        }
        Action action = super.playTurn(actions, lastAction, map, display);
        if (action != null) {
            // if Bowser already has an action, perform it
            return action;
        }
        else {
            // Attack behaviour. Attack player automatically.
            action = getBehaviours().get(10).getAction(this, map);
            if (action != null) {
                if(!this.hasCapability(Status.ATTACKED)) {
                    this.addCapability(Status.ATTACKED);
                }
                return action;
            } else if (this.hasCapability(Status.ATTACKED)) {
                // Follow behaviour
                action = getBehaviours().get(11).getAction(this, map);
                if (action != null) {
                    return action;
                }
            }
        }
        return new DoNothingAction();
    }

    /**
     * adds the RESET_NOW enum
     */
    @Override
    public void resetInstance() {
        this.addCapability(Status.RESET_NOW);
    }
}
