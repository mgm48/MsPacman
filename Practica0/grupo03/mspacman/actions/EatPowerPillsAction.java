package es.ucm.fdi.ici.c2324.practica2.grupo03.mspacman.actions;

import java.util.List;

import es.ucm.fdi.ici.Action;
import pacman.game.Constants.DM;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class EatPowerPillsAction implements Action{
	private int closestPowerPillIndex;
	
	@Override
	public String getActionId() {
		// TODO Auto-generated method stub
		return "pacman yendo hacia " + closestPowerPillIndex;
	}

	@Override
	public MOVE execute(Game game) {
		closestPowerPillIndex = game.getClosestNodeIndexFromNodeIndex(game.getPacmanCurrentNodeIndex(), game.getActivePowerPillsIndices(), DM.PATH);
		return game.getApproximateNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), 
				closestPowerPillIndex , game.getPacmanLastMoveMade(), DM.PATH);
	}

}
