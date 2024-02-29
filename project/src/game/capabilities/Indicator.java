package game.capabilities;

/**
 * Use this to indicate a specific item or ground type
 */
public enum Indicator {
    POWERSTAR_POINTER, // use this enum to tell if the actor is holding a power star
    WRENCH_POINTER, // use this enum to tell if the actor is holding a wrench
    SUPERMUSHROOM_POINTER, // use this enum to tell if the actor is holding a super mushroom
    KEY_POINTER, // use this enum to tell if the actor is holding a key
    BUCKET_POINTER, // use this enum to tell if the actor is holding a bucket
    BUFF_WATER, // tell if the ground can power up the base damage of an actor after interacting with it
    HEALING_WATER, // tell if the ground can heal an actor after interacting with it
    MAGICAL_CONTAINER, // tell if the item is a magical container that can fill in magical water
    IS_FERTILE, // Use this status to indicate if a ground is fertile
    SPURTABLE, // tell if the ground is able to spurt water (fountain)
    BODY_OF_WATER // tell if the ground is the body of water (e.g. lake, river etc.)
}
