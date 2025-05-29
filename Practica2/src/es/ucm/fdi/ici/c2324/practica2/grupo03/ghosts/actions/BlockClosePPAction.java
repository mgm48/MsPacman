package es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.actions;

import es.ucm.fdi.ici.Action;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class BlockClosePPAction implements Action {
	
	GHOST ghost;
	public BlockClosePPAction(GHOST ghost) {
		this.ghost = ghost;
	}

	@Override
	public MOVE execute(Game game) {
		
		 if (game.doesGhostRequireAction(ghost)) {       //if it requires an action
	        
			int[] pPills = game.getActivePowerPillsIndices();
			double d = Double.MAX_VALUE;
			int PP=-1;
				
				for(int i = 0; i < game.getNumberOfActivePowerPills(); i++){
						
						if(game.getDistance(pPills[i], game.getPacmanCurrentNodeIndex(), DM.PATH) <= d) {
							d = game.getDistance(pPills[i], game.getPacmanCurrentNodeIndex(), DM.PATH);
							PP=pPills[i];
						}
					}
				
				return game.getApproximateNextMoveTowardsTarget(game.getGhostCurrentNodeIndex(ghost),PP , game.getGhostLastMoveMade(ghost), DM.PATH);
	        }
		 
		return MOVE.NEUTRAL;
	}

	@Override
	public String getActionId() {
		return ghost + " blocks closest powerpill";
	}
}