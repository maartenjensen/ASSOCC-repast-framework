package aSSOCC_v2_framework.environment;

import aSSOCC_v2_framework.common.Logger;
import aSSOCC_v2_framework.common.RepastParam;
import aSSOCC_v2_framework.decisionMaking.ContextLocation;
import repast.simphony.space.grid.GridPoint;

public class Shop extends GatheringPoint {
	
	public Shop(int id, GridPoint location, int width, int height) {
		super(id, "Shop", location, width, height, ContextLocation.SHOP);	
		
		open = true;
	}
	
	public void step() {
		
		Logger.logMain("Shop step");
		if (id == 1) {
			open = RepastParam.getShopOpen1();
		}
		else {
			open = RepastParam.getShopOpen2();
		}
	}
	
	public String getLabel() {
		if (open)
			return id + " " + name + " is open";
		else
			return id + " " + name + " is closed";
	}
}