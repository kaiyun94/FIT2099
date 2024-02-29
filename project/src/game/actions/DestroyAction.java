package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.items.SuperMushroom;

/**
 *  A class that executes the action of destroy the Koopa's shell
 */
public class DestroyAction extends Action {

    /**
     * actor to be destroyed
     */
    private Actor target;

    /**
     * Constructor
     * @param target actor to be destroyed
     */
    public DestroyAction(Actor target) {
        this.target = target;
    }

    /**
     * Perform the action to destroy the shell and turns into super mushroom
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return a description of what happened that can be displayed to the user.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        String result = "";
        map.locationOf(target).addItem(new SuperMushroom());
        map.removeActor(target);
        return result;
    }

    /**
     * Returns a descriptive string
     * @param actor The actor performing the action.
     * @return the text we put on the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " " + actor.getWeapon().verb() + " " + target + "(dormant)";
    }
}
