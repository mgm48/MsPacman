package es.ucm.fdi.ici.c2324.practica4.grupo03.mspacman.actions;

import es.ucm.fdi.ici.Action;
import es.ucm.fdi.ici.c2324.practica4.grupo03.mspacman.Utils;
import es.ucm.fdi.ici.rules.RulesAction;
import jess.Fact;
import jess.JessException;
import jess.Value;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class RunAwayClosestChasingGhostAction implements RulesAction {
	private String ghost;

	@Override
	public String getActionId() {
		return "pacman huye " + ghost;
	}

	@Override
	public MOVE execute(Game game) {
		 if (game.isJunction(game.getPacmanCurrentNodeIndex())) {        //if it requires an action
				int pos=posGhost(ghost,game);	
				return game.getApproximateNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(),pos , game.getPacmanLastMoveMade(), DM.PATH);
			 }
		return MOVE.NEUTRAL;
	}

	@Override
	public void parseFact(Fact actionFact) {
		try {
			Value value = actionFact.getSlotValue("ghost");
			if(value == null)
				return;
			String strategyValue = value.stringValue(null);
			ghost = String.valueOf(strategyValue);
		} catch (JessException e) {
			e.printStackTrace();
		}
	}
	private int posGhost(String name,Game game) {
		switch(name) {
		case "SUE":
			return game.getGhostCurrentNodeIndex(GHOST.SUE);
		case "BLINKY":
			return game.getGhostCurrentNodeIndex(GHOST.BLINKY);
		case "INKY":
			return game.getGhostCurrentNodeIndex(GHOST.INKY);
		case "PINKY":
			return game.getGhostCurrentNodeIndex(GHOST.PINKY);
		}
		return -1;
	}

}
