package aSSOCC_v2_framework.environment;

public enum ContextStepType {

	NONE, INSIDE, TRANSITION;
	
	public static String getGeneralContextName() {
		return "StepType";
	}
}