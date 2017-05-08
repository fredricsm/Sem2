package inf101.simulator.objects.examples;



import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import java.util.List;

import inf101.simulator.Direction;
import inf101.simulator.Habitat;
import inf101.simulator.MediaHelper;
import inf101.simulator.Position;
import inf101.simulator.objects.AbstractMovingObject;
import inf101.simulator.objects.IEdibleObject;
import inf101.simulator.objects.ISimListener;
import inf101.simulator.objects.ISimObject;
import inf101.simulator.objects.SimEvent;



import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

	public class SimAnimal extends AbstractMovingObject implements ISimListener {
	
		private static final double defaultSpeed = 1.0;
		private Habitat habitat;
		private Image animalCoat = MediaHelper.getImage("boulder.png");
		private double energy = 100;
		SimEvent event = new SimEvent(this, "nom", null, "NOM");
		Comparator<IEdibleObject> c;
		List<IEdibleObject> food = new ArrayList<>();

		public SimAnimal(Position pos, Habitat hab) {
			super(new Direction(0), pos, defaultSpeed);
			this.habitat = hab;
			habitat.addListener(this, this);
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
			for (ISimObject obj : habitat.nearbyObjects(this, getRadius() + 2000 )) {
				if (obj instanceof IEdibleObject){
					IEdibleObject s = (IEdibleObject) obj;
					Collections.sort
				}
			Collections.sort(food, c);
			System.out.println(food);
			food.add(s);

			}
		
			return null;
		}
	
		public IEdibleObject getClosestFood() {
			for (ISimObject obj : habitat.nearbyObjects(this, getRadius() +200 )) {
				if (obj instanceof IEdibleObject){
					return (IEdibleObject) obj;
					
				}
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
//		List<ISimObject> nearbyFood = habitat.nearbyObjects(this, 2000);
		
		List<ISimObject> nearbyObjects = habitat.nearbyObjects(this, getRadius() +200 );
			for (ISimObject o : nearbyObjects) {
				if (o instanceof SimFeed) {
					SimFeed s = (SimFeed) o;
					getBestFood();

					
					dir = dir.turnTowards(directionTo(s.getPosition()), 1);
					if(distanceToTouch(s)<20){
						dir = dir.turnTowards(directionTo(s.getPosition()), 5);				
						habitat.triggerEvent(event);
						s.eat(0.5);
						energy = energy * 1.000199;
					}
					
			}

			else if (o instanceof SimRepellant) {
					dir = dir.turnTowards(-90, 5);
					accelerateTo(defaultSpeed*1.5, 1);
					
					
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
		energy = energy * 0.99998;
		super.step();

	}
	

	@Override
	public void eventHappened(SimEvent event) {
		 event.getType();
		say("BAAAK");
//		System.out.println("This happened: " + event);
		
	
	}
	
}
