package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.capabilities.Ability;
import game.capabilities.Indicator;
import game.capabilities.Status;
import game.actors.Player;
import game.items.MagicalItem;

/**
 * A class that handles the effects and outcome caused by consuming an item
 */
public class ConsumeAction extends Action {

    private MagicalItem item;

    /**
     * assigns the magical item to the current instance of item
     * @param item the magical item consumed
     */
    public ConsumeAction(MagicalItem item) {
        this.item = item;
    }

    /**
     * Checks the item consumed by the actor and updates the actor's current capabilities along with
     * stat changes associated with the item. Then, remove the consumed item based on its current location
     * on the map or in the actor's inventory
     *
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return a string based on what the actor consumed
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        String result = "";
        boolean isConsumed = false;

        if(this.item.hasCapability(Indicator.POWERSTAR_POINTER)) {
            actor.addCapability(Ability.INVINCIBLE);
            ((Player)actor).getAbilityManager().addAbility(Ability.INVINCIBLE, 10);
            actor.heal(200);
            isConsumed = true;
            result += actor + " is INVINCIBLE";

        } else if(this.item.hasCapability(Indicator.SUPERMUSHROOM_POINTER)) {
            actor.addCapability(Ability.TALL);
            actor.increaseMaxHp(50);
            isConsumed = true;
            result += actor + " is ENLARGED";

        } else {
                result = "no item was consumed.";
        }

        if(isConsumed) {
            if (map.locationOf(actor).getItems().contains(this.item)) {
                map.locationOf(actor).removeItem(this.item);
            } else {
                actor.removeItemFromInventory(this.item);
            }
        }

        return result;
    }


    /**
     * prints a string for the player to decide if they want to perform the provided option.
     * if the item has a FADE capability, it shows the countdown to how many turns the item has before it
     * fades away from the game.
     * @param actor The actor performing the action.
     * @return a description for the player on the option's outcome
     */
    @Override
    public String menuDescription(Actor actor) {
        String result = actor + " consumes " + this.item;
        if(this.item.hasCapability(Status.FADABLE)) {
            result += " - " +  this.item.getTickCounter() + " turns remaining";
        }
        return result;
    }
}
