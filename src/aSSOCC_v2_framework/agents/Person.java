package aSSOCC_v2_framework.agents;

import java.util.HashMap;

import aSSOCC_v2_framework.common.Logger;
import aSSOCC_v2_framework.common.SU;
import aSSOCC_v2_framework.environment.ContextLocation;
import aSSOCC_v2_framework.environment.GatheringPoint;
import aSSOCC_v2_framework.environment.House;

public class Person {

	protected int id;
	protected int stay;
	protected boolean sick;
	protected boolean socialDistancing;
	
	protected HashMap<ContextLocation, GatheringPoint> myGatheringPoints = new HashMap<ContextLocation, GatheringPoint>();
	protected GatheringPoint currentGp;
	protected GatheringPoint nextGp;
	
	public Person(int id) {
		
		this.id = id;
		stay = 0;
		sick = false;
		socialDistancing = false;
		
		nextGp = null;
		
		SU.getContext().add(this);
		
		myGatheringPoints.put(ContextLocation.HOME, SU.getOneObjectAllRandom(House.class));
		//myGatheringPoints.put(ContextLocation.SHOP_REG, SU.getOneObjectAllRandom(Shop.class));
		
		moveToGatheringPoint(ContextLocation.HOME);

		Logger.logAgent(id, "Spawned!");
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
		
		if (!myGatheringPoints.containsKey(gpName)) {
			Logger.logError("Error in Person " + id + " no gp with name '" + gpName + "' found.");
		}
		
		//GridPoint gpLocation = myGatheringPoints.get(gpName).getRandomLocationOnGP();
		//moveTo(gpLocation);
		GatheringPoint gpReference = myGatheringPoints.get(gpName);
		SU.moveToGp(this, gpReference, currentGp);
		
		currentGp = gpReference;
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
