package aSSOCC_v2_framework.environment;

public enum DayPart {

	MORNING(0), AFTERNOON(1), EVENING(2), NIGHT(3);
	
	private DayPart(final int tick_n) {
		this.tick_n = tick_n;
	}
	
	private int tick_n;
	
	public int tickNumber() {
		return tick_n;
	}
	
	public static DayPart getDayPart(int tick_n) {
		switch (tick_n) {
		case 0:
			return DayPart.MORNING;
		case 1:
			return DayPart.AFTERNOON;
		case 2:
			return DayPart.EVENING;
		case 3:
			return DayPart.NIGHT;
		default:
			return DayPart.MORNING;
		}
	}
}
