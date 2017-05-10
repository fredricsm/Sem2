package inf101.simulator.objects.examples;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import inf101.simulator.Direction;
import inf101.simulator.Habitat;
import inf101.simulator.MediaHelper;
import inf101.simulator.Position;
import inf101.simulator.SimMain;
import inf101.simulator.objects.AbstractMovingObject;
import inf101.simulator.objects.ISimObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class SimCrab extends AbstractMovingObject {

	private static double defaultSpeed = 1.0;
	private Habitat habitat;
	private Image animalCoat = MediaHelper.getImage("Crab.png");
	private List<ISimObject> sharkList = new ArrayList<>();
	private Random r = new Random();
	
	
	public SimCrab(Position pos, Habitat hab) {
		super(new Direction(0), pos, defaultSpeed);
		this.habitat = hab;		// TODO Auto-generated constructor stub
	}

	
	

	
	@Override
	public void draw(GraphicsContext context) {
		super.draw(context);

//		double dir = getDirection().toAngle();
		context.drawImage(animalCoat, 0, 0, getWidth(), getHeight());
		// direction image is walking in.
		
	}

	@Override
	public void step() {
		dir = dir.turn(r.nextDouble()-0.2);
		
		super.step();
		// go towards bottom if we're close to the border
				if (!habitat.contains(getPosition(), getRadius() * 1.2)) {
					dir = dir.turnTowards(directionTo(habitat.getBottom()), 5);
					if (!habitat.contains(getPosition(), getRadius())) {
						// we're actually outside
						accelerateTo(5 * defaultSpeed, 0.3);
					}
				}
	
	
	}
	
	
	
	
	@Override
	public double getHeight() {
		return 200;
	}

	@Override
	public double getWidth() {
		return 200;
	}

}
