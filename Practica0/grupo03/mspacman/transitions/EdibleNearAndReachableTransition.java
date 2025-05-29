package es.ucm.fdi.ici.c2324.practica2.grupo03.mspacman.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2324.practica2.grupo03.mspacman.MsPacManInput;
import es.ucm.fdi.ici.fsm.Transition;

public class EdibleNearAndReachableTransition implements Transition {

	public static double thresold = 25;
	
	public EdibleNearAndReachableTransition() {
		super();
	}

	@Override
	public boolean evaluate(Input in) {
		MsPacManInput input = (MsPacManInput) in;
		EdibleNearTransition near = new EdibleNearTransition();
		EdibleReachableTransition reachable = new EdibleReachableTransition();
		return reachable.evaluate(input) && near.evaluate(input);
	}
	
	@Override
	public String toString() {
		return "Nearest Edible Ghost near and reachable";
	}



}
