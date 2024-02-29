package game.grounds;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import game.capabilities.Indicator;

/**
 * A class that represents bare dirt.
 */
public class Dirt extends Ground {

	/**
	 * Constructor for dirt. A dirt is a fertile ground.
	 */
	public Dirt() {
		super('.');
		this.addCapability(Indicator.IS_FERTILE);
	}

	/**
	 * Checks if an actor can move into this ground type
	 * @param actor the Actor to check
	 * @return a boolean to indicate if actor can move into this ground type
	 */
	@Override
	public boolean canActorEnter(Actor actor) {
		// Every actor can walk on dirt
		return true;
	}
}
