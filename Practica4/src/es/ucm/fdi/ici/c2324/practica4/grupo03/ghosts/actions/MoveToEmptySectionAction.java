package es.ucm.fdi.ici.c2324.practica4.grupo03.ghosts.actions;

import java.util.ArrayList;
import java.util.Random;

import es.ucm.fdi.ici.rules.RulesAction;
import jess.Fact;
import jess.JessException;
import jess.Value;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class MoveToEmptySectionAction implements RulesAction {

	private GHOST ghost;
	private String section;

	private int[][] sections = { // Xmin, Xmax, Ymin, Ymax
		{0, 52, 0, 58}, //Sector arriba izq
		{53, 104, 0, 58}, //Sector arriba der
		{0, 52, 59, 116}, //Sector abajo izq
		{53, 104, 59, 116} //Sector abajo der
	};
	
	public MoveToEmptySectionAction(GHOST ghost) {
		this.ghost = ghost;
	}

	@Override
	public MOVE execute(Game game) {
	
		if (game.doesGhostRequireAction(ghost)) {        //if it requires an action
			return moveToSection(game);		
		}
		
		return MOVE.NEUTRAL;
	}

	@Override
	public String getActionId() {
		return ghost + "moves to empty section";
	}

	private MOVE moveToSection(Game game) {
		
		int[] cruces = game.getJunctionIndices(); 		
  		ArrayList<Integer> cuadrante= new ArrayList<Integer>();
  		int actualsection = SectionToInt(section);

		for(int i = 0; i < cruces.length; i++){

  			if(isInSection(actualsection, cruces[i], game)) {
  			cuadrante.add(cruces[i]);
  			}
  		}
  		
  		if(cuadrante.isEmpty()) 
  			return game.getApproximateNextMoveAwayFromTarget(game.getGhostCurrentNodeIndex(ghost), game.getPacmanCurrentNodeIndex(), game.getGhostLastMoveMade(ghost), DM.PATH);
  		
  		Random r = new Random(cuadrante.size());// va a un cruce aleatorio del cuadrante
  		return game.getApproximateNextMoveTowardsTarget(game.getGhostCurrentNodeIndex(ghost), cuadrante.get(r.nextInt()), game.getGhostLastMoveMade(ghost), DM.PATH);
	}

	@Override
	public void parseFact(Fact actionFact) {
		
		try {
			Value value = actionFact.getSlotValue("section");
			if(value == null)
				return;
			int strategyValue = value.intValue(null);
			section= String.valueOf(strategyValue);
		} catch (JessException e) {
			e.printStackTrace();
		}
	}

	public boolean isInSection(int section, int index, Game game) {

		if(sections[section][0] <= game.getNodeXCood(index) && game.getNodeXCood(index) <= sections[section][1] 
		&& sections[section][2] <= game.getNodeYCood(index) && game.getNodeXCood(index) <= sections[section][3])
			return true;
		else
			return false;
	}

	private int SectionToInt(String section) {
		switch(section) {
		case "section1":
			return 0;
		case "section2":
			return 1;
		case "section3":
			return 2;
		case "section4":
			return 3;
		}
		return 0;
	}
}
