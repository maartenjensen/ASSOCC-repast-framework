package aSSOCC_v2_framework.visual;

import java.awt.Color;
import java.awt.Font;

import aSSOCC_v2_framework.DataCollector;
import aSSOCC_v2_framework.common.Constants;
import repast.simphony.visualizationOGL2D.DefaultStyleOGL2D;
import saf.v3d.scene.Position;
import saf.v3d.scene.VSpatial;

public class DataCollectorStyleOGL2D2 extends DefaultStyleOGL2D {

	@Override
	public VSpatial getVSpatial(Object agent, VSpatial spatial) {
		
	    return shapeFactory.createCircle(Constants.GRID_CELL_SIZE * 0.5f, 12);
	}

	@Override
	public Color getColor(final Object object) {

		return new Color(0xFF, 0x00, 0x00);
	}
	
	@Override
	public String getLabel(Object object) {

		if (object instanceof DataCollector) {
			final DataCollector dc = (DataCollector) object;
			return dc.getLabel();
		}
		
		return "Warning label not found for object";
	}
	
	@Override
	public Font getLabelFont(Object object) {
		
	    return Constants.FONT_SMALL;
	}
	
	@Override
	public Position getLabelPosition(Object object) {
	    return Position.SOUTH_WEST;
	}
}