package es.ucm.fdi.ici.c2324.practica2.grupo03.mspacman.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2324.practica2.grupo03.mspacman.MsPacManInput;
import es.ucm.fdi.ici.fsm.Transition;

public class NoChasingNearTransition implements Transition {

public static double threshold = 25;
	
	public NoChasingNearTransition() {
		super();
	}

	@Override
	public boolean evaluate(Input in) {
		MsPacManInput input = (MsPacManInput) in;
		return input.getClosestChasingDistance() > threshold;
	}

	@Override
	public String toString() {
		return "MsPacman not near Chasing Ghost";
	}

}
