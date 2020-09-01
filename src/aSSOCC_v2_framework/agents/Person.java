package aSSOCC_v2_framework.agents;

import java.util.ArrayList;
import java.util.HashMap;

import aSSOCC_v2_framework.common.Constants;
import aSSOCC_v2_framework.common.Logger;
import aSSOCC_v2_framework.common.RepastParam;
import aSSOCC_v2_framework.common.SU;
import aSSOCC_v2_framework.decisionMaking.AgentContext;
import aSSOCC_v2_framework.decisionMaking.AgentDecisionMaking;
import aSSOCC_v2_framework.decisionMaking.ContextCorona;
import aSSOCC_v2_framework.decisionMaking.ContextLocation;
import aSSOCC_v2_framework.decisionMaking.Actions.Action;
import aSSOCC_v2_framework.decisionMaking.Actions.ActionNoSocialDistance;
import aSSOCC_v2_framework.decisionMaking.Actions.ActionSocialDistance;
import aSSOCC_v2_framework.environment.GatheringPoint;
import aSSOCC_v2_framework.environment.House;
import aSSOCC_v2_framework.environment.Shop;
import repast.simphony.space.grid.GridPoint;

public class Person {

	private int id;
	private int stay;
	private boolean sick;
	private boolean socialDistancing;
	
	private HashMap<ContextLocation, GatheringPoint> myGatheringPoints = new HashMap<ContextLocation, GatheringPoint>();
	private ContextLocation currentGpName;
	
	private AgentContext myContext;
	private AgentDecisionMaking myDecisionMaker;
	
	public Person(int id) {
		
		this.id = id;
		stay = 0;
		sick = false;
		socialDistancing = false;
		
		SU.getContext().add(this);
		
		myGatheringPoints.put(ContextLocation.HOME, SU.getOneObjectAllRandom(House.class));
		myGatheringPoints.put(ContextLocation.SHOP_REG, SU.getOneObjectAllRandom(Shop.class));
		
		moveToGatheringPoint(ContextLocation.HOME);
		
		myContext = new AgentContext(id, ContextLocation.HOME, ContextCorona.getCoronaContext(RepastParam.getCoronaExists(), RepastParam.getCoronaRiskHigh()) );
		myDecisionMaker = new AgentDecisionMaking(id, RepastParam.getImitationPreference());
		
		Logger.logAgent(id, "Spawned!");
	}
	
	/**
	 * This functions may seem complicated but it is relatively straight forward
	 * 1. if an agent is staying at a place it does not do anything special
	 * 2. if it is at the shop it will move back home 
	 * 3. if it is at home there is a chance of the agent moving to the grocerystore
	 * Decision making
	 * 1. move to the place
	 * 2. update the context to this new place
	 * 3. ask the decision maker for an action to perform based on 1) possible set of actions, 2) level of familiarity of the context, 3) the most frequentAction
	 * 4. perform action
	 * 5. plug in the action into myContext instance to update the frequency action and familiarity
	 */
	public void step() {
		
		if (stay > 0) {
			stay -= 1;
			return ;
		}
		
		if (currentGpName.equals(ContextLocation.SHOP_REG)) {
			
			moveToGatheringPoint(ContextLocation.HOME);
			
			myContext.updateContext( ContextLocation.HOME, ContextCorona.getCoronaContext(RepastParam.getCoronaExists(), RepastParam.getCoronaRiskHigh()) );
			Logger.logAgent(id, "Moved to context:" + myContext.getCurrentContext().toString());
			
			Action chosenAction = myDecisionMaker.makeDecision(getPossibleActions(), myContext.getCurrentContextFamiliarity(), myContext.getCurrentContextMostFrequentAction(), getPreferedActionOfOthers());
			Logger.logAgent(id, "Chosen action:" + chosenAction.getClass().getSimpleName());
			if (chosenAction instanceof ActionNoSocialDistance) {
				socialDistancing = false;
			}
			else if (chosenAction instanceof ActionSocialDistance) {
				socialDistancing = true;
			}
			myContext.updateActionFrequency(chosenAction);
			stay = Constants.TICKS_STAY_HOME;
		}
		else if (SU.getProbTrue(Constants.PROB_GO_TO_GROCERY)) {
			
			moveToGatheringPoint(ContextLocation.SHOP_REG);
			
			myContext.updateContext( ContextLocation.SHOP_REG, ContextCorona.getCoronaContext(RepastParam.getCoronaExists(), RepastParam.getCoronaRiskHigh()) );
			Logger.logAgent(id, "Moved to context:" + myContext.getCurrentContext().toString());
			
			Action chosenAction = myDecisionMaker.makeDecision(getPossibleActions(), myContext.getCurrentContextFamiliarity(), myContext.getCurrentContextMostFrequentAction(), getPreferedActionOfOthers());
			Logger.logAgent(id, "Chosen action:" + chosenAction.getClass().getSimpleName());
			if (chosenAction instanceof ActionNoSocialDistance) {
				socialDistancing = false;
			}
			else if (chosenAction instanceof ActionSocialDistance) {
				socialDistancing = true;
			}
			myContext.updateActionFrequency(chosenAction);
			stay = Constants.TICKS_STAY_GROCERY;
		}
	}
	
	/**
	 * This should be dependent on where the agent is and the context
	 * @return
	 */
	private ArrayList<Action> getPossibleActions() {
		ArrayList<Action> possibleActions = new ArrayList<Action>();
		possibleActions.add(new ActionNoSocialDistance());
		possibleActions.add(new ActionSocialDistance());
		return possibleActions;
	}
	
	/**
	 * This function is only made for the social distancing action, but can be updated to
	 * incorporate other actions as well if the agent saves its last action/the action it performs in the given context.
	 * Later this action should only be called when the agent is actually going for imitation.
	 * @return
	 */
	private Action getPreferedActionOfOthers() {
		
		HashMap<Action, Integer> actionFrequency = new HashMap<Action, Integer>();
		ActionSocialDistance   socDis = new ActionSocialDistance();
		ActionNoSocialDistance noSocDis = new ActionNoSocialDistance();
		actionFrequency.put(socDis, 0);
		actionFrequency.put(noSocDis, 0);
		for (Person otherAtGp : SU.getPersonsAllRandomExcludedGpMax(this, getCurrentGpId(), RepastParam.getImitationNumberOfPeople())) {
			if (otherAtGp.getSocialDistancing()) {
				actionFrequency.put(socDis, actionFrequency.get(socDis) + 1);
			}
			else {
				actionFrequency.put(noSocDis, actionFrequency.get(noSocDis) + 1);
			}
		}
		
		// The frequencies should not be the same, then it is checked whether social distance or no social distance is more frequent
		if (actionFrequency.get(socDis) != actionFrequency.get(noSocDis)) {
			if (actionFrequency.get(socDis) > actionFrequency.get(noSocDis)) {
				return socDis;
			}
			else {
				return noSocDis;
			}
		}
		else {
			return null;
		}
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
		
		GridPoint gpLocation = myGatheringPoints.get(gpName).getRandomLocationOnGP();

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
	
	public boolean isSick() {
		return sick;
	}
	
	public String getCurrentContext() {
		return myContext.getCurrentContext();
	}
	
	public String getMyContexts() {
		return myContext.getMyContexts();
	}
	
	public int getCurrentGpId() {
		return myGatheringPoints.get(currentGpName).getId();
	}
}
