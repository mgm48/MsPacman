package es.ucm.fdi.ici.c2324.practica5.grupoYY.ghosts;

import java.util.Map;

import es.ucm.fdi.ici.fuzzy.FuzzyInput;
import pacman.game.Game;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;

public class GhostsInput extends FuzzyInput {
	
	private double[] distancepc;
	private int[] edibles;
	private double[] distancegh;//entre todos, la mas cercana ni idea

	
	public GhostsInput(Game game) {
		super(game);
	}

	@Override
	public Map<String, Double> getFuzzyValues() {
		// TODO aqui es para pasar los inputs a las reglas fuzzy
		return null;
	}

	@Override
	public void parseInput() {
		distancepc = new double[] {-1,-1,-1,-1};
		distancegh = new double[] {-1,-1,-1,-1};
		edibles = new int[] {0,0,0,0};

		for(GHOST gs: GHOST.values()) {
			int index = gs.ordinal();
		
			if(game.isGhostEdible(gs))
				edibles[index]=game.getGhostEdibleTime(gs);
			
			if(game.getPacmanCurrentNodeIndex() != -1)
				distancepc[index] = game.getPacmanCurrentNodeIndex();
			
			for(GHOST g: GHOST.values()) {
				
				int pos = game.getGhostCurrentNodeIndex(g);
				
				if(!g.equals(gs)&& game.getGhostLairTime(g)==0) {
					distancegh[index] = game.getDistance(game.getPacmanCurrentNodeIndex(), pos,game.getPacmanLastMoveMade(), DM.PATH);
				}
				else {
					distancegh[index] = -1;
				}
			}
		}
	}
	public boolean PacmanisVisible( int index )
	{
		return distancepc[index]!=-1;
	}
}
