package aSSOCC_v2_framework.decisionMaking;

public enum ContextCorona {

	NO_CORONA, LOW_RISK, HIGH_RISK;
	
	public static ContextCorona getCoronaContext(boolean coronaExists, boolean coronaRiskHigh) {
		if (!coronaExists) {
			return ContextCorona.NO_CORONA;
		}
		else {
			if (coronaRiskHigh) {
				return ContextCorona.HIGH_RISK;
			}
			else {
				return ContextCorona.LOW_RISK;
			}
		}
	}
	
	public static String getGeneralContextName() {
		return "Corona";
	}
}