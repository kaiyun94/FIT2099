package game.monologue;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 *  A class that executes the action of speaking
 */
public class SpeakAction extends Action {

    /**
     * the actor that speaks
     */
    private Actor character;

    /**
     * a monologue instance
     */
    private Monologue monologue;

    /**
     * Constructor
     * @param character the actor that speaks
     */
    public SpeakAction(Actor character) {
        this.character = character;
        this.monologue = Monologue.getInstance();
    }

    /**
     * Perform the action to speak a random sentence
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return a description of what happened that can be displayed to the user.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        return monologue.run(actor, character);
    }

    /**
     * Returns a descriptive string
     * @param actor The actor performing the action.
     * @return the text we put on the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " talks with Toad";
    }

}

