package game.reset;

import java.util.ArrayList;
import java.util.List;

/**
 * A global Singleton manager that does soft-reset on the instances.
 */
public class ResetManager{
    /**
     * A list of resettable instances (any classes that implements Resettable,
     * such as Player implements Resettable will be stored in here)
     */
    private List<Resettable> resettableList;

    /**
     * A singleton reset manager instance
     */
    private static ResetManager instance;

    /**
     * A counter to keep track how many times the game is reset
     */
    private int counter = 0;

    /**
     * Get the singleton instance of reset manager
     * @return ResetManager singleton instance
     */
    public static ResetManager getInstance(){
        if(instance == null){
            instance = new ResetManager();
        }
        return instance;
    }

    /**
     * Constructor
     */
    private ResetManager(){
        resettableList = new ArrayList<>();
    }


    /**
     * Reset the game by traversing through all the list
     * By doing this way, it will avoid using `instanceof` all over the place.
     */
    public void run() {
        counter += 1;
        // for each resettable instance, the instance is reset and the resettable list is cleaned up
        for (Resettable resettable : this.getResettableList()) {
            resettable.resetInstance();
            this.cleanUp(resettable); // To prevent the resettable list from growing too big
        }
    }

    /**
     * Add the Resettable instance to the list
     */
    public void appendResetInstance(Resettable reset){
        resettableList.add(reset);
    }

    /**
     * Remove a Resettable instance from the list
     * @param resettable resettable object
     */
    public void cleanUp(Resettable resettable){
        resettableList.remove(resettable);
    }

    /**
     * Getter for resettable instances
     * @return an array list of resettable instances
     */
    public ArrayList<Resettable> getResettableList() {
        return new ArrayList<>(this.resettableList);
    }

    /**
     * Get the counter to know how many times reset is being run
     * @return the number of times reset is run
     */
    public int getCounter() {
        return counter;
    }
}
