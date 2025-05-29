package es.ucm.fdi.ici.c2324.practica5.grupoYY.mspacman.actions;


import java.util.Random;

import es.ucm.fdi.ici.Action;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class RunAwayAction implements Action {
    
	private Random rnd = new Random();
	public RunAwayAction() {
	}
	
	@Override
	public MOVE execute(Game game) {
		int pc  = game.getPacmanCurrentNodeIndex();
		MOVE lastmove = game.getPacmanLastMoveMade();
		MOVE[] possibleMoves = game.getPossibleMoves(pc, lastmove);
		
		System.out.println("I am runawaying");
		if(possibleMoves.length > 1){
			GHOST mascercano = null;
			double distance = Double.MAX_VALUE;
			for(GHOST g: GHOST.values()) {
				int pos = game.getGhostCurrentNodeIndex(g);
				if(game.isNodeObservable(pos)){
					if(!game.isGhostEdible(g) && game.getGhostLairTime(g) == 0 && game.getDistance(pc, pos,lastmove, DM.PATH) < distance){
						distance = game.getDistance(pc, pos,lastmove, DM.PATH);
						mascercano = g;
					}
				}
			}
			if(mascercano != null){
				return game.getApproximateNextMoveAwayFromTarget(pc, game.getGhostCurrentNodeIndex(mascercano), lastmove, DM.PATH);
			}
		} 

		return possibleMoves[rnd.nextInt(possibleMoves.length)];	
    }
	
	@Override
	public String getActionId() {
		return "Runaway";
	}
            
}
