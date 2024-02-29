package game.reset;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * An action to reset the game
 */
public class ResetAction extends Action{

    /**
     * an instance of reset manager
     */
    private final ResetManager resetManager;

    /**
     * Constructor
     */
    public ResetAction() {
        this.resetManager = ResetManager.getInstance() ;
    }

    /**
     * Perform the reset action
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return a description of what happened that can be displayed to the user.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        resetManager.run(); // invoke the reset manager to reset
        return "Game is reset!";
    }

    /**
     * Returns a descriptive string
     * @param actor The actor performing the action.
     * @return the text we put on the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return  "Reset the game";
    }

    /**
     * Returns the key used in the menu to trigger this Action.
     * @return the key we use for this Action in the menu
     */
    public String hotkey() {
        return "r";
    }
}
