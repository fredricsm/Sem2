package inf101.simulator.objects.examples;


import java.util.Random;

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

public class SimKraken extends AbstractMovingObject {
	private static final double defaultSpeed = 1;
	private Habitat habitat;
	private Image animalCoat = MediaHelper.getImage("kraken.png");
	private Random r = new Random();
	
	public SimKraken(Position pos, Habitat hab, SimMain main) {
		super(new Direction(0), pos, defaultSpeed);
		this.habitat = hab;
	}

	@Override
	public void draw(GraphicsContext context) {

		double dir = getDirection().toAngle();
		if ((dir <= 90 && dir >= -90)) {
			context.translate(0, getHeight());
			context.scale(1, -1);
			context.drawImage(animalCoat, 0, 0, getWidth(), getHeight());
		} else if (dir < -90 || dir < 180) {
			context.drawImage(animalCoat, 0, 0, getWidth(), getHeight());
		}

		// direction image is walking in.

	}

	public IEdibleObject getClosestFood() {
		for (ISimObject obj : habitat.nearbyObjects(this, getRadius() + 200)) {
			if (obj instanceof SimTurtle || obj instanceof SimDolphin) {
				return (IEdibleObject) obj;

			}
		}

		return null;

	}

	@Override
	public double getHeight() {
		return 350;
	}

	@Override
	public double getWidth() {
		return 500;
	}

	@Override
	public void step() {
		
		dir = dir.turn(r.nextDouble()-0.2);


		
		// go towards center if we're close to the border
		if (!habitat.contains(getPosition(), getRadius() * 1.2)) {
			dir = dir.turnTowards(directionTo(habitat.getCenter()), 5);
			if (!habitat.contains(getPosition(), getRadius())) {
				// we're actually outside
				accelerateTo(5 * defaultSpeed, 0.3);
			}
		}
		super.step();

	}


	

}