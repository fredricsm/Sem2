package inf101.simulator.objects.examples;

import java.util.ArrayList;
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

public class SimShark extends AbstractMovingObject implements ISimListener {

	private static final double defaultSpeed = 1.0;
	private Habitat habitat;
	private Image animalCoat = MediaHelper.getImage("Shark.png");

	private double energyX = 250;
	private double energyY = 150;

	SimEvent event = new SimEvent(this, "nom", null, "NOM");
	Comparator<IEdibleObject> c;
	List<IEdibleObject> food = new ArrayList<>();

	public SimShark(Position pos, Habitat hab) {
		super(new Direction(0), pos, defaultSpeed);
		this.habitat = hab;
		habitat.addListener(this, this);

	}

	@Override
	public void draw(GraphicsContext context) {
		super.draw(context);

//		double dir = getDirection().toAngle();
		context.drawImage(animalCoat, 0, 0, getWidth(), getHeight());
		// direction image is walking in.
		
	}

	
	public IEdibleObject getBestFood() {
	
		IEdibleObject best = food.get(0);
				for(IEdibleObject edb : food){
					if(food.isEmpty()){
						return null;	
					}
					else if(edb.getNutritionalValue() > best.getNutritionalValue()){
						best = edb;
					}
				}
		return best;
	
	}

	public IEdibleObject getClosestFood() {
		for (ISimObject obj : habitat.nearbyObjects(this, getRadius() + 200)) {
			if (obj instanceof IEdibleObject) {
				return (IEdibleObject) obj;

			}
		}

		return null;

	}

	@Override
	public double getHeight() {
		return energyY;
	}

	@Override
	public double getWidth() {
		return energyX;
	}

	
	@Override
	public void step() {
		List<ISimObject> nearbyObjects = habitat.nearbyObjects(this, getRadius() + 200);
		for (ISimObject o : nearbyObjects) {
			if (o instanceof SimTurtle) {				
				food.add((IEdibleObject) o);
				dir = dir.turnTowards(directionTo(getBestFood().getPosition()), 1);
				if (distanceTo(getBestFood()) < 20) {
					dir = dir.turnTowards(directionTo(getBestFood().getPosition()), 5);
					habitat.triggerEvent(event);
					getBestFood().eat(getBestFood().getNutritionalValue());
					energyX = energyX * 1.161;
					energyY = energyY * 1.161;

					food.clear();
				}
			
			}
			
			else if (o instanceof SimRepellant) {
				dir = dir.turnTowards(-90, 5);
				accelerateTo(defaultSpeed * 2, 1);

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
		energyX = energyX * 0.9999198;
		energyY = energyY * 0.9999198;

		super.step();

	}

	
	
	@Override
	public void eventHappened(SimEvent event) {
		event.getType();
		say("BAAAK");
		// System.out.println("This happened: " + event);

	}

}
