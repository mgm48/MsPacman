package es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts;

import es.ucm.fdi.ici.Input;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Game;

public class GhostsInput extends Input {

	private boolean BLINKYedible;
	private boolean INKYedible;
	private boolean PINKYedible;
	private boolean SUEedible;
	private double BLINKYnearGhost;
	private double INKYnearGhost;
	private double PINKYnearGhost;
	private double SUEnearGhost;
	private GHOST BLINKYnearGhostEdible;
	private GHOST INKYnearGhostEdible;
	private GHOST PINKYnearGhostEdible;
	private GHOST SUEnearGhostEdible;
	private GHOST BLINKYnearComingGhost;
	private GHOST INKYnearComingGhost;
	private GHOST PINKYnearComingGhost;
	private GHOST SUEnearComingGhost;
	private boolean BLINKYSecurePath;
	private boolean INKYSecurePath;
	private boolean PINKYSecurePath;
	private boolean SUESecurePath;
	private double BLINKYPacmandistance;
	private double INKYPacmandistance;
	private double PINKYPacmandistance;
	private double SUEPacmandistance;
	private double minPacmanDistancePPill;
	private double minGhostDistancePPill;
	private GHOST closestGhosttoPill;
	
	public GhostsInput(Game game) {
		super(game);
	}

	@Override
	public void parseInput() {
		
		this.BLINKYedible = game.isGhostEdible(GHOST.BLINKY);
		this.INKYedible = game.isGhostEdible(GHOST.INKY);
		this.PINKYedible = game.isGhostEdible(GHOST.PINKY);
		this.SUEedible = game.isGhostEdible(GHOST.SUE);
	
		int pacman = game.getPacmanCurrentNodeIndex();
		this.minPacmanDistancePPill = Double.MAX_VALUE;
		this.minGhostDistancePPill = Double.MAX_VALUE;
		int pill=-1;
		
		for(int ppill: game.getActivePowerPillsIndices()) {

			double distance = game.getDistance(pacman, ppill, DM.PATH);
			if(distance<this.minPacmanDistancePPill) pill=ppill;
			this.minPacmanDistancePPill = Math.min(distance, this.minPacmanDistancePPill);
			
		}		
		
		for(GHOST ghosttype: GHOST.values()) {

			if(game.getGhostLairTime(ghosttype) <= 0) {
				
				double distance = game.getDistance(game.getGhostCurrentNodeIndex(ghosttype), pill, DM.PATH);
				if(distance < this.minGhostDistancePPill)
					closestGhosttoPill = ghosttype;
				this.minGhostDistancePPill = Math.min(distance, this.minGhostDistancePPill);
			}
		}	
		
		if(game.getGhostLairTime(GHOST.BLINKY)<=0) {

			if(BLINKYnearGhostEdible != null)
				this.BLINKYnearGhost = closestGhost(GHOST.BLINKY, BLINKYnearGhostEdible);
			
			this.BLINKYnearComingGhost = closestGhostDir(GHOST.BLINKY);
			this.BLINKYPacmandistance = game.getDistance(game.getGhostCurrentNodeIndex(GHOST.BLINKY), pacman, game.getGhostLastMoveMade(GHOST.BLINKY), DM.PATH);
			
			if(BLINKYnearComingGhost != null && game.getGhostLairTime(BLINKYnearComingGhost)<=0)
				this.BLINKYSecurePath = (game.getDistance(pacman, game.getGhostCurrentNodeIndex(GHOST.BLINKY), game.getPacmanLastMoveMade(), DM.PATH) > game.getDistance(game.getGhostCurrentNodeIndex(BLINKYnearComingGhost), game.getGhostCurrentNodeIndex(GHOST.BLINKY), game.getGhostLastMoveMade(BLINKYnearComingGhost), DM.PATH));
		
		}
		
		if(game.getGhostLairTime(GHOST.INKY)<=0) {
			
			if(INKYnearGhostEdible != null)
				this.INKYnearGhost = closestGhost(GHOST.INKY, INKYnearGhostEdible);
			
			this.INKYnearComingGhost = closestGhostDir(GHOST.INKY);
			this.INKYPacmandistance = game.getDistance(game.getGhostCurrentNodeIndex(GHOST.INKY), pacman, game.getGhostLastMoveMade(GHOST.INKY), DM.PATH);
		
			if(INKYnearComingGhost != null  && game.getGhostLairTime(INKYnearComingGhost)<=0)
				this.INKYSecurePath = (game.getDistance(pacman, game.getGhostCurrentNodeIndex(GHOST.INKY), game.getPacmanLastMoveMade(), DM.PATH) > game.getDistance(game.getGhostCurrentNodeIndex(INKYnearComingGhost), game.getGhostCurrentNodeIndex(GHOST.INKY), game.getGhostLastMoveMade(INKYnearComingGhost), DM.PATH));
			
		}
		
		if(game.getGhostLairTime(GHOST.PINKY)<=0) {
			
			if(PINKYnearGhostEdible != null)
				this.PINKYnearGhost = closestGhost(GHOST.PINKY, PINKYnearGhostEdible);
			
			this.PINKYnearComingGhost = closestGhostDir(GHOST.PINKY);
			this.PINKYPacmandistance = game.getDistance(game.getGhostCurrentNodeIndex(GHOST.PINKY), pacman, game.getGhostLastMoveMade(GHOST.PINKY), DM.PATH);
		
			if(PINKYnearComingGhost != null  && game.getGhostLairTime(PINKYnearComingGhost)<=0)
				this.PINKYSecurePath = (game.getDistance(pacman, game.getGhostCurrentNodeIndex(GHOST.PINKY), game.getPacmanLastMoveMade(), DM.PATH) > game.getDistance(game.getGhostCurrentNodeIndex(PINKYnearComingGhost), game.getGhostCurrentNodeIndex(GHOST.PINKY), game.getGhostLastMoveMade(PINKYnearComingGhost), DM.PATH));
			
		}
		
		if(game.getGhostLairTime(GHOST.SUE)<=0) {
			
			if(SUEnearGhostEdible != null)
				this.SUEnearGhost = closestGhost(GHOST.SUE, SUEnearGhostEdible);
			
			this.SUEnearComingGhost = closestGhostDir(GHOST.SUE);
			this.SUEPacmandistance = game.getDistance(game.getGhostCurrentNodeIndex(GHOST.SUE), pacman, game.getGhostLastMoveMade(GHOST.SUE), DM.PATH);
	
			if(SUEnearComingGhost != null  && game.getGhostLairTime(SUEnearComingGhost)<=0)
				this.SUESecurePath = (game.getDistance(pacman, game.getGhostCurrentNodeIndex(GHOST.SUE), game.getPacmanLastMoveMade(), DM.PATH) > game.getDistance(game.getGhostCurrentNodeIndex(SUEnearComingGhost), game.getGhostCurrentNodeIndex(GHOST.SUE), game.getGhostLastMoveMade(SUEnearComingGhost), DM.PATH));
		
		}
	}
	
	private double closestGhost(GHOST ghosttype, GHOST ghostnear){
		
		double distance, distancemin = Double.MAX_VALUE;
		
		for(GHOST g: GHOST.values()) {
			
			if(g!= ghosttype && game.getGhostLairTime(g)<=0) {
				
				distance = game.getDistance(game.getGhostCurrentNodeIndex(ghosttype),game.getGhostCurrentNodeIndex(g), game.getGhostLastMoveMade(ghosttype),DM.PATH);
				if(distancemin > distance)
					ghostnear = g;
				distancemin = Math.min(distancemin, distance);
			}				
		}		
		return distancemin;
	}

	private GHOST closestGhostDir(GHOST ghosttype){

		double distance, distancemin = Double.MAX_VALUE;
		GHOST ghostnear = null;

		for(GHOST g: GHOST.values()) {
			
			if(g != ghosttype && game.getGhostLairTime(g)<=0) {
				
				distance = game.getDistance(game.getGhostCurrentNodeIndex(g),game.getGhostCurrentNodeIndex(ghosttype), game.getGhostLastMoveMade(g),DM.PATH);
				if(distancemin > distance)
					ghostnear = g;
				distancemin = Math.min(distancemin, distance);
			}				
		}	
			
		return ghostnear;
	}
	
	public double getMinPacmanDistancePPill() {
		return minPacmanDistancePPill;
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
	
	public double getBLINKYnearGhost() {
		return BLINKYnearGhost;
	}

	public double getINKYnearGhost() {
		return INKYnearGhost;
	}

	public double getPINKYnearGhost() {
		return PINKYnearGhost;
	}

	public double getSUEnearGhost() {
		return SUEnearGhost;
	}

	public double getBLINKYPacmandistance() {
		return BLINKYPacmandistance;
	}

	public double getINKYPacmandistance() {
		return INKYPacmandistance;
	}

	public double getPINKYPacmandistance() {
		return PINKYPacmandistance;
	}

	public double getSUEPacmandistance() {
		return SUEPacmandistance;
	}

	public GHOST getClosestGhosttoPill() {
		return closestGhosttoPill;
	}

	public double getMinGhostDistancePPill() {
		return minGhostDistancePPill;
	}
	
	public GHOST getBLINKYnearGhostEdible() {
		return BLINKYnearGhostEdible;
	}

	public GHOST getINKYnearGhostEdible() {
		return INKYnearGhostEdible;
	}

	public GHOST getPINKYnearGhostEdible() {
		return PINKYnearGhostEdible;
	}

	public GHOST getSUEnearGhostEdible() {
		return SUEnearGhostEdible;
	}

	public GHOST getBLINKYnearComingGhost() {
		return BLINKYnearComingGhost;
	}

	public GHOST getINKYnearComingGhost() {
		return INKYnearComingGhost;
	}

	public GHOST getPINKYnearComingGhost() {
		return PINKYnearComingGhost;
	}

	public GHOST getSUEnearComingGhost() {
		return SUEnearComingGhost;
	}

	public boolean isBLINKYSecurePath() {
		return BLINKYSecurePath;
	}

	public boolean isINKYSecurePath() {
		return INKYSecurePath;
	}

	public boolean isPINKYSecurePath() {
		return PINKYSecurePath;
	}

	public boolean isSUESecurePath() {
		return SUESecurePath;
	}	
}