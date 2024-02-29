package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.capabilities.Ability;
import game.Util;
import game.grounds.JumpableTerrain;


/**
 *  The action that allows an actor to jump. Determines a successful jump based on jump rate. A failed jump hurts the actor.
 */
public class JumpAction extends Action {

    /**
     * Target location
     */
    private Location moveToLocation;

    /**
     * One of the 8-d navigation
     */
    private String direction;


    /**
     * Constructor for jump action. Allows an actor to jump on to another ground.
     * @param moveToLocation the destination of the move
     * @param direction the direction of the move (to be displayed in menu)
     */
    public JumpAction(Location moveToLocation, String direction){
        this.moveToLocation = moveToLocation;
        this.direction = direction;
    }

    /**
     * When the actor performs a jump action, compute if the jump is successful. A successful jump moves the actor into the new location
     * and returns a string to indicate success, a failed jump does not move the actor, the actor receives damage and returns a
     * string to indicate failure.
     *
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return a string to describe whether the jump was a success or not
     */
    @Override
    public String execute(Actor actor, GameMap map) {

        // Cast to Jumpable terrain to get to jump rate and fall damage.
        JumpableTerrain terrain = (JumpableTerrain) moveToLocation.getGround();

        // if actor is TALL, then actor has 100% jump success rate.
        if(actor.hasCapability(Ability.TALL)){
            map.moveActor(actor, moveToLocation);
            return "I'm so tall, that my jump never fails!!";
        }

        // Get jump rate
        int jumpSuccessRate = terrain.getJumpRate();

        if(Util.isSuccess(jumpSuccessRate)){
            // Actor jumped successfully
            map.moveActor(actor, moveToLocation);
            return "I jump so high, higher than the sky";
        }
        else{
            // Actor's jump failed and he receives damage
            int fallDamage = terrain.getFallDamage();
            actor.hurt(fallDamage);
            return "Ouch! That hurts!! You received " + fallDamage + " damage";
        }

    }

    /**
     * Returns a description of this movement suitable to display in the menu.
     * @param actor The actor performing the action.
     * @return A string describing the actor that jumps in a direction to a ground with it's coordinates
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " jumps " + this.direction + " to "+ moveToLocation.getGround().getClass().getSimpleName() + "(" + moveToLocation.x() + ", " + moveToLocation.y() + ")" ;
    }
}
