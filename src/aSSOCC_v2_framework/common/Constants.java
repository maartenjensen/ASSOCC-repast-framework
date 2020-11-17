package aSSOCC_v2_framework.common;

import java.awt.Font;

public final class Constants {

	// Repast specific
	public static final String ID_CONTEXT = "ASSOCC_v2_framework";
	public static final String ID_SPACE = "space";
	public static final String ID_GRID = "grid";
	
	public static final int GRID_CELL_SIZE = 15;
	public static final int GRID_WIDTH  = 100;
	public static final int GRID_HEIGHT = 50;
	
	// Simulation specific
	public static final float PROB_GO_TO_GROCERY = 0.1f;
	public static final int TICKS_STAY_HOME = 6;
	public static final int TICKS_STAY_GROCERY = 0; // This is set to zero so the agents move after exactly one tick out of the grocery store
	
	public static final float PROB_CLOSE_SHOP = 0.04f;
	public static final int TICKS_CLOSE_SHOP = 5;
	
	public static final int TICKS_PER_DAY = 4;

	// Visualization
	public static final int VIS_AGENT_RADIUS_NO_DISTANCE = 14;
	public static final int VIS_AGENT_RADIUS_DISTANCE = 4;
	
	public static final Font FONT_SMALL = new Font("Tahoma", Font.PLAIN , 10);
	public static final Font FONT_MEDIUM = new Font("Tahoma", Font.PLAIN , 15);
}