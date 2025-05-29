package es.ucm.fdi.ici.c2324.practica2.grupo03.mspacman.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2324.practica2.grupo03.mspacman.MsPacManInput;
import es.ucm.fdi.ici.fsm.Transition;

public class CloseToPillsTransition implements Transition {

	public static int threshold = 25;
	public static int pills_n = 5;
	
	
	public CloseToPillsTransition() {
		super();
	}
	@Override
	public boolean evaluate(Input in) {
		MsPacManInput input = (MsPacManInput) in;
		return (input.getClosestPillDistance() < threshold && input.numberOfPillsNear(threshold) > pills_n)
				|| input.getActivePillsNumber() <= pills_n;
	}
	
	/*@Override
	public String toString() {
		return "MsPacman near pills";
	}*/

}
