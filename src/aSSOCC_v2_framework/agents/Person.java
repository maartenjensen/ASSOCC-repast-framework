package aSSOCC_v2_framework.agents;

import aSSOCC_v2_framework.common.Logger;
import aSSOCC_v2_framework.common.SU;
import aSSOCC_v2_framework.environment.ContextLocation;
import aSSOCC_v2_framework.environment.Location;
import repast.simphony.space.grid.GridPoint;

public class Person {

	protected int id;
	protected int stay;
	protected boolean sick;
	protected boolean socialDistancing;
	
	//public HashMap<ContextLocation, Location> myGatheringPoints = new HashMap<ContextLocation, Location>();
	public Location currentGp;
	public Location nextGp;
	
	public Person(int id) {
		
		this.id = id;
		stay = 0;
		sick = false;
		socialDistancing = false;
		
		nextGp = null;
		
		SU.getContext().add(this);
		
		//myGatheringPoints.put(ContextLocation.HOME, SU.getOneObjectAllRandom(House.class));
		//myGatheringPoints.put(ContextLocation.SHOP_REG, SU.getOneObjectAllRandom(Shop.class));
		
		//moveToGatheringPoint(ContextLocation.HOME);

		GridPoint point = new GridPoint(2, 2);
		
		if (!SU.getGrid().moveTo(this, point.getX(), point.getY())) {
			Logger.logError("Person " + id + " could not be placed, coordinate: " + point);
		}
		
		Logger.logAgent(id, "Spawned!");
	}
	
	//Only use when moving to spots inbetween gatheringpoints /Emil
	public void moveTo(GridPoint point) {
		Logger.logAgent(id, " move this agent to " + point.getX() + ", " + point.getY());
		if (!SU.getGrid().moveTo(this, point.getX(), point.getY())) {
			Logger.logError("Person " + id + " could not be placed, coordinate: " + point);
		}
	}

	public void defineContext() {
		
		
	}
	
	/** This function is performed half a tick before step()
	 * 
	 */
	public void stepTransfer() {
		
	}
	
	public void stepStay() {
		
	}

	
	/**
	 * Move to gathering point dependent given the gathering type name (e.g. Shop, Home)
	 * It automatically places the agent close to the center (no social distancing) or
	 * further from the center (social distancing).
	 * @param gpName
	 */
	public void moveToGatheringPoint(ContextLocation gpName) {
		
		//if (!myGatheringPoints.containsKey(gpName)) {
		//	Logger.logError("Error in Person " + id + " no gp with name '" + gpName + "' found.");
		//}
		
		//GridPoint gpLocation = myGatheringPoints.get(gpName).getRandomLocationOnGP();
		//moveTo(gpLocation);
		//Location gpReference = myGatheringPoints.get(gpName);
		//SU.moveToGp(this, gpReference, currentGp);
		
		//currentGp = gpReference;
	}
	
	public boolean getSocialDistancing() {
		return socialDistancing;
	}
	
	public int getId() {
		return id;
	}
	
	public boolean isSick() {
		return sick;
	}
}
