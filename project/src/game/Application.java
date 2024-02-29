package game;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.FancyGroundFactory;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.positions.World;
import game.actors.*;
import game.grounds.*;
import game.items.Bucket;
import game.items.PowerStar;
import game.items.SuperMushroom;
import game.items.Wrench;

import java.util.Arrays;
import java.util.List;

/**
 * The main class for the Mario World game.
 *
 */
public class Application {

	/**
	 * The main method of the game. Everything begins from here.
	 * @param args a list of strings
	 */
	public static void main(String[] args) {

		// Create the world
		World world = new World(new Display());

		FancyGroundFactory groundFactory = new FancyGroundFactory(new Dirt(), new Wall(), new Floor(), new Sprout(), new HealthFountain(), new PowerFountain(), new River());
		FancyGroundFactory groundFactory2 = new FancyGroundFactory(new Dirt(), new Wall(), new Floor(), new Sprout(), new HealthFountain(), new PowerFountain(), new Lava());

		List<String> map2 = Arrays.asList(
				// 50 by 15
				"......A.H.....LLLLL..........LLLLLL...............",
				"..........LLLLL..............LLLLLL...............",
				".+..................+........LLLLLL...............",
				".............................LLLLLL...............",
				".....L######L................LLLLLL..........+....",
				".............................LLLLLL...............",
				".......................+.....LLLLLL...............",
				".............................LLLLLL...............",
				".............................LLLLLLLLLLLLLLLLLL...",
				".............................LLLLLLLLLLLLLLLLLL...",
				"....LL............................................",
				"......LLL.........................................",
				".....L...L...........................+............",
				"......+..L.........._________.....................",
				".................................................."
		);

		// The map to be created
		List<String> map = Arrays.asList(
				"..........................................##..........+.........................",
				"............+............+..................#...................................",
				"............................................#...................................",
				".............................................##......................+..........",
				"...............................................#................................",
				"................................................#...............................",
				".................+................................#.............................",
				"............................................+++..##.............................",
				"................................................##..............................",
				"........................................+#_#__####.................+............",
				".........+.............................+#_____###...............................",
				"........~~~~~~~~~~......+..............+#______###..............................",
				".....~~~~~~~~~~~~~~~~...................+#_____###..............................",
				"..~~~~~~~~~~~~~~~~~~~~~~~~.......................##.............+...............",
				"~~~~~~~~~~~____~~~~~~~~~~~~~...............A.H.....#............................",
				"~~~~~~~~~~______~~~~~~~~~~~~~.......................#..................+........",
				"~~~~~~~~~~~____~~~~~~~~~~~~~~~.......................#..........................",
				"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~....+..............+...#.........................",
				"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~......................##......+................");

		// Create a startingMap and add it to world
		GameMap startingMap = new GameMap(groundFactory, map);
		world.addGameMap(startingMap);

		// Create a lavaZone and add it to world
		GameMap lavaZone = new GameMap(groundFactory2, map2);
		world.addGameMap(lavaZone);

		// Set up a default location for the warp pipe, which is at Lava Zone(0,0)
		Location defaultLocation = lavaZone.at(0,0);

		// Create a pipe at the default location
		defaultLocation.setGround(new WarpPipe(defaultLocation));

		// Manually create warp pipes at startingMap connect them to the default destination
		startingMap.at(33,15).setGround(new WarpPipe(defaultLocation));
		startingMap.at(37,15).setGround(new WarpPipe(defaultLocation));

		// Alternatively, randomly place pipes on the starting map. They all lead to warp pipe at Lava Zone(0,0)
		RandomPipeGenerator rpg = new RandomPipeGenerator(startingMap,5,defaultLocation);
		rpg.run();

		// Create the player and add it to world.
		Actor mario = new Player("Mario", 'm', 500);
		world.addPlayer(mario, startingMap.at(44, 11));


		// Add a bucket, some powerstars and super mushrooms to the starting map
		startingMap.at(12, 15).addItem(new Bucket());
		startingMap.at(13, 15).addItem(new SuperMushroom());
		startingMap.at(11, 15).addItem(new SuperMushroom());
		startingMap.at(44, 10).addItem(new SuperMushroom());
		startingMap.at(44, 10).addItem(new PowerStar());

		// Add Bowser and Princess Peach to the second map
		lavaZone.at(10,3).addActor(new Bowser());
		lavaZone.at(17,3).addActor(new PrincessPeach());

		// Create a toad and place it on the map
		Toad toad = new Toad();
		startingMap.at(45, 12).addActor(toad);

		world.run();

	}
}
