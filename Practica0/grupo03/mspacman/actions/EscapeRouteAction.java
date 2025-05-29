package es.ucm.fdi.ici.c2324.practica2.grupo03.mspacman.actions;

import es.ucm.fdi.ici.Action;
import es.ucm.fdi.ici.c2324.practica2.grupo03.mspacman.Utils;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class EscapeRouteAction implements Action {
	private GHOST closestChasing;
	private int escapeRoute;
	
	@Override
	public String getActionId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MOVE execute(Game game) {
		boolean escape= false;
		this.closestChasing = Utils.closestChasingGhost(game);
		int pc = game.getPacmanCurrentNodeIndex();
		int[] path =game.getShortestPath(pc, game.getGhostCurrentNodeIndex(closestChasing));
		
		if(game.isJunction(pc)) {
			return game.getApproximateNextMoveAwayFromTarget(pc, game.getGhostCurrentNodeIndex(closestChasing), 
					game.getPacmanLastMoveMade(), DM.PATH);
		}
		else {
			for(int i : path) {
				if(i == game.getGhostCurrentNodeIndex(closestChasing)) {
					break;
				}
				if(game.getDistance(pc, i, DM.PATH) < game.getDistance(game.getGhostCurrentNodeIndex(closestChasing), i, DM.PATH) 
					&& game.isJunction(i)) {
					escape = true;
					escapeRoute = i;
				}
			}
			if(!escape) {
				return game.getApproximateNextMoveAwayFromTarget(pc, game.getGhostCurrentNodeIndex(closestChasing), 
						game.getPacmanLastMoveMade(), DM.PATH);
			}
			
		}
		
		
		return game.getApproximateNextMoveTowardsTarget(pc,  escapeRoute, 
				game.getPacmanLastMoveMade(), DM.PATH);
	
	}

}
