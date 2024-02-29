package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.grounds.Fire;

/**
 * allows an actor to set ground on fire
 */
public class ArsonAction extends Action {

    /**
     * The Actor that is to be attacked
     */
    private Actor target;

    /**
     * constructor
     * @param target the targeted actor
     */
    public ArsonAction(Actor target){
        this.target = target;
    }

    /**
     * the attacker will set the ground of where the target is currently standing on fire, if the ground is already
     * on fire, extend the fire duration.
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return a string which varies if the ground is already on fire or not
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        Location location = map.locationOf(this.target);
        Ground currGround = location.getGround();
        String output;

        if(!(currGround instanceof Fire)) {
            location.setGround(new Fire(currGround));
            output = actor + " has ignited the ground";
        } else {
            // if the ground is ardy on fire, add duration
            ((Fire) currGround).addTimer();
            output = actor + " has caused the ground to burn longer";
        }

        return output;

    }

    /**
     * does not return a menu description as only Bowser is setting the ground on fire
     * @param actor The actor performing the action.
     * @return null
     */
    @Override
    public String menuDescription(Actor actor) {
        return null;
    }
}
