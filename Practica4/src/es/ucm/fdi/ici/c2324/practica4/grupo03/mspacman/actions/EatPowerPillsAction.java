package es.ucm.fdi.ici.c2324.practica4.grupo03.mspacman.actions;


import es.ucm.fdi.ici.rules.RulesAction;
import jess.Fact;
import jess.JessException;
import jess.Value;
import pacman.game.Constants.DM;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class EatPowerPillsAction implements RulesAction{
	private int closestPPillIndex;
	 
	@Override
	public String getActionId() {
		return "pacman yendo hacia " + closestPPillIndex;
	}
	

	@Override
	public MOVE execute(Game game) {
		 if (game.isJunction(game.getPacmanCurrentNodeIndex())) {        //if it requires an action
				return game.getApproximateNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(),closestPPillIndex , game.getPacmanLastMoveMade(), DM.PATH);
			 }
		return MOVE.NEUTRAL;
	}

	@Override
	public void parseFact(Fact actionFact) {
		try {
			Value value = actionFact.getSlotValue("powerpill");
			if(value == null)
				return;
			String strategyValue = value.stringValue(null);
			closestPPillIndex = Integer.valueOf(strategyValue);
		} catch (JessException e) {
			e.printStackTrace();
		}
	}
}
