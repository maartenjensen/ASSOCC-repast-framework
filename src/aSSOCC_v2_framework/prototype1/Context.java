package aSSOCC_v2_framework.prototype1;

import java.util.HashMap;

import aSSOCC_v2_framework.environment.ContextLocation;
import aSSOCC_v2_framework.environment.ContextStepType;
import aSSOCC_v2_framework.environment.DayPart;

public class Context {

	HashMap<String, String> myContextParts  = new HashMap<String, String>(); 
	Action actionRepetition;
	double actionFamiliarity;
	
	/**
	 * A minimal context is a location and a time
	 */
	public Context(ContextLocation location, DayPart time, ContextStepType contextStepType) {
		
		myContextParts.put("location", location.name());
		myContextParts.put("time", time.name());
		myContextParts.put("type", contextStepType.name());
		
		actionRepetition = Action.UNSELECTED;
		actionFamiliarity = 0;
	}
	
	public void addNeed(Needs need) {
		myContextParts.put("need", need.name());
	}
	
	public String toString() {
		return myContextParts.toString();
	}
	
	public String actionStatus() {
		return actionRepetition.name() + ", familiarity: " + actionFamiliarity;
	}
	
	public void updateContextWithAction(Action actionRepetition) {
		
		if (actionRepetition == Action.UNSELECTED) {
			return ;
		}
		
		if (actionRepetition == this.actionRepetition) {
			actionFamiliarity = Math.min(1, actionFamiliarity + 0.25);
		}
		else {
			actionFamiliarity = 0;
			this.actionRepetition = actionRepetition;
		}
	}
	
	public double getActionFamiliarity() {
		return actionFamiliarity;
	}
	
	public Action getActionRepetition() {
		return actionRepetition;
	}
}