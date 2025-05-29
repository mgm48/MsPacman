package es.ucm.fdi.ici.c2324.practica2.grupo03.mspacman.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2324.practica2.grupo03.mspacman.MsPacManInput;
import es.ucm.fdi.ici.fsm.Transition;

public class NotNearPillsTransition implements Transition {
	public static int pills_n = 10;
	
	
	public NotNearPillsTransition() {
		super();
	}
	@Override
	public boolean evaluate(Input in) {
		MsPacManInput input = (MsPacManInput) in;
		CloseToPillsTransition pills = new CloseToPillsTransition();
		return !pills.evaluate(input);
	}
	
	@Override
	public String toString() {
		return "MsPacman not near pills";
	}


}
