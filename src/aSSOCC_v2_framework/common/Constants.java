package aSSOCC_v2_framework.common;

import java.awt.Font;

public final class Constants {

	// Repast specific
	public static final String ID_CONTEXT = "ASSOCC_v2_framework";
	public static final String ID_SPACE = "space";
	public static final String ID_GRID = "grid";
	
	public static final int GRID_CELL_SIZE = 15;
	
	// Simulation specific
	public static final float PROB_GO_TO_GROCERY = 0.1f;
	public static final int TICKS_STAY_HOME = 5;
	public static final int TICKS_STAY_GROCERY = 3;
	
	public static final float PROB_CLOSE_SHOP = 0.04f;
	public static final int TICKS_CLOSE_SHOP = 5;

	// Visualization
	public static final int VIS_AGENT_RADIUS_NO_DISTANCE = 14;
	public static final int VIS_AGENT_RADIUS_DISTANCE = 4;
	
	public static final Font FONT_SMALL = new Font("Tahoma", Font.PLAIN , 10);
}