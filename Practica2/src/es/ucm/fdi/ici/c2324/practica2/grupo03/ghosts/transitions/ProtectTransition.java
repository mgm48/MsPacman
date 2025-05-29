package es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.GhostsInput;
import es.ucm.fdi.ici.fsm.Transition;
import pacman.game.Constants.GHOST;

public class ProtectTransition implements Transition {

	GHOST ghost;
	
	public ProtectTransition(GHOST ghost) {
		super();
		this.ghost = ghost;
	}
	
	@Override
	public boolean evaluate(Input in) {
		
		GhostsInput input = (GhostsInput)in;
		GhostsEdibleTransition edible = new GhostsEdibleTransition(ghost);
		IsAnyEdibleTransition anyEdible = new IsAnyEdibleTransition();
		return !edible.evaluate(input) && anyEdible.evaluate(input);
	}

	@Override
	public String toString() {
		return ghost.name()+" Protects edible ghost";
	}	
}
