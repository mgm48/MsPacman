package es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.GhostsInput;
import es.ucm.fdi.ici.fsm.Transition;
import pacman.game.Constants.GHOST;

public class noGhostEdibletransition implements Transition {
	
	public noGhostEdibletransition() {
		super();
	}

	@Override
	public boolean evaluate(Input in) {
		
		GhostsInput input = (GhostsInput)in;
		return !(input.isBLINKYedible() || input.isINKYedible() || input.isPINKYedible() || input.isSUEedible());	
	}

	@Override
	public String toString() {
		return "Any Ghost is edible";
	}	
}
