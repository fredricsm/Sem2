package inf101.simulator;

import inf101.simulator.objects.examples.Blob;
import inf101.simulator.objects.examples.SimFishSchool;
import inf101.simulator.objects.examples.SimFishSchoolLeader;
import inf101.simulator.objects.examples.SimShark;
import inf101.util.generators.PositionGenerator;
import inf101.simulator.objects.examples.SimFishFood;
import inf101.simulator.objects.examples.SimRepellant;

public class Setup {

	/** This method is called when the simulation starts */
	public static void setup(SimMain main, Habitat habitat) {
		habitat.addObject(new SimShark(new Position(400, 400), habitat));
		main.music();

		habitat.addObject(new Blob(new Direction(0), new Position(400, 400), 1));
		
	habitat.addObject(new SimFishSchoolLeader( new Position(20,20), habitat));

	
		
//		for (int i = 0; i < 3; i++)
//			habitat.addObject(new SimRepellant(main.randomPos()));
//		
		for(int i = 0; i <100; i++){

			habitat.addObject(new SimFishSchool( new Position(i,i), habitat));
		
		}
		SimMain.registerSimObjectFactory((Position pos, Habitat hab) -> new SimFishSchool(pos, hab), "SimFeed™", "FishSchool.png");
		SimMain.registerSimObjectFactory((Position pos, Habitat hab) -> new SimShark(pos, hab), "SimFeed™", "Shark.png");
		SimMain.registerSimObjectFactory((Position pos, Habitat hab) -> new SimFishFood(pos, main.getRandom().nextDouble()*2+0.5), "SimFeed™", "pipp.png");
		SimMain.registerSimObjectFactory((Position pos, Habitat hab) -> new SimRepellant(pos), "SimRepellant™",	SimRepellant.PAINTER);
	}

	/**
	 * This method is called for each step, you can use it to add objects at
	 * random intervals
	 */
	

	
	public static void step(SimMain main, Habitat habitat) {
		if (main.getRandom().nextInt(300) == 0)
			habitat.addObject(new SimFishFood(main.randomPos(), main.getRandom().nextDouble()*2+0.5));


	}
}
