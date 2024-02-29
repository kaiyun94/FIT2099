package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import game.capabilities.Status;
import game.reset.Resettable;

/**
 * a fancy plant with jaws that lives in warp pipes
 */
public class PiranhaPlant extends Enemy implements Resettable {

    /**
     * declare the base damage
     */
    public static final int BASE_DAMAGE = 90;

    /**
     * declare the base hitrate
     */
    private static final int BASE_HITRATE = 50;

    /**
     * declare its attack verb
     */
    private static final String ATTACK_VERB = "chomps";

    /**
     * constructor
     */
    public PiranhaPlant(){
        super("PiranhaPlant", 'Y', 150);
        setDamage(BASE_DAMAGE);
        setHitrate(BASE_HITRATE);
        setAttackVerb(ATTACK_VERB);
    }

    /**
     * If the player resets the game while Piranha Plant is alive, it will regen its hp and increased its max hp by 50
     * Piranha plants can allow attack opposing actors and is unable to move around
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return an action based on its current situation
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {

        if(this.hasCapability(Status.RESET_NOW)) {
            this.increaseMaxHp(50);
            this.heal(this.getMaxHp());
            this.removeCapability(Status.RESET_NOW);
            return new DoNothingAction();
        }
        Action action = super.playTurn(actions, lastAction, map, display);
        if (action != null) {
            // if Piranha Plant already has an action, perform it
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
