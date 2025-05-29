package es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.GhostsInput;
import pacman.game.Constants.GHOST;

public class SecurePathTransition {

    public static double thresold = 20;
	GHOST ghost;
	
	public SecurePathTransition(GHOST ghost) {
		super();
		this.ghost = ghost;
	}
        
	public boolean evaluate(Input in) {// camino seguro entre dos fantasmas
		
		GhostsInput input = (GhostsInput)in;
        
        switch(ghost) {
        case BLINKY:
            return input.isBLINKYSecurePath();
        case INKY:
            return input.isINKYSecurePath();
        case PINKY:
            return input.isPINKYSecurePath();
        case SUE:
            return input.isSUESecurePath();
        default:
            return false;
		}    
    }
}