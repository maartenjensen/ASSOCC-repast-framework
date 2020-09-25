package aSSOCC_v2_framework;

import aSSOCC_v2_framework.agents.Person;
import aSSOCC_v2_framework.common.Constants;
import aSSOCC_v2_framework.common.Logger;
import aSSOCC_v2_framework.common.SU;
import aSSOCC_v2_framework.decisionMaking.ContextLocation;
import aSSOCC_v2_framework.environment.GatheringPoint;
import aSSOCC_v2_framework.environment.House;
import aSSOCC_v2_framework.environment.Shop;
import repast.simphony.context.Context;
import repast.simphony.context.space.continuous.ContinuousSpaceFactoryFinder;
import repast.simphony.context.space.grid.GridFactoryFinder;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.parameter.Parameters;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.continuous.RandomCartesianAdder;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridBuilderParameters;
import repast.simphony.space.grid.GridPoint;
import repast.simphony.space.grid.SimpleGridAdder;

public class TownContextBuilder implements ContextBuilder<Object> {
	
	@Override
	public Context<Object> build(Context<Object> context) {
		
		context.setId(Constants.ID_CONTEXT);
		context.add(this); //this can be removed if there is no ScheduledMethod in this contextbuilder
		
		SU.resetGatheringPointId();
		SU.setContext(context);
		createContinuousSpace(context);
		createGrid(context);

		DataCollector dataCollector = new DataCollector(context);
		SU.setDataCollector(dataCollector);
		
		CreateWorld();
		
		if (RunEnvironment.getInstance().isBatch()) {
			RunEnvironment.getInstance().endAt(20);
		}

		moveGatheringPoints();
		return context;
	}
	
	/**
	 * To make this step more efficient we could decide to only take the subset of agets that are actually moving to the new place
	 * and letting the agents that do not move do nothing.
	 */
	@ScheduledMethod(start = 1, interval = 1, priority = 0)
	public void mainStep() {
		
		SU.getDataCollector().resetTemporaryVariables();
		
		double tick = RunEnvironment.getInstance().getCurrentSchedule().getTickCount();
		Logger.logMain("-------------------------- [ Step " + tick + ": " + SU.getDayAndTime() + " : social distance ] --------------------------");
		
		SU.getObjectsAllRandom(Shop.class).forEach(a -> a.step());
		SU.getObjectsAllRandom(Person.class).forEach(a -> a.step());
	}
	
	/**
	 * In this step the agents determine if they move to another place
	 */
	@ScheduledMethod(start = 0.5, interval = 1, priority = 0)
	public void smallSteps() {
		
		double tick = RunEnvironment.getInstance().getCurrentSchedule().getTickCount();
		Logger.logMain("-------------------------- [ Step " + tick + ": " + SU.getDayAndTime() + " : move to ] --------------------------");
		
		SU.getObjectsAllRandom(Shop.class).forEach(a -> a.step());
		SU.getObjectsAllRandom(Person.class).forEach(a -> a.stepGoTo());
	}

	/**
	 * Spawn houses, supermarket, persons and context and move them to 
	 * the right place in the grid.
	 */
	public void CreateWorld() {
		
		Parameters params = RunEnvironment.getInstance().getParameters();
		int shopCount = (Integer) params.getValue("shop_count");
		for (int i = 0; i < shopCount; i++) {
			Shop tShop = new Shop(SU.getNewGatheringPointId(), new GridPoint(30, 15 + i * 22), 21, 21);
			tShop.moveTo(tShop.getLocation());
		}
		
		int houseCount = (Integer) params.getValue("house_count");
		for (int i = 0; i < houseCount; i++) {
			House tHouse = new House(SU.getNewGatheringPointId(), new GridPoint(5, 5 + i * 9), 7, 7);
			tHouse.moveTo(tHouse.getLocation());
		}
		
		int personCount = (Integer) params.getValue("person_count");
		for (int i = 0; i < personCount; i++) {
			new Person(SU.getNewPersonId());
		}

		for (Object obj : SU.getContext()) {
			NdPoint pt = SU.getSpace().getLocation(obj);
			SU.getGrid().moveTo(obj, (int) pt.getX(), (int) pt.getY());
		}
	}
	
	/**
	 * This function exists to fix a bug where the houses and shop are not
	 * properly moved in the context creation function
	 * @return
	 */
	private void moveGatheringPoints() {
		
		SU.getObjectsAll(GatheringPoint.class).forEach(g -> g.moveTo(g.getLocation()));
		SU.getObjectsAll(Person.class).forEach(p -> p.moveToGatheringPoint(ContextLocation.HOME));
		SU.getDataCollector().moveTo(new GridPoint(49, 49));
	}
	
	private ContinuousSpace<Object> createContinuousSpace(final Context<Object> context) {
		
		Parameters params = RunEnvironment.getInstance().getParameters();
		int shopCount = (Integer) params.getValue("shop_count");
		int houseCount = (Integer) params.getValue("house_count");
		int height = Math.max(5 + shopCount * 22, 5 + houseCount * 9);
		
		final ContinuousSpace<Object> space = ContinuousSpaceFactoryFinder.createContinuousSpaceFactory(null).
				createContinuousSpace( 	Constants.ID_SPACE, context,
										new RandomCartesianAdder<Object>(),
										new repast.simphony.space.continuous.BouncyBorders(),
										50, height);
		return space;
	}
	
	private Grid<Object> createGrid(final Context<Object> context) {
		
		Parameters params = RunEnvironment.getInstance().getParameters();
		int shopCount = (Integer) params.getValue("shop_count");
		int houseCount = (Integer) params.getValue("house_count");
		int height = Math.max(5 + shopCount * 22, 5 + houseCount * 9);
		
		final Grid<Object> grid = GridFactoryFinder.createGridFactory(null).createGrid(
										Constants.ID_GRID, context,
										new GridBuilderParameters<Object>(
										new repast.simphony.space.grid.BouncyBorders(),
										new SimpleGridAdder<Object>(), true,
										50, height));
		return grid;
	}
	
}