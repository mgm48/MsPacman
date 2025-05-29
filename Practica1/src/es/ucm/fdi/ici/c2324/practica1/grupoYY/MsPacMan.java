package es.ucm.fdi.ici.c2324.practica1.grupoYY;

import pacman.controllers.PacmanController;
import pacman.game.Constants;
import pacman.game.Game;
import pacman.game.GameView;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public class MsPacMan extends PacmanController {
	int powerpillcercana=-1;
    public MsPacMan() {
    }

    @Override
    public MOVE getMove(Game game, long dueTime) {
    	
    	int limit = 40;
    	
    	GHOST g = NearestChasingGhost(game, limit);
    	
    	if(g != null)// si hay fantasma cerca huye
        	return huir(game,g);
    	g = NearestEdibleGhost(game, limit); 
    	
    	if(g != null)// comer a fantasmas cercanos
        	return perseguir(game,g);
    	
    	return comer(game);// va a por pills
    }
    
    private GHOST NearestChasingGhost(Game game, int limit){
    	
    	GHOST closestGhost = null;
    	double d = limit;
    	for(Constants.GHOST g: Constants.GHOST.values()){
    		if(game.getGhostEdibleTime(g)<=0)
	    		if(game.getDistance(game.getGhostCurrentNodeIndex(g), game.getPacmanCurrentNodeIndex(), DM.PATH) <= d) {
	    			d = game.getDistance(game.getGhostCurrentNodeIndex(g), game.getPacmanCurrentNodeIndex(),game.getPacmanLastMoveMade(), DM.PATH);
	    			closestGhost = g;
	    		}
    	}
    	return closestGhost;
    }
    
	private GHOST NearestEdibleGhost(Game game, int limit){
	    	
	    	GHOST closestGhost = null;
	    	double d = limit;
	    	for(Constants.GHOST g: Constants.GHOST.values()){
	    		if(game.getGhostEdibleTime(g)>0)
		    		if(game.getDistance(game.getGhostCurrentNodeIndex(g), game.getPacmanCurrentNodeIndex(), DM.PATH) <= d) {
		    			d = game.getDistance(game.getGhostCurrentNodeIndex(g), game.getPacmanCurrentNodeIndex(),game.getPacmanLastMoveMade() ,DM.PATH);
		    			closestGhost = g;
		    		}
	    	}
	    	return closestGhost;
	    }
	
	private int getNearestPill(Game game){// coge las pastilla cercanas que no esten cerca de una powerpill
		
		int[] pills = game.getActivePillsIndices();
		double d = -1;
		int ind = 0;
		double power=1000;
		int limite=10*game.getPacmanNumberOfLivesRemaining();
		
		for(int i = 0; i < game.getNumberOfActivePills(); i++){
			power=1000;
			for(int j = 0; j < game.getNumberOfActivePowerPills(); j++){
				if(power> game.getDistance(pills[i], game.getActivePowerPillsIndices()[j],game.getPacmanLastMoveMade(), DM.PATH))
				power = game.getDistance(pills[i], game.getActivePowerPillsIndices()[j],game.getPacmanLastMoveMade(), DM.PATH);
			}
			if((game.getDistance(pills[i], game.getPacmanCurrentNodeIndex(), game.getPacmanLastMoveMade(), DM.PATH) <= d || d == -1) && power>limite ) {
				d = game.getDistance(pills[i], game.getPacmanCurrentNodeIndex(),game.getPacmanLastMoveMade(), DM.PATH);	
				ind = pills[i];
			}
			
		}
		return ind;
	}
	private MOVE huir(Game game, GHOST g) {
		if(getNearestPowerPill(game))//devuelve si el fantasma esta más cerca de la powerpill que pacman
		return game.getApproximateNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), powerpillcercana, game.getPacmanLastMoveMade(), DM.PATH);
		else //huye del fantasma
			return game.getApproximateNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(g),  game.getPacmanLastMoveMade(), DM.PATH);
	}
	private boolean getNearestPowerPill(Game game) {
		int[] pPills = game.getActivePowerPillsIndices();
		double d = -1;

		for(int i = 0; i < game.getNumberOfActivePowerPills(); i++) {
			if(game.getDistance(pPills[i], game.getPacmanCurrentNodeIndex(), DM.PATH) <= d || d == -1) {
				d = game.getDistance(pPills[i], game.getPacmanCurrentNodeIndex(), DM.PATH);
				powerpillcercana = pPills[i];
			}
		}
		
		for(Constants.GHOST g: Constants.GHOST.values()){
    		if(game.getDistance(game.getGhostCurrentNodeIndex(g), powerpillcercana, DM.PATH)<d)
    			return false;// pacman esta más lejos de la powerpill que el fantasma más proximo
    	}
		return true;// pacman esta más cerca de la powerpill que el fantasma
	}
	private MOVE perseguir(Game game, GHOST g) {
		// si no lo vas a alcanzar a tiempo te alejas de él
		if(game.getGhostEdibleTime(g)<game.getDistance(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(g), DM.PATH))
			return game.getApproximateNextMoveAwayFromTarget(game.getPacmanCurrentNodeIndex(),game.getGhostCurrentNodeIndex(g), game.getPacmanLastMoveMade(), DM.PATH);
		return game.getApproximateNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(),game.getGhostCurrentNodeIndex(g), game.getPacmanLastMoveMade(), DM.PATH);
	}
	private MOVE comer(Game game) {
		int d= getNearestPill(game);
		return game.getApproximateNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), d, game.getPacmanLastMoveMade(), DM.PATH);
	}
}


