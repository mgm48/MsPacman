package es.ucm.fdi.ici.c2324.practica5.grupoYY.ghosts.actions;

import java.util.Random;

import es.ucm.fdi.ici.Action;
import pacman.game.Game;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public class SearchAction  implements Action {
    
	private Random rnd = new Random();
	GHOST ghost;
	public SearchAction(GHOST ghost) {
		this.ghost=ghost;
	}
	
	@Override
	public MOVE execute(Game game) {
	// forma facil, alejarse de otros fantasmas para abarcar más
	//forma dificil, tener en un mapa las pills y cada vez que vemos que no estan las pills mirar si lo habiamos guardado ya o no.
		// si no estaba en el mapa implica que ha pasado hace poco por ahi
		
		int pc  = game.getPacmanCurrentNodeIndex();//modo facil
		int gc = game.getGhostCurrentNodeIndex(ghost);
		MOVE lastmove = game.getGhostLastMoveMade(ghost);
		MOVE[] possibleMoves = game.getPossibleMoves(gc, lastmove);
		double distance=Double.MAX_VALUE;
		GHOST mascercano = null;
		
		for(GHOST g: GHOST.values()) {
			int pos = game.getGhostCurrentNodeIndex(g);
			if(!g.equals(ghost) && game.getDistance(pc, pos,lastmove, DM.PATH) < distance){
				distance = game.getDistance(pc, pos,lastmove, DM.PATH);
				mascercano = g;
			}
		}
		if(mascercano != null){
			return game.getApproximateNextMoveAwayFromTarget(pc, game.getGhostCurrentNodeIndex(mascercano), lastmove, DM.PATH);
		}
		


		return possibleMoves[rnd.nextInt(possibleMoves.length)];
    }
	
	@Override
	public String getActionId() {
		return "Search pacman";
	}
            
}

