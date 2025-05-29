package es.ucm.fdi.ici.c2324.practica4.grupo03.mspacman.actions;

import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;
import es.ucm.fdi.ici.rules.RulesAction;
import jess.Fact;
import jess.JessException;
import jess.Value;

public class EscapeRouteAction implements RulesAction {
	private String closestChasing;
	private int target;
	private int escapeRoute;
	private String strategy;
	
	@Override
	public String getActionId() {
		return "pacman buscando via de escape";
	}

	@Override
	public MOVE execute(Game game) {
		boolean escape= false;
		if(strategy=="CHASING")
			this.target = posGhost(closestChasing,game);
		int pc = game.getPacmanCurrentNodeIndex();
		int[] path =game.getShortestPath(pc, target);
		
		if(game.isJunction(pc)) {
			return game.getApproximateNextMoveAwayFromTarget(pc, target, game.getPacmanLastMoveMade(), DM.PATH);
		}
		else {
			for(int i : path) {
				if(i == target) {
					break;
				}
				if(game.getDistance(pc, i, DM.PATH) < game.getDistance(target, i, DM.PATH) 
					&& game.isJunction(i)) {
					escape = true;
					escapeRoute = i;
				}
			}
			if(!escape) {
				return game.getApproximateNextMoveAwayFromTarget(pc, target, game.getPacmanLastMoveMade(), DM.PATH);
			}
			
		}
		
		
		return game.getApproximateNextMoveTowardsTarget(pc,  escapeRoute, 
				game.getPacmanLastMoveMade(), DM.PATH);
	
	}

	@Override
	public void parseFact(Fact actionFact) {
		try {
			Value value = actionFact.getSlotValue("runawaystrategy");
			if(value == null)
				return;
			String strategyValue = value.stringValue(null);
			strategy = String.valueOf(strategyValue);
			if(strategy=="CHASING") {
				Value val = actionFact.getSlotValue("CHASING");
				String g = val.stringValue(null);
			}
			if(strategy=="PP") {
				Value val = actionFact.getSlotValue("PP");
				String g = val.stringValue(null);
				target = Integer.valueOf(g);
			}
			if(strategy=="Pill") {
				Value val = actionFact.getSlotValue("Pill");
				String g = val.stringValue(null);
				target = Integer.valueOf(g);
			}
				
		} catch (JessException e) {
			e.printStackTrace();
		}
	}
	private int posGhost(String name,Game game) {
		switch(name) {
		case "SUE":
			return game.getGhostCurrentNodeIndex(GHOST.SUE);
		case "BLINKY":
			return game.getGhostCurrentNodeIndex(GHOST.BLINKY);
		case "INKY":
			return game.getGhostCurrentNodeIndex(GHOST.INKY);
		case "PINKY":
			return game.getGhostCurrentNodeIndex(GHOST.PINKY);
		}
		return -1;
	}

}
