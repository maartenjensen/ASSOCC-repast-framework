package aSSOCC_v2_framework.environment;

import aSSOCC_v2_framework.common.Logger;
import aSSOCC_v2_framework.common.SU;
import repast.simphony.space.grid.GridPoint;

public class House extends GatheringPoint {

	public House(int id, GridPoint location, int width, int height) {
		super(id, "House", location, width, height);	
		
		SU.getContext().add(this);
		if (!SU.getGrid().moveTo(this, location.getX(), location.getY())) {
			Logger.logError("Person could not be placed, coordinate: " + location);
		}
	}	
}