package es.ucm.fdi.ici.c2324.practica3.grupo03;

import java.util.EnumMap;

import es.ucm.fdi.gaia.jcolibri.exception.ExecutionException;
import es.ucm.fdi.ici.c2324.practica3.grupo03.ghost.GhostCBRengine;
import es.ucm.fdi.ici.c2324.practica3.grupo03.ghost.GhostInput;
import es.ucm.fdi.ici.c2324.practica3.grupo03.ghost.GhostStorageManager;
import pacman.controllers.GhostController;
import pacman.game.Game;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public class Ghosts extends GhostController{

	GhostCBRengine cbrEngine;
	GhostStorageManager storageManager;
		
	public Ghosts()
	{		
		this.storageManager = new GhostStorageManager();
		cbrEngine = new GhostCBRengine(storageManager);
	}
	
	@Override
	public void preCompute(String opponent) {
		cbrEngine.setOpponent(opponent);
		try {
			cbrEngine.configure();
			cbrEngine.preCycle();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void postCompute() {
		try {
			cbrEngine.postCycle();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public EnumMap<GHOST, MOVE> getMove(Game game, long timeDue) {
		EnumMap<GHOST, MOVE> movimientos = new EnumMap<GHOST,MOVE>(GHOST.class);;
		for(GHOST ghost: GHOST.values()){
			if(!game.isJunction(game.getGhostCurrentNodeIndex(ghost)))
				movimientos.put(ghost, MOVE.NEUTRAL);
			else {
				try {
					GhostInput input = new GhostInput(game);
					input.setGhost(ghost);
					input.parseInput();
					storageManager.setGame(game);
					cbrEngine.cycle(input.getQuery());
					MOVE move = cbrEngine.getSolution();
					movimientos.put(ghost,move);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return movimientos;
	}

}

