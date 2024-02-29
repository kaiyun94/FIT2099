package game.items;

import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.weapons.Weapon;
import game.capabilities.Indicator;
import game.trades.Tradable;

/**
 * A weapon that modifies the wielder's attack stat, hit rate and attack options
 */
public class Wrench extends Item implements Tradable, Weapon {

    /**
     * price of the wrench
     */
    private static final int PRICE = 200;

    /**
     * damage rate of the wrench
     */
    private static final int DAMAGE = 50;

    /**
     * chance to hit the target
     */
    private static final int HITRATE = 80;

    /**
     * Constructor
     * adds WRENCH_POINTER capability
     */
    public Wrench() {
        super("Wrench", 'p', true);
        this.addCapability(Indicator.WRENCH_POINTER);
    }

    /**
     * The amount of damage Wrench will inflict
     * @return the damage, in hitpoints
     */
    @Override
    public int damage() {
        return DAMAGE;
    }

    /**
     * A verb to use when displaying the results of attacking with this Wrench
     * @return String, e.g. "punches", "zaps"
     */
    @Override
    public String verb() {
        return "whacks";
    }

    /**
     * A chance to hit the target (this is the dividend of percentage)
     * @return the chance, in integer for probability with nextInt()
     */
    @Override
    public int chanceToHit() {
        return HITRATE;
    }

    /**
     * Get the price of the wrench
     * @return price in Integer
     */
    @Override
    public int getPrice() {
        return PRICE;
    }

}
