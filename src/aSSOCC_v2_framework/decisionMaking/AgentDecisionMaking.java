package aSSOCC_v2_framework.decisionMaking;

import java.util.ArrayList;

import aSSOCC_v2_framework.common.Logger;
import aSSOCC_v2_framework.common.RepastParam;
import aSSOCC_v2_framework.common.SU;
import repast.simphony.random.RandomHelper;

/**
 * This class represents the CAFCA decision making module in a simplified way
 * Currently we have repetition, imitation, rational choice 
 * It should be used by the agent
 * @author Maarten
 *
 */
public class AgentDecisionMaking {

	private int agentId;
	
	public AgentDecisionMaking(int agentId) {
		this.agentId = agentId;
	}
	
	/**
	 * This function should take into account familiarity and choose between repetition, imitation and rational choice
	 * @param possibleActions
	 * @param familiarity
	 * @param mostFrequentAction
	 * @return
	 */
	public Action makeDecision(ArrayList<Action> possibleActions, float familiarity, Action mostFrequentAction) {
		
		Logger.logAgent(agentId, "[DELIBERATE] on" + possibleActions.toString() + ", context familiarity:" + familiarity + ", mostFrequentAction:" + mostFrequentAction);
		
		if (mostFrequentAction != null && SU.getProbTrue(Math.min(familiarity, 0.95f))) { //TODO check if possibleActions contains mostFrequentAction
			Logger.logAgent(agentId, "[Repetition]" + mostFrequentAction.toString());
			return mostFrequentAction;
		}
		else if (possibleActions.size() > 0) { //Action is now selected at random
			if (SU.getProbTrue(0.5f)) {
				Logger.logAgent(agentId, "[Imitation NOT implemented]");
				return possibleActions.get(RandomHelper.nextIntFromTo(0, possibleActions.size() - 1));
			}
			else {
				Logger.logAgent(agentId, "[Rational choice]");
				if (RepastParam.getCoronaExistsContext()) { //TODO implement this better and dependent on possibleActions
					return new ActionSocialDistance();
				}
				else {
					return new ActionNoSocialDistance();
				}
			}
		}
		return new ActionNoAction();
	}
}
