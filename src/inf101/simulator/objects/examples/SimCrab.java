package inf101.simulator.objects.examples;


import java.util.Random;

import inf101.simulator.Direction;
import inf101.simulator.Habitat;
import inf101.simulator.MediaHelper;
import inf101.simulator.Position;
import inf101.simulator.SimMain;
import inf101.simulator.objects.AbstractMovingObject;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class SimCrab extends AbstractMovingObject {

	private static double defaultSpeed = 1.0;
	private Habitat habitat;
	private Image animalCoat = MediaHelper.getImage("Crab.png");
	private Random r = new Random();
	private double energy = getWidth();
	Canvas bottom = SimMain.getInstance().getBottomCanvas();

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
		
		if(distanceTo(habitat.getBottom()) > 200){
			dir = dir.turnTowards(dir.turnDown(), 2);
			// go towards bottom if we're close to the border
						if (!habitat.contains(getPosition(), getRadius() * 1.2)) {
							dir = dir.turnTowards(directionTo(habitat.getBottom()), 5);
							if (!habitat.contains(getPosition(), getRadius())) {
								// we're actually outside
								accelerateTo(defaultSpeed, 0.3);
							}
						}
						else{
							System.out.println("Walk the line");
						}
		}
		
		if(energy > 500){
			System.out.println("Doom");
			this.destroy();
			System.exit(0);
			
		}
		
		// go towards bottom if we're close to the border
			if (!habitat.contains(getPosition(), getRadius() * 1.2)) {
				dir = dir.turnTowards(directionTo(habitat.getBottom()), 5);
				if (!habitat.contains(getPosition(), getRadius())) {
					// we're actually outside
					accelerateTo(defaultSpeed, 0.3);
				}
			}
	
				super.step();
	
	}
	
	
	
	
	@Override
	public double getHeight() {
		return 100;
	}

	@Override
	public double getWidth() {
		return 200;
	}

}
