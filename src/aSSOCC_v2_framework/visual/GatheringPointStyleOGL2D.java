package aSSOCC_v2_framework.visual;

import java.awt.Color;

import aSSOCC_v2_framework.common.Constants;
import aSSOCC_v2_framework.environment.GatheringPoint;
import repast.simphony.visualizationOGL2D.DefaultStyleOGL2D;
import saf.v3d.scene.VSpatial;

public class GatheringPointStyleOGL2D extends DefaultStyleOGL2D {

	@Override
	public VSpatial getVSpatial(Object agent, VSpatial spatial) {
		
		if (agent instanceof GatheringPoint) {
			
			GatheringPoint gatheringPoint = (GatheringPoint) agent;
			return shapeFactory.createRectangle(Constants.GRID_CELL_SIZE * gatheringPoint.getWidth(), Constants.GRID_CELL_SIZE * gatheringPoint.getHeight());
		}
	    return shapeFactory.createRectangle(Constants.GRID_CELL_SIZE, Constants.GRID_CELL_SIZE);
	}

	@Override
	public Color getColor(final Object agent) {
		return new Color(0xE4, 0xC8, 0xA0);
	}
}