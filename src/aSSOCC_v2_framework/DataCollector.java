package aSSOCC_v2_framework;

import repast.simphony.context.Context;

public class DataCollector {

	private int actionRepeat;
	private int actionImitate;
	private int actionRational;
	
	public DataCollector(final Context<Object> context) {
		context.add(this); // This class is added to the context so that it can be used by the plotting functions of repast
	}
	
	public void resetVariables() {
		
		actionRepeat   = 0;
		actionImitate  = 0;
		actionRational = 0;
	}
	
	public void addActionRepeat() {
		actionRepeat ++;
	}
	
	public void addActionImitate() {
		actionImitate ++;
	}
	
	public void addActionRational() {
		actionRational ++;
	}
	
	public int getActionRepeat() {
		return actionRepeat;
	}
	
	public int getActionImitate() {
		return actionImitate;
	}
	
	public int getActionRational() {
		return actionRational;
	}
}