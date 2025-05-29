package es.ucm.fdi.ici.c2324.practica3.grupo03.ghost;

import java.util.ArrayList;

import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRQuery;
import es.ucm.fdi.ici.cbr.CBRInput;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;
import pacman.game.internal.Ghost;

public class GhostInput extends CBRInput {
	
	
	public GhostInput(Game game) {
		super(game);
	}
	public GhostInput(GHOST ghost, Game game) {
		this(game);	
	}
	
	ArrayList<Double> DistanciaGhost= new ArrayList<Double>(4);
	Integer TiempoComestible;
	ArrayList<Double> DistanciaPP= new ArrayList<Double>(4);
	ArrayList<Double> DistanciaPacman= new ArrayList<Double>(4);
	Integer numeroVidas;
	Integer nivel;
	Integer score;
	Integer nPP;
	GHOST ghost;
	Double distancia;
	
	
	@Override
	public void parseInput() {
		if(ghost!=null) {
			computeNearestGhost(game);
			computeNearestPPill(game);
			computePacman(game);
			TiempoComestible =game.getGhostEdibleTime(ghost);
			score = game.getScore();
			numeroVidas=game.getPacmanNumberOfLivesRemaining();
			nivel=game.getCurrentLevel();
			nPP=game.getNumberOfActivePowerPills();
			distancia = game.getDistance(game.getGhostCurrentNodeIndex(ghost), game.getPacmanCurrentNodeIndex(),game.getGhostLastMoveMade(ghost) ,DM.PATH);
		}
	}

	@Override
	public CBRQuery getQuery() {
		GhostDescription description = new GhostDescription();
		description.setDistanciaGhost(DistanciaGhost);
		description.setDistanciaPacman(DistanciaPacman);
		description.setDistanciaPP(DistanciaPP);
		description.setTiempoComestible(TiempoComestible);
		description.setScore(score);
		description.setNumeroVidas(numeroVidas);
		description.setNivel(nivel);
		description.setNPP(nPP);
		description.setDistancia(distancia);
		description.setGhost(ghost);
		CBRQuery query = new CBRQuery();
		query.setDescription(description);
		return query;
	}
	
	private void computeNearestGhost(Game game) {
		DistanciaGhost= new ArrayList<Double>(4);
		for(int i=0;i<4;i++) {
			DistanciaGhost.add(i,-1.0);
		}
		
		if(game.getGhostLairTime(ghost) == 0) {
			for(MOVE move : game.getPossibleMoves(game.getPacmanCurrentNodeIndex(), game.getPacmanLastMoveMade())) {
				if(move != MOVE.NEUTRAL) {
					int i = moveIndex(move);
					for(GHOST g: GHOST.values()) {
						if(!g.equals(ghost)) {
							double distance = -1;
							if(game.getGhostLairTime(g)==0) {
								distance = game.getDistance(game.getGhostCurrentNodeIndex(ghost), game.getGhostCurrentNodeIndex(g),move, DM.PATH);
							}
							if((distance < DistanciaGhost.get(i)&& distance!=-1)||(DistanciaGhost.get(i)==-1)){
								DistanciaGhost.set(i, distance);
							}
						}
					}
				}
			}
		}	
	}
	private void computePacman(Game game) {
		DistanciaPacman= new ArrayList<Double>(4);
		for(int i=0;i<4;i++) {
			DistanciaPacman.add(i,-1.0);
		}
		if(game.getGhostLairTime(ghost)==0) {
			for(MOVE move : game.getPossibleMoves(game.getPacmanCurrentNodeIndex(), game.getPacmanLastMoveMade())) {
				if(move != MOVE.NEUTRAL) {
					int i = moveIndex(move);
					DistanciaPacman.set(i, game.getDistance(game.getGhostCurrentNodeIndex(ghost), game.getPacmanCurrentNodeIndex(),move, DM.PATH));

				}
			}
		}
	}
	
	private void computeNearestPPill(Game game) {
		DistanciaPP= new ArrayList<Double>(4);
		for(int i=0;i<4;i++) {
			DistanciaPP.add(i,-1.0);
		}
		if(game.getGhostLairTime(ghost)==0) {
			for(MOVE move : game.getPossibleMoves(game.getPacmanCurrentNodeIndex(), game.getPacmanLastMoveMade())) {
				if(move != MOVE.NEUTRAL) {
					int i = moveIndex(move);
					for(int pos: game.getActivePowerPillsIndices()) {
						double distance = game.getDistance(game.getPacmanCurrentNodeIndex(), pos,move, DM.PATH);
						if(distance < DistanciaPP.get(i) || DistanciaPP.get(i) == -1)
							DistanciaPP.set(i,distance);
						
					}
				}
			}
		}
	}
	public void setGhost(GHOST ghost) {
		this.ghost=ghost;
	}
	public GHOST getGhost() {
		return this.ghost;
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