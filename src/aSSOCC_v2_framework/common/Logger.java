package aSSOCC_v2_framework.common;

import repast.simphony.engine.environment.RunEnvironment;

public class Logger {

	private static boolean logMain = true;
	private static boolean logErrors = true;
	
	public static void logMain(String output) {
		if (logMain)
			System.out.println(output);
	}
	
	public static void logError(String error) {
		if (logErrors) {
			System.err.println("Error: " + error);
			new Exception().printStackTrace();
			RunEnvironment.getInstance().endRun();
		}
	}
}
