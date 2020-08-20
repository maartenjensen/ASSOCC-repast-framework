package aSSOCC_v2_framework.visual;

import java.awt.Color;

import aSSOCC_v2_framework.agents.Person;
import repast.simphony.visualizationOGL2D.DefaultStyleOGL2D;

public class PersonStyleOGL2D extends DefaultStyleOGL2D {

	@Override
	public Color getColor(final Object agent) {
		
		if (agent instanceof Person) {
			final Person person = (Person) agent;
			if (person.getSocialDistancing()) {
				return new Color(0x00, 0x00, 0xFF);
			}
			else {
				return new Color(0x00, 0xFF, 0x00);
			}
		}
		return new Color(0x00, 0x00, 0x00);
	}
}