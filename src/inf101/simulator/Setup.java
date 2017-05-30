package inf101.simulator;

import inf101.simulator.objects.examples.SimCrab;
import inf101.simulator.objects.examples.SimDolphin;
import inf101.simulator.objects.examples.SimFishSchool;
import inf101.simulator.objects.examples.SimFishSchoolLeader;
import inf101.simulator.objects.examples.SimKraken;
import inf101.simulator.objects.examples.SimPlants;
import inf101.simulator.objects.examples.SimResidue;
import inf101.simulator.objects.examples.SimShark;
import inf101.simulator.objects.examples.SimTurtle;

public class Setup {

	/** This method is called when the simulation starts */
	public static void setup(SimMain main, Habitat habitat) {

		habitat.addObject(new SimShark(new Position(400, 400), habitat, main));
		main.music();

		habitat.addObject(new SimFishSchoolLeader(new Position(20, 20), habitat));

		//habitat.addObject(new SimCrab(new Position(100, 100), habitat));

		for (int i = 0; i < 100; i++) {
			habitat.addObject(new SimFishSchool(new Position(i, i), habitat));

		}
		SimMain.registerSimObjectFactory((Position pos, Habitat hab) -> new SimFishSchool(pos, hab), "SimFishSchool™","Nemo.gif");
		
		SimMain.registerSimObjectFactory((Position pos, Habitat hab) -> new SimShark(pos, hab, main), "SimShark™","SharkAnim.gif");
		
		SimMain.registerSimObjectFactory((Position pos, Habitat hab) -> new SimTurtle(pos, main.getRandom().nextDouble() * 2 + 0.5),"SimTurtle™", "Turtle.gif");
		
		SimMain.registerSimObjectFactory((Position pos, Habitat hab) -> new SimDolphin(pos, hab, main), "SimDolphin™","Dolphin.gif");
		
		SimMain.registerSimObjectFactory2((Position pos, Habitat hab) -> new SimCrab(pos, hab, main), "SimCrab™", "Crab.png");
	
		SimMain.registerSimObjectFactory2((Position pos, Habitat hab) -> new SimPlants(pos), "SimPlant™", "plant1.png");

		SimMain.registerSimObjectFactory((Position pos, Habitat hab) -> new SimResidue(pos, hab), "SimResidue™", "food.png");
		
		SimMain.registerSimObjectFactory((Position pos, Habitat hab) -> new SimKraken(pos, hab, main), "SimKraken™","kraken.png");

	}

	/**
	 * This method is called for each step, you can use it to add objects at
	 * random intervals
	 */

	public static void step(SimMain main, Habitat habitat) {
		if (main.getRandom().nextInt(600) == 0) {
			habitat.addObject(new SimTurtle(main.randomPos(), main.getRandom().nextDouble() * 2 + 0.5));
			habitat.addObject(new SimPlants(main.randomPosBottom()));
		}
	}

}
