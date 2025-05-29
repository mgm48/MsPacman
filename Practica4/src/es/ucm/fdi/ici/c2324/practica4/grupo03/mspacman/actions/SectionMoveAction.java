package es.ucm.fdi.ici.c2324.practica4.grupo03.mspacman.actions;

import java.util.Arrays;
import es.ucm.fdi.ici.c2324.practica4.grupo03.mspacman.Utils;
import es.ucm.fdi.ici.rules.RulesAction;
import jess.Fact;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class SectionMoveAction implements RulesAction {
	private static int[][] sections = { // Xmin, Xmax, Ymin, Ymax
		{0, 52, 0, 58}, //Sector arriba izq
		{53, 104, 0, 58}, //Sector arriba der
		{0, 52, 59, 116}, //Sector abajo izq
		{53, 104, 59, 116} //Sector abajo der
	};
	
	@Override
	public String getActionId() {
		return "pacman moves section";
	}

	@Override
	public MOVE execute(Game game) {
		int sec = bestSectionToMove(game);
		int junction = RandomJunctionInSection(sec, game);
		
		return game.getApproximateNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), 
				junction, game.getPacmanLastMoveMade(), DM.PATH);
	}

	@Override
	public void parseFact(Fact actionFact) {
		// Nothing to parse
	}
	
	public int RandomJunctionInSection(int section,Game game) {	
		for(int j :  game.getJunctionIndices()) {
			if(isInSection(section, j, game)) {
				return j;
			}
		}
		return -1;
	}
		
	public boolean isInSection(int section, int index, Game game) {

		if(sections[section][0] <= game.getNodeXCood(index) && game.getNodeXCood(index) <= sections[section][1] 
		&& sections[section][2] <= game.getNodeYCood(index) && game.getNodeXCood(index) <= sections[section][3])
			return true;
		else
			return false;
	}

	public int getSection(int index, Game game){

	int centroX = 52;
	int centroY = 58;

	//sectores:
	//{0, 1}
	//{2, 3}

		if (game.getNodeXCood(index) < centroX) {
			if (game.getNodeYCood(index) < centroY) 
				return 0;
			else 
				return 2;
		} 
		else {
			if (game.getNodeYCood(index) < centroY)
				return 1;
			else 
				return 3;
		}
	}

	public int bestSectionToMove(Game game) {
		int[] sec = {0,0,0,0};
		int pacman_sec = getSection(game.getPacmanCurrentNodeIndex(),game);
		
		sec[pacman_sec] += 1000;
		
	
		for(GHOST g : GHOST.values()) {
			if(!game.isGhostEdible(g) && !(game.getGhostLairTime(g) > 0)) {
				sec[getSection(game.getGhostCurrentNodeIndex(g),game)]+= 10;
			}
			else if(game.isGhostEdible(g) && !(game.getGhostLairTime(g) > 0)) {
				sec[getSection(game.getGhostCurrentNodeIndex(g),game)]-= 10;
			}
		}

		for(int pill : game.getActivePillsIndices()) {
			sec[getSection(pill, game)]--;
		}

		for(int pp : game.getActivePowerPillsIndices()){
			sec[getSection(pp, game)]-= 10;
		}

		int min = Arrays.stream(sec).min().getAsInt();
		
		return min;
	}
	
}
