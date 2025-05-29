package es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.actions;

import es.ucm.fdi.ici.Action;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class BlockNextJuntionAction implements Action {
	
	GHOST ghost;
	public BlockNextJuntionAction(GHOST ghost) {
		this.ghost = ghost;
	}

	@Override
	public MOVE execute(Game game) {
		
		if (game.doesGhostRequireAction(ghost)) {        //if it requires an action
		        
			int[] cruces = game.getJunctionIndices();
	  		double d = Double.MAX_VALUE;
	  		int res = game.getPacmanCurrentNodeIndex();
	  		
	  		for(int i = 0; i < cruces.length; i++){
	  			
	  			if(game.getDistance(cruces[i], game.getPacmanCurrentNodeIndex(), DM.PATH) <= d) {
	  				
	  				d = game.getDistance(cruces[i], game.getPacmanCurrentNodeIndex(), DM.PATH);
	  				res=cruces[i];
	  			}
	  		}
	  		
	  		return game.getApproximateNextMoveTowardsTarget(game.getGhostCurrentNodeIndex(ghost), res, game.getGhostLastMoveMade(ghost), DM.PATH);
	    }
		 
		 return MOVE.NEUTRAL;
	}

	@Override
	public String getActionId() {
		return ghost + "blocks next junction";
	}
}
