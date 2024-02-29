package game.grounds;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.capabilities.Status;

/**
 *  A wall class. It can be jumped over, therefore it implements JumpableTerrain
 */
public class Wall extends Ground implements JumpableTerrain{

	/**
	 * Constant related to jump success rate
	 */
	private static final int JUMP_SUCCESS_RATE = 80;

	/**
	 * Constant related to jump fall damage
	 */
	private static final int JUMP_FALL_DAMAGE = 20;

	/**
	 * Constructor for Wall.
	 */
	public Wall() {
		super('#');
	}

	/**
	 * Checks if a player is eligible for jumping over a ground.
	 * @param actor the Actor acting
	 * @param location the current Location
	 * @param direction the direction of the Ground from the Actor
	 * @return action list
	 */
	public ActionList allowableActions(Actor actor, Location location, String direction){
		return allowJumpAction(actor, location, direction);
	}

	/**
	 * Checks if an actor can move into this ground type. An actor cannot move into a jumpable terrain, he must jump or fly.
	 * @param actor the Actor to check
	 * @return a boolean to indicate if actor can move into this ground type
	 */
	@Override
	public boolean canActorEnter(Actor actor) {
		if(actor.hasCapability(Status.FLIGHT)){
			return true;
		}
		return canActorMove(actor);
	}

	/**
	 * A getter to get jump rate.
	 * @return JUMP_SUCCESS_RATE
	 */
	@Override
	public int getJumpRate(){
		return JUMP_SUCCESS_RATE;
	}

	/**
	 * A getter to get fall damage.
	 * @return JUMP_FALL_DAMAGE
	 */
	@Override
	public int getFallDamage() {
		return JUMP_FALL_DAMAGE;
	}




}
