package inf101.simulator.objects.examples;

import inf101.simulator.Direction;
import inf101.simulator.Habitat;
import inf101.simulator.MediaHelper;
import inf101.simulator.Position;
import inf101.simulator.objects.AbstractMovingObject;
import inf101.simulator.objects.IEdibleObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class SimResidue extends AbstractMovingObject implements IEdibleObject {

	public SimResidue(Position pos, Habitat hab) {
		super(new Direction(0), pos, defaultSpeed);
		this.habitat = hab;

	}

	private static final double NUTRITION_FACTOR = 10;

	private static double defaultSpeed = 1.0;
	private Image animalCoat = MediaHelper.getImage("food.png");
	private Habitat habitat;
	double size = 3.0;
	private double DIAMETER = 10;

	public void draw(GraphicsContext context) {

		context.drawImage(animalCoat, 0, 0, getWidth(), getHeight());
		super.draw(context);

	}

	@Override
	public double getHeight() {
		// TODO Auto-generated method stub
		return DIAMETER * size;
	}

	@Override
	public double getWidth() {
		// TODO Auto-generated method stub
		return DIAMETER * size;
	}

	@Override
	public void step() {
		dir = dir.turnTowards(-90, 2);
		if (!habitat.contains(getPosition(), 0)) {
			destroy();
		}
		super.step();

	}

	@Override
	public double eat(double howMuch) {
		double deltaSize = Math.min(size, howMuch / NUTRITION_FACTOR);
		size -= deltaSize;
		if (size == 0)
			destroy();

		return deltaSize * NUTRITION_FACTOR;
	}

	@Override
	public double getNutritionalValue() {
		// TODO Auto-generated method stub
		return NUTRITION_FACTOR * size;
	}

}
