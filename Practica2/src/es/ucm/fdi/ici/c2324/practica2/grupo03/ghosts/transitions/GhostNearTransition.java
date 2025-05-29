package es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.GhostsInput;
import es.ucm.fdi.ici.fsm.Transition;
import pacman.game.Constants.GHOST;

public class GhostNearTransition implements Transition  {
	
	public static double thresold = 20;
	GHOST ghost;
	
	public GhostNearTransition(GHOST ghost) {
		super();
		this.ghost = ghost;
	}

	@Override
	public boolean evaluate(Input in) {// fantasma cerca de ti
		
		GhostsInput input = (GhostsInput)in;
		
		switch(ghost) {
			case BLINKY:
				return input.getBLINKYnearGhost() < thresold;
			case INKY:
				return input.getINKYPacmandistance() < thresold;
			case PINKY:
				return input.getPINKYnearGhost() < thresold;
			case SUE:
				return input.getSUEnearGhost() < thresold;
			default:
				return false;
		}
	}

	@Override
	public String toString() {
		return "Ghost is close to "+ghost.name();
	}	
}
