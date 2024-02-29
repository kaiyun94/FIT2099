package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.capabilities.Indicator;
import game.capabilities.Status;
import game.actions.AttackAction;
import game.behaviours.*;
import game.reset.Resettable;

import java.util.Map;
import java.util.TreeMap;

/**
 * An abstract class that implements commonly used methods by enemies
 */
public abstract class Enemy extends Actor implements Resettable {

    private final Map<Integer, Behaviour> behaviours = new TreeMap<>(); // priority, behaviour

    private static final int BASE_DAMAGE = 5;
    private static final int BASE_HITRATE = 50;

    private int damage;
    private int hitrate;
    private String attackVerb;

    /**
     * Constructor.
     * assigns the wander behaviour to be the default and least prioritised behaviour.
     * registers the instance of the enemy
     * @param name enemy's name
     * @param displayChar the enemy's display character
     * @param hitPoints the enemy's hit points
     */
    public Enemy(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
        this.behaviours.put(9, new DrinkBehaviour());
        this.behaviours.put(10, new AttackBehaviour());
        this.behaviours.put(12, new WanderBehaviour());
        this.registerInstance();
        setDamage(BASE_DAMAGE);
        setHitrate(BASE_HITRATE);
    }

    /**
     * provides the allowable actions to be performed by the actor based on its current location and situation
     * all enemies can only be attacked or attack HOSTILE opponents
     * if the opponent has the INVINCIBLE status, the actor if immediately killed (removed from the game)
     *
     * @param otherActor the Actor that might be performing attack
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return the available actions that can be performed by actor
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = new ActionList();
        // it can be attacked only by the HOSTILE opponent, and this action will not attack the HOSTILE enemy back.
        if(otherActor.hasCapability(Status.HOSTILE_TO_ENEMY)) {
            actions.add(new AttackAction(this,direction));
            this.behaviours.put(11, new FollowBehaviour(otherActor));
        }
        return actions;

    }

    /**
     * By default, it wipes out all enemies currently present on the map
     *
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return null
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        if(this.hasCapability(Status.RESET_NOW)) {
           map.removeActor(this);
           this.removeCapability(Status.RESET_NOW);
           return new DoNothingAction();
        }
        Action action = null;
        // Drink Behaviour as the first priority if the enemy is on the fountain.
        if(map.locationOf(this).getGround().hasCapability(Indicator.SPURTABLE)) {
            action = getBehaviours().get(9).getAction(this, map);
        }
        return action;
    }

    /**
     * Helper method to reset enemies.
     */
    @Override
    public void resetInstance() {
        this.addCapability(Status.RESET_NOW);
    }

    /**
     * @return enemy's damage stat
     */
    public int getDamage(){return this.damage;}

    /**
     *
     * @return enemy's hit rate
     */
    public int getHitRate(){return this.hitrate;}

    /**
     * @return enemy's hashmap behaviour
     */
    public Map<Integer, Behaviour> getBehaviours() {
        return this.behaviours;
    }

    public void setDamage(int newDamage){
        this.damage = newDamage;
    }

    /**
     * add extra damage to the enemy's attack base damage
     * @param extraDamage extra damage to be added into enemy's attack base damage
     */
    public void addDamage(int extraDamage) {
        if(this.hasCapability(Status.POWERED_UP))
            this.damage += extraDamage;
    }

    /**
     * set enemy's hit rate to the new hit rate
     * @param newHitrate new hit rate that enemy has
     */
    public void setHitrate(int newHitrate){
        this.hitrate = newHitrate;
    }

    /**
     * set a verb that describes the attack performed by enemy
     * @param newAttackVerb the verb used to describe the attack done by enemy to the other actor
     */
    public void setAttackVerb(String newAttackVerb){
        this.attackVerb = newAttackVerb;
    }

    /**
     * create an intrinsic weapon for the enemy
     * @return an instantiated intrinsic weapon
     */
    @Override
    protected IntrinsicWeapon getIntrinsicWeapon() {
        return new IntrinsicWeapon(this.getDamage(), this.attackVerb);
    }
}
