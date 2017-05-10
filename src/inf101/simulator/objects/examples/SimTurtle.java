package inf101.simulator.objects.examples;

import java.util.Random;
import java.util.function.Consumer;


import inf101.simulator.Direction;
import inf101.simulator.MediaHelper;
import inf101.simulator.Position;
import inf101.simulator.objects.AbstractMovingObject;
import inf101.simulator.objects.IEdibleObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class SimTurtle extends AbstractMovingObject implements IEdibleObject {
	private static final double NUTRITION_FACTOR = 10;
	private static final double DIAMETER = 25;
	private static Image foodLook = MediaHelper.getImage("Turtle.png");
	private Random r = new Random();
	private double defaultSpeed = 1.0;
	public static final Consumer<GraphicsContext> PAINTER = (GraphicsContext context) -> {
		SimTurtle obj = new SimTurtle(new Position(0, 0), 1.0);
		obj.hideAnnotations = true;
		context.scale(1 / obj.getWidth(), 1 / obj.getHeight());
		obj.draw(context);
	};

	private double size = 3.0;

	public SimTurtle(Position pos, double size) {
		super(new Direction(0), pos, size);
		this.size = size;
	}

	@Override
	public void draw(GraphicsContext context) {
		super.draw(context);
		context.scale(1, 1);
		context.drawImage(foodLook, 0,0,getWidth(), getHeight());

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
	public double getHeight() {
		return DIAMETER * size;
	}

	@Override
	public double getNutritionalValue() {
		return size * NUTRITION_FACTOR;
	}

	@Override
	public double getWidth() {
		return DIAMETER * size;
	}

	@Override
	public void step() {
		dir = dir.turn(r.nextDouble()-0.2);
		accelerateTo(defaultSpeed, 0.2);
		super.step();

	
	}
}
