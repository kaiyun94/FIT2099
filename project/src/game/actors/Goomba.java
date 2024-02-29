package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.Weapon;
import game.capabilities.Status;
import game.Util;

/**
 * A little fungus guy.
 */
public class Goomba extends Enemy {

	/**
	 * declare the base damage
	 */

	private static final int BASE_DAMAGE = 10;

	/**
	 * declare the base hitrate
	 */
	private static final int BASE_HITRATE = 50;

	private static final String ATTACK_VERB = "punches";

	/**
	 * declare the constant suicide rate
	 */
	private static final int SUICIDE = 10;


	/**
	 * Constructor.
	 */
	public Goomba() {
		super("Goomba", 'g', 20);
		setDamage(BASE_DAMAGE);
		setHitrate(BASE_HITRATE);
		setAttackVerb(ATTACK_VERB);
	}


	/**
	 * playTurn handles the behaviours and actions performed by Goomba based on its current situation,
	 * status and position on the map
	 *
	 * @param actions collection of possible Actions for this Actor
	 * @param lastAction the last action performed by Goomba
	 * @param map the map containing the Actor
	 * @param display the I/O object to which messages may be written
	 * @return the suitable action based on its current situation/position
	 */
	@Override
	public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {

		if (Util.isSuccess(SUICIDE)) { // Goomba has a 10% chance to be removed from the map
			map.removeActor(this);
		}
		else{
			Action action = super.playTurn(actions, lastAction, map, display);
			if (action != null) {
				// if Goomba already has an action, perform it
				return action;
			}
			else {
				// Attack behaviour. Attack player automatically.
				action = getBehaviours().get(10).getAction(this, map);
				if (action != null) {
					if(!this.hasCapability(Status.ATTACKED)) {
						this.addCapability(Status.ATTACKED);
					}
					return action;
				}
				else if (this.hasCapability(Status.ATTACKED)) {
					// Follow behaviour
					action = getBehaviours().get(11).getAction(this, map);
					this.removeCapability(Status.CONSUMED_WATER);  // if Goomba is away from the fountain and now following the player, remove the capability of already consumed water
				}
				else {
					// Wander behaviour
					action = getBehaviours().get(12).getAction(this, map);
				}
				if(action != null){
					return action;
				}
				else{
					return new DoNothingAction();
				}

			}
		}
		return new DoNothingAction();
	}


	/**
	 * @return Goomba's current weapon/method of combat
	 */
	@Override
	public Weapon getWeapon() {
		return super.getWeapon();
	}
}