package es.ucm.fdi.ici.c2324.practica2.grupo03.mspacman.actions;

import es.ucm.fdi.ici.Action;
import es.ucm.fdi.ici.c2324.practica2.grupo03.mspacman.Utils;
import pacman.game.Constants.DM;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class SectionMoveByPillsAction implements Action {

	@Override
	public String getActionId() {
		return "pacman moves section";
	}

	@Override
	public MOVE execute(Game game) {
		int sec = Utils.bestSectionToMoveByPills(game);
		int junction = Utils.RandomJunctionInSection(sec, game);
		
		return game.getApproximateNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), 
				junction, game.getPacmanLastMoveMade(), DM.PATH);
	}

}
