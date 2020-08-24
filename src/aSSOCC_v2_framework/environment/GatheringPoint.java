package aSSOCC_v2_framework.environment;

import aSSOCC_v2_framework.common.Logger;
import aSSOCC_v2_framework.common.SU;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.grid.GridPoint;

public class GatheringPoint {
	
	private int id;
	private int width;
	private int height;
	private final GridPoint location;
	
	protected GatheringPoint(int id, String name, GridPoint location, int width, int height) {
		this.id = id;
		this.width = width;
		this.height = height;
		this.location = location;
		
		Logger.logMain("Spawned this " + name + " with id:" + id);
		
		SU.getContext().add(this);
		if (!SU.getGrid().moveTo(this, location.getX(), location.getY())) {
			Logger.logError("GatheringPoint could not be placed, coordinate: " + location);
		}
	}
	
	public int getId() {
		return id;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public GridPoint getLocation() {
		return location;
	}
	
	/**
	 * TODO: Make a function like this but while excluding locations that have a person on them
	 * @return
	 */
	public GridPoint getRandomLocationOnGP() {
		
		int halfWidth  = (width - 1) / 2;
		int halfHeight = (height - 1) / 2;
		int xPos = RandomHelper.nextIntFromTo(location.getX() - halfWidth, location.getX() + halfWidth);
		int yPos = RandomHelper.nextIntFromTo(location.getY() - halfHeight, location.getY() + halfHeight);
		return new GridPoint(xPos, yPos);
	}
	
	public void moveTo(GridPoint location) {
		if (!SU.getGrid().moveTo(this, location.getX(), location.getY())) {
			Logger.logError("GatheringPoint could not be placed, coordinate: " + location);
		}
	}
}