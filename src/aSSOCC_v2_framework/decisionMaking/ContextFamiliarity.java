package aSSOCC_v2_framework.decisionMaking;

import java.util.ArrayList;
import java.util.HashMap;

import aSSOCC_v2_framework.common.Logger;
import repast.simphony.random.RandomHelper;

public class ContextFamiliarity {

	private float familiarity;
	private HashMap<Action, Integer> actionFrequencies; 
	
	public ContextFamiliarity(float familiarity) {
		
		this.familiarity = familiarity;		
		actionFrequencies = new HashMap<Action, Integer>();
	}
	
	public float getFamiliarity() {
		return familiarity;
	}
	
	public String toString() {
		Action mostFrequentAction = getMostFrequentAction();
		if (mostFrequentAction == null) {
			return familiarity + ", null";
		}
		else {
			return familiarity + ", " + getMostFrequentAction().toString();
		}
		
	}
	
	public String toStringExt() {
		String str = toString() + ":";
		for (Action action : actionFrequencies.keySet()) {
			str += action.toString() + " " + actionFrequencies.get(action) + ", ";
		}
		return str;
	}
	
	/**
	 * Increase frequency or add an action with frequency 1
	 * @param performedAction
	 */
	public void addPerformedAction(Action performedAction) {
		
		familiarity = Math.min(familiarity + 0.25f, 1.0f);
		for (Action actionKey : actionFrequencies.keySet()) {
			
			if (actionKey.getClass() == performedAction.getClass()) {
				actionFrequencies.put(actionKey, actionFrequencies.get(actionKey) + 1);
				Logger.logMain("Update old");
				return ;
			}
		}
		
		actionFrequencies.put(performedAction, 1);
		Logger.logMain("Add new!");		
	}

	/**
	 * Retrieves the action that is most frequently used
	 * null when there is no action
	 * the first action when there is only one action
	 * and some complicated stuff when there are more than one action,
	 * basically iterate through it and make a new arraylist of actions that
	 * are the most frequent, and resets this array when a higher frequent action has been found
	 * @return
	 */
	public Action getMostFrequentAction() {
		
		if (actionFrequencies.isEmpty()) {
			return null;
		}
		else if (actionFrequencies.values().size() == 1) {
			for (Action action: actionFrequencies.keySet()) {
				return action;
			}
		}
		else {
			int maxFrequency = 0;
			ArrayList<Action> mostFrequentActions = new ArrayList<Action>();
			for (Action action: actionFrequencies.keySet()) {
				if (actionFrequencies.get(action) > maxFrequency) {
					mostFrequentActions.clear();
					mostFrequentActions.add(action);
					maxFrequency = actionFrequencies.get(action);
				}
				else if (actionFrequencies.get(action) == maxFrequency) {
					mostFrequentActions.add(action);
				}
			}
			
			if (mostFrequentActions.size() > 0) {
				return mostFrequentActions.get(RandomHelper.nextIntFromTo(0, mostFrequentActions.size() - 1));
			}
			else {
				Logger.logError(getClass().getName() + "Error in implementation of getMostFrequentAction(), the size is 0 for actionFrequencies:" + actionFrequencies.toString()); 
			}
		}
		return null;
	}
}
