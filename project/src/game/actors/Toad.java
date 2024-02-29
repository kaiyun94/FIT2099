package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.GetAction;
import game.items.Bottle;
import game.items.PowerStar;
import game.items.SuperMushroom;
import game.items.Wrench;
import game.monologue.SpeakAction;
import game.trades.TradeAction;

/**
 * An actor that stands in the middle of the game map
 */
public class Toad extends Actor{

    /**
     * Constructor
     */
    public Toad() {
        super("Toad", 'O', 500);
    }

    /**
     * Returns a new collection of the Actions that the otherActor can do to the Toad.
     * @param otherActor the Actor that might be performing action
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return a list of allowable actions that can perform to the current actor
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = new ActionList();
        addTradeAction(actions);
        actions.add(new SpeakAction(this));
        if(GetAction.getCounter()<1) {
            actions.add(new GetAction(new Bottle()));
        }
        return actions;
    }

    /**
     * Select and return an action to perform on the current turn.
     * @param actions    collection of possible Actions for Toad
     * @param lastAction The Action Toad took last turn.
     * @param map        the map containing the Toad
     * @param display    the I/O object to which messages may be written
     * @return a list of allowable actions that can perform to the current actor
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        if (lastAction.getNextAction() != null)
            return lastAction.getNextAction();
		return new DoNothingAction();
    }

    /**
     * Add a capability to the Toad.
     * @param capability the Capability to add
     */
    @Override
    public void addCapability(Enum<?> capability) {
        super.addCapability(capability);
    }

    /**
     * Add trade action into the action list
     * @param actions action list that Toad has
     */
    public void addTradeAction(ActionList actions) {
        actions.add(new TradeAction(new Wrench()));
        actions.add(new TradeAction(new SuperMushroom()));
        actions.add(new TradeAction(new PowerStar()));
    }

}
