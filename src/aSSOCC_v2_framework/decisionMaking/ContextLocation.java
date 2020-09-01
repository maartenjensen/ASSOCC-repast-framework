package aSSOCC_v2_framework.decisionMaking;

public enum ContextLocation {

	HOME, SHOP_REG, SHOP_NON_REG;
	
	public static String getGeneralContextName() {
		return "Location";
	}
}