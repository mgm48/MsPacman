package es.ucm.fdi.ici.c2324.practica2.grupo03.mspacman.actions;

import es.ucm.fdi.ici.Action;
import es.ucm.fdi.ici.c2324.practica2.grupo03.mspacman.Utils;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class AttractGhostsToPowerPillAction implements Action {
	private int EXTRA = 10;
	@Override
	public String getActionId() {
		return "AttractGhostsToPowerPill";
	}

	@Override
	public MOVE execute(Game game) {
		int pacmanNodeIndex = game.getPacmanCurrentNodeIndex();
        int powerPillNodeIndex = game.getClosestNodeIndexFromNodeIndex(game.getPacmanCurrentNodeIndex(), game.getActivePowerPillsIndices(), DM.PATH);
		
        GHOST closestGhost = Utils.closestChasingGhost(game);
        int closestGhostIndex = game.getGhostCurrentNodeIndex(closestGhost);
        
        if(!game.isGhostEdible(closestGhost)) {
        	
            int closestGhostDistance = game.getShortestPathDistance(pacmanNodeIndex, closestGhostIndex);

            // Calcular el movimiento en dirección al fantasma más cercano.
            MOVE moveToGhost = game.getApproximateNextMoveTowardsTarget(pacmanNodeIndex, closestGhostIndex, game.getPacmanLastMoveMade(), DM.PATH);
            
            // Si la distancia al fantasma más cercano es menor o igual a la distancia a la power pill, atrae a los fantasmas.
            if (closestGhostDistance <= game.getShortestPathDistance(pacmanNodeIndex, powerPillNodeIndex)) {
                return moveToGhost;
            }
        }
        
        // Si no hay fantasmas no comestibles cerca o si ir hacia la power pill es más seguro, sigue con el movimiento original de Pac-Man.
        return game.getApproximateNextMoveTowardsTarget(pacmanNodeIndex, powerPillNodeIndex, game.getPacmanLastMoveMade(), DM.PATH);
	}

}
