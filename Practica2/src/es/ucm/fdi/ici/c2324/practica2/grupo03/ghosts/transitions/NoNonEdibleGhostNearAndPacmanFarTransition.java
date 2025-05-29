package es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.GhostsInput;
import es.ucm.fdi.ici.fsm.Transition;
import pacman.game.Constants.GHOST;

public class NoNonEdibleGhostNearAndPacmanFarTransition implements Transition {

	GHOST ghost;
	
	public NoNonEdibleGhostNearAndPacmanFarTransition(GHOST ghost) {
		super();
		this.ghost = ghost;
	}
	
	@Override
	public boolean evaluate(Input in) {
		
		GhostsInput input = (GhostsInput)in;
		NonEdibleGhostNearTransition Nonediblenear = new NonEdibleGhostNearTransition(ghost);
		PacmanCloseTransition pacmannear = new PacmanCloseTransition(ghost);
		return !Nonediblenear.evaluate(input) && !pacmannear.evaluate(input);
	}

//	@Override
//	public String toString() {
//		return "Nonedible ghosts near " + ghost.name() + " and MsPacman far";
//	}
}