package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.items.Coin;
import game.trades.Wallet;

/**
 *  A class that executes the action of picking up coin
 */

public class PickUpCoinAction extends Action {

    /**
     an instance of a coin
     */
    private Coin coin;

    /**
     * Constructor
     * @param coin the coin to pick up
     */
    public PickUpCoinAction(Coin coin) {
        this.coin = coin;
    }

    /**
     * Perform the action to pick up the coin
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return a description of what happened that can be displayed to the user.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        Wallet.getInstance().addBalance(coin);
        map.locationOf(actor).removeItem(coin);
        return menuDescription(actor);
    }

    /**
     * Returns a descriptive string
     * @param actor The actor performing the action.
     * @return the text we put on the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " picks up the coin" + " ($" + coin.getAmount() + ")";
    }
}
