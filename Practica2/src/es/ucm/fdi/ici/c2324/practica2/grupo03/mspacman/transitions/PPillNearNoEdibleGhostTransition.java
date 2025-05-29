package es.ucm.fdi.ici.c2324.practica2.grupo03.mspacman.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2324.practica2.grupo03.mspacman.MsPacManInput;
import es.ucm.fdi.ici.fsm.Transition;

public class PPillNearNoEdibleGhostTransition implements Transition {
	public PPillNearNoEdibleGhostTransition() {
		super();
	}

	@Override
	public boolean evaluate(Input in) {
		MsPacManInput input = (MsPacManInput) in;
		EdibleReachableTransition reachable = new EdibleReachableTransition();
		PPillNearTransition ppill = new PPillNearTransition();
		
		return !reachable.evaluate(input) && ppill.evaluate(input);
	}
	
	@Override
	public String toString() {
		return "PPill near, no Edible Ghost reachable";
	}

}
