package inf101.simulator.objects.examples;



import java.util.ArrayList;
import java.util.List;

import inf101.simulator.Direction;
import inf101.simulator.Habitat;
import inf101.simulator.MediaHelper;
import inf101.simulator.Position;
import inf101.simulator.SimMain;
import inf101.simulator.objects.AbstractMovingObject;
import inf101.simulator.objects.IEdibleObject;
import inf101.simulator.objects.ISimObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class SimCrab extends AbstractMovingObject {

	private static double defaultSpeed = 1.0;
	private Habitat habitat;
	private Image animalCoat = MediaHelper.getImage("Crab.png");
	private double energy = 100;
	private List<IEdibleObject> food = new ArrayList<>();
	private SimMain main;
	private static Position posLeft = new Position(0,50);
	
	public SimCrab(Position pos, Habitat hab, SimMain main) {
		super(new Direction(0), posLeft, defaultSpeed);
		this.habitat = hab; 
		this.main=main;
	}

	@Override
	public void draw(GraphicsContext context) {
		super.draw(context);
		double dir = getDirection().toAngle();
		if(dir== 0){
			context.translate(0, getHeight());
			context.scale(1, -1);
			context.drawImage(animalCoat, 0, 0, getWidth(), getHeight());
		}
		else if(dir ==180){
			context.drawImage(animalCoat, 0, 0, getWidth(), getHeight());
		}

		
		
		// double dir = getDirection().toAngle();
		context.drawImage(animalCoat, 0, 0, getWidth(), getHeight());
		// direction image is walking in.

	}

	@Override
	public void step() {

		if (!habitat.contains(getPosition(), 0)) {
			dir = dir.turnBack();		
			System.out.println("Turning");
		}
		
		List<ISimObject> nearbyObjects = habitat.nearbyObjects(this, getRadius() + 30);
		for (ISimObject o : nearbyObjects) {
			if (o instanceof SimResidue) {
				((SimResidue) o).eat(habitat.getResidueNutrition());
				food.clear();
				main.bite();
				energy=energy*1.1;
			}		
		}
		
		if (energy > 500) {
			System.out.println("Doom");
			this.destroy();
			System.exit(0);

		}

		// go towards bottom if we're close to the border
		
		super.step();
	}

	
	

	
	
	@Override
	public double getHeight() {
		return energy;
	}

	@Override
	public double getWidth() {
		return energy*2;
	}

}
