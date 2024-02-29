package game.items;

import edu.monash.fit2099.engine.actors.Actor;
import game.capabilities.Indicator;
import game.grounds.Fountain;
import game.grounds.WaterSource;

import java.util.ArrayList;

/**
 *  A class that represents a bottle which is a magical item to store infinite magical water
 */
public class Bottle extends MagicalItem implements RefillCapable {

    /**
     * an array list of magical water
     */
    private ArrayList<MagicalWater> magicalWaters;


    /***
     * Constructor.
     * Add capability to bottle to indicate that it can be refilled
     */
    public Bottle() {
        super("Bottle", 'b', false);
        this.addCapability(Indicator.MAGICAL_CONTAINER);
        magicalWaters = new ArrayList<>();
    }

    /**
     * water slot that a player can consume
     */
    private static final int PLAYER_WATER_SLOT = 1;

    /**
     * add water into the bottle
     * @param magicalWater
     */
    public void addWater(MagicalWater magicalWater) {
        magicalWaters.add(magicalWater);
    }

    /**
     * get the last magical water in the bottle
     */
    public MagicalWater getMagicalWater() {
        if(magicalWaters.size() > 0)
            return magicalWaters.get(magicalWaters.size()-1);
        return null;
    }

    /**
     * remove the last magical water in the bottle
     */
    public void removeFromBottle() {
        if(magicalWaters.size() > 0) {
            magicalWaters.remove(magicalWaters.size()-1);
        }
    }

    /**
     * get the size of the bottle
     */
    public int getSize() {
        return magicalWaters.size();
    }

    /**
     * Check if the bottle contains water
     * @return true if the bottle contains water, false otherwise
     */
    @Override
    public boolean containsWater() {
        return magicalWaters.size() > 0;
    }

    /**
     * return a description of the bottle
     */
    @Override
    public String toString() {
        return "Bottle" + magicalWaters;
    }

    /**
     * A method to fill the water from the water source into a bottle
     * @param refillCapable an instance that implements refillCapable interface (bottle)
     * @param actor an actor that is acting
     * @param waterSource the water source to be obtained from
     * @return a string description after executing fill method
     */
    @Override
    public String fill(RefillCapable refillCapable, Actor actor, WaterSource waterSource) {
        // if there is a bottle, add magical water into it
        if(refillCapable != null) {
            Bottle bottle = (Bottle) refillCapable;
            bottle.addWater(((Fountain)waterSource).getMagicalWater());
            // since the water slot in the fountain should not be less than 0, the player can only drink the maximum amount of water that is available in the fountain if there is insufficient amount of water
            Fountain fountain = (Fountain)waterSource;
            fountain.setRefillCounter(Math.max(fountain.getRefillCounter() - PLAYER_WATER_SLOT, 0));
            return "Yayy! The bottle is filled!><";
        }
        return "You may need to get a bottle!";
    }

}
