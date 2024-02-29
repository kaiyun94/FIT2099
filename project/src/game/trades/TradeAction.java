package game.trades;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * An action to perform trading of items
 */
public class TradeAction extends Action {

    /**
     * a tradable instance
     */
    private final Tradable tradable;

    /**
     * Constructor
     * @param tradable a tradable instance
     */
    public TradeAction(Tradable tradable) {
        this.tradable = tradable;
    }

    /**
     * Execute the trade action
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return a description of what happened that can be displayed to the user.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        int balance = Wallet.getInstance().getBalance();
        int price = tradable.getPrice();
        // if the balance is enough to buy the tradable item, add the item into actor's inventory, update the balance and get a message to be printed out
        if(balance - price >= 0) {
            actor.addItemToInventory((Item) tradable);
            Wallet.getInstance().removeBalance(price);
            return actor + " obtained " + tradable;
        }
        // if balance is not enough, print this statement
        return "You don't have enough coins!";
    }

    /**
     * Returns a descriptive string
     * @param actor The actor performing the action.
     * @return the text we put on the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " buys " + tradable + " " + "($" + tradable.getPrice() + ")" ;
    }

}
