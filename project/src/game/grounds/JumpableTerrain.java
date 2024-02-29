package game.grounds;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Location;
import game.capabilities.Ability;
import game.actions.JumpAction;
import game.actions.PowerMoveAction;
import game.actors.Jumpable;

/**
 * Grounds that can be jumped over should implement this interface.
 */
public interface JumpableTerrain {

    /**
     * Computes jump rate from ground
     */
    int getJumpRate();

    /**
     * Computes fall damage from ground
     */
    int getFallDamage();


    /**
     * Checks if a player is eligible for jumping over a ground.
     * @param actor The actor involved
     * @param location the location involved
     * @param direction the direction of the jump
     * @return an action list, a list of actions that an actor can perform
     */
    default ActionList allowJumpAction(Actor actor, Location location, String direction){
        // The logic that checks if a player can be allowed to jump. Override this method to control who can jump over a
        // specific ground.

        // Ground returns actions to player. Ground checks if player is eligible for jumping.
        ActionList actions = new ActionList();
        if (location.containsAnActor()){
            // To prevent an actor from jumping at its own spot
            return actions;
        }
        // A player with INVINCIBLE does not need a jump action to enter a jumpable terrain. He will have a power move instead.
        else if (actor instanceof Jumpable && actor.hasCapability(Ability.INVINCIBLE)) {
            // If actor can jump, add a jump action to actions
            actions.add(new PowerMoveAction(location, direction));
        }
        else if (actor instanceof Jumpable) {
            // If actor can jump, add a jump action to actions
            actions.add(new JumpAction(location, direction));
        }
        return actions;
    }

    /**
     * Checks if an actor can move into this ground type. An actor cannot move into a jumpable terrain, he must jump, unless he is
     * INVINCIBLE, then he is given a power move.
     * Override this method to change if an actor can move or jump.
     * @param actor the Actor to check
     * @return a boolean to indicate if actor can move into this ground type
     */
    default boolean canActorMove(Actor actor){
        return false;
    }

}


