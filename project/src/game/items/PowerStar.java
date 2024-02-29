package game.items;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Location;
import game.capabilities.Indicator;
import game.capabilities.Status;
import game.actions.ConsumeAction;
import game.trades.Tradable;

import java.util.List;

/**
 * A class that implements the PowerStar magical item
 */
public class PowerStar extends MagicalItem implements Tradable {

    /**
     * A countdown to how many rounds the item has before fading away
     */
    private int tickCounter = 10;

    /**
     *  the fixed trade price of the item
     */
    private static final int PRICE = 600;

    /**
     * Constructor
     * Adds POWERSTAR_POINTER and FADE capabilities to PowerStar
     */
    public PowerStar() {
        super("PowerStar", '*', true);
        this.addCapability(Indicator.POWERSTAR_POINTER);
        this.addCapability(Status.FADABLE);
        this.addAction(new ConsumeAction(this));
    }

    /**
     * Performs a countdown for the current instance of PowerStar and removes the item from the ground
     * if the countdown reaches 0
     * @param location the current location of the item
     */
    @Override
    public void tick(Location location) {
        if(this.tickCounter <= 1){
            location.removeItem(this);
            this.tickCounter = 10;
        } else{
            this.tickCounter--;
        }
    }

    /**
     * @return a list of actions that PowerStar can perform
     */
    @Override
    public List<Action> getAllowableActions() {
        return super.getAllowableActions();
    }

    /**
     * Performs a countdown for the current instance of PowerStar and removes the item from the actor's inventory
     * if the countdown reaches 0
     * @param currentLocation The location of the actor carrying this Item.
     * @param actor The actor carrying this Item.
     */
    @Override
    public void tick(Location currentLocation, Actor actor) {
        if(actor.getInventory().contains(this)) {
            if (this.tickCounter <=1) {
                actor.removeItemFromInventory(this);
                this.tickCounter = 10;
            } else {
                this.tickCounter--;
            }
        }
    }

    /**
     * @return PowerStar's trade price
     */
    @Override
    public int getPrice() {
        return PRICE;
    }


    /**
     * @return the current countdown of the item
     */
    public int getTickCounter() {
        return this.tickCounter;
    }

}
