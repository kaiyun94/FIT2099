package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.Weapon;
import game.capabilities.Ability;
import game.capabilities.Status;
import game.Util;

/**
 * Special Action for attacking other Actors.
 */
public class AttackAction extends Action {

	/**
	 *  A ridiculous amount of damage. Used to one-shot enemies.
	 */
	private static final int ONE_SHOT = 100000;

	/**
	 * The Actor that is to be attacked
	 */
	private Actor target;

	/**
	 * The direction of incoming attack.
	 */
	private String direction;

	/**
	 * Constructor.
	 * 
	 * @param target the Actor to attack
	 * @param direction the direction to attack
	 */
	public AttackAction(Actor target, String direction) {
		this.target = target;
		this.direction = direction;
	}

	/**
	 * processes the outcome of an actor attacking another actor and checking the various status
	 * and conditions which could affect the outcome
	 *
	 * @param actor The actor performing the action.
	 * @param map The map the actor is on.
	 * @return the outcome of the attack action
	 */
	@Override
	public String execute(Actor actor, GameMap map) {

		Weapon weapon = actor.getWeapon();

		// To indicate that the target has been attacked and is now engaged in a fight.
		target.addCapability(Status.ATTACKED);

		if (!Util.isSuccess(weapon.chanceToHit())) {
			// if actors missed attack
			return actor + " misses " + target + ".";
		}

		int damage = weapon.damage();

		if(target.hasCapability(Ability.INVINCIBLE)) {
			// if the opponent is invincible, the attacker does 0 damage
			damage = 0;
		}
		if(actor.hasCapability(Ability.INVINCIBLE)) {
			// if the attacker is invincible, the attacker does 100000 damage. Also known as instant kill
			damage = ONE_SHOT;
		}
		String result = actor + " " + weapon.verb() + " " + target + " for " + damage + " damage.";
		target.hurt(damage); // target receives damage

		if(target.hasCapability(Ability.TALL)) {
			//if the attacked opponent had the TALL capability, once hurt, the capability is removed
			if(damage != 0) {
				target.removeCapability(Ability.TALL);
			}
		}

		if(actor.hasCapability(Status.ARSONIST)){
			Action arson = new ArsonAction(this.target);
			arson.execute(actor, map);
		}

		if (!target.isConscious()) {
			ActionList dropActions = new ActionList();
			// drop all items
			for (Item item : target.getInventory())
				dropActions.add(item.getDropAction(actor));
			for (Action drop : dropActions)
				drop.execute(target, map);
			if(target.hasCapability(Status.DORMANT_CAPABLE)) {
				result += System.lineSeparator() + target + " goes to Dormant State.";
			}
			else {
				map.removeActor(target);
				result += System.lineSeparator() + target + " is killed.";
			}
		}
		return result;
	}

	/**
	 * @param actor The actor performing the action.
	 * @return the string option for the player to attack an enemy at a specific direction
	 */
	@Override
	public String menuDescription(Actor actor) {
		return actor + " attacks " + target + " at " + direction;
	}

}
