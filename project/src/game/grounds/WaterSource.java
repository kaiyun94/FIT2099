package game.grounds;

import edu.monash.fit2099.engine.positions.Ground;

/**
 * An abstract class that represents the source of water, e.g. fountain, river and etc.
 */
public abstract class WaterSource extends Ground {

    /**
     * Constructor to create a water source instance
     * @param displayChar character to display for water source
     */
    public WaterSource(char displayChar) {
        super(displayChar);
    }
}
