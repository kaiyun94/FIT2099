package game.capabilities;

/**
 * It is useful to give a `state` to abilities or actions that can be attached-detached.
 */
public enum Status {
    HOSTILE_TO_ENEMY, // use this status to be considered hostile towards enemy (e.g., to be attacked by enemy)
    DORMANT_CAPABLE, // use this status to tell if the actor is able to become dormant
    ATTACKED, // this status indicated if an actor has been attacked before and has attacked another actor
    FADABLE, // use this to tell if the item has fading time
    RESET_NOW, // Use this as a flag to tell resettable instances that it is time for a reset
    HEALED, // tell if the actor has consumed healing water
    POWERED_UP, // tell if the actor has consumed power water
    CONSUMED_WATER, // tell if the enemy has consumed the magical water to prioritise drink behaviour before attack behaviour when there's a scenario where the enemy is in the surrounding of the fountain and the player
    FLIGHT, // tells if an actor is flight capable
    ARSONIST // tells if an actor is capable of turning ground into fire
}

