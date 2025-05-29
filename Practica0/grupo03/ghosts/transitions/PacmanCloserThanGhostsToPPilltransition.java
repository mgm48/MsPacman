package es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.GhostsInput;
import es.ucm.fdi.ici.fsm.Transition;

public class PacmanCloserThanGhostsToPPilltransition implements Transition {

	public PacmanCloserThanGhostsToPPilltransition() {
		super();
	}

	@Override
	public boolean evaluate(Input in) {
		
		GhostsInput input = (GhostsInput) in;// fantasma más cerca que 
		return input.getMinPacmanDistancePPill() < input.getMinGhostDistancePPill();
	}

	@Override
	public String toString() {
		return "Ghost closest to PPill";
	}	
}