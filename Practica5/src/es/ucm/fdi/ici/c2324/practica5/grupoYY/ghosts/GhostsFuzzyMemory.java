package es.ucm.fdi.ici.c2324.practica5.grupoYY.ghosts;

import java.util.HashMap;

import es.ucm.fdi.ici.c2324.practica5.grupoYY.mspacman.MsPacManInput;
import pacman.game.Constants.GHOST;

public class GhostsFuzzyMemory {//TODO solo esta copiado del pacman. 
	//supongo que habra que poner la confianza de pacman o algo asi
	HashMap<String,Double> mem;
	
	double[] confidence = {100,100,100,100};//confianza de cada fantasma a pacaca

	
	public GhostsFuzzyMemory() {
		mem = new HashMap<String,Double>();
	}
	
	public void getInput(GhostsInput input)
	{
		for(GHOST g: GHOST.values()) {
			double conf = confidence[g.ordinal()];
			if(input.PacmanisVisible(g.ordinal())) {
				conf = 100;
			}	
			else {
				conf = Double.max(0, conf-5);
			}
			confidence[g.ordinal()]=conf;
			mem.put(g.name()+"confidence", conf);			
		}

	}
	
	public HashMap<String, Double> getFuzzyValues() {
		return mem;
	}
}
