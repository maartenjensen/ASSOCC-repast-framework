package aSSOCC_v2_framework;

import repast.simphony.context.Context;

public class DataCollector {

	private int actionRepeat;
	private int actionImitate;
	private int actionRational;
	
	private int actionRepeatTemp;
	private int actionImitateTemp;
	private int actionRationalTemp;
	
	public DataCollector(final Context<Object> context) {
		context.add(this); // This class is added to the context so that it can be used by the plotting functions of repast
	}
	
	public void resetVariables() {
		
		actionRepeat   = 0;
		actionImitate  = 0;
		actionRational = 0;
		
		actionRepeatTemp   = 0;
		actionImitateTemp  = 0;
		actionRationalTemp = 0;
	}
	
	public void resetTemporaryVariables() {
		
		actionRepeatTemp   = 0;
		actionImitateTemp  = 0;
		actionRationalTemp = 0;
	}
	
	public void addActionRepeat() {
		actionRepeat ++;
		actionRepeatTemp ++;
	}
	
	public void addActionImitate() {
		actionImitate ++;
		actionImitateTemp ++;
	}
	
	public void addActionRational() {
		actionRational ++;
		actionRationalTemp ++;
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
	
	public float getActionRepeatPerc() {
		return actionRepeat / (float) (actionRepeat + actionImitate + actionRational);
	}
	
	public float getActionImitatePerc() {
		return actionImitate / (float) (actionRepeat + actionImitate + actionRational);
	}
	
	public float getActionRationalPerc() {
		return actionRational / (float) (actionRepeat + actionImitate + actionRational);
	}
	
	public float getActionRepeatTemp() {
		return actionRepeatTemp;
	}
	
	public float getActionImitateTemp() {
		return actionImitateTemp;
	}
	
	public float getActionRationalTemp() {
		return actionRationalTemp;
	}
}