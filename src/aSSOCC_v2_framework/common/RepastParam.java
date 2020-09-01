package aSSOCC_v2_framework.common;

import repast.simphony.engine.environment.RunEnvironment;

public class RepastParam {
	
	public static void setRepastParameters() {
		
	}
	
	public static float getImitationPreference() {
		return RunEnvironment.getInstance().getParameters().getFloat("imitation_preference");
	}
	
	public static int getImitationNumberOfPeople() {
		return RunEnvironment.getInstance().getParameters().getInteger("imitation_number_of_people");
	}
	
	public static boolean getCoronaExists() {
		return RunEnvironment.getInstance().getParameters().getBoolean("corona_exists");
	}
	
	public static boolean getCoronaRiskHigh() {
		return RunEnvironment.getInstance().getParameters().getBoolean("corona_risk_high");
	}
	
	public static float getProbabilityRepeatFamiliar() {
		return RunEnvironment.getInstance().getParameters().getFloat("probability_repeat_familiar");
	}
}