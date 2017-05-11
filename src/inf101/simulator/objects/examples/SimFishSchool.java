package inf101.simulator.objects.examples;

import java.util.List;
import java.util.Random;

import inf101.simulator.Direction;
import inf101.simulator.Habitat;
import inf101.simulator.MediaHelper;
import inf101.simulator.Position;
import inf101.simulator.objects.AbstractMovingObject;
import inf101.simulator.objects.ISimObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class SimFishSchool extends AbstractMovingObject {
	private Image animalCoat = MediaHelper.getImage("Nemo.gif");
	private Random random = new Random();
	private Habitat habitat;
	private static double defaultSpeed = 1.0;

	public SimFishSchool(Position pos, Habitat hab) {
		super(new Direction(0), pos, defaultSpeed);
		this.habitat = hab;
	}

	@Override
	public void draw(GraphicsContext context) {
		super.draw(context);
		double dir = getDirection().toAngle();

		if ((dir <= 90 && dir >= -90)) {
			context.translate(0, getHeight());
			context.scale(1, -1);
			context.drawImage(animalCoat, 0, 0, getWidth(), getHeight());
		} else if (dir < -90 || dir < 180) {
			context.drawImage(animalCoat, 0, 0, getWidth(), getHeight());
		}
	}

	@Override
	public double getHeight() {
		return 35;
	}

	@Override
	public double getWidth() {
		return 35;
	}

	@Override
	public void step() {
		/*
		 * This bit of code will check if any nearby fish is in a given
		 * vicinity, and turn towards it. A random count is also added to
		 * provide sharper turns at given intervals.
		 */
		List<ISimObject> closeFish = habitat.nearbyObjects(this, getRadius() + 500);
		for (ISimObject o : closeFish) {
			if (o instanceof SimFishSchool) {
				SimFishSchool f = (SimFishSchool) o;
				closeFish.add(f);
				dir = dir.turnTowards(directionTo(f.getPosition()), 1);

				if (closeFish.size() < 100) {
					dir = dir.turnTowards(directionTo(habitat.getLeader().getPosition()), 5);
					accelerateTo(defaultSpeed * 2, 0.5);
				}

				closeFish.clear();

				if (random.nextInt(50) == 0) {
					dir = dir.turnTowards(directionTo(f.getPosition()), 60);
				}

				break;
			}

		}
		// go towards center if we're close to the border
		if (!habitat.contains(getPosition(), getRadius() * 1.2)) {
			dir = dir.turnTowards(directionTo(habitat.getCenter()), 5);
			if (!habitat.contains(getPosition(), getRadius())) {
				// we're actually outside
				accelerateTo(5 * defaultSpeed, 0.3);
			}
		}
		accelerateTo(defaultSpeed, 0.1);
		super.step();
	}

}
