package game.grounds;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Location;
import game.capabilities.Indicator;
import game.capabilities.Status;
import game.actions.FillAction;
import game.items.MagicalWater;
import game.items.PowerWater;

/**
 * A class that represents a power fountain
 */
public class PowerFountain extends Fountain{

    /**
     * Constructor to create a power fountain
     */
    public PowerFountain() {
        super('A');
        this.addCapability(Indicator.BUFF_WATER);
    }

    /**
     * the actions that the actor can perform on the power fountain
     * @param actor the Actor acting
     * @param location the current Location
     * @param direction the direction of the Ground from the Actor
     * @return a new, empty collection of Actions
     */
    @Override
    public ActionList allowableActions(Actor actor, Location location, String direction) {
        ActionList actions = super.allowableActions(actor, location, direction);
        // if an actor is standing on the fountain and the actor is a player
        if(location.containsAnActor() && actor.hasCapability(Status.HOSTILE_TO_ENEMY)) {
            // if the player has a bottle, and there is at least one water slot in the fountain, the player has a fill action
            if(actor.hasCapability(Indicator.MAGICAL_CONTAINER) && (this.getRefillCounter() > 0)) {
                actions.add(new FillAction(this));
            }
        }
        return actions;
    }

    /**
     * get a new instance of power water from the power fountain
     * @return a new power water slot
     */
    @Override
    public MagicalWater getMagicalWater() {
        MagicalWater powerWater = new PowerWater();
        return powerWater;
    }

    /**
     * a string description of the power fountain
     * @return a string that displays the name of the fountain
     */
    @Override
    public String toString() {
        return "Power Fountain";
    }

}
