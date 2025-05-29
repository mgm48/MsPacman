package es.ucm.fdi.ici.c2324.practica4.grupo03.ghosts.actions;

import es.ucm.fdi.ici.rules.RulesAction;
import jess.Fact;
import jess.JessException;
import jess.Value;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class MoveAwayGhostAction implements RulesAction {

	GHOST ghost;
	GHOST ghostnear;
	public MoveAwayGhostAction(GHOST ghost) {
		this.ghost = ghost;
	}

	@Override
	public MOVE execute(Game game) {
		
		if (game.doesGhostRequireAction(ghost))        //if it requires an action
        {

	        MOVE moveghost = null;
	
	        if(ghostnear != null)
	            moveghost = game.getApproximateNextMoveAwayFromTarget(game.getGhostCurrentNodeIndex(ghost), game.getGhostCurrentNodeIndex(ghostnear), game.getGhostLastMoveMade(ghost), DM.PATH); 
	
	        if(moveghost != null)
	            return moveghost;
	        else
	            return game.getApproximateNextMoveAwayFromTarget(game.getGhostCurrentNodeIndex(ghost), game.getPacmanCurrentNodeIndex(), game.getGhostLastMoveMade(ghost), DM.PATH);
        }
		return MOVE.NEUTRAL;
    }

    @Override
	public String getActionId() {
		return ghost + "moves away from edible ghosts";
	}

	@Override
	public void parseFact(Fact actionFact) {
		try {
			Value value = actionFact.getSlotValue("ghost");
			if(value == null)
				return;
			String strategyValue = value.stringValue(null);
			ghostnear= nameGhost(String.valueOf(strategyValue));
		} catch (JessException e) {
			e.printStackTrace();
		}
	}
	
	private GHOST nameGhost(String name) {
		
		switch(name) {
		case "SUE":
			return GHOST.SUE;
		case "BLINKY":
			return GHOST.BLINKY;
		case "INKY":
			return GHOST.INKY;
		case "PINKY":
			return GHOST.PINKY;
		}
		return null;
	}
}
