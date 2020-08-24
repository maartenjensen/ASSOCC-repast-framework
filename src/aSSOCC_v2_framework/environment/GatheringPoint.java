package aSSOCC_v2_framework.environment;

import aSSOCC_v2_framework.common.Logger;
import aSSOCC_v2_framework.common.SU;
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
	
	public void moveTo(GridPoint location) {
		if (!SU.getGrid().moveTo(this, location.getX(), location.getY())) {
			Logger.logError("GatheringPoint could not be placed, coordinate: " + location);
		}
	}
}