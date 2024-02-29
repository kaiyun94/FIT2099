package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * A class that performs win action
 */
public class WinAction extends Action {

    /**
     * Constructor for this action
     */
    public WinAction() {
    }

    /**
     * execute the action for the player to win
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        // Player wins and game ends
        map.removeActor(actor);
        return "PHEWWW ~~ MARIO WON THE GAME!!";
    }

    /**
     * a description to be printed out in the console menu
     * @param actor The actor performing the action.
     * @return a string description of this action
     */
    @Override
    public String menuDescription(Actor actor) {
        return "Free Princess Peach from her cage!!";
    }


}
