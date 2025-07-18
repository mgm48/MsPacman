package es.ucm.fdi.ici.c2324.practica2.grupo03.mspacman.actions;

import es.ucm.fdi.ici.Action;
import es.ucm.fdi.ici.c2324.practica2.grupo03.mspacman.Utils;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class RunAwayClosestChasingGhostAction implements Action {
	private GHOST closestChasing;
		
	@Override
	public String getActionId() {
		return "pacman huye " + closestChasing;
	}

	@Override
	public MOVE execute(Game game) {
		this.closestChasing = Utils.closestChasingGhost(game);
		if(closestChasing != null) {
			return game.getApproximateNextMoveAwayFromTarget(game.getPacmanCurrentNodeIndex(), 
					game.getGhostCurrentNodeIndex(closestChasing), game.getPacmanLastMoveMade(), DM.PATH);
		}
		return MOVE.NEUTRAL;
	}

}
