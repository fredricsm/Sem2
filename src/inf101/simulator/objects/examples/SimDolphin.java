package inf101.simulator.objects.examples;


import java.util.ArrayList;
import java.util.List;

import inf101.simulator.Direction;
import inf101.simulator.Habitat;
import inf101.simulator.MediaHelper;
import inf101.simulator.Position;
import inf101.simulator.SimMain;
import inf101.simulator.objects.AbstractMovingObject;
import inf101.simulator.objects.ISimObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class SimDolphin extends AbstractMovingObject{

	private static double defaultSpeed = 1.0;
	private Habitat habitat;
	private Image animalCoat = MediaHelper.getImage("Dolphin.gif");
	private List<ISimObject> sharkList = new ArrayList<>();
	private double energy = 120;


	
	public SimDolphin( Position pos, Habitat hab, SimMain main) {
		super(new Direction(0), pos, defaultSpeed);
		this.habitat = hab;
		//main.click();
	
	}

	@Override
	public void draw(GraphicsContext context) {
	
		super.draw(context);
		drawBar(context, energy, -1, Color.RED, Color.GREEN);
//		super.draw(context);
		double dir = getDirection().toAngle();
		if((dir <= 90 && dir >= -90)){
			context.setStroke(Color.YELLOW.deriveColor(0.0, 1.0, 1.0, 0.5));
			context.translate(0, getHeight());
			context.scale(1, -1);
			context.drawImage(animalCoat, 0, 0, getWidth(), getHeight());
		}
		else if(dir < -90 || dir < 180 ){
			context.drawImage(animalCoat, 0, 0, getWidth(), getHeight());
		}
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
		energy= energy*0.999;
	
		super.step();
		if(energy<50){
			this.destroy();
		}
	}
	
	
	@Override
	public double getHeight() {
		// TODO Auto-generated method stub
		return energy/2.4;
	}

	@Override
	public double getWidth() {
		// TODO Auto-generated method stub
		return energy;
	}








}
