package es.ucm.fdi.ici.c2324.practica4.grupo03.mspacman;

import pacman.game.Game;

import java.util.Arrays;

import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public class Utils {

	private static int[][] sections = { // Xmin, Xmax, Ymin, Ymax
			{0, 52, 0, 58}, //Sector arriba izq
			{53, 104, 0, 58}, //Sector arriba der
			{0, 52, 59, 116}, //Sector abajo izq
			{53, 104, 59, 116} //Sector abajo der
		};
	
	public static int RandomJunctionInSection(int section,Game game) {	
		for(int j :  game.getJunctionIndices()) {
			if(isInSection(section, j, game)) {
				return j;
			}
		}
		return -1;
	}
		
	public static boolean isInSection(int section, int index, Game game) {

		if(sections[section][0] <= game.getNodeXCood(index) && game.getNodeXCood(index) <= sections[section][1] 
		&& sections[section][2] <= game.getNodeYCood(index) && game.getNodeXCood(index) <= sections[section][3])
			return true;
		else
			return false;
	}
	
	public static int getSection(int index, Game game){

		int centroX = 52;
        int centroY = 58;

		//sectores:
		//{0, 1}
		//{2, 3}

        if (game.getNodeXCood(index) < centroX) {
            if (game.getNodeYCood(index) < centroY) {
                return 0;
            } else {
                return 2;
            }
        } else {
            if (game.getNodeYCood(index) < centroY) {
                return 1;
            } else {
                return 3;
            }
        }
	}
	
	public static int bestSectionToMove(Game game) {
		int[] sec = {0,0,0,0};
		int pacman_sec = getSection(game.getPacmanCurrentNodeIndex(),game);
		
		sec[pacman_sec] += 50;
		
		
		for(GHOST g : GHOST.values()) {
			if(!game.isGhostEdible(g) && !(game.getGhostLairTime(g) > 0)) {
				sec[getSection(game.getGhostCurrentNodeIndex(g),game)]++;
			}
			
		}
		int min = Arrays.stream(sec).min().getAsInt();
		
		return min;
	}
	
	public static int bestSectionToMoveByPills(Game game) {
		int[] sec = {0,0,0,0};
		int pacman_sec = getSection(game.getPacmanCurrentNodeIndex(),game);
		
		sec[pacman_sec] += -100;
		
		for(int pills : game.getActivePillsIndices()) {
			sec[getSection(game.getPillIndex(pills), game)]++;
		}
		
		int max = Arrays.stream(sec).max().getAsInt();
		return max;
		
	}
	
	
	//Calcula el ghost que persigue a pacman mas cercano dentro de un limite
     public static GHOST closestChasingGhost(Game game) {
    	GHOST res = null;
    	int d = Integer.MAX_VALUE;
    	int pc = game.getPacmanCurrentNodeIndex();
    	
    	for (GHOST g : GHOST.values()) {
    		int gn = game.getGhostCurrentNodeIndex(g);
    		if (!game.isGhostEdible(g) && game.getGhostLairTime(g) <= 0) {
    			int aux = game.getShortestPathDistance(gn,pc);
    			if (aux < d) {
        			d = aux;
        			res = g;
        		}
    		}	
    	}
    	return res;
    }
    
    
    // Calcula el ghost comible ms cercano a pacman dentro de un limite
    public static GHOST closestEdibleGhost(Game game) {
    	GHOST res = null;
    	int d = Integer.MAX_VALUE;
    	int pc = game.getPacmanCurrentNodeIndex();
    	MOVE lastmove = game.getPacmanLastMoveMade();
    	
    	for (GHOST g : GHOST.values()) {
    		int gn = game.getGhostCurrentNodeIndex(g);
    		if (game.isGhostEdible(g) && game.getGhostLairTime(g) <= 0) {
    			int aux = game.getShortestPathDistance(pc, gn, lastmove);
    			if (aux < d) {
        			d = aux;
        			res = g;
        		}
    		}
    	}
    	return res;
    }
	 
    
}
