package es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.GhostsInput;
import es.ucm.fdi.ici.fsm.Transition;
import pacman.game.Constants.GHOST;

public class NoClosestGhostToPacmanTransition implements Transition  {
	
	GHOST ghost;
	
	public NoClosestGhostToPacmanTransition(GHOST ghost) {
		super();
		this.ghost = ghost;
	}

	@Override
	public boolean evaluate(Input in) {// fantasma mas cercano a Pacman
		
		GhostsInput input = (GhostsInput)in;
		ClosestGhosttoPacmanTransition close = new ClosestGhosttoPacmanTransition(ghost);
		return !close.evaluate(input);
	}
	
	@Override
	public String toString() {
		return ghost.name()+" is not the closest ghost to Pacman";
	}
}
