package game.items;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import game.actions.SelfPourAction;
import game.capabilities.Indicator;
import game.grounds.WaterSource;

/**
 * a neat metal object that stores water
 */
public class Bucket extends Item implements RefillCapable{

    /**
     * Indicates the default bucket state (false means bucket is empty)
     */
    private static final boolean DEFAULT_BUCKET_STATE = false;

    /**
     * bucket state shows the current state of the bucket whether it contains water or not
     */
    private boolean bucketState;

    /**
     * Constructor
     * adds the BUCKET_POINTER capability
     * set the default bucket state to false (empty)
     * adds the SelfPourAction to its possible actions
     */
    public Bucket(){
        super("Bucket", '@', true);
        this.addCapability(Indicator.BUCKET_POINTER);
        setBucketState(DEFAULT_BUCKET_STATE);
        this.addAction(new SelfPourAction(this));
    }

    /**
     * setter for bucket state
     * @param newState a boolean to indicate the bucket state
     */
    public void setBucketState(boolean newState){
        this.bucketState = newState;
    }

    /**
     * getter for bucket state
     * @return the current bucket state
     */
    @Override
    public boolean containsWater(){
        return this.bucketState;
    }

    /**
     * A method to fill the water from the water source into a bucket
     * @param refillCapable an instance that implements refillCapable interface (bucket)
     * @param actor an actor that is acting
     * @param waterSource the water source to be obtained from
     * @return a string description after executing fill method
     */
    @Override
    public String fill(RefillCapable refillCapable, Actor actor, WaterSource waterSource) {
        this.setBucketState(true);
        if(refillCapable.containsWater()) {
            return "The bucket is successfully filled!!";
        }
        return "The bucket is out of water";
    }
}
