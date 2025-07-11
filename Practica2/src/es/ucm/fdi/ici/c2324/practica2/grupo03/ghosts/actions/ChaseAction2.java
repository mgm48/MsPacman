package es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.actions;

import es.ucm.fdi.ici.Action;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class ChaseAction2 implements Action {

    GHOST ghost;
	public ChaseAction2( GHOST ghost) {
		this.ghost = ghost;
	}

	@Override
	public MOVE execute(Game game) {
        if (game.doesGhostRequireAction(ghost))        //if it requires an action
        {
            return game.getApproximateNextMoveTowardsTarget(
            	   game.getGhostCurrentNodeIndex(ghost),
                   game.getPacmanCurrentNodeIndex(), 
                   game.getGhostLastMoveMade(ghost), DM.PATH);
        }
        return MOVE.NEUTRAL;
	}

	@Override
	public String getActionId() {
		return ghost + "chases";
	}
}
