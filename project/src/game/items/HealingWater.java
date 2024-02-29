package game.items;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.capabilities.Indicator;
import game.capabilities.Status;
import game.actors.Player;

/**
 * A class that represents healing water
 */
public class HealingWater extends MagicalWater {

    /**
     * the water slot that an enemy can consume
     */
    private static final int ENEMY_WATER_SLOT = 5;

    /**
     * the water slot that a player can consume
     */
    private static final int PLAYER_WATER_SLOT = 1;

    /**
     * Constructor to instantiate a healing water slot
     */
    public HealingWater() {
        super("Healing Water", 'h', true);
        this.addCapability(Indicator.HEALING_WATER);
    }

    /**
     * A method that perform the operation when the actor is consuming the healing water
     * @param actor the actor that consume the healing water
     * @param map the map that the actor is currently in
     * @return a string describe the status of the actor after consuming the healing water
     */
    @Override
    public String consume(Actor actor, GameMap map) {
        String result ="";
        int healPoint = 50;
        // if actor is a player, after consuming the healing water, the heal point is increased,
        // and the water slot that has been consumed will be removed from the bottle
        if (actor.hasCapability(Status.HOSTILE_TO_ENEMY)) {
            healPoint *= PLAYER_WATER_SLOT;
            Player player = (Player) actor;
            player.getBottle().removeFromBottle();
        // else if the actor is an enemy, the heal point is increased
        } else {
            healPoint *= ENEMY_WATER_SLOT;
        }
        // actor will be healed
        actor.addCapability(Status.HEALED);
        actor.heal(healPoint);
        result += actor + " is HEALED";
        return result;
    }
}
