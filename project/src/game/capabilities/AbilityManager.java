package game.capabilities;

import edu.monash.fit2099.engine.actors.Actor;

import java.util.concurrent.ConcurrentHashMap;

/**
 * A manager class that keeps tracks of buffs/debuffs that expire over time.
 */
public class AbilityManager {

    /**
     * A hashmap used to store the ability and the duration of the ability.
     * A concurrent hashmap is used since we are modifying the hashmap while iterating over it.
     */
    private ConcurrentHashMap<Enum<?>, Integer> hashmap;

    /**
     * The actor involved
     */
    private Actor actor;

    /**
     * Constructor.
     * @param actor the actor that the ability manager is handling
     */
    public AbilityManager(Actor actor){
        this.hashmap = new ConcurrentHashMap<>();
        this.actor = actor;
    }

    /**
     * Add an ability along with it's duration.
     * @param status the ability
     * @param duration
     */
    public void addAbility(Enum<?> status, int duration){
        hashmap.put(status, duration);
    }

    /**
     * Remove an ability.
     * @param status the ability
     */
    public void removeAbility(Enum<?> status){
        hashmap.remove(status);
    }

    /**
     * Loop through the hashtable and reduce duration at each turn. Remove the ability when the duration is up.
     * @return a string which displays the abilities a player has along with the time remaining.
     */
    public String run(){
        String output = "";
        for (Enum<?> status: hashmap.keySet()) {
            int time = hashmap.get(status);
            output += this.actor + " is " + status + " " + time + " turns remaining. ";
            hashmap.put(status, time - 1);
            if (time == 1) {
                hashmap.remove(status);
                actor.removeCapability(status);
            }
        }
        return output;
    }

    /**
     * Remove all abilities.
     */
    public void clearAllAbilities(){
        this.hashmap.clear();
    }
}
