package inf101.simulator.objects.examples;

import java.util.Random;

import inf101.simulator.Direction;
import inf101.simulator.Habitat;
import inf101.simulator.MediaHelper;
import inf101.simulator.Position;
import inf101.simulator.objects.AbstractMovingObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class SimFishSchoolLeader extends AbstractMovingObject {
	private Image fishSchool = MediaHelper.getImage("Nemo.gif");
	private Random random = new Random();
	private Habitat habitat;
	private static double defaultSpeed = 1.0;

	public SimFishSchoolLeader(Position pos, Habitat hab) {
		super(new Direction(0), pos, defaultSpeed);
		this.habitat = hab;
	}

	@Override
	public void draw(GraphicsContext context) {
		super.draw(context);
		context.drawImage(fishSchool, 0, 0, getWidth(), getHeight());
	}

	public Position getPosition() {
		return pos;
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

		dir = dir.turn(random.nextDouble() - 0.2);
		super.step();
		accelerateTo(0.7 * defaultSpeed, 0.2);

		// go towards center if we're close to the border
		if (!habitat.contains(getPosition(), getRadius() * 1.2)) {
			dir = dir.turnTowards(directionTo(habitat.getCenter()), 5);
			if (!habitat.contains(getPosition(), getRadius())) {
				// we're actually outside
				accelerateTo(defaultSpeed, 0.3);
			}
		}

	}
}