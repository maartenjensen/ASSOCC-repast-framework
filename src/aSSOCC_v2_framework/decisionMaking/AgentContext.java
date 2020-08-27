package aSSOCC_v2_framework.decisionMaking;

import java.util.HashMap;

import aSSOCC_v2_framework.common.Logger;
import aSSOCC_v2_framework.decisionMaking.Actions.Action;

/**
 * This class should be used by the agent
 * @author Maarten
 *
 */
public class AgentContext {

	private int agentId;
	HashMap<String, ContextFamiliarity> myContexts; 
	HashMap<String, String> myCurrentContext;
	
	/**
	 * The constructor, in the future it should actually read the context from a file.
	 */
	public AgentContext(int agentId, String location, String coronaExists) {
		
		this.agentId = agentId;
		myContexts = new HashMap<String, ContextFamiliarity>(); 
		myCurrentContext = new HashMap<String, String>();
		
		updateContext(location, coronaExists);
		
		Logger.logMain(myCurrentContext.toString());
	}
	
	/**
	 * Should be used every time an agent switches context
	 * @param location
	 * @param coronaExists
	 */
	public void updateContext(String location, String coronaExists) {
		
		myCurrentContext.put("Location", location);
		myCurrentContext.put("CoronaExists", coronaExists);
		addCurrentContextToContexts();
	}
	
	/**
	 * This function adds contexts to the myContexts hashmap, new contexts get a familiarity of 0.
	 */
	private void addCurrentContextToContexts() {
		
		String strCurrentContext = myCurrentContext.toString();
		if (!myContexts.containsKey(strCurrentContext)) {
			myContexts.put(strCurrentContext, new ContextFamiliarity(0.0f));
		}
	}
	
	public void updateActionFrequency(Action performedAction) {
		
		ContextFamiliarity contextFamiliarity = myContexts.get(myCurrentContext.toString());
		contextFamiliarity.addPerformedAction(performedAction);
		Logger.logAgent(agentId, "Added performed action:" + performedAction + " to " + myCurrentContext.toString() + " which now has " + contextFamiliarity.toStringExt());
	}
	
	public float getCurrentContextFamiliarity() {
		return myContexts.get(myCurrentContext.toString()).getFamiliarity();
	}
	
	public Action getCurrentContextMostFrequentAction() {
		return myContexts.get(myCurrentContext.toString()).getMostFrequentAction();
	}
	
	public String getCurrentContext() {
		return myCurrentContext.toString();
	}
	
	public String getMyContexts() {
		return myContexts.toString();
	}
}