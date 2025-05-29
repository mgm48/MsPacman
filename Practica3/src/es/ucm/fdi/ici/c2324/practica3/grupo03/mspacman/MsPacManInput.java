package es.ucm.fdi.ici.c2324.practica3.grupo03.mspacman;

import java.util.ArrayList;

import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRQuery;
import es.ucm.fdi.ici.cbr.CBRInput;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class MsPacManInput extends CBRInput {

	public MsPacManInput(Game game) {
		super(game);
		
	}
	ArrayList<Double> DistanciaGhost= new ArrayList<Double>(4);
	ArrayList<Integer> TiempoComestible= new ArrayList<Integer>(4);
	ArrayList<Double> DistanciaPP= new ArrayList<Double>(4);
	ArrayList<Double> DistanciaPill= new ArrayList<Double>(4);
	Integer numeroVidas;
	Integer nivel;
	Integer score;
	Integer nPP;
	
	@Override
	public void parseInput() {
		computeNearestGhost(game);
		computeNearestPPill(game);
		score = game.getScore();
		numeroVidas=game.getPacmanNumberOfLivesRemaining();
		nivel=game.getCurrentLevel();
		nPP=game.getNumberOfActivePowerPills();
	}

	@Override
	public CBRQuery getQuery() {
		MsPacManDescription description = new MsPacManDescription();
		description.setDistanciaGhost(DistanciaGhost);
		description.setDistanciaPill(DistanciaPill);
		description.setDistanciaPP(DistanciaPP);
		description.setTiempoComestible(TiempoComestible);
		description.setScore(score);
		description.setNumeroVidas(numeroVidas);
		description.setNivel(nivel);
		description.setNPP(nPP);
		CBRQuery query = new CBRQuery();
		query.setDescription(description);
		return query;
	}
	
	private void computeNearestGhost(Game game) {
		DistanciaGhost= new ArrayList<Double>(4);
		TiempoComestible= new ArrayList<Integer>(4);
		for(int i=0;i<4;i++) {
			DistanciaGhost.add(i,-1.0);
			TiempoComestible.add(i,0);
		}
		for(MOVE move : game.getPossibleMoves(game.getPacmanCurrentNodeIndex(), game.getPacmanLastMoveMade())) {
			if(move != MOVE.NEUTRAL) {
				int i = moveIndex(move);
				GHOST nearest = null;
				for(GHOST g: GHOST.values()) {
					double distance = -1;
					if(game.getGhostLairTime(g) == 0) {
						distance = game.getDistance(game.getPacmanCurrentNodeIndex(),game.getGhostCurrentNodeIndex(g),move, DM.PATH);
					}	
					if((distance < DistanciaGhost.get(i) && distance !=-1)||(DistanciaGhost.get(i) == -1)){
						DistanciaGhost.set(i, distance);
						nearest = g;
					}
				}
				if(nearest != null)
					TiempoComestible.set(i,game.getGhostEdibleTime(nearest));
			}
		}
	}
	
	private void computeNearestPPill(Game game) {
		DistanciaPP= new ArrayList<Double>(4);
		DistanciaPill= new ArrayList<Double>(4);
		for(int i=0;i<4;i++) {
			DistanciaPP.add(i,-1.0);
			DistanciaPill.add(i,-1.0);
		}

		for(MOVE move : game.getPossibleMoves(game.getPacmanCurrentNodeIndex(), game.getPacmanLastMoveMade())) {
			if(move != MOVE.NEUTRAL) {
				int i = moveIndex(move);
				for(int pos: game.getActivePowerPillsIndices()) {
					double distance = game.getDistance(game.getPacmanCurrentNodeIndex(), pos,move, DM.PATH);
					if(distance < DistanciaPP.get(i) || DistanciaPP.get(i) == -1)
						DistanciaPP.set(i,distance);
				}
				for(int pos: game.getActivePillsIndices()) {
					double distance = game.getDistance(game.getPacmanCurrentNodeIndex(), pos, move, DM.PATH);
					if(distance < DistanciaPill.get(i) || DistanciaPill.get(i) == -1)
						DistanciaPill.set(i,distance);
				
				}
			}
		}
		
	}
	
	int moveIndex(MOVE move) {
		if(move == MOVE.LEFT) {
			return 0;
		}
		if(move == MOVE.RIGHT) {
			return 1;
		}
		if(move == MOVE.UP) {
			return 2;
		}
		if(move == MOVE.DOWN) {
			return 3;
		}
		return -1;
	}
	
}