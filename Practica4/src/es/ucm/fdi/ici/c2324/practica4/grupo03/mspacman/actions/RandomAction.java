package es.ucm.fdi.ici.c2324.practica4.grupo03.mspacman.actions;

import java.util.Random;

import es.ucm.fdi.ici.Action;
import es.ucm.fdi.ici.rules.RulesAction;
import jess.Fact;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class RandomAction implements RulesAction {

    private Random rnd = new Random();
    private MOVE[] allMoves = MOVE.values();

	@Override
	public MOVE execute(Game game) {
		return allMoves[rnd.nextInt(allMoves.length)];
	}

	@Override
	public String getActionId() {
		return "Random Action";
	}

	@Override
	public void parseFact(Fact actionFact) {
		// Nothing to parse
	}
}