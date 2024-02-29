package game.reset;


/**
 * An interface for resettable instances
 */
public interface Resettable {
    /**
     * Allows any classes that use this interface to reset abilities, attributes, and/or items.
     */
    void resetInstance();

    /**
     * a default interface method that register current instance to the Singleton manager.
     */
    default void registerInstance(){
        ResetManager.getInstance().appendResetInstance(this);
    }
}


