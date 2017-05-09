package inf101.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import inf101.simulator.Habitat;
import inf101.simulator.Position;
import inf101.simulator.SimMain;
import inf101.simulator.objects.examples.SimShark;
import inf101.simulator.objects.examples.SimFishFood;

public class SimAnimalBestFoodTest {
	private SimMain main;

	@Before
	public void setup() {
		main = new SimMain();
	}

	/**
	 * Test scenario: food is placed around a sim animal. The animal correctly
	 * identifies the best food.
	 */
	@Test
	public void willFindBestFoodTest() {
		Habitat hab = new Habitat(main, 500, 500);
		SimShark sim1 = new SimShark(new Position(250, 250), hab);
		SimFishFood feed1 = new SimFishFood(new Position(200, 250), 1.0);
		SimFishFood feed2 = new SimFishFood(new Position(320, 250), .5);
		SimFishFood feed3 = new SimFishFood(new Position(250, 320), 2.0);
		SimFishFood feed4 = new SimFishFood(new Position(250, 220), 1.3);
		hab.addObject(sim1);
		hab.addObject(feed1);
		hab.addObject(feed2);
		hab.addObject(feed3);
		hab.addObject(feed4);

		assertEquals(feed4, sim1.getClosestFood());
		assertEquals(feed3, sim1.getBestFood());
	}

}
