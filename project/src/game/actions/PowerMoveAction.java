package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.grounds.Dirt;
import game.items.Coin;

/**
 * An action that allows an INVINCIBLE actor to access jumpable terrains without having to jump. At the same time,
 * destroys jumpable terrains and converts them into coins.
 */
public class PowerMoveAction extends Action {

    /**
     * Target location
     */
    private Location moveToLocation;

    /**
     * One of the 8-d navigation
     */
    private String direction;

    /**
     * Constructor.
     * @param moveToLocation the location to move to
     * @param direction the direction to move to
     */
    public PowerMoveAction(Location moveToLocation, String direction){
        this.moveToLocation = moveToLocation;
        this.direction = direction;
    }

    /**
     * Moves the actor. Converts the ground into dirt and produces a coin.
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return a message describing the action performed
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        map.moveActor(actor,moveToLocation);
        moveToLocation.setGround(new Dirt());
        moveToLocation.addItem(new Coin(5));
        return "Free money yay";
    }

    /**
     * Displays the menu
     * @param actor The actor performing the action.
     * @return a menu description
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " moves " + direction;
    }
}
