package es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.GhostsInput;
import es.ucm.fdi.ici.fsm.Transition;
import pacman.game.Constants.GHOST;

public class ClosestGhosttoPacmanTransition implements Transition  {
	
	GHOST ghost;
	
	public ClosestGhosttoPacmanTransition(GHOST ghost) {
		super();
		this.ghost = ghost;
	}

	@Override
	public boolean evaluate(Input in) {// fantasma mas cercano a Pacman
		GhostsInput input = (GhostsInput)in;
		switch(ghost) {
			case BLINKY:
				return input.getBLINKYPacmandistance() < Math.min(input.getINKYPacmandistance(),Math.min(input.getPINKYPacmandistance(), input.getSUEPacmandistance()));
			case INKY:
				return input.getINKYPacmandistance() < Math.min(input.getBLINKYPacmandistance(),Math.min(input.getPINKYPacmandistance(), input.getSUEPacmandistance()));
			case PINKY:
				return input.getPINKYnearGhost() <  Math.min(input.getINKYPacmandistance(),Math.min(input.getBLINKYPacmandistance(), input.getSUEPacmandistance()));
			case SUE:
				return input.getSUEnearGhost() < Math.min(input.getINKYPacmandistance(),Math.min(input.getPINKYPacmandistance(), input.getBLINKYPacmandistance()));
			default:
				return false;
		}
		
	}
	@Override
	public String toString() {
		return ghost.name()+" is the closest ghost to Pacman";
	}
}
