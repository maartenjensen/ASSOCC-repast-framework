package aSSOCC_v2_framework.prototype1;

import java.util.HashMap;

import aSSOCC_v2_framework.agents.Person;
import aSSOCC_v2_framework.common.Logger;
import aSSOCC_v2_framework.common.SU;
import aSSOCC_v2_framework.environment.ContextLocation;
import aSSOCC_v2_framework.environment.ContextStepType;
import aSSOCC_v2_framework.environment.DayPart;
import aSSOCC_v2_framework.environment.House;
import aSSOCC_v2_framework.environment.Location;
import aSSOCC_v2_framework.environment.Shop;
import aSSOCC_v2_framework.environment.Workplace;
import repast.simphony.space.grid.GridPoint;

public class Person1 extends Person {

	public int food;

	public HashMap<String, Context> myKnownContexts = new HashMap<String, Context>(); 
	public HashMap<ContextLocation, Location> myGatheringPoints = new HashMap<ContextLocation, Location>();
	
	protected String temp_text;
	
	public Location currentLoc;
	public Location nextLoc;
	
	public Person1(int id) {
		super(id);

		food = 100;
		temp_text = "";

		myGatheringPoints.put(ContextLocation.HOME, SU.getOneObjectAllRandom(House.class));
		myGatheringPoints.put(ContextLocation.SHOP, SU.getOneObjectAllRandom(Shop.class));
		myGatheringPoints.put(ContextLocation.WORK, SU.getOneObjectAllRandom(Workplace.class));
		
		moveToGatheringPoint(ContextLocation.HOME);
		
		nextLoc = null;
		currentLoc = currentGp;
		
		Logger.logAgent(id, "Test spawn!");
	}

	public Context getCurrentContext() {
			
		// Get physical context
		Context minimalContext = new Context(currentLoc.getContextLocation(), SU.getDayPart(), SU.getStepType());
		// Update needs
		Logger.logMain(minimalContext.toString());
		Needs salientNeed = getSalientNeed();
		if (salientNeed != Needs.NONE) {
			minimalContext.addNeed(salientNeed); }
		
		return minimalContext;
	}
	
	/**
	 * Returns no need or the most salient need
	 */
	public Needs getSalientNeed() {
		if (SU.getDayPart() == DayPart.MORNING || SU.getDayPart() == DayPart.AFTERNOON) {
			return Needs.CONFORM;
		}
		else if (SU.getDayPart() == DayPart.NIGHT) {
			return Needs.REST;
		}
		else if (food < 20) {
			return Needs.FOOD;
		}
		return Needs.NONE;
	}
	
	/** This function is performed half a tick before step()
	 * 
	 */
	public void stepTransfer() {
		
		// Check whether the context is known, if not add the context
		Context currentContext = getCurrentContext();
		Logger.logMain(currentContext.toString());
		
		if (myKnownContexts.containsKey(currentContext.toString())) {
			Logger.logMain("The context is known");
		}
		else {
			Logger.logMain("The context is not known");
			myKnownContexts.put(currentContext.toString(), currentContext);
		}
		
		// Retrieve the context from memory
		Context contextInMemory = myKnownContexts.get(currentContext.toString());
		
		// Meta selector
		Action chosenAction = null;
		if (contextInMemory.getActionFamiliarity() == 1) {
			// Repetition
			chosenAction = contextInMemory.getActionRepetition();
			temp_text = "Repetition selected ";
		}
		else {
			// Deliberate
			chosenAction = decisionDeliberate(contextInMemory);
		}
		
		// Check if the chosenAction is not null
		if (chosenAction != null) {
			// Select an action
			Logger.logAgent(id, temp_text + " and chose action:" + chosenAction.name());
			chosenAction.performAction(this);
			contextInMemory.updateContextWithAction(chosenAction);
			Logger.logAgent(id, myKnownContexts.get(currentContext.toString()).actionStatus());
			//myKnownContexts.get(c)
		}
		else {
			Logger.logError("No chosen action! chosenAction == null");
		}
	}
	
	public Action decisionDeliberate(Context currentContext) {

		if (SU.getStepType() == ContextStepType.TRANSITION) {

			Needs salientNeed = getSalientNeed();
			temp_text = "Deliberation selected on type " + SU.getStepType() + ", need " + salientNeed.name();
			switch (salientNeed) {
			case FOOD:
				return Action.MOVE_SHOP;
			case REST:
				return Action.MOVE_HOME;
			case CONFORM:
				return Action.MOVE_WORK;
			default:
				break;
			}
			temp_text = "Deliberation selected on type " + SU.getStepType() + " and default";
			return Action.MOVE_HOME;
		}
		
		temp_text = "Deliberation selected nothing";
		return Action.UNSELECTED;
	}
	
	public void stepStay() {
		
		Logger.logAgent(id, "Next location:" + nextLoc.getLabel());
		// Finish moving
		if (nextLoc != null) {
			moveToGatheringPoint(nextLoc);
		}
		
		Context currentContext = getCurrentContext();
		Logger.logMain(currentContext.toString());
		
		if (currentLoc.getContextLocation() == ContextLocation.SHOP) {
			food = 100;
		}
		else {
			food -= 5;
		}
	}
	
	/**
	 * Move to gathering point dependent given the gathering type name (e.g. Shop, Home)
	 * @param gpName
	 */
	public void moveToGatheringPoint(ContextLocation locName) {
		if (!myGatheringPoints.containsKey(locName)) {
			Logger.logError("Error in Person " + id + " no gp with name '" + locName + "' found.");
		}
		
		GridPoint locLocation = myGatheringPoints.get(locName).getRandomLocationOnGP();
		moveTo(locLocation);
		
		currentLoc = myGatheringPoints.get(locName);
	}
	
	public void moveToGatheringPoint(Location location) {

		GridPoint locLocation = location.getRandomLocationOnGP();
		moveTo(locLocation);
		currentLoc = location;
		nextLoc = null;
	}
	
	public int getFood() {
		return food;
	}
	
}