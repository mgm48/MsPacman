package es.ucm.fdi.ici.c2324.practica4.grupo03.ghosts.actions;


import es.ucm.fdi.ici.rules.RulesAction;
import jess.Fact;
import jess.JessException;
import jess.Value;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class BlockClosePPAction implements RulesAction {
	
	GHOST ghost;
	int powerpill;//el nodo de la powerpill
	public BlockClosePPAction(GHOST ghost) {
		this.ghost = ghost;
	}

	@Override
	public MOVE execute(Game game) {
		if (game.doesGhostRequireAction(ghost))//if it requires an action
			return game.getApproximateNextMoveTowardsTarget(game.getGhostCurrentNodeIndex(ghost),powerpill , game.getGhostLastMoveMade(ghost), DM.PATH);
		return MOVE.NEUTRAL;
	}

	@Override
	public String getActionId() {
		return ghost + " blocks closest powerpill";
	}

	@Override
	public void parseFact(Fact actionFact) {
		try {
			Value value = actionFact.getSlotValue("powerpill");
			if(value == null)
				return;
			int strategyValue = value.intValue(null);
			powerpill= Integer.valueOf(strategyValue);
		} catch (JessException e) {
			e.printStackTrace();
		}
	}
}