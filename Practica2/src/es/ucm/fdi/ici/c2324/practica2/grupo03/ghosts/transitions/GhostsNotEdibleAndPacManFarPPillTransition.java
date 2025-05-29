package es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.GhostsInput;
import es.ucm.fdi.ici.fsm.Transition;
import pacman.game.Constants.GHOST;

public class GhostsNotEdibleAndPacManFarPPillTransition implements Transition {

	GHOST ghost;
	
	public GhostsNotEdibleAndPacManFarPPillTransition(GHOST ghost) {
		super();
		this.ghost = ghost;
	}
	
	@Override
	public boolean evaluate(Input in) {
		
		GhostsInput input = (GhostsInput)in;
		GhostsEdibleTransition edible = new GhostsEdibleTransition(ghost);
		PacManNearPPillTransition near = new PacManNearPPillTransition();
		return !edible.evaluate(input) && !near.evaluate(input);
	}

	@Override
	public String toString() {
		return ghost.name()+" not edible and MsPacman far PPill";
	}
}