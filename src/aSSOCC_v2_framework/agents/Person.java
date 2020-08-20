package aSSOCC_v2_framework.agents;

import aSSOCC_v2_framework.common.Logger;
import aSSOCC_v2_framework.common.SU;

public class Person {

	private int id;
	private boolean socialDistancing;
	
	public Person(int id) {
		
		this.id = id;
		this.socialDistancing = false;
		SU.getContext().add(this);
	}
	
	public void run() {
		// get the grid location of this Human
		
	}
	
	public boolean getSocialDistancing() {
		return socialDistancing;
	}
	
	public void randomSocialDistancing() {
		Logger.logMain("Change random distancing:" + id);
		socialDistancing = SU.getRandomBoolean();
	}
	
	public int getId() {
		return id;
	}
}
