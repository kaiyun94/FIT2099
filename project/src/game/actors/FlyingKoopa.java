package game.actors;

import game.capabilities.Status;

/**
 * a neat turtle thing that flies
 */
public class FlyingKoopa extends KoopaType {
    /**
     * Constructor
     */
    public FlyingKoopa() {
        super("Flying Koopa", 'F', 150);
        this.addCapability(Status.FLIGHT);
    }
}
