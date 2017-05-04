package inf101.simulator.objects.examples;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import inf101.simulator.Direction;
import inf101.simulator.GraphicsHelper;
import inf101.simulator.Habitat;
import inf101.simulator.MediaHelper;
import inf101.simulator.Position;
import inf101.simulator.SimMain;
import inf101.simulator.objects.AbstractMovingObject;
import inf101.simulator.objects.IEdibleObject;
import inf101.simulator.objects.ISimListener;
import inf101.simulator.objects.ISimObject;
import inf101.simulator.objects.SimEvent;

import java.util.function.Consumer;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class SimAnimal extends AbstractMovingObject implements ISimListener {

	private static final double defaultSpeed = 1.0;
	private Habitat habitat;
	private Image animalCoat = MediaHelper.getImage("boulder.png");
	private double energy = 100;
	private SimEvent event;
	private ISimListener listen;
	private double a,b;
	
	public SimAnimal(Position pos, Habitat hab) {
		super(new Direction(0), pos, defaultSpeed);
		this.habitat = hab;
	}
	
	@Override
	public void draw(GraphicsContext context) {
		super.draw(context);
		// context.fillOval(20, 20, getWidth(), getHeight());
		//double dir = getDirection().toAngle();
		// System.out.println(dir);
		//context.scale(1.0, 1.0);
		context.drawImage(animalCoat, 0, 0, getWidth(), getHeight());

		// Implement code to switch direction of image depending on the
		
		// direction image is walking in.
		// if(dir < 90 || dir > -90){
		// context.scale(-1.0, -1.0);
		// context.drawImage(animalCoat, 0, 0, getWidth(), getHeight());
		// }
		// else if(dir > 90 || dir < -90 ){
		// context.scale(1.0, -1.0);
		// context.drawImage(animalCoat, 0, 0, getWidth(), getHeight());
		//
		// }

	}

	public IEdibleObject getBestFood() {
		return getClosestFood();
	}

	public IEdibleObject getClosestFood() {
		for (ISimObject obj : habitat.nearbyObjects(this, getRadius() +200 )) {
			if (obj instanceof IEdibleObject)
				return (IEdibleObject) obj;
		}

		return null;
	}

	@Override
	public double getHeight() {
		return energy;
	}

	@Override
	public double getWidth() {
		return energy;
	}

	@Override
	public void step() {
		habitat.addListener(this, listen);
		
		// by default, move slightly towards center
		// dir = dir.turnTowards(directionTo(habitat.getCenter()), .5);
		List<ISimObject> nearbyObjects = habitat.nearbyObjects(this, getRadius() +200 );
			
			System.out.println(nearbyObjects);
			//Comparator<IEdibleObject> eatable = Double.compare(a, b);
			//Collections.sort(nearbyObjects, eatable);
			for (ISimObject o : nearbyObjects) {
				if (o instanceof SimFeed) {
					SimFeed s = (SimFeed) o;
					dir = dir.turnTowards(directionTo(s.getPosition()), 1);
					if(distanceToTouch(s)<20){
						dir = dir.turnTowards(directionTo(s.getPosition()), 5);
						s.eat(0.5);
						energy = energy * 1.000199;
						System.out.println("nom");	
						
			}
						//habitat.triggerEvent(event);
			}

			else if (o instanceof SimRepellant) {
					dir = dir.turnTowards(-90, 5);
					accelerateTo(defaultSpeed*1.5, 1);
					System.out.println("halp");

					
			}
//		Maybe implement to refine movement of objects. TBD		
//		List<ISimObject> nearbyObject = habitat.nearbyObjects(this,getRadius() + 20);
//			for (ISimObject m : nearbyObject) {
//				if (m instanceof SimFeed) {
//					SimFeed s2 = (SimFeed) m;
//				
//		List<ISimObject> nearbyDanger = habitat.nearbyObjects(this, getRadius() + 100);
//		for(ISimObject m : nearbyDanger){
//			 if (m instanceof SimRepellant) {
//				dir = dir.turnTowards(-2, 3);
//				accelerateTo(defaultSpeed*1.5, 1);
//				System.out.println("halp");
//
//			}

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
		energy = energy * 0.99998;
		System.out.println(energy);
		super.step();

	}


	@Override
	public void eventHappened(SimEvent event) {
		say(event.getType());
	}
}
