package aSSOCC_v2_framework.agents;

import java.util.HashMap;

import aSSOCC_v2_framework.common.Constants;
import aSSOCC_v2_framework.common.Logger;
import aSSOCC_v2_framework.common.SU;
import aSSOCC_v2_framework.environment.GatheringPoint;
import aSSOCC_v2_framework.environment.House;
import aSSOCC_v2_framework.environment.Shop;
import repast.simphony.space.grid.GridPoint;

public class Person {

	private int id;
	private boolean socialDistancing;
	HashMap<String, GatheringPoint> myGatheringPoints = new HashMap<String, GatheringPoint>();
	String currentGpName;
	
	private int stay;
	
	public Person(int id) {
		
		this.id = id;
		this.socialDistancing = false;
		SU.getContext().add(this);
		
		myGatheringPoints.put("Shop", SU.getOneObjectAllRandom(Shop.class));
		myGatheringPoints.put("Home", SU.getOneObjectAllRandom(House.class));
		
		moveToGatheringPoint("Home");
	}
	
	public void step() {
		
		if (stay > 0) {
			stay -= 1;
			return ;
		}
		
		if (currentGpName.equals("Shop")) {
			moveToGatheringPoint("Home");
			stay = Constants.ticksStayHome;
			randomSocialDistancing();
		}
		else if (SU.getProbTrue(Constants.probGoToGrocery)) {
			moveToGatheringPoint("Shop");
			stay = Constants.ticksStayGrocery;
			randomSocialDistancing();
		}
	}
	
	/**
	 * Move to gathering point dependent given the gathering type name (e.g. Shop, Home)
	 * It automatically places the agent close to the center (no social distancing) or
	 * further from the center (social distancing).
	 * @param gpName
	 */
	public void moveToGatheringPoint(String gpName) {
		if (!myGatheringPoints.containsKey(gpName)) {
			Logger.logError("Error in Person " + id + " no gp with name '" + gpName + "' found.");
		}
		
		GridPoint gpLocation = myGatheringPoints.get(gpName).getLocation();

		if (!SU.getGrid().moveTo(this, gpLocation.getX(), gpLocation.getY())) {
			Logger.logError("Person " + id + " could not be placed, coordinate: " + gpLocation);
		}
		
		currentGpName = gpName;
	}
	
	public boolean getSocialDistancing() {
		return socialDistancing;
	}
	
	public void randomSocialDistancing() {
		Logger.logMain("Change random distancing:" + id);
		socialDistancing = SU.getRandomBoolean();
	}
	
	public int getId() {
		return id;
	}
}
