package es.ucm.fdi.ici.c2324.practica2.grupo03.mspacman.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.fsm.Transition;

public class PillsCercaTransition implements Transition {

	public PillsCercaTransition() {
		super();
	}
	@Override
	public boolean evaluate(Input in) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public String toString() {
		return "MsPacman near pill";
	}

}
