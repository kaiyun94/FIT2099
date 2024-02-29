package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;

import java.util.Stack;

/**
 * Allows an actor to teleport to a new world/
 */
public class TeleportAction extends Action {


    /**
     * A stack to store the source and destination.
     */
    private static Stack <Location> stack = new Stack<>();

    /**
     * The source location
     */
    private Location source;

    /**
     * The destination location
     */
    private Location destination;

    /**
     * Constructor
     * @param source source location
     * @param destination destination location
     */
    public TeleportAction(Location source, Location destination){
        this.source = source;
        this.destination = destination;
    }

    /**
     * If the stack is empty, we are teleporting from the source world to another world.
     * If the stack is not empty, we are teleporting from the another world back to source world.
     * @param actor The actor performing the action.
     * @param gameMap the game map
     * @return the message of the action
     */
    @Override
    public String execute(Actor actor, GameMap gameMap) {
        String message;
        if(stack.isEmpty()){
            stack.push(this.source);
            stack.push(this.destination);

            // When teleporting, we can kill the piranha plant that is on the warp pipe.
            if(this.destination.containsAnActor()){
                Actor otherActor = this.destination.getActor();
                    gameMap.removeActor(otherActor);
            }
            // Teleporting
            gameMap.moveActor(actor,this.destination);
            message = "Teleporting to Lava Zone";
        }
        else{
            Location destination = stack.pop();
            Location source = stack.pop();
            // When teleporting, we can kill the piranha plant that is on the warp pipe.
            if(source.containsAnActor()){
                Actor otherActor = source.getActor();
                gameMap.removeActor(otherActor);
            }
            // Teleporting
            gameMap.moveActor(actor, source);
            message = "Teleporting back home";
        }
        return message;
    }

    /**
     * Shows the menu description
     * @param actor The actor performing the action.
     * @return the menu description
     */
    @Override
    public String menuDescription(Actor actor) {
        return "Teleport to another world";
    }
}
