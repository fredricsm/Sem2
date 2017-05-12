package inf101.simulator.objects.examples;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;


import inf101.simulator.Direction;
import inf101.simulator.MediaHelper;
import inf101.simulator.Position;
import inf101.simulator.objects.AbstractMovingObject;
import inf101.simulator.objects.IEdibleObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import inf101.simulator.Habitat;


public class SimTurtle extends AbstractMovingObject implements IEdibleObject {
	private static final double NUTRITION_FACTOR = 10;
	private static final double DIAMETER = 25;
	private static Image animalCoat = MediaHelper.getImage("Turtle.gif");
	private Habitat habitat;
	private Random r = new Random();
	private double defaultSpeed = 0.5;

	
	private double size = 3.0;

	public SimTurtle(Position pos, double size) {
		super(new Direction(0), pos, size);
		this.size = size;
	}

	@Override
	public void draw(GraphicsContext context) {
	
		drawBar(context, getNutritionalValue(), 0, Color.RED, Color.GREEN);
		double dir = getDirection().toAngle();
		
		if((dir <= 90 && dir >= -90)){
			context.translate(0, getHeight());
			context.scale(1, -1);
			context.drawImage(animalCoat, 0, 0 , getWidth(), getHeight());
		}
		else if(dir < -90 || dir < 180 ){
			context.drawImage(animalCoat, 0, 0, getWidth(), getHeight());
		}
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
