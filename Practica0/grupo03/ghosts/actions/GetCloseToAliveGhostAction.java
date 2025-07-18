package es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.actions;

import es.ucm.fdi.ici.Action;
import es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.Utiles;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class GetCloseToAliveGhostAction implements Action {
	
	GHOST ghost;
	public GetCloseToAliveGhostAction(GHOST ghost) {
		this.ghost = ghost;
	}

	@Override
	public MOVE execute(Game game) {
		
		Utiles.emptySection(ghost);
		
		 if (game.doesGhostRequireAction(ghost))        //if it requires an action
	        {
			 double distance, distancemin = Double.MAX_VALUE;
				GHOST ghostnear = null;

				for(GHOST g: GHOST.values()) {
					
					if(g!= ghost && game.getGhostEdibleTime(g)<=0) {
						
						distance = game.getDistance(game.getGhostCurrentNodeIndex(ghost),game.getGhostCurrentNodeIndex(g), game.getGhostLastMoveMade(ghost),DM.PATH);
						if(distancemin > distance)
							ghostnear = g;
						distancemin = Math.min(distancemin, distance);
					}				
				}	
					
			 return game.getApproximateNextMoveTowardsTarget(game.getGhostCurrentNodeIndex(ghost), game.getGhostCurrentNodeIndex(ghostnear), game.getGhostLastMoveMade(ghost), DM.PATH);
	        }
		return MOVE.NEUTRAL;
	}

	@Override
	public String getActionId() {
		
		return ghost+" moves to alive ghost";
	}
}
