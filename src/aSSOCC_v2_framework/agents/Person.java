package aSSOCC_v2_framework.agents;

import java.util.ArrayList;
import java.util.HashMap;

import aSSOCC_v2_framework.common.Constants;
import aSSOCC_v2_framework.common.Logger;
import aSSOCC_v2_framework.common.RepastParam;
import aSSOCC_v2_framework.common.SU;
import aSSOCC_v2_framework.decisionMaking.Action;
import aSSOCC_v2_framework.decisionMaking.ActionNoSocialDistance;
import aSSOCC_v2_framework.decisionMaking.ActionSocialDistance;
import aSSOCC_v2_framework.decisionMaking.AgentContext;
import aSSOCC_v2_framework.decisionMaking.AgentDecisionMaking;
import aSSOCC_v2_framework.environment.GatheringPoint;
import aSSOCC_v2_framework.environment.House;
import aSSOCC_v2_framework.environment.Shop;
import repast.simphony.space.grid.GridPoint;

public class Person {

	private int id;
	private int stay;
	private boolean sick;
	private boolean socialDistancing;
	
	HashMap<String, GatheringPoint> myGatheringPoints = new HashMap<String, GatheringPoint>();
	String currentGpName;
	
	private AgentContext myContext;
	private AgentDecisionMaking myDecisionMaker;
	
	public Person(int id) {
		
		this.id = id;
		stay = 0;
		sick = false;
		socialDistancing = false;
		
		
		SU.getContext().add(this);
		
		myGatheringPoints.put("Shop", SU.getOneObjectAllRandom(Shop.class));
		myGatheringPoints.put("Home", SU.getOneObjectAllRandom(House.class));
		
		moveToGatheringPoint("Home");
		
		myContext = new AgentContext(id, "Home", RepastParam.getCoronaExistsContextStr());
		myDecisionMaker = new AgentDecisionMaking(id);
		
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
		
		if (currentGpName.equals("Shop")) {
			
			moveToGatheringPoint("Home");
			
			myContext.updateContext("Home", RepastParam.getCoronaExistsContextStr());
			Logger.logAgent(id, "Moved to context:" + myContext.getCurrentContext().toString());
			
			Action chosenAction = myDecisionMaker.makeDecision(getPossibleActions(), myContext.getCurrentContextFamiliarity(), myContext.getCurrentContextMostFrequentAction());
			Logger.logAgent(id, "Chosen action:" + chosenAction.getClass().getSimpleName());
			if (chosenAction instanceof ActionNoSocialDistance) {
				socialDistancing = false;
			}
			else if (chosenAction instanceof ActionSocialDistance) {
				socialDistancing = true;
			}
			myContext.updateActionFrequency(chosenAction);
			
			stay = Constants.ticksStayHome;
		}
		else if (SU.getProbTrue(Constants.probGoToGrocery)) {
			
			moveToGatheringPoint("Shop");
			
			myContext.updateContext("Shop", RepastParam.getCoronaExistsContextStr());
			Logger.logAgent(id, "Moved to context:" + myContext.getCurrentContext().toString());
			
			Action chosenAction = myDecisionMaker.makeDecision(getPossibleActions(), myContext.getCurrentContextFamiliarity(), myContext.getCurrentContextMostFrequentAction());
			Logger.logAgent(id, "Chosen action:" + chosenAction.getClass().getSimpleName());
			if (chosenAction instanceof ActionNoSocialDistance) {
				socialDistancing = false;
			}
			else if (chosenAction instanceof ActionSocialDistance) {
				socialDistancing = true;
			}
			myContext.updateActionFrequency(chosenAction);
			
			stay = Constants.ticksStayGrocery;
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
	 * Move to gathering point dependent given the gathering type name (e.g. Shop, Home)
	 * It automatically places the agent close to the center (no social distancing) or
	 * further from the center (social distancing).
	 * @param gpName
	 */
	public void moveToGatheringPoint(String gpName) {
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
}
