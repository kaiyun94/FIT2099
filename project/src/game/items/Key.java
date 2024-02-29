package game.items;

import edu.monash.fit2099.engine.items.Item;
import game.capabilities.Indicator;

/**
 * a key used to free Princess Peach
 */
public class Key extends Item {
    /**
     * constructor
     */
    public Key(){
        super("Key",'k', true);
        this.addCapability(Indicator.KEY_POINTER);
    }
}
