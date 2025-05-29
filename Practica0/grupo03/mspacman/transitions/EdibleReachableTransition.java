package es.ucm.fdi.ici.c2324.practica2.grupo03.mspacman.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2324.practica2.grupo03.mspacman.MsPacManInput;
import es.ucm.fdi.ici.fsm.Transition;

public class EdibleReachableTransition implements Transition {

	public double thresold = 30;
	
	public EdibleReachableTransition() {
		super();
	}

	@Override
	public boolean evaluate(Input in) {
		MsPacManInput input = (MsPacManInput) in;
		return input.isClosestEdibleReachable() && input.getClosestEdibleDistance() < thresold;
	}
	
	
	/*public String toString() {
		return "Closest Edible Ghost reachable";
	}*/

}
