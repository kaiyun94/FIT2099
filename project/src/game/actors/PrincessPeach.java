package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import game.capabilities.Indicator;
import game.actions.WinAction;

/**
 * Mario's crush, which somehow keeps getting kidnapped every weekend
 * You would think being a princess of a kingdom would have better security
 */
public class PrincessPeach extends Actor {

    /**
     * constructor
     */
    public PrincessPeach(){super("Princess_Peach", 'P', 500);}

    /**
     * she does nothing as she is trapped in a cage, waiting for her prince charming to save her
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return DoNothingAction
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        return new DoNothingAction();
    }

    /**
     * if Mario has a key and player interacts with Princess Peach, the win condition (WinAction) will commence
     * @param otherActor the Actor that might be performing attack
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return action based on its current situation
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = super.allowableActions(otherActor, direction, map);
        if(otherActor.hasCapability(Indicator.KEY_POINTER)) {
            actions.add(new WinAction());
        }
        return actions;
    }
}
