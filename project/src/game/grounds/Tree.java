package game.grounds;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.capabilities.Status;
import game.Util;
import game.reset.Resettable;

/**
 *  An abstract Tree class. It can be reset, therefore it implements Resettable.
 *  It can be jumped over, therefore it implements JumpableTerrain.
 */
public abstract class Tree extends Ground implements Resettable, JumpableTerrain {

    /**
     * Trees have a 50% chance to reset when reset action is performed.
     */
    private static final int CHANCE_TO_RESET = 50;

    /**
     * Constructor for abstract Tree class. Registers itself as a resetable instance.
     *
     * @param displayChar character to display for this type of terrain
     */
    public Tree(char displayChar) {
        super(displayChar);
        this.registerInstance();
    }

    /**
     * Checks if a player is eligible for jumping over a ground.
     * @param actor the Actor acting
     * @param location the current Location
     * @param direction the direction of the Ground from the Actor
     * @return action list
     */
    public ActionList allowableActions(Actor actor, Location location, String direction){
        return allowJumpAction(actor, location, direction);
    }

    /**
     * Checks if an actor can move into this ground type. An actor cannot move into a jumpable terrain, he must jump or fly.
     * @param actor the Actor to check
     * @return a boolean to indicate if actor can move into this ground type
     */
    public boolean canActorEnter(Actor actor) {
        if(actor.hasCapability(Status.FLIGHT)){
            return true;
        }
        return canActorMove(actor);
    }

    /**
     * When reset action is called, trees have a CHANCE_TO_RESET change to become dirt
     * @param location
     */
    public void reset(Location location){
        if (this.hasCapability(Status.RESET_NOW)){
            if (Util.isSuccess(CHANCE_TO_RESET)){
                location.setGround(new Dirt());
            }
            this.removeCapability(Status.RESET_NOW);
        }
    }

    /**
     * adds the RESET_NOW capability
     */
    @Override
    public void resetInstance() {
        this.addCapability(Status.RESET_NOW);
    }



}



