package es.ucm.fdi.ici.c2324.practica2.grupo03.mspacman.actions;

import es.ucm.fdi.ici.Action;
import es.ucm.fdi.ici.c2324.practica2.grupo03.mspacman.Utils;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class ChaseClosestEdibleAction implements Action {
	private GHOST nearestEdible;
	
	@Override
	public String getActionId() {
		return "pacman persigue " + nearestEdible;
	}
	
	@Override
	public MOVE execute(Game game) {
		this.nearestEdible = Utils.closestEdibleGhost(game);
		if(nearestEdible != null) {
			return game.getApproximateNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), 
					game.getGhostCurrentNodeIndex(nearestEdible), game.getPacmanLastMoveMade(), DM.PATH);
		}
		return MOVE.NEUTRAL;
	}
}
