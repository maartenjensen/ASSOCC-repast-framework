package aSSOCC_v2_framework.prototype1;

import aSSOCC_v2_framework.common.Logger;
import aSSOCC_v2_framework.environment.ContextLocation;

public enum Action {

	UNSELECTED, STAY, MOVE_HOME, MOVE_SHOP, MOVE_WORK;
	
	public boolean performAction(Person1 agent) {
		
		switch (this) {
		case MOVE_HOME:
			return actMoveHome(agent);
			
		case MOVE_SHOP:
			return actMoveToShop(agent);
			
		case MOVE_WORK:
			return actMoveToWork(agent);
		
		case STAY:
			return actStay();
					
		case UNSELECTED:
			Logger.logError("The action " + this.name() + " is not supposed to be performed");
			return false;
			
		default:
			Logger.logError("The action " + this.name() + " is not known");
			return false;
		}
	}
	
	public boolean actStay() {
		return true;
	}
	
	public boolean actMoveHome(Person1 agent) {
		agent.nextLoc = agent.myGatheringPoints.get(ContextLocation.HOME);
		return true;
	}
	
	public boolean actMoveToShop(Person1 agent) {
		agent.nextLoc = agent.myGatheringPoints.get(ContextLocation.SHOP);
		return true;
	}
	
	public boolean actMoveToWork(Person1 agent) {
		agent.nextLoc = agent.myGatheringPoints.get(ContextLocation.WORK);
		return true;
	}
	
	public boolean moveToLocation(Person1 agent) {
		return true;
	}
}