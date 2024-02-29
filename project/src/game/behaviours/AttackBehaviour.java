package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.capabilities.Status;
import game.actions.AttackAction;
import game.actors.Enemy;

/**
 * A class that determines and or automates the attack action of an actor
 */
public class AttackBehaviour implements Behaviour {


    /**
     * Constructor.
     */
    public AttackBehaviour() {

    }

    /**
     * Allows the actor to attack the opponent automatically
     * @param actor the Actor acting
     * @param map the GameMap containing the Actor
     * @return the attack action of the actor if conditions are met, else null
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        // Location of actor
        Location here = map.locationOf(actor);

        // Loop through exits check if there is another adjacent actor
        for (Exit exit : here.getExits()) {
            Location destination = exit.getDestination();
            Actor target = map.getActorAt(destination);
            if(target != null) {
                // If there's an adjacent actor, check if it is hostile to enemy. If it is, actor will attack target.
                Enemy enemy = (Enemy)actor;
                if(target.hasCapability(Status.HOSTILE_TO_ENEMY)) {
                    enemy.addCapability(Status.ATTACKED);
                    return new AttackAction(target, exit.getName());
                }
            }
        }
        return null;
    }
}