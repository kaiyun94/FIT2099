package game.items;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 *  A class that represents magical water
 */
public abstract class MagicalWater extends Item {

    /***
     * Constructor.
     *  @param name the name of this Item
     * @param displayChar the character to use to represent this item if it is on the ground
     * @param portable true if and only if the Item can be picked up
     */
    public MagicalWater(String name, char displayChar, boolean portable) {
        super(name, displayChar, portable);
    }

    /**
     * An abstract method that perform the operation when the actor is consuming the magical water
     * @param actor the actor that consume the magical water
     * @param map the map that the actor is currently in
     * @return a string describe the status of the actor after consuming the magical water
     */
    public abstract String consume(Actor actor, GameMap map);
}
