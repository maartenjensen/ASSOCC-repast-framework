package aSSOCC_v2_framework.environment;

import aSSOCC_v2_framework.decisionMaking.ContextLocation;
import repast.simphony.space.grid.GridPoint;

public class House extends GatheringPoint {

	public House(int id, GridPoint location, int width, int height) {
		super(id, "House", location, width, height, ContextLocation.HOME);	
	}	
	
	public String getLabel() {
		return id + " " + name;
	}
}