package aSSOCC_v2_framework.environment;

import repast.simphony.space.grid.GridPoint;

public class Workplace extends Location {

	public Workplace(int id, GridPoint location, int width, int height) {
		super(id, "Workplace", location, width, height, ContextLocation.WORK);	
	}	
	
	public String getLabel() {
		return id + " " + name;
	}
}