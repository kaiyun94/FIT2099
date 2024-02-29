package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.MoveActorAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;

/**
 * A class that figures out a MoveAction that will move the actor one step 
 * closer to a target Actor.
 * @see edu.monash.fit2099.demo.mars.Application
 */
public class FollowBehaviour implements Behaviour {

	private Actor target;

	/**
	 * Constructor.
	 *

	 */
	public FollowBehaviour(Actor target) {
		this.target = target;
	}

	/**
	 * allows the actor to follow its target by one move
	 * @param actor the Actor acting
	 * @param map the GameMap containing the Actor
	 * @return the MoveActorAction in direction of the target if conditions are met, else null
	 */
	@Override
	public Action getAction(Actor actor, GameMap map) {
		// If target or actor are no longer on map, return null
		if(!map.contains(target) || !map.contains(actor))
			return null;

		Location here = map.locationOf(actor);
		Location there = map.locationOf(target);

		// Compute Manhattan distance
		int currentDistance = distance(here, there);

		// Loop through exits, and return a moveActorAction that closes the distance between actor and target.
		for (Exit exit : here.getExits()) {
			Location destination = exit.getDestination();
			if (destination.canActorEnter(actor)) {
				int newDistance = distance(destination, there);
				if (newDistance < currentDistance) {
					return new MoveActorAction(destination, exit.getName());
				}
			}
		}
		return null;
	}

	/**
	 * Compute the Manhattan distance between two locations.
	 *
	 * @param a the first location
	 * @param b the first location
	 * @return the number of steps between a and b if you only move in the four cardinal directions.
	 */
	private int distance(Location a, Location b) {
		return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
	}
}