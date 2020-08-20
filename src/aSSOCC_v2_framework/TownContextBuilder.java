package aSSOCC_v2_framework;

import java.util.ArrayList;

import aSSOCC_v2_framework.agents.Person;
import aSSOCC_v2_framework.common.Constants;
import aSSOCC_v2_framework.common.Logger;
import aSSOCC_v2_framework.common.SU;
import aSSOCC_v2_framework.environment.House;
import aSSOCC_v2_framework.environment.Shop;
import repast.simphony.context.Context;
import repast.simphony.context.space.continuous.ContinuousSpaceFactoryFinder;
import repast.simphony.context.space.grid.GridFactoryFinder;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.parameter.Parameters;
import repast.simphony.random.RandomHelper;
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

		CreateWorld();
		
		if (RunEnvironment.getInstance().isBatch()) {
			RunEnvironment.getInstance().endAt(20);
		}

		return context;
	}
	
	@ScheduledMethod(start = 1, interval = 1, priority = 0)
	public void mainStep() {
		
		Logger.logMain("Main step");
		
		// Make one person change the social distancing at random (just for testing)
		ArrayList<Person> randomPersons = SU.getObjectsAllRandom(Person.class);
		randomPersons.get(RandomHelper.nextIntFromTo(0, randomPersons.size() - 1)).randomSocialDistancing();
	}
	
	/**
	 * Spawn houses, supermarket, persons and context and move them to 
	 * the right place in the grid.
	 */
	public void CreateWorld() {
		
		Parameters params = RunEnvironment.getInstance().getParameters();
		int houseCount = (Integer) params.getValue("house_count");
		for (int i = 0; i < houseCount; i++) {
			new House(SU.getNewGatheringPointId(), new GridPoint(2, 2 + i * 5), 5, 5);
		}
		
		new Shop(SU.getNewGatheringPointId(), new GridPoint(30, 25), 15, 15);
		
		int personCount = (Integer) params.getValue("person_count");
		for (int i = 0; i < personCount; i++) {
			new Person(SU.getNewPersonId());
		}

		for (Object obj : SU.getContext()) {
			NdPoint pt = SU.getSpace().getLocation(obj);
			SU.getGrid().moveTo(obj, (int) pt.getX(), (int) pt.getY());
		}
	}
	
	private ContinuousSpace<Object> createContinuousSpace(final Context<Object> context) {
		
		final ContinuousSpace<Object> space = ContinuousSpaceFactoryFinder.createContinuousSpaceFactory(null).
				createContinuousSpace( 	Constants.ID_SPACE, context,
										new RandomCartesianAdder<Object>(),
										new repast.simphony.space.continuous.BouncyBorders(),
										50, 50);
		return space;
	}
	
	private Grid<Object> createGrid(final Context<Object> context) {
		
		final Grid<Object> grid = GridFactoryFinder.createGridFactory(null).createGrid(
										Constants.ID_GRID, context,
										new GridBuilderParameters<Object>(
										new repast.simphony.space.grid.BouncyBorders(),
										new SimpleGridAdder<Object>(), true,
										50, 50));
		return grid;
	}
}
