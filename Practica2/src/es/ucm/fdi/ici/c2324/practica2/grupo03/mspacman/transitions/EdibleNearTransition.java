package es.ucm.fdi.ici.c2324.practica2.grupo03.mspacman.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2324.practica2.grupo03.mspacman.MsPacManInput;
import es.ucm.fdi.ici.fsm.Transition;

public class EdibleNearTransition implements Transition {

	public static double thresold = 25;
	
	public EdibleNearTransition() {
		super();
	}

	@Override
	public boolean evaluate(Input in) {
		MsPacManInput input = (MsPacManInput) in;
		return input.getClosestEdibleDistance() < thresold;
	}
	
	@Override
	public String toString() {
		return "Closest Edible Ghost near";
	}


}
