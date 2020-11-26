package aSSOCC_v2_framework.visual;

import java.awt.Color;
import java.awt.Font;

import aSSOCC_v2_framework.common.Constants;
import aSSOCC_v2_framework.environment.Location;
import repast.simphony.visualizationOGL2D.DefaultStyleOGL2D;
import saf.v3d.scene.Position;
import saf.v3d.scene.VSpatial;

public class LocationStyleOGL2D extends DefaultStyleOGL2D {

	@Override
	public VSpatial getVSpatial(Object agent, VSpatial spatial) {
		
		if (agent instanceof Location) {
			
			Location gatheringPoint = (Location) agent;
			return shapeFactory.createRectangle(Constants.GRID_CELL_SIZE * gatheringPoint.getWidth(), Constants.GRID_CELL_SIZE * gatheringPoint.getHeight());
		}
	    return shapeFactory.createRectangle(Constants.GRID_CELL_SIZE, Constants.GRID_CELL_SIZE);
	}

	@Override
	public Color getColor(final Object object) {
		if (object instanceof Location) {
			Location gp = (Location) object;
			if (gp.isOpen())
				return new Color(0xFF, 0xF4, 0xD1);
			else
				return new Color(0xFF, 0xD1, 0xD1);
		}
		return new Color(0xFF, 0x00, 0x00);
	}
	
	@Override
	public String getLabel(Object object) {

		if (object instanceof Location) {
			final Location gp = (Location) object;
			return gp.getLabel();
		}
		
		return "Warning label not found for object";
	}
	
	@Override
	public Font getLabelFont(Object object) {
		
	    return Constants.FONT_MEDIUM;
	}
	
	@Override
	public Position getLabelPosition(Object object) {
	    return Position.EAST;
	}
}