package game.grounds;

import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;

import java.util.Random;

/**
 * A class to help generate warp pipes randomly on a map.
 */
public class RandomPipeGenerator {

    /**
     * The width of the map.
     */
    private int width;

    /**
     * The height of the map.
     */
    private int height;

    /**
     * The number of pipes to be created.
     */
    private int numberOfPipes;

    /**
     * The destination of the warp pipe.
     */
    private Location defaultLocation;

    /**
     * The map that warp pipes are to be generated on.
     */
    private GameMap gameMap;

    /**
     * Constructor.
     * @param gameMap The map that warp pipes are to be generated on.
     * @param numberOfPipes The number of pipes to be created.
     * @param defaultLocation The destination of the warp pipe.
     */
    public RandomPipeGenerator(GameMap gameMap, int numberOfPipes, Location defaultLocation) {
        this.width = gameMap.getXRange().max() + 1;
        this.height = gameMap.getYRange().max() + 1;
        this.gameMap = gameMap;
        this.numberOfPipes = numberOfPipes;
        this.defaultLocation = defaultLocation;
    }


    /**
     * Based on the given map in constructor, randomly generate numberOfPipes number of warp pipes that all lead to the default location.
     */
    public void run() {
        for (int i = 0; i < numberOfPipes; i++) {
            Random random = new Random();
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            gameMap.at(x, y).setGround(new WarpPipe(defaultLocation));
        }
    }
}

