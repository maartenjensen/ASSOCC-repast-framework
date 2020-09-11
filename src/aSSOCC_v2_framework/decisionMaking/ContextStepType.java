package aSSOCC_v2_framework.decisionMaking;

public enum ContextStepType {

	INSIDE, TRANSITION;
	
	public static String getGeneralContextName() {
		return "StepType";
	}
}