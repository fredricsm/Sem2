package inf101.simulator.objects.examples;

import java.util.ArrayList;
import java.util.List;
import inf101.simulator.Direction;
import inf101.simulator.Habitat;
import inf101.simulator.MediaHelper;
import inf101.simulator.Position;
import inf101.simulator.SimMain;
import inf101.simulator.objects.AbstractMovingObject;
import inf101.simulator.objects.IEdibleObject;
import inf101.simulator.objects.ISimListener;
import inf101.simulator.objects.ISimObject;
import inf101.simulator.objects.SimEvent;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class SimShark extends AbstractMovingObject implements ISimListener {
	private static final double defaultSpeed = 0.5;
	private Habitat habitat;
	private Image animalCoat = MediaHelper.getImage("SharkAnim.gif");

	private double energyX = 250;
	private double energyY = 150;
	private SimEvent event = new SimEvent(this, "nom", null, "NOM");
	private List<IEdibleObject> food = new ArrayList<>();
	private SimMain main;
	
	public SimShark(Position pos, Habitat hab, SimMain main) {
		super(new Direction(0), pos, defaultSpeed);
		this.habitat = hab;
		this.main = main;
		habitat.addListener(this, this);
	}

	@Override
	public void draw(GraphicsContext context) {
		super.draw(context);

		double dir = getDirection().toAngle();
		drawBar(context, energyX, 0, Color.RED, Color.GREEN);
		if ((dir <= 90 && dir >= -90)) {
			context.translate(0, getHeight());
			context.scale(1, -1);
			context.drawImage(animalCoat, 0, 0, getWidth(), getHeight());
		} else if (dir < -90 || dir < 180) {
			context.drawImage(animalCoat, 0, 0, getWidth(), getHeight());
		}

	}

	public IEdibleObject getBestFood() {
		IEdibleObject best = food.get(0);
		for (IEdibleObject edb : food) {
			if (food.isEmpty()) {
				return null;
			} else if (edb.getNutritionalValue() > best.getNutritionalValue()) {
				best = edb;
			}
		}
		return best;

	}
	public void createResidue(){
		habitat.addObject(new SimResidue(pos, habitat));
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
		List<ISimObject> nearbyObjects = habitat.nearbyObjects(this, getRadius() + 400);
		for (ISimObject o : nearbyObjects) {
			if (o instanceof SimTurtle) {
				food.add((IEdibleObject) o);
				dir = dir.turnTowards(directionTo(getBestFood().getPosition()), 1);
				accelerateTo(defaultSpeed * 2, -0.2);

				if (distanceTo(getBestFood()) < 100) {
					accelerateTo(defaultSpeed * 5, -0.2);
					dir = dir.turnTowards(directionTo(getBestFood().getPosition()), 5);
					habitat.triggerEvent(event);
					getBestFood().eat(getBestFood().getNutritionalValue());
					createResidue();
					energyX = energyX * 1.0019111;
					energyY = energyY * 1.001911;
					food.clear();
					main.bite();
					
				}

			}

			else if (o instanceof SimDolphin) {
				dir = dir.turnTowards(-90, 5);
				accelerateTo(defaultSpeed * 5, 1);

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
		energyX = energyX * 0.9999598;
		energyY = energyY * 0.9999598;

		super.step();

	}

	@Override
	public void eventHappened(SimEvent event) {
		event.getType();
		say("Food!");
		System.out.println("This happened: " + event);

	}

}
