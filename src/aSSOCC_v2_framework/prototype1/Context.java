package aSSOCC_v2_framework.prototype1;

import java.util.HashMap;

import aSSOCC_v2_framework.environment.ContextLocation;
import aSSOCC_v2_framework.environment.DayPart;

public class Context {

	HashMap<String, String> myContextParts  = new HashMap<String, String>(); 
	
	/**
	 * A minimal context is a location and a time
	 */
	public Context(ContextLocation location, DayPart time) {
				
		myContextParts.put("location", location.name());
		myContextParts.put("time", time.name());
	}
	
	public void addNeed(Needs need) {
		myContextParts.put("need", need.name());
	}
	
	public String toString() {
		return myContextParts.toString();
	}
}
