package game.items;

import edu.monash.fit2099.engine.actors.Actor;
import game.grounds.WaterSource;

/**
 * An interface for refillCapable instance
 */
public interface RefillCapable {

     /**
      * A method to fill the water from the water source into a container
      * @param refillCapable an instance that implements this interface
      * @param actor an actor that is acting
      * @param waterSource the water source to be obtained from
      * @return a string description after executing fill method
      */
     String fill(RefillCapable refillCapable, Actor actor, WaterSource waterSource);

     /**
      * A method to check if the item contains water
      * @return true if the item contains water, false otherwise
      */
     boolean containsWater();

}
