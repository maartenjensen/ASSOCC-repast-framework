package aSSOCC_v2_framework.environment;

import aSSOCC_v2_framework.common.Logger;
import aSSOCC_v2_framework.common.SU;
import aSSOCC_v2_framework.preprototype.ContextLocation;
import repast.simphony.space.grid.GridPoint;

public class Shop extends GatheringPoint {
	
	public Shop(int id, GridPoint location, int width, int height) {
		super(id, "Shop", location, width, height, ContextLocation.SHOP);	
		
		open = true;
	}
	
	public void step() {
		
		Logger.logMain("Shop step");
		if (SU.getDayPart() == DayPart.NIGHT) {
			open = false;
		}
		else {
			open = true;
		}
		/* This part is commented
		 if (id == 1) {
			open = RepastParam.getShopOpen1();
		}
		else {
			open = RepastParam.getShopOpen2();
		}*/
	}
	
	public String getLabel() {
		if (open)
			return id + " " + name + " is open";
		else
			return id + " " + name + " is closed";
	}
}