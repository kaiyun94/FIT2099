package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import game.capabilities.Indicator;
import game.capabilities.Status;
import game.actions.AttackAction;
import game.actions.DestroyAction;
import game.behaviours.FollowBehaviour;

import java.util.Objects;

/**
 * A bipedal turtle that's hostile towards the player
 */
public abstract class KoopaType extends Enemy {


    /**
     * show if koopa is in dormant state
     */
    private boolean isDormant = false;

    /**
     * declare the base damage
     */
    private static final int BASE_DAMAGE = 30;

    /**
     * declare the constant hitrate
     */
    private static final int BASE_HITRATE = 50;

    private static final String ATTACK_VERB = "punches";

    /**
     * Constructor
     * adds the DORMANT_CAPABLE capability to Koopa
     */
    public KoopaType(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
        this.addCapability(Status.DORMANT_CAPABLE);
        setDamage(BASE_DAMAGE);
        setHitrate(BASE_HITRATE);
        setAttackVerb(ATTACK_VERB);
    }

    /**
     * Koopa overrides allowableActions by adding on checks to see if it is currently not dormant before getting
     * actions, as Koopa is unable to move during its Dormant state.
     * Furthermore, if another actor is holding a wrench and attacks Koopa in its Dormant state, it will be killed
     * @param otherActor the other actor Koopa is interacting with
     * @param direction the direction Koopa is facing
     * @param map Koopa's location on the map
     * @return the available actions that can be performed by Koopa
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = new ActionList();
        // it can be attacked only by the HOSTILE opponent, and this action will not attack the HOSTILE enemy back.
        if(otherActor.hasCapability(Status.HOSTILE_TO_ENEMY) && !isDormant) {
            actions.add(new AttackAction(this,direction));
            getBehaviours().put(11, new FollowBehaviour(otherActor));
        }

        if(otherActor.hasCapability(Indicator.WRENCH_POINTER) && isDormant) {
            actions.add(new DestroyAction(this)); // if Koopa is killed by a wrench in dormant state, call destroy action
        }
        return actions;
    }

    /**
     * playTurn handles the behaviours and actions performed by Koopa based on its current situation,
     * status and position on the map
     *
     * @param actions collection of possible Actions for this Actor
     * @param lastAction the last action performed by Koopa
     * @param map the map containing the Actor
     * @param display the I/O object to which messages may be written
     * @return the suitable action based on its current situation/position
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        Action action = super.playTurn(actions, lastAction, map, display);
        if(action != null && !isDormant) {
            return action;
        } else {
            if(!this.isConscious() && !isDormant) {
                this.goToDormantState(); // if it's knocked out, enter Dormant state
            }
            else if (this.isConscious() && !isDormant){
                // Attack behaviour. Attack player automatically.
                action = getBehaviours().get(10).getAction(this, map);
                if (action != null) {
                    if(!this.hasCapability(Status.ATTACKED)) {
                        this.addCapability(Status.ATTACKED);
                    }
                    return action;
                }
                else if (this.hasCapability(Status.ATTACKED)) {
                    // Follow behaviour
                    action = getBehaviours().get(11).getAction(this, map);
                    this.removeCapability(Status.CONSUMED_WATER);  // if Koopa is away from the fountain and now following the player, remove the capability of already consumed water
                }
                else {
                    // Wander behaviour
                    action = getBehaviours().get(12).getAction(this, map);
                }
                return Objects.requireNonNullElseGet(action, DoNothingAction::new);
            }
        }
        return new DoNothingAction();
    }

    /**
     * changes the Koopa's display character to D and changes its dormant state to be true
     */
    public void goToDormantState() {
        this.isDormant = true;
        this.setDisplayChar('D');
    }

    /**
     * @return Koopa's hit rate
     */
    @Override
    public int getHitRate() {
        return this.BASE_HITRATE;
    }

}