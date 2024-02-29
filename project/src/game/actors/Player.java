package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.displays.Menu;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.actions.DrinkAction;
import game.capabilities.Ability;
import game.capabilities.AbilityManager;
import game.capabilities.Indicator;
import game.capabilities.Status;
import game.items.Bottle;
import game.reset.ResetAction;
import game.reset.ResetManager;
import game.reset.Resettable;
import game.trades.Wallet;

import java.util.List;

/**
 * Class representing the Player.
 */
public class Player extends Actor implements Resettable, Jumpable, Swimmable {
	/**
	 * the constant menu provided for the player
	 */
	private static final Menu menu = new Menu();

	/**
	 * creates a new wallet for player
	 */
	private Wallet wallet;

	/**
	 * The base damage that the player can deal to other actors
	 */
	private int baseDamage = 5;

	/**
	 * The player's ability manager
	 */
	private AbilityManager abilityManager;


	/**
	 * Constructor.
	 * adds the HOSTILE_TO_ENEMY capability
	 * receives an instance of wallet
	 * @param name        Name to call the player in the UI
	 * @param displayChar Character to represent the player in the UI
	 * @param hitPoints   Player's starting number of hitpoints
	 */
	public Player(String name, char displayChar, int hitPoints) {
		super(name, displayChar, hitPoints);
		this.addCapability(Status.HOSTILE_TO_ENEMY);
		wallet = Wallet.getInstance();
		this.registerInstance();
		this.abilityManager = new AbilityManager(this);
	}

	/**
	 * provides the allowable actions to be performed by the actor based on its current location and situation
	 *
	 * @param otherActor the other actor interacting with the current actor
	 * @param direction  String representing the direction of the Actor
	 * @param map        current GameMap
	 * @return the available actions that can be performed by actor
	 */
	@Override
	public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
		return super.allowableActions(otherActor, direction, map);
	}

	/**
	 * Processes what the player has done, what it can do, and any lingering status effects every turn
	 *
	 * @param actions    collection of possible Actions for this Actor
	 * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
	 * @param map        the map containing the Actor
	 * @param display    the I/O object to which messages may be written
	 * @return the console menu
	 */
	@Override
	public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
		// Handle multi-turn Actions
		if (lastAction.getNextAction() != null)
			return lastAction.getNextAction();

		// if the player has a bottle on hand and the bottle contains water, add consume action
		if(this.getBottle() != null && this.getBottle().containsWater()) {
			actions.add(new DrinkAction());
		}

		Location locationOfActor = map.locationOf(this);
		display.println(this.name + this.printHp() + " at " + "(" + locationOfActor.x() + ", " + locationOfActor.y() + ")");
		display.println(Wallet.getInstance().toString());

		// if the game hasn't been reset before, add reset action
		if(ResetManager.getInstance().getCounter() < 1)
			actions.add(new ResetAction());

		// Run the ability Manager. Keeps tracks of status that will expire after some time
		String displayAbilities = abilityManager.run();
		if (displayAbilities.length() != 0){
			display.println(displayAbilities);
		}

		// return/print the console menu
		return menu.showMenu(this, actions, display);
	}

	/**
	 * a capability check for player
	 * @param capability the capability required
	 * @return if player currently has the capability
	 */
	@Override
	public boolean hasCapability(Enum<?> capability) {
		return super.hasCapability(capability);
	}

	/**
	 * checks if the player has TALL status, if yes returns M, if no returns m
	 * @return M or m depending on the character's current status
	 */
	@Override
	public char getDisplayChar(){
		return this.hasCapability(Ability.TALL) ? Character.toUpperCase(super.getDisplayChar()): super.getDisplayChar();
	}

	/**
	 * @return the wallet of the player
	 */
	private Wallet getWallet(){
		return wallet;
	}

	/**
	 * adds the RESETTABLE capability to player
	 * when a player is reset, their max hp will be reset back to default and current buffs will be removed
	 */
	@Override
	public void resetInstance() {
		// Reset HP
		this.resetMaxHp(this.getMaxHp());

		// Loop through capabilities and remove status'
		List<Enum<?>> capabilitiesList = capabilitiesList();
		for (Enum<?> status: capabilitiesList){
			if(status instanceof Ability){
				this.removeCapability(status);
			}
		}
		// Remove all abilities from ability manager as well
		abilityManager.clearAllAbilities();
	}

	/**
	 * create an intrinsic weapon for the player
	 * @return an instantiated intrinsic weapon
	 */
	@Override
	protected IntrinsicWeapon getIntrinsicWeapon() {
		if(this.hasCapability(Status.POWERED_UP)) {
			return new IntrinsicWeapon(this.baseDamage, "jabs");
		}
		return super.getIntrinsicWeapon();
	}

	/**
	 * add extra damage to player's attack base damage
	 * @param extraDamage extra damage to be added into player's attack base damage
	 */
	public void addDamage (int extraDamage) {
		if(this.hasCapability(Status.POWERED_UP))
			this.baseDamage += extraDamage;
	}

	/**
	 * get the bottle from player's inventory
	 * @return a bottle instance that the player has in the inventory
	 */
	public Bottle getBottle() {
		for(Item item: this.getInventory()) {
			if(item.hasCapability(Indicator.MAGICAL_CONTAINER)) {
				return (Bottle) item;
			}
		}
		return null;
	}

	/**
	 * getter for abilityManager
	 * @return abilityManager
	 */
	public AbilityManager getAbilityManager() {
		return abilityManager;
	}
}
