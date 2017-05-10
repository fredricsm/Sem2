package inf101.simulator.objects.examples;

import java.util.ArrayList;
import java.util.List;

import inf101.simulator.Direction;
import inf101.simulator.Habitat;
import inf101.simulator.MediaHelper;
import inf101.simulator.Position;
import inf101.simulator.objects.AbstractMovingObject;
import inf101.simulator.objects.IEdibleObject;
import inf101.simulator.objects.ISimObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class SimDolphin extends AbstractMovingObject {

	private static double defaultSpeed = 1.0;
	private Habitat habitat;
	private Image animalCoat = MediaHelper.getImage("Dolphin.png");
	private List<ISimObject> sharkList = new ArrayList<>();
	
	public SimDolphin( Position pos, Habitat hab) {
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
		List<ISimObject> nearbyObjects = habitat.nearbyObjects(this, getRadius() + 2000);
		for (ISimObject o : nearbyObjects) {
			if (o instanceof SimShark) {				
				sharkList.add((ISimObject) o);
				dir = dir.turnTowards(directionTo(habitat.getShark().getPosition()), 1);
				if (distanceTo(habitat.getShark()) < 100) {
					accelerateTo(defaultSpeed *5, -0.2);
					dir = dir.turnTowards(directionTo(habitat.getShark().getPosition()), 5);
					sharkList.clear();
									}
			
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
	
	
	@Override
	public double getHeight() {
		// TODO Auto-generated method stub
		return 100;
	}

	@Override
	public double getWidth() {
		// TODO Auto-generated method stub
		return 150;
	}






}
