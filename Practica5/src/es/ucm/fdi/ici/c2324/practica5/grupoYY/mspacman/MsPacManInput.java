package es.ucm.fdi.ici.c2324.practica5.grupoYY.mspacman;

import java.util.HashMap;

import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Game;
import es.ucm.fdi.ici.fuzzy.FuzzyInput;

public class MsPacManInput extends FuzzyInput {

	private double[] distanceghost;
	private double distancepill;
	private double distancePpill;
	private int[] timeedible;
	
	
	public MsPacManInput(Game game) {
		super(game);
	}
	
	@Override
	public void parseInput() {

		distanceghost = new double[] {-1,-1,-1,-1};
		distancepill = -1;
		distancePpill = -1;
		timeedible = new int[] {-1,-1,-1,-1};

		
		for(GHOST g: GHOST.values()) {
			int index = g.ordinal();
			int pos = game.getGhostCurrentNodeIndex(g);
			if(pos != -1 && game.getGhostLairTime(g)==0) {
				distanceghost[index] = game.getDistance(game.getPacmanCurrentNodeIndex(), pos,game.getPacmanLastMoveMade(), DM.PATH);
				if(game.isGhostEdible(g)) {
					timeedible[index]=game.getGhostEdibleTime(g);
				}
				else{
					timeedible[index] = 0;
				}
			}
			else {
				distanceghost[index] = -1;
			}
				
		}
		//calcular distancia a pill mas cercana
		int[] pills = game.getActivePillsIndices();
		double distance = Double.MAX_VALUE;
		if(pills.length!=0) {
			for(int pill: pills) {
				distance=Math.min(distance,game.getDistance(game.getPacmanCurrentNodeIndex(), pill,game.getPacmanLastMoveMade(), DM.PATH));
			}
			distancepill=distance;
		}
		//calcular distancia a PPill mas cercana
		int[] Ppills = game.getActivePowerPillsIndices();
		 distance = Double.MAX_VALUE;
		if(Ppills.length!=0) {
			for(int pill: Ppills) {
				distance=Math.min(distance,game.getDistance(game.getPacmanCurrentNodeIndex(), pill,game.getPacmanLastMoveMade(), DM.PATH));
			}
			distancePpill=distance;
		}
		
		
	}
	
	public boolean isVisible(GHOST ghost)
	{
		return distanceghost[ghost.ordinal()]!=-1;
	}
	
	

	@Override
	public HashMap<String, Double> getFuzzyValues() {
		HashMap<String,Double> vars = new HashMap<String,Double>();
		for(GHOST g: GHOST.values()) {
			vars.put(g.name()+"distance",   distanceghost[g.ordinal()]);
			vars.put(g.name()+"edible",   distanceghost[g.ordinal()]);
		}
		vars.put("Pilldistance", distancepill);
		vars.put("PPilldistance", distancePpill);
		return vars;
	}

}
