package es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.GhostsInput;
import es.ucm.fdi.ici.fsm.Transition;
import pacman.game.Constants.GHOST;

public class CloseToPacmanAndFarFromPPTransition  implements Transition  {
	
	GHOST ghost;
	
	public CloseToPacmanAndFarFromPPTransition(GHOST ghost) {
		super();
		this.ghost = ghost;
	}

	@Override
	public boolean evaluate(Input in) {// fantasma mas cercano a Pacman
		
		GhostsInput input = (GhostsInput)in;
		PacmanCloseTransition close = new PacmanCloseTransition(ghost);
		PacManNearPPillTransition PP = new PacManNearPPillTransition();
		return close.evaluate(input) && !PP.evaluate(input);
		
	}
	
	@Override
	public String toString() {
		return ghost.name()+" is close to Pacman and far from PP";
	}
}
