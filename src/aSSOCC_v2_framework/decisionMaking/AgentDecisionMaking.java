package aSSOCC_v2_framework.decisionMaking;

import java.util.ArrayList;

import aSSOCC_v2_framework.common.Logger;
import aSSOCC_v2_framework.common.RepastParam;
import aSSOCC_v2_framework.common.SU;
import aSSOCC_v2_framework.decisionMaking.Actions.Action;
import aSSOCC_v2_framework.decisionMaking.Actions.ActionNoAction;
import aSSOCC_v2_framework.decisionMaking.Actions.ActionNoSocialDistance;
import aSSOCC_v2_framework.decisionMaking.Actions.ActionSocialDistance;

/**
 * This class represents the CAFCA decision making module in a simplified way
 * Currently we have repetition, imitation, rational choice 
 * It should be used by the agent
 * @author Maarten
 *
 */
public class AgentDecisionMaking {

	private int agentId;
	private float imitationPreference;
	
	public AgentDecisionMaking(int agentId, float imitationPreference) {
		this.agentId = agentId;
		this.imitationPreference = imitationPreference;
	}
	
	/**
	 * This function should take into account familiarity and choose between repetition, imitation and rational choice
	 * Later it should be an iterative function that first checks whether the agent can repeat, if not the next simplest action is chosen if possible.
	 * It could use an ordered list of DecisionMakingTypes/CAFCA_Cells that it iteratively goes through, one gets removed from this list when one is not
	 * possible for example (imitation when there are no people around).
	 * @param possibleActions
	 * @param familiarity
	 * @param mostFrequentAction
	 * @return
	 */
	public Action makeDecision(ArrayList<Action> possibleActions, float familiarity, Action mostFrequentAction, Action imitationAction) {
		
		Logger.logAgent(agentId, "[DELIBERATE] on" + possibleActions.toString() + ", context familiarity:" + familiarity + ", mostFrequentAction:" + mostFrequentAction);
		
		if (mostFrequentAction != null && SU.getProbTrue(Math.min(familiarity, RepastParam.getProbabilityRepeatFamiliar()))) { //TODO check if possibleActions contains mostFrequentAction
			Logger.logAgent(agentId, "[Repetition]" + mostFrequentAction.toString());
			SU.getDataCollector().addActionRepeat();
			return mostFrequentAction;
		}
		else if (possibleActions.size() > 0) { //Action is now selected at random
			if (imitationAction != null && SU.getProbTrue(imitationPreference)) {
				Logger.logAgent(agentId, "[Imitation]");
				SU.getDataCollector().addActionImitate();
				return imitationAction;
			}
			else {
				Logger.logAgent(agentId, "[Rational choice]");
				SU.getDataCollector().addActionRational();
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
