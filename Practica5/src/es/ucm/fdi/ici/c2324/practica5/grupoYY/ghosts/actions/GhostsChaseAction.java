package es.ucm.fdi.ici.c2324.practica5.grupoYY.ghosts.actions;


import java.util.Random;

import es.ucm.fdi.ici.Action;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class GhostsChaseAction implements Action {
    
	private Random rnd = new Random();
	GHOST ghost;
	public GhostsChaseAction(GHOST ghost) {
		this.ghost=ghost;
	}
	
	@Override
	public MOVE execute(Game game) {
		int pc  = game.getPacmanCurrentNodeIndex();
		int gc = game.getGhostCurrentNodeIndex(ghost);
		MOVE lastmove = game.getGhostLastMoveMade(ghost);
		MOVE[] possibleMoves = game.getPossibleMoves(gc, lastmove);
		

		if(possibleMoves.length > 1 && pc!=-1){
			return game.getApproximateNextMoveTowardsTarget(gc, pc, lastmove, DM.PATH);
		} 

		return possibleMoves[rnd.nextInt(possibleMoves.length)];		
    }
	
	@Override
	public String getActionId() {
		return "Chase";
	}
            
}
