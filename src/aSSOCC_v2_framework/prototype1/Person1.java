package aSSOCC_v2_framework.prototype1;

import java.util.HashMap;

import aSSOCC_v2_framework.agents.Person;
import aSSOCC_v2_framework.common.Logger;
import aSSOCC_v2_framework.common.SU;
import aSSOCC_v2_framework.environment.DayPart;

public class Person1 extends Person {

	protected int food;
	
	HashMap<String, Context> myKnownContexts = new HashMap<String, Context>(); 
	
	public Person1(int id) {
		super(id);
		
		food = 100;
	}

	public Context getCurrentContext() {
		
		Logger.logMain("test " + currentGp.getContextLocation().name());
		Logger.logMain("daypart " + SU.getDayPart().name());
		
		// Get physical context
		Context minimalContext = new Context(currentGp.getContextLocation(), SU.getDayPart(), SU.getStepType());
		// Update needs
		Logger.logMain(minimalContext.toString());
		Needs salientNeed = getSalientNeed();
		if (salientNeed != Needs.NONE) {
			minimalContext.addNeed(salientNeed); }
		
		// Look for existing contexts
		Logger.logMain(minimalContext.toString());
		
		return minimalContext;
	}
	
	/**
	 * Returns no need or the most salient need
	 */
	public Needs getSalientNeed() {
		if (SU.getDayPart() == DayPart.MORNING || SU.getDayPart() == DayPart.AFTERNOON) {
			return Needs.CONFORM;
		}
		else if (SU.getDayPart() == DayPart.NIGHT) {
			return Needs.REST;
		}
		else if (food < 20) {
			return Needs.FOOD;
		}
		return Needs.NONE;
	}
	
	/** This function is performed half a tick before step()
	 * 
	 */
	public void stepTransfer() {
		
		Context currentContext = getCurrentContext();
		if (myKnownContexts.containsKey(currentContext.toString())) {
			Logger.logMain("The context is known");
			Context contextToDealWith = myKnownContexts.get(currentContext.toString());
		}
		else {
			Logger.logMain("The context is not known");
			myKnownContexts.put(currentContext.toString(), currentContext);
		}
		Context contextInMemory = myKnownContexts.get(currentContext.toString());
		
		//Check familiarity
		
		//Check succesfullness
	}
	
	public void stepStay() {
		
		Context currentContext = getCurrentContext();
		
		food -= 5;
	}
}