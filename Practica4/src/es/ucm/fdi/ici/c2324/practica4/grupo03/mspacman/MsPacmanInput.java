package es.ucm.fdi.ici.c2324.practica4.grupo03.mspacman;

import java.util.Collection;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Vector;

import es.ucm.fdi.ici.rules.RulesInput;
import pacman.game.Game;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;



public class MsPacmanInput extends RulesInput {
	private String nearGhostEdible;
	private String nearGhostChasing;
	private double nearGhostEdibleDistance;
	private double nearGhostChasingDistance;
	private boolean pacmandead;
	private double mindistancePPill;
	private int closestPP;
	private double mindistancePill;
	private int closestpill;
	private boolean securePathToEdibleGhost;
	private boolean securePathToPP;
	private boolean securePathToPill;

	private MOVE lastmove;
	private int  pc;
	private EnumMap<GHOST,Integer> gs;	


	public MsPacmanInput(Game game) {
		super(game);
		this.gs = new EnumMap<GHOST, Integer>(GHOST.class);
		parseInput();
	
	}

	@Override
	public Collection<String> getFacts() {
		Vector<String> facts = new Vector<String>();
        facts.add(String.format("(MSPACMAN (nearGhostEdible %s))", this.nearGhostEdible));
        facts.add(String.format("(MSPACMAN (nearGhostEdibleDistance %d))",(int) this.nearGhostEdibleDistance));
        facts.add(String.format("(MSPACMAN (nearGhostChasing %s))", this.nearGhostChasing));
        facts.add(String.format("(MSPACMAN (nearGhostChasingDistance %d))", (int) this.nearGhostChasingDistance));
        facts.add(String.format("(MSPACMAN (pacmandead %s))", this.pacmandead));
        facts.add(String.format("(MSPACMAN (mindistancePPill %d))", (int) this.mindistancePPill));
        facts.add(String.format("(MSPACMAN (closestPP %d))", (int) this.closestPP));
        facts.add(String.format("(MSPACMAN (mindistancePill %d))", (int) this.mindistancePill));
        facts.add(String.format("(MSPACMAN (closestpill %d))", (int) this.closestpill));
        facts.add(String.format("(MSPACMAN (securePathToEdibleGhost %s))", this.securePathToEdibleGhost));
        facts.add(String.format("(MSPACMAN (securePathToPP %s))", this.securePathToPP));
        facts.add(String.format("(MSPACMAN (securePathToPill %s))", this.securePathToPill));
        return facts;
	}

	@Override
	public void parseInput() {
		this.pc = game.getPacmanCurrentNodeIndex();
		this.lastmove = game.getPacmanLastMoveMade();

		for (GHOST ghost: GHOST.values()) {
    		gs.put(ghost, game.getGhostCurrentNodeIndex(ghost));
		}

		GHOST edible = closestEdibleGhost();
		GHOST chasing = closestChasingGhost();
		this.nearGhostEdible = ghostToString(edible);
		this.nearGhostChasing = ghostToString(chasing);
		this.nearGhostEdibleDistance = ((edible != null) ? game.getShortestPathDistance(pc, game.getGhostCurrentNodeIndex(edible)) : Double.MAX_VALUE);
		this.nearGhostChasingDistance = ((chasing != null) ? game.getShortestPathDistance(pc, game.getGhostCurrentNodeIndex(chasing)) : Double.MAX_VALUE);
		this.securePathToEdibleGhost = ((edible != null) ? canGhostGetMe(gs.get(edible)) : false);
		
		this.pacmandead = game.wasPacManEaten();

		if(game.getNumberOfActivePowerPills() > 0){
			this.closestPP = game.getClosestNodeIndexFromNodeIndex(pc, game.getActivePowerPillsIndices(), DM.PATH);
			this.mindistancePPill = game.getShortestPathDistance(pc, this.closestPP, lastmove);
			this.securePathToPP = canGhostGetMe(this.closestPP);
		}
		else{
			this.closestPP = -1;
			this.mindistancePPill = Double.MAX_VALUE;
			this.securePathToPP = false;
		}

		this.closestpill = game.getClosestNodeIndexFromNodeIndex(pc, game.getActivePillsIndices(), DM.PATH);
		this.mindistancePill = game.getShortestPathDistance(pc, this.closestpill, lastmove);
		this.securePathToPill = canGhostGetMe(this.closestpill);
		
		
		
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
		int pacman_sec = getSection(pc,game);
		
		sec[pacman_sec] += 1000;
		
		
		for(GHOST g : GHOST.values()) {
			if(!game.isGhostEdible(g) && !(game.getGhostLairTime(g) > 0)) {
				sec[getSection(gs.get(g),game)]+= 10;
			}
			else if(game.isGhostEdible(g) && !(game.getGhostLairTime(g) > 0)) {
				sec[getSection(gs.get(g),game)]-= 10;
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

	
	public boolean ghostsInMyWay(int node){
		
		for (GHOST g : GHOST.values()) {
			if (!game.isGhostEdible(g) && game.getGhostLairTime(g) <= 0) {
				int[] pacmanPath = game.getShortestPath(pc, node, lastmove);
				boolean ghost_in_direct_path = Arrays.asList(pacmanPath).contains(gs.get(g));
				if(ghost_in_direct_path)
					return true;
			}
		}
		return false;
	}

	

	public boolean canGhostGetMe(int node) {
		
    	for (GHOST g : GHOST.values()) {
    		if (!game.isGhostEdible(g) && game.getGhostLairTime(g) <= 0) {
    			int[] ghostPath = game.getShortestPath(gs.get(g), node);
    			int[] pacmanPath = game.getShortestPath(pc, node, lastmove);
    			
    			boolean ghost_in_direct_path = Arrays.asList(pacmanPath).contains(gs.get(g));
    			boolean ghost_will_eat_me = Arrays.asList(game.getShortestPath(gs.get(g), pc)).contains(node)
    					&& pacmanPath.length < ghostPath.length;
    			
    			if(ghost_in_direct_path || ghost_will_eat_me) {
    				return true;
    			}
    		}
    	}
    	return false;
    }

	//Calcula el ghost que persigue a pacman mas cercano dentro de un limite
     public GHOST closestChasingGhost() {
    	GHOST res = null;
    	int d = Integer.MAX_VALUE;
    	
    	for (GHOST g : GHOST.values()) {
    		int gn = gs.get(g);
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
    public GHOST closestEdibleGhost() {
    	GHOST res = null;
    	int d = Integer.MAX_VALUE;

    	
    	for (GHOST g : GHOST.values()) {
    		int gn = gs.get(g);
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
	private String ghostToString(GHOST g){
		if(g == GHOST.BLINKY)
			return "BLINKY";
		else if(g == GHOST.INKY)
			return "INKY";
		else if(g == GHOST.PINKY)
			return "PINKY";
		else if(g == GHOST.SUE)
			return "SUE";
		else
			return "NONE";
		
	}
}
