package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.capabilities.Status;

import java.util.ArrayList;
import java.util.Random;

/**
 * a class which determines how an actor wanders around the map
 */
public class WanderBehaviour extends Action implements Behaviour {

	/**
	 * creates a new random object
	 */
	private final Random random = new Random();

	/**
	 * Returns a MoveAction to wander to a random location, if possible.  
	 * If no movement is possible, returns null.
	 * 
	 * @param actor the Actor enacting the behaviour
	 * @param map the map that actor is currently on
	 * @return an Action, or null if no MoveAction is possible
	 */
	@Override
	public Action getAction(Actor actor, GameMap map) {
		ArrayList<Action> actions = new ArrayList<>();

		// Loop through exits and check which direction actor can move in.
		for (Exit exit : map.locationOf(actor).getExits()) {
            Location destination = exit.getDestination();
            if (destination.canActorEnter(actor)) {
            	actions.add(exit.getDestination().getMoveAction(actor, "around", exit.getHotKey()));
            }
        }

		// Randomly select a moveAction
		if (!actions.isEmpty()) {
			actor.removeCapability(Status.CONSUMED_WATER); //
			return actions.get(random.nextInt(actions.size()));
		}
		else {
			return null;
		}

	}

	/**
	 * @param actor The actor performing the action.
	 * @param map The map the actor is on.
	 * @return the menu description for the actor
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		return menuDescription(actor);
	}

	/**
	 * @param actor The actor performing the action.
	 * @return the text speech of the actor in the menu
	 */
	@Override
	public String menuDescription(Actor actor) {
		return "Raagrh...";
	}
}
