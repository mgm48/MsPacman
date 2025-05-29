package es.ucm.fdi.ici.c2324.practica2.grupo03.mspacman;

import java.util.Arrays;
import java.util.EnumMap;

import es.ucm.fdi.ici.Input;
import pacman.game.Game;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public class MsPacManInput extends Input {

	private boolean BLINKYedible;
	private boolean INKYedible;
	private boolean PINKYedible;
	private boolean SUEedible;
	private boolean pacmandied;
	private GHOST closestEdible;
	private GHOST closestChasing;
	private MOVE lastmove;
	private int  pc;
	private EnumMap<GHOST,Integer> gs;
	private double minPacmanDistancePPill;
	private int[] activePills;
	private int[] activePowerPills;
	
	
	public MsPacManInput(Game game) {
		super(game);
		
	}

	@Override
	public void parseInput() {
		this.BLINKYedible = game.isGhostEdible(GHOST.BLINKY);
		this.INKYedible = game.isGhostEdible(GHOST.INKY);
		this.PINKYedible = game.isGhostEdible(GHOST.PINKY);
		this.SUEedible = game.isGhostEdible(GHOST.SUE);
		
		this.gs = new EnumMap<GHOST, Integer>(GHOST.class);
		for (GHOST ghost: GHOST.values()) {
    		gs.put(ghost, game.getGhostCurrentNodeIndex(ghost));
		}
		closestEdible = Utils.closestEdibleGhost(game);
		closestChasing = Utils.closestChasingGhost(game);
		
		
		this.pacmandied = game.wasPacManEaten();
		
		this.activePills = game.getActivePillsIndices();
		this.activePowerPills = game.getActivePowerPillsIndices();
		
		this.lastmove = game.getPacmanLastMoveMade();
		this.pc = game.getPacmanCurrentNodeIndex();
		for(int ppill: game.getPowerPillIndices()) {
			double distance = game.getDistance(pc, ppill, DM.PATH);
			this.minPacmanDistancePPill = Math.min(distance, this.minPacmanDistancePPill);
		}
	}
	
	
	public boolean isPacmanDead() {
		return this.pacmandied;
	}
	
	public int getClosestEdibleDistance() {
		return game.getShortestPathDistance(pc, gs.get(closestEdible), lastmove);
	}
	public boolean isClosestEdibleReachable() {
		return !canGhostGetMe("edible");
	}
	


	public boolean ghostsInMyWay(String opt){
		int node = gs.get(closestEdible);
		if(opt.equals("powerpill")) {
			node = game.getClosestNodeIndexFromNodeIndex(pc, activePowerPills, DM.PATH);
		}
		if(opt.equals("pill")) {
			node = game.getClosestNodeIndexFromNodeIndex(pc, activePills, DM.PATH);
		}
		
		
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

	

	public boolean canGhostGetMe(String opt) {
		int node = gs.get(closestEdible);
		if(opt.equals("powerpill")) {
			node = game.getClosestNodeIndexFromNodeIndex(pc, activePowerPills, DM.PATH);
		}
		if(opt.equals("pill")) {
			node = game.getClosestNodeIndexFromNodeIndex(pc, activePills, DM.PATH);
		}
		
		
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
	
	
	public boolean anyGhostsOut() {	
		return game.getGhostLairTime(GHOST.BLINKY) > 0 || game.getGhostLairTime(GHOST.INKY) > 0
				|| game.getGhostLairTime(GHOST.PINKY) > 0 || game.getGhostLairTime(GHOST.SUE) > 0;
	}

	
	public boolean anyGhostsEdible() {
		return BLINKYedible || INKYedible || PINKYedible || SUEedible;
	}
	
	public boolean isBLINKYedible() {
		return BLINKYedible;
	}

	public boolean isINKYedible() {
		return INKYedible;
	}

	public boolean isPINKYedible() {
		return PINKYedible;
	}

	public boolean isSUEedible() {
		return SUEedible;
	}
	
	public int numberOfPillsNear(int limit) {
		int n = 0;
		for(int pill : activePills) {
			if(game.getShortestPathDistance(pc, pill, lastmove) <= limit) {
				n++;
			}
		}
		return n;
	}
	
 	public int getClosestPPillDistance() {
		return game.getShortestPathDistance(pc, game.getClosestNodeIndexFromNodeIndex(pc, activePowerPills, DM.PATH), lastmove);
	}
	
	public int getClosestPillDistance() {
		return game.getShortestPathDistance(pc, game.getClosestNodeIndexFromNodeIndex(pc, activePills, DM.PATH), lastmove);
	}
	
	public int getActivePillsNumber() {
		return activePills.length;
	}
	
	public int getActivePowerPillsNumber() {
		return activePowerPills.length;
	}
}
