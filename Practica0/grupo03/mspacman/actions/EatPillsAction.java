package es.ucm.fdi.ici.c2324.practica2.grupo03.mspacman.actions;

import es.ucm.fdi.ici.Action;
import pacman.game.Constants.DM;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class EatPillsAction implements Action {
	private int closestPillIndex;
	
	
	@Override
	public String getActionId() {
		return "pacman yendo hacia " + closestPillIndex;
	}

	@Override
	public MOVE execute(Game game) {
		closestPillIndex = game.getClosestNodeIndexFromNodeIndex(game.getPacmanCurrentNodeIndex(), game.getActivePillsIndices(), DM.PATH);
		return game.getApproximateNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), 
				closestPillIndex , game.getPacmanLastMoveMade(), DM.PATH);
	}

}
