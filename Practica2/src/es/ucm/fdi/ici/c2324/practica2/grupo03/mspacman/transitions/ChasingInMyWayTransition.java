package es.ucm.fdi.ici.c2324.practica2.grupo03.mspacman.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2324.practica2.grupo03.mspacman.MsPacManInput;
import es.ucm.fdi.ici.fsm.Transition;

public class ChasingInMyWayTransition implements Transition {
	private String opt;
	
	public ChasingInMyWayTransition(String opt) {
		super();
		this.opt = opt;
	}

	@Override
	public boolean evaluate(Input in) {
		MsPacManInput input = (MsPacManInput) in;
		return input.ghostsInMyWay(opt);
	}

	/*@Override
	public String toString() {
		return "Chasing Ghost blocking path";
	}*/
}
