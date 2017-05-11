package inf101.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import inf101.simulator.Habitat;
import inf101.simulator.Position;
import inf101.simulator.SimMain;
import inf101.simulator.objects.examples.SimShark;
import inf101.simulator.objects.examples.SimDolphin;
import inf101.simulator.objects.examples.SimRepellant;

public class SimAnimalAvoidingTest {
	private SimMain main;

	/**
	 * Test scenario: check that animal turns away from repellant
	 */
	@Test
	public void avoidDangerTest1() {
		Habitat hab = new Habitat(main, 2000, 500);
		SimShark sim1 = new SimShark(new Position(250, 250), hab, main);
		SimDolphin rep1 = new SimDolphin(new Position(550, 250), hab, main);
		hab.addObject(sim1);
		hab.addObject(rep1);

		// we're currently facing the repellant
		assertTrue(Math.abs(sim1.getPosition().directionTo(rep1.getPosition()).toAngle() - sim1.getDirection().toAngle()) < 10);
		for(int i = 0; i < 100; i++) {
//			System.out.println(sim1.getDirection());
			hab.step();
		}
		// we're currently facing away from the repellant
		assertTrue(Math.abs(sim1.getPosition().directionTo(rep1.getPosition()).toAngle() - sim1.getDirection().toAngle()) > 1);
		for (int i = 0; i < 1000; i++) {
			hab.step();
		}
	}
	/**
	 * Test scenario: check that animal avoid repellant
	 */
	@Test
	public void avoidDangerTest2() {
		Habitat hab = new Habitat(main, 1000, 1000);
		SimShark sim1 = new SimShark(new Position(250, 250), hab, main);
		SimRepellant rep1 = new SimRepellant(new Position(500, 500));
		hab.addObject(sim1);
		hab.addObject(rep1);

		for (int i = 0; i < 50000; i++) {
			hab.step();
			// we should never be touching the repellant
			assertTrue(sim1.getPosition().distanceTo(rep1.getPosition()) > sim1.getRadius() + rep1.getRadius());
		}
	}

	@Before
	public void setup() {
		main = new SimMain();
	}
}
