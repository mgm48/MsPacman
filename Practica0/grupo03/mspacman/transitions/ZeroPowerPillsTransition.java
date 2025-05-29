package es.ucm.fdi.ici.c2324.practica2.grupo03.mspacman.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2324.practica2.grupo03.mspacman.MsPacManInput;
import es.ucm.fdi.ici.fsm.Transition;

public class ZeroPowerPillsTransition implements Transition {

	public ZeroPowerPillsTransition() {
		super();
	}
	@Override
	public boolean evaluate(Input in) {
		MsPacManInput input = (MsPacManInput) in;
		return input.getActivePowerPillsNumber() == 0;
	}

	@Override
	public String toString() {
		return "no active Power Pills left";
	}
}
