package inf101.simulator.objects.examples;
import java.util.List;

import inf101.simulator.Direction;
import inf101.simulator.GraphicsHelper;
import inf101.simulator.Habitat;
import inf101.simulator.MediaHelper;
import inf101.simulator.Position;
import inf101.simulator.SimMain;
import inf101.simulator.objects.AbstractMovingObject;
import inf101.simulator.objects.IEdibleObject;
import inf101.simulator.objects.ISimObject;
import inf101.simulator.objects.SimEvent;


import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class SimAnimal extends AbstractMovingObject {
	private static final double defaultSpeed = 1.0;
	private Habitat habitat;
	private Image animalCoat = MediaHelper.getImage("boulder.png");
	public static final Random random = new Random();

	public SimAnimal(Position pos, Habitat hab) {
		super(new Direction(0), pos, defaultSpeed);
		this.habitat = hab;
	}

	@Override
	public void draw(GraphicsContext context) {
		super.draw(context);
		//context.fillOval(20, 20, getWidth(), getHeight());
		
		double dir = getDirection().toAngle();
		//System.out.println(dir);
		context.scale(-1.0, -1.0);
		context.drawImage(animalCoat, 0, 0, getWidth(), getHeight());

		
// Implement code to switch direction of image depending on the direction image is walking in.
//		if(dir < 90 || dir > -90){
//			context.scale(-1.0, -1.0);
//			context.drawImage(animalCoat, 0, 0, getWidth(), getHeight());
//		}
//		else if(dir > 90 || dir < -90  ){
//			context.scale(1.0, -1.0);
//			context.drawImage(animalCoat, 0, 0, getWidth(), getHeight());
//
//		}
		
	}

	public IEdibleObject getBestFood() {
		return getClosestFood();
	}

	public IEdibleObject getClosestFood() {
		for (ISimObject obj : habitat.nearbyObjects(this, getRadius()+200)) {
			if(obj instanceof IEdibleObject)
				return (IEdibleObject) obj;
		}
		
		return null;
	}

	@Override
	public double getHeight() {
		return 50;
	}

	@Override
	public double getWidth() {
		return 50;
	}
	
	@Override
	public void step() {
		// by default, move slightly towards center
		//dir = dir.turnTowards(directionTo(habitat.getCenter()), .5);

		List<ISimObject> nearbyObjects = habitat.nearbyObjects(this,  getRadius()+200);
		for (ISimObject o : nearbyObjects) {
			if (o instanceof SimFeed) {
				SimFeed s = (SimFeed) o;
				
				dir = dir.turnTowards(pos.directionTo(s.getPosition()), 1);
				accelerateTo(defaultSpeed*2, 0.5);
				List<ISimObject> nearbyObject = habitat.nearbyObjects(this,  50);
				for(ISimObject m : nearbyObject){
					if(m instanceof SimFeed){
						SimFeed s2 = (SimFeed) m;
						s2.eat(0.1);
						System.out.println("nom");
						
				}
		
		
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
}
