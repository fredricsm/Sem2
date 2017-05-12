package inf101.simulator.objects.examples;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import inf101.simulator.Direction;
import inf101.simulator.MediaHelper;
import inf101.simulator.Position;
import inf101.simulator.objects.AbstractSimObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class SimPlants extends AbstractSimObject {
	private static final double DIAMETER = 50;

	private static final Random r = new Random();
	private int size = r.nextInt(5);
	private int rPlant = r.nextInt(7);

	private Image plant1 = MediaHelper.getImage("plant1.png");
	private Image plant2 = MediaHelper.getImage("plant2.png");
	private Image plant3 = MediaHelper.getImage("plant3.png");
	private Image plant4 = MediaHelper.getImage("plant4.png");
	private Image plant5 = MediaHelper.getImage("plant5.png");
	private Image plant6 = MediaHelper.getImage("plant6.png");
	private Image plant7 = MediaHelper.getImage("plant7.png");
	private Image plant8 = MediaHelper.getImage("plant8.png");

	private List<Image> plantList = new ArrayList<>();

	public SimPlants(Position pos) {
		super(new Direction(0), pos);

		plantList.add(plant1);
		plantList.add(plant2);
		plantList.add(plant3);
		plantList.add(plant4);
		plantList.add(plant5);
		plantList.add(plant6);
		plantList.add(plant7);
		plantList.add(plant8);
	}

	@Override
	public void draw(GraphicsContext context) {
		context.translate(0, getHeight());
		context.scale(1, -1);
		context.drawImage(plantList.get(rPlant), 0.0, 0.0, getWidth(), getHeight());
	}

	@Override
	public double getHeight() {
		return DIAMETER * size;
	}

	@Override
	public double getWidth() {
		return DIAMETER * size;
	}

	@Override
	public void step() {
	}
}
