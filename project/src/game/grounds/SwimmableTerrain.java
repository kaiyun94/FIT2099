package game.grounds;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.SwimAction;
import game.actors.Swimmable;


/**
 * Grounds that can be swam over should implement this interface.
 */
public interface SwimmableTerrain {

    /**
     * Gives a swim action to actors that can swim.
     * @param actor the actor
     * @param location the current location
     * @param direction the direction
     * @return a list of actions
     */
    default ActionList allowSwimAction(Actor actor, Location location, String direction){
        ActionList actions = new ActionList();
        if (location.containsAnActor()){
            // To prevent an actor from swimming at its own spot
            return actions;
        }
        else if (actor instanceof Swimmable) {
            // If actor can swim, add a swim action to actions
            actions.add(new SwimAction(location, direction));
        }
        return actions;
    }

    /**
     * Checks if an actor can move into this ground type. An actor cannot move into a swimmable terrain, he must swim.
     * @param actor the Actor to check
     * @return a boolean to indicate if actor can move into this ground type
     */
    default boolean canActorMove(Actor actor){
        return false;
    }
}
