package game.items;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.capabilities.Indicator;
import game.capabilities.Status;
import game.actors.Enemy;
import game.actors.Player;
import game.grounds.Fountain;

/**
 * A class represents power water
 */
public class PowerWater extends MagicalWater {

    /**
     * the water slot that an enemy can consume
     */
    private static final int ENEMY_WATER_SLOT = 5;

    /**
     * the water slot that a player can consume
     */
    private static final int PLAYER_WATER_SLOT = 1;

    /**
     * the amount of damage increased after drinking the power water
     */
    private static final int FOUNTAIN_DMG_INCREASE = 15;

    /**
     * Constructor to instantiate a power water slot
     */
    public PowerWater() {
        super("Power Water", 'p', true);
        this.addCapability(Indicator.BUFF_WATER);
    }

    /**
     * A method that perform the operation when the actor is consuming the power water
     * @param actor the actor that consume the power water
     * @param map the map that the actor is currently in
     * @return a string describe the status of the actor after consuming the power water
     */
    @Override
    public String consume(Actor actor, GameMap map) {
        String result = "";
        // if actor is a player, after consuming the power water, the attack base damage is increased
        // and the water slot that has been consumed will be removed from the bottle
        if(actor.hasCapability(Status.HOSTILE_TO_ENEMY)) {
            Player player = (Player) actor;
            player.addCapability(Status.POWERED_UP);
            player.addDamage(FOUNTAIN_DMG_INCREASE * PLAYER_WATER_SLOT);
            player.getBottle().removeFromBottle();
        }
        // else if the actor is an enemy, the attack base damage will be increased
        else {
            Enemy enemy = (Enemy) actor;
            enemy.addCapability(Status.POWERED_UP);
            Fountain fountain = (Fountain) map.locationOf(actor).getGround();
            // since the water slot in the fountain should not be less than 0, the enemy can only drink the maximum amount of water that is available in the fountain if there is insufficient amount of water
            fountain.setRefillCounter(Math.max(fountain.getRefillCounter() - ENEMY_WATER_SLOT, 0));
            enemy.addDamage(FOUNTAIN_DMG_INCREASE * ENEMY_WATER_SLOT);
        }
        result += actor + " is POWERED UP";
        return result;
    }
}
