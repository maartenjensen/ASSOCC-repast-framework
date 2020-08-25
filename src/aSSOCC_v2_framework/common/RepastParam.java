package aSSOCC_v2_framework.common;

import repast.simphony.engine.environment.RunEnvironment;

public class RepastParam {
	
	public static void setRepastParameters() {
		
	}
	
	public static boolean getCoronaExistsContext() {
		return RunEnvironment.getInstance().getParameters().getBoolean("corona_exists");
	}
	
	public static String getCoronaExistsContextStr() {
		if (RunEnvironment.getInstance().getParameters().getBoolean("corona_exists")) {
			return "YesCorona";
		}
		else {
			return "NoCorona";
		}
	}
}