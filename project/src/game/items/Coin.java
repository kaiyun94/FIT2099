package game.items;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.DropItemAction;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.items.PickUpItemAction;
import edu.monash.fit2099.engine.positions.Location;
import game.capabilities.Status;
import game.actions.PickUpCoinAction;
import game.reset.Resettable;
import java.util.ArrayList;
import java.util.List;

/**
 * the currency of the game
 */
public class Coin extends Item implements Resettable {

    /**
     * set up the attributes of the class
     */
    private int amount;

    /**
     * Constructor
     */
    public Coin(int amount) {
        super("Coin", '$', true);
        this.amount = amount;
        this.registerInstance();
    }

    /**
     * @return the value of the coin
     */
    public int getAmount() {
        return amount;
    }

    /**
     * if the game is reset, wipes all instance of coins around the map
     * @param currentLocation The location of the ground on which we lie.
     */
    @Override
    public void tick(Location currentLocation) {
        if(this.hasCapability(Status.RESET_NOW)) {
            currentLocation.removeItem(this);
            this.removeCapability(Status.RESET_NOW);
        }
    }

    /**
     * allows the actor to pick up the coin
     * @param actor the actor performing the action
     * @return new pick up action instance of the coin
     */
    @Override
    public PickUpItemAction getPickUpAction(Actor actor) {
        return null;
    }

    /**
     * Allows an actor to pick up coins.
     * @return a list of actions to perform on the coin
     */
    public List<Action> getAllowableActions(){
        List <Action> actions= new ArrayList<>();
        actions.add(new PickUpCoinAction(this));
        return actions;
    }

    /**
     * does not allow the actor to drop coins
     * @param actor the actor performing the action
     * @return null
     */
    @Override
    public DropItemAction getDropAction(Actor actor) {
        return null;
    }


    /**
     * adds the RESET_NOW capability
     */
    public void resetInstance() {
        this.addCapability(Status.RESET_NOW);
    }
}
