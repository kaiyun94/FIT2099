package game.grounds;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.capabilities.Status;
import game.actions.JumpAction;
import game.actions.TeleportAction;
import game.actors.Jumpable;
import game.actors.PiranhaPlant;
import game.reset.Resettable;

/**
 * A warp pipe. Piranha plants love to hang around here. Warp pipes allows us to teleport to another world.
 * A warp pipe is a jumpable terrain, so an actor must jump to reach it.
 * A warp pipe is ressetable, because it needs to revive dead Piranha plants.
 */
public class WarpPipe extends Ground implements JumpableTerrain, Resettable {

    /**
     * Constant related to jump success rate
     */
    private static final int JUMP_SUCCESS_RATE = 100;

    /**
     * Constant related to jump fall damage
     */
    private static final int JUMP_FALL_DAMAGE = 0;

    /**
     * Piranha plants spawn on the 2nd turn.
     */
    private static final int Spawn_At_Turn = 2;

    /**
     * A counter used to keep track of number of turns. Piranha plants spawn on the 2nd turn.
     */
    private int counter;

    /**
     * The default destination of the warp pipe.
     */
    private Location defaultLocation;

    /**
     * Constructor.
     * @param location The location of the warp pipe to be teleported to.
     */
    public WarpPipe(Location location){
        super('C');
        this.counter = 1;
        this.defaultLocation = location;
        this.registerInstance();
    }

    /**
     * Allows a player to jump on it. Then allows a player to teleport.
     * @param actor the Actor acting
     * @param location the current Location
     * @param direction the direction of the Ground from the Actor
     * @return a list of actions
     */
    @Override
    public ActionList allowableActions(Actor actor, Location location, String direction) {
        // Allows an actor to jump on to the warp pipe.
        ActionList actions = allowJumpAction(actor,location,direction);
        // Indicate that an actor is already standing on the warp pipe, thus allow a teleport action
        if(actions.size() == 0){
            actions.add(new TeleportAction(location, this.defaultLocation));
        }
        return actions;
    }


    /**
     * Override the allowJumpAction method. We don't want an actor with INVINCIBLE to destroy warp pipe. Thus, even an
     * INVINCIBLE actor must jump on warp pipes.
     *
     * @param actor The actor involved
     * @param location the location involved
     * @param direction the direction of the jump
     * @return a list of actions
     */
    @Override
    public ActionList allowJumpAction(Actor actor, Location location, String direction) {
        // We never want warp pipe to be destroyed even if the actor is INVINCIBLE. Even if the actor is INVINCIBLE,
        // the actor has to jump onto a warp pipe. Note that the warp pipe can never be destroyed and turned into coins.
        ActionList actions = new ActionList();
        if (location.containsAnActor()){
            // To prevent an actor from jumping at its own spot
            return actions;
        }
        else if (actor instanceof Jumpable) {
            // If actor can jump, add a jump action to actions
            actions.add(new JumpAction(location, direction));
        }
        return actions;
    }

    /**
     * A getter to get jump rate.
     * @return JUMP_SUCCESS_RATE
     */
    @Override
    public int getJumpRate() {
        return JUMP_SUCCESS_RATE;
    }

    /**
     * A getter to get fall damage.
     * @return JUMP_FALL_DAMAGE
     */
    @Override
    public int getFallDamage() {
        return JUMP_FALL_DAMAGE;
    }

    /**
     * At every turn, the ground ticks. At the 2nd turn of the game, we spawn a Piranha Plant.
     * If reset is called, we revive a Piranha Plant.
     *
     * @param location location of the ground
     */
    @Override
    public void tick(Location location) {
        counter ++;
        if(this.hasCapability(Status.RESET_NOW)){
            // If reset is called, spawn a piranha plant. If there is an actor there already, then we do not spawn piranha plant.
            if(!location.containsAnActor()){
                location.addActor(new PiranhaPlant());
            }
            this.removeCapability(Status.RESET_NOW);

        }

        if(counter == Spawn_At_Turn){
            // In case there is already an actor blocking the warp pipe already by the 2nd turn.
            // If that is the case, we do not spawn Piranha Plant anymore
            if (!location.containsAnActor())
                location.addActor(new PiranhaPlant());
        }
    }

    /**
     * No actor can walk onto this ground, but flying actors can.
     * @param actor the Actor to check
     * @return
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        return actor.hasCapability(Status.FLIGHT);
    }

    /**
     * Informs the ground that it is time for a reset.
     */
    @Override
    public void resetInstance() {
        this.addCapability(Status.RESET_NOW);
    }
}
