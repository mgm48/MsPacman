package es.ucm.fdi.ici.c2324.practica2.grupo03.mspacman.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2324.practica2.grupo03.mspacman.MsPacManInput;
import es.ucm.fdi.ici.fsm.Transition;

public class ChasingNearTransition implements Transition {

	public static double thresold = 25;
	
	public ChasingNearTransition() {
		super();
	}

	@Override
	public boolean evaluate(Input in) {
		MsPacManInput input = (MsPacManInput) in;
		return input.getClosestChasingDistance() < thresold;
	}

	@Override
	public String toString() {
		return "MsPacman near Chasing Ghost";
	}
}
