package es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.GhostsInput;
import es.ucm.fdi.ici.fsm.Transition;
import pacman.game.Constants.GHOST;

public class NonEdibleGhostNearTransition implements Transition {

    GHOST ghost;
    
	public NonEdibleGhostNearTransition(GHOST ghost) {
		super();
		this.ghost = ghost;
	}
	
	public boolean evaluate(Input in) {
		
		GhostsInput input = (GhostsInput)in;
		GhostNearTransition GhostNear = new GhostNearTransition(ghost);
		GhostsEdibleTransition edible = new GhostsEdibleTransition(ghost);
		return !edible.evaluate(input) && GhostNear.evaluate(input);
	}

//	@Override
//	public String toString() {
//		return "Edible ghost is near to " + ghost.name();
//	} 
}