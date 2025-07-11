package es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.GhostsInput;
import es.ucm.fdi.ici.fsm.Transition;
import pacman.game.Constants.GHOST;

public class GhostNotEdibletransition implements Transition  {

	GHOST ghost;
	
	public GhostNotEdibletransition(GHOST ghost) {
		super();
		this.ghost = ghost;
	}

	@Override
	public boolean evaluate(Input in) {
		
		GhostsInput input = (GhostsInput)in;
		
		switch(ghost) {
			case BLINKY:
				return !input.isBLINKYedible();
			case INKY:
				return !input.isINKYedible();
			case PINKY:
				return !input.isPINKYedible();
			case SUE:
				return !input.isSUEedible();
			default:
				return false;
		}
	}

	@Override
	public String toString() {
		return "Ghost is not edible";
	}	
}