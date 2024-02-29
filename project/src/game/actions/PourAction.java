package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.grounds.Fire;


/**
 * Actor pours water on ground
 */
public class PourAction extends Action {

    /**
     * to keep track of the specific fire
     */
    private Fire fire;

    /**
     * to keep track  of the fire's location
     */
    private Location fireLocation;

    /**
     * Constructor
     * @param fire the specific fire selected in the interaction
     * @param location the location of the specific fire
     */
    public PourAction(Fire fire, Location location){
        this.fire = fire;
        this.fireLocation = location;
    }

    /**
     * Actor uses the bucket and puts out the fire on the map
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return a string indicating the actor has put out the fire
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        String result = actor + " has put out the fire";
        this.fire.setTimer(0);
        return result;
    }

    /**
     * @param actor The actor performing the action.
     * @return a string showing the possible interaction the player and the fire
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " put out the fire at (" + this.fireLocation.x() + ", " + this.fireLocation.y() + ")";
    }
}
