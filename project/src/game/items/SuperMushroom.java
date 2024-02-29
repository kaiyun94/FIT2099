package game.items;

import game.capabilities.Indicator;
import game.actions.ConsumeAction;
import game.trades.Tradable;

/**
 * A class that implements the SuperMushroom magical item
 */
public class SuperMushroom extends MagicalItem implements Tradable {

    /**
     * the fixed trade price of the item
     */
    private static final int PRICE = 400;

    /**
     * Constructor
     * Adds SUPERMUSHROOM_POINTER capability to SuperMushroom
     */
    public SuperMushroom() {
        super("SuperMushroom", '^', true);
        this.addCapability(Indicator.SUPERMUSHROOM_POINTER);
        this.addAction(new ConsumeAction(this));
    }

    /**
     * @return SuperMushroom's trade price
     */
    @Override
    public int getPrice() {
        return PRICE;
    }


}
