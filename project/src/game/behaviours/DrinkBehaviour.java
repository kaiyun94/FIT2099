package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.capabilities.Indicator;
import game.capabilities.Status;
import game.actions.DrinkAction;
import game.items.HealingWater;
import game.items.PowerWater;

/**
 * A class represents a drink behaviour of an actor
 */
public class DrinkBehaviour implements Behaviour{

    /**
     * Constructor.
     */
    public DrinkBehaviour() {

    }

    /**
     * Allows the actor to get the drink action if condition is met
     * @param actor the Actor acting
     * @param map the GameMap containing the Actor
     * @return the attack action of the actor if conditions are met, else null
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        // Location of actor
        Location here = map.locationOf(actor);

        // if the enemy did not consume the magical water before and the enemy stands on the fountain, add consume action to it
        if(!actor.hasCapability(Status.CONSUMED_WATER)) {
            Ground ground = here.getGround();
            if(ground.hasCapability(Indicator.HEALING_WATER)) {
                return new DrinkAction(new HealingWater());
            } else if (ground.hasCapability(Indicator.BUFF_WATER)) {
                return new DrinkAction(new PowerWater());
            }
        }
        return null;
    }
}