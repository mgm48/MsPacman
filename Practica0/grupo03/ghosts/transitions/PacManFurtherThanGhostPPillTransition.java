package es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.GhostsInput;
import es.ucm.fdi.ici.fsm.Transition;
import pacman.game.Constants.GHOST;

public class PacManFurtherThanGhostPPillTransition implements Transition {

	GHOST ghost;
	
	public PacManFurtherThanGhostPPillTransition(GHOST ghost) {
		super();
		this.ghost = ghost;
	}

	@Override
	public boolean evaluate(Input in) {
		
		GhostsInput input = (GhostsInput) in;// fantasma más cerca que pacman a powerpill 
		return input.getMinPacmanDistancePPill() > input.getMinGhostDistancePPill() && ghost == input.getClosestGhosttoPill();
	}

//	@Override
//	public String toString() {
//		return "Ghost closest to PPill";
//	}	
}