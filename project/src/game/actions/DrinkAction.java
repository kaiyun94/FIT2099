package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.capabilities.Indicator;
import game.capabilities.Status;
import game.actors.Player;
import game.items.MagicalWater;

/**
 *  A class that executes the action of drinking water
 */
public class DrinkAction extends Action {

    /**
    * magical water instance
     */
    private MagicalWater magicalWater;

    /**
     * the amount of damage increased after drinking the power water
     */
    private static final int FOUNTAIN_DMG_INCREASE = 15;

    /**
     * Constructor for the player to instantiate the drink action
     */
    public DrinkAction() {
        magicalWater= null;
    }

    /**
     * Constructor for the enemy to instantiate the drink action
     * @param magicalWater magical water instance
     */
    public DrinkAction(MagicalWater magicalWater) {
        this.magicalWater = magicalWater;
    }

    /**
     * Execute the drink action so that the actor can drink the water when the actor stands on the fountain
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return a string that shows the output of the action
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        String result = "";
        // if the player has a magical container(bottle) in his/her inventory, get the magical water from the bottle
        if(actor.hasCapability(Status.HOSTILE_TO_ENEMY)) {
            if(actor.hasCapability(Indicator.MAGICAL_CONTAINER)) {
                magicalWater = ((Player)actor).getBottle().getMagicalWater();
            }
        }
        else {
            actor.addCapability(Status.CONSUMED_WATER);
        }
        // if the actor is still conscious, the actor can consume the magical water
        if(actor.isConscious()) {
            result = magicalWater.consume(actor, map);
        }
        return result;
    }

    /**
     * Returns a descriptive string that describes what can be consumed by the actor
     * @param actor The actor performing the action.
     * @return a description of the action that is printed out in the console menu
     */
    @Override
    public String menuDescription(Actor actor) {
       String result = "";
       // if the actor is a player, print this statement
       if(actor.hasCapability(Status.HOSTILE_TO_ENEMY)) {
           result = actor + " drinks " + ((Player) actor).getBottle();
        }
       return result;
    }


}
