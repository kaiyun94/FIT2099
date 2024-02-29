package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.items.Bottle;

/**
 * A class that performs the get action
 */
public class GetAction extends Action {

    /**
     * an instance of bottle
     */
    Bottle bottle;

    /**
     * a counter to ensure the get action only executed once
     */
    private static int counter = 0;

    /**
     * Constructor for this action
     * @param bottle an instance of bottle
     */
    public GetAction(Bottle bottle) {
        this.bottle = bottle;
    }

    /**
     * execute the action of getting a bottle
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return a string the describes the action output
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        counter += 1;
        actor.addItemToInventory(bottle);
        return "Mario obtained a bottle";
    }

    /**
     * a description to be printed out in the console menu
     * @param actor The actor performing the action.
     * @return a string description of this action
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " gets " + bottle;
    }

    /**
     * get the counter
     * @return an integer of counter
     */
    public static int getCounter() {
        return counter;
    }
}

