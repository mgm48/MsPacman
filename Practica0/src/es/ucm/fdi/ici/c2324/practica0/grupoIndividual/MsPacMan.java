package es.ucm.fdi.ici.c2324.practica0.grupoIndividual;

import pacman.controllers.PacmanController;
import pacman.game.Constants;
import pacman.game.Game;
import pacman.game.GameView;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public class MsPacMan extends PacmanController {

    public MsPacMan() {
    }

    @Override
    public MOVE getMove(Game game, long dueTime) {
    	
    	int limit = 40;
    	
    	GHOST g = NearestChasingGhost(game, limit);
    	
    	if(g != null)
        	return game.getApproximateNextMoveAwayFromTarget(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(g), game.getPacmanLastMoveMade(), DM.PATH);	

    	g = NearestEdibleGhost(game, limit); 
    	
    	if(g != null)
        	return game.getApproximateNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(g), game.getPacmanLastMoveMade(), DM.PATH);
    		
    	int pill = getNearestPill(game);
    	return game.getApproximateNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), pill, game.getPacmanLastMoveMade(), DM.PATH);	
    }
    
    private GHOST NearestChasingGhost(Game game, int limit){
    	
    	GHOST closestGhost = null;
    	double d = limit;
    	for(Constants.GHOST g: Constants.GHOST.values()){
    		if(!game.wasPowerPillEaten())
	    		if(game.getDistance(game.getGhostCurrentNodeIndex(g), game.getPacmanCurrentNodeIndex(), DM.PATH) <= d) {
	    			d = game.getDistance(game.getGhostCurrentNodeIndex(g), game.getPacmanCurrentNodeIndex(), DM.PATH);
	    			closestGhost = g;
	    		}
    	}
    	
    	if(closestGhost != null) {
	    	int ghost = game.getGhostCurrentNodeIndex(closestGhost);
	    	int mspacman = game.getPacmanCurrentNodeIndex();
	    	java.awt.Color colour = java.awt.Color.CYAN;
			if(game.getGhostLairTime(closestGhost)<=0)
				GameView.addPoints(game,colour,game.getShortestPath(ghost,mspacman));
    	}
    	return closestGhost;
    }
    
	private GHOST NearestEdibleGhost(Game game, int limit){
	    	
	    	GHOST closestGhost = null;
	    	double d = limit;
	    	for(Constants.GHOST g: Constants.GHOST.values()){
	    		if(game.wasPowerPillEaten())
		    		if(game.getDistance(game.getGhostCurrentNodeIndex(g), game.getPacmanCurrentNodeIndex(), DM.PATH) <= d) {
		    			d = game.getDistance(game.getGhostCurrentNodeIndex(g), game.getPacmanCurrentNodeIndex(), DM.PATH);
		    			closestGhost = g;
		    		}
	    	}
	    	
	    	if(closestGhost != null) {
		    	int ghost = game.getGhostCurrentNodeIndex(closestGhost);
		    	int mspacman = game.getPacmanCurrentNodeIndex();
		    	java.awt.Color colour = java.awt.Color.CYAN;
				if(game.getGhostLairTime(closestGhost)<=0)
				 	GameView.addPoints(game,colour,game.getShortestPath(ghost,mspacman));
	    	}
	    	return closestGhost;
	    }
	
	private int getNearestPill(Game game){
		
		int[] pills = game.getActivePillsIndices();
		int[] pPills = game.getActivePowerPillsIndices();
		double d = -1;
		int ind = 0;
		
		for(int i = 0; i < game.getNumberOfActivePills(); i++){
			
			if(game.getDistance(pills[i], game.getPacmanCurrentNodeIndex(), DM.PATH) <= d || d == -1) {
				d = game.getDistance(pills[i], game.getPacmanCurrentNodeIndex(), DM.PATH);
				ind = pills[i];
			}
		}
		
		for(int i = 0; i < game.getNumberOfActivePowerPills(); i++){
			
			if(game.getDistance(pPills[i], game.getPacmanCurrentNodeIndex(), DM.PATH) <= d || d == -1) {
				d = game.getDistance(pPills[i], game.getPacmanCurrentNodeIndex(), DM.PATH);
				ind = pPills[i];
			}
		}
		return ind;
	}
}


