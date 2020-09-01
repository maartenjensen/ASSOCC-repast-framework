package aSSOCC_v2_framework.environment;

import aSSOCC_v2_framework.common.Constants;
import aSSOCC_v2_framework.common.Logger;
import aSSOCC_v2_framework.common.SU;
import repast.simphony.space.grid.GridPoint;

public class Shop extends GatheringPoint {

	private int stepsClosed;
	
	public Shop(int id, GridPoint location, int width, int height) {
		super(id, "Shop", location, width, height);	
		
		open = true;
		stepsClosed = 0;
	}
	
	public void step() {
		
		Logger.logMain("Shop step");
		if (!open) {
			stepsClosed -= 1;
			if (stepsClosed == 0)
				open = true;
			else
				return ;
		}
		
		if (SU.getProbTrue(Constants.PROB_CLOSE_SHOP)) {
			open = false;
			stepsClosed = Constants.TICKS_CLOSE_SHOP;
		}
	}
	
	public String getLabel() {
		if (open)
			return id + " " + name + " is open";
		else
			return id + " " + name + " is closed";
	}
}