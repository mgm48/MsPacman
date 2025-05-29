package es.ucm.fdi.ici.c2324.practica5.grupoYY.ghosts.actions;

import java.util.Random;

import es.ucm.fdi.ici.Action;
import es.ucm.fdi.ici.rules.RulesAction;
import jess.Fact;
import jess.JessException;
import jess.Value;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class ProtectAction implements Action{
	
	private Random rnd = new Random();
	GHOST ghost;
	String protect;
	public ProtectAction(GHOST ghost) {
		this.ghost = ghost;
	}

	@Override
	public MOVE execute(Game game) {
		int pc  = game.getPacmanCurrentNodeIndex();
		int gc = game.getGhostCurrentNodeIndex(ghost);
		MOVE lastmove = game.getGhostLastMoveMade(ghost);
		MOVE[] possibleMoves = game.getPossibleMoves(gc, lastmove);
		double distance=Double.MAX_VALUE;
		GHOST mascercano = null;
		
		for(GHOST g: GHOST.values()) {
			int pos = game.getGhostCurrentNodeIndex(g);
			if(!g.equals(ghost) && game.isGhostEdible(g) && game.getDistance(pc, pos,lastmove, DM.PATH) < distance){
				distance = game.getDistance(pc, pos,lastmove, DM.PATH);
				mascercano = g;
			}
		}
		if(mascercano != null){
			return game.getApproximateNextMoveTowardsTarget(pc, game.getGhostCurrentNodeIndex(mascercano), lastmove, DM.PATH);
		}
		


		return possibleMoves[rnd.nextInt(possibleMoves.length)];
	}

	@Override
	public String getActionId() {
		return ghost + "protects";
	}

	
}
