package es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.GhostsInput;
import es.ucm.fdi.ici.fsm.Transition;
import pacman.game.Constants.GHOST;

public class ClosestToPacmanAndFarFromPPTransition implements Transition  {
	
	GHOST ghost;
	
	public ClosestToPacmanAndFarFromPPTransition(GHOST ghost) {
		super();
		this.ghost = ghost;
	}

	@Override
	public boolean evaluate(Input in) {// fantasma mas cercano a Pacman
		
		GhostsInput input = (GhostsInput)in;
		ClosestGhosttoPacmanTransition close = new ClosestGhosttoPacmanTransition(ghost);
		PacManNearPPillTransition PP = new PacManNearPPillTransition();
		return close.evaluate(input) && !PP.evaluate(input);		
	}
	
	@Override
	public String toString() {
		return ghost.name()+" is the closest ghost to Pacman and far from PP";
	}
}
