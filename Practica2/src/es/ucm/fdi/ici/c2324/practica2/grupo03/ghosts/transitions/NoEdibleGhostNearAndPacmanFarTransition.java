package es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.GhostsInput;
import es.ucm.fdi.ici.fsm.Transition;
import pacman.game.Constants.GHOST;

public class NoEdibleGhostNearAndPacmanFarTransition implements Transition {

	GHOST ghost;
	
	public NoEdibleGhostNearAndPacmanFarTransition(GHOST ghost) {
		super();
		this.ghost = ghost;
	}
	
	@Override
	public boolean evaluate(Input in) {
		
		GhostsInput input = (GhostsInput)in;
		EdibleGhostNearTransition ediblenear = new EdibleGhostNearTransition(ghost);
		PacmanCloseTransition pacmannear = new PacmanCloseTransition(ghost);
		return !ediblenear.evaluate(input) && !pacmannear.evaluate(input);
	}

	@Override
	public String toString() {
		return "Nonedible ghosts near " + ghost.name() + " and MsPacman far";
	}
}