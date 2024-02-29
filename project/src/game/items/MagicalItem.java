package game.items;

import edu.monash.fit2099.engine.items.Item;

/**
 * an abstract class that implements commonly used methods by magical items
 */
public abstract class MagicalItem extends Item {

    /***
     * Constructor.
     * allows magical items to be consumed by an actor
     *  @param name the name of this Item
     * @param displayChar the character to use to represent this item if it is on the ground
     * @param portable true if and only if the Item can be picked up
     */
    public MagicalItem(String name, char displayChar, boolean portable) {
        super(name, displayChar, portable);
//        this.addAction(new ConsumeAction(this));
    }

    /**
     * @return the remaining number of ticks an item has
     */
    public int getTickCounter() {return 0;}
}
