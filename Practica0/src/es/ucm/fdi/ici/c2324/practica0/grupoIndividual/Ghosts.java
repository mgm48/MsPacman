package es.ucm.fdi.ici.c2324.practica0.grupoIndividual;

import java.util.EnumMap;
import java.util.Random;

import pacman.controllers.GhostController;
import pacman.game.Game;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public final class Ghosts extends GhostController {
    private EnumMap<GHOST, MOVE> moves = new EnumMap<GHOST, MOVE>(GHOST.class);
    private MOVE[] allMoves = MOVE.values();
    private Random rnd = new Random();
    private int limit = 15;
    private int pacman = 0;
    @Override
    
    public EnumMap<GHOST, MOVE> getMove(Game game, long timeDue) {
        moves.clear();
        for (GHOST ghostType : GHOST.values()) {
            if (game.doesGhostRequireAction(ghostType)) {
            	pacman = game.getPacManInitialNodeIndex();
                double nextFloat = rnd.nextDouble(1.0);
                
				if(PacManEdible(game,limit) || PowerPillNear(game,limit))
                 	 moves.put(ghostType, game.getApproximateNextMoveAwayFromTarget(game.getGhostCurrentNodeIndex(ghostType), pacman, game.getGhostLastMoveMade(ghostType), DM.PATH));	
                else if(nextFloat < 0.9)
                 	 moves.put(ghostType, game.getApproximateNextMoveTowardsTarget(game.getGhostCurrentNodeIndex(ghostType), pacman, game.getGhostLastMoveMade(ghostType), DM.PATH));	
                else
                	 moves.put(ghostType, allMoves[rnd.nextInt(allMoves.length)]);
            }
        }
        return moves;
    }       
    
    private boolean PacManEdible(Game game,int limit) {
    	
    	if(game.wasPowerPillEaten())
    		return true;
    	else
    		return false;    	
    }
    
    private boolean PowerPillNear(Game game,int limit) {
    	
    	if(getNearestPillDistance(game, limit) < limit)
    		return true;
    	else
    		return false;  
    }
    
    private double getNearestPillDistance(Game game, int limit){
		
		int[] pills = game.getActivePillsIndices();
		int[] pPills = game.getActivePowerPillsIndices();
		double d = limit;

		for(int i = 0; i < game.getNumberOfActivePills(); i++){
			
			if(game.getDistance(pills[i], game.getPacmanCurrentNodeIndex(), DM.PATH) <= d) {
				d = game.getDistance(pills[i], game.getPacmanCurrentNodeIndex(), DM.PATH);
			}
		}
		
		for(int i = 0; i < game.getNumberOfActivePowerPills(); i++){
			
			if(game.getDistance(pPills[i], game.getPacmanCurrentNodeIndex(), DM.PATH) <= d) {
				d = game.getDistance(pPills[i], game.getPacmanCurrentNodeIndex(), DM.PATH);
			}
		}
		return d;
	}    
}

