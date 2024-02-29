package game.grounds;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import game.actors.Enemy;

/**
 * A class that represents the floor inside a building.
 */
public class Floor extends Ground {

	/**
	 * Constructor for floor.
	 */
	public Floor() {
		super('_');
	}

	/**
	 * Checks if an actor can move into this ground type
	 * @param actor the Actor to check
	 * @return a boolean to indicate if actor can move into this ground type
	 */
	@Override
	public boolean canActorEnter(Actor actor) {
		// All actors except for enemies can enter floor
		return !(actor instanceof Enemy);
	}
}
