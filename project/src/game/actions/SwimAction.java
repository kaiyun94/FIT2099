package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;

/**
 * An action that allows actors to swim across a swimmable terrain.
 */
public class SwimAction extends Action {

    /**
     * Target location
     */
    private Location moveToLocation;

    /**
     * One of the 8-d navigation
     */
    private String direction;

    /**
     * Constructor
     * @param moveToLocation
     * @param direction
     */
    public SwimAction(Location moveToLocation, String direction){
        this.moveToLocation = moveToLocation;
        this.direction = direction;
    }

    /**
     * Allows the actor to swim to a location
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return the message when the action is executed
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        map.moveActor(actor, this.moveToLocation);
        return menuDescription(actor);
    }

    /**
     * The display menu
     * @param actor The actor performing the action.
     * @return a menu description
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " swims " + this.direction;
    }
}
