package es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.GhostsInput;
import es.ucm.fdi.ici.fsm.Transition;
import pacman.game.Constants.GHOST;

public class PacmanCloseTransition implements Transition  {

	GHOST ghost;
	public static double thresold = 20;
	
	public PacmanCloseTransition(GHOST ghost) {
		super();
		this.ghost = ghost;
	}

	@Override
	public boolean evaluate(Input in) {
		
		GhostsInput input = (GhostsInput)in;
		
		switch(ghost) {
			case BLINKY:
				return input.getBLINKYPacmandistance()<thresold;
			case INKY:
				return input.getINKYPacmandistance()<thresold;
			case PINKY:
				return input.getPINKYPacmandistance()<thresold;
			case SUE:
				return input.getSUEPacmandistance()<thresold;
			default:
				return false;
		}
	}

	@Override
	public String toString() {
		return "MsPacman is close to "+ ghost.name();
	}	
}
