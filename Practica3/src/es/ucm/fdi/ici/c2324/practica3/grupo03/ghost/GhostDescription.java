package es.ucm.fdi.ici.c2324.practica3.grupo03.ghost;


import java.util.ArrayList;

import es.ucm.fdi.gaia.jcolibri.cbrcore.Attribute;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CaseComponent;
import pacman.game.Constants.GHOST;

public class GhostDescription implements CaseComponent {

	Integer id;
	
	Double distanciaGhostLEFT;
	Double distanciaGhostRIGHT;
	Double distanciaGhostUP;
	Double distanciaGhostDOWN;
	Integer tiempoComestible;
	Double distanciaPPLEFT;
	Double distanciaPPRIGHT;
	Double distanciaPPUP;
	Double distanciaPPDOWN;
	Double distanciaPacmanLEFT;
	Double distanciaPacmanRIGHT;
	Double distanciaPacmanUP;
	Double distanciaPacmanDOWN;
	
	GHOST ghost;
	Double distancia;
	Integer numeroVidas;
	Integer nivel;
	Integer score;
	Integer nPP;
	
	public Integer getNPP() {
		return nPP;
	}

	public Double getDistancia() {
		return distancia;
	}

	public void setDistancia(Double distancia) {
		this.distancia = distancia;
	}

	public void setNPP(Integer nPP) {
		this.nPP = nPP;
	}

	public Integer getNumeroVidas() {
		return this.numeroVidas;
	}

	public void setNumeroVidas(Integer numeroVidas) {
		this.numeroVidas = numeroVidas;
	}

	public Integer getNivel() {
		return nivel;
	}

	public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Double getDistanciaGhostLEFT() {
		return distanciaGhostLEFT;
	}

	public void setDistanciaGhostLEFT(Double distanciaGhostLEFT) {
		this.distanciaGhostLEFT = distanciaGhostLEFT;
	}

	public Double getDistanciaGhostRIGHT() {
		return distanciaGhostRIGHT;
	}

	public void setDistanciaGhostRIGHT(Double distanciaGhostRIGHT) {
		this.distanciaGhostRIGHT = distanciaGhostRIGHT;
	}

	public Double getDistanciaGhostUP() {
		return distanciaGhostUP;
	}

	public void setDistanciaGhostUP(Double distanciaGhostUP) {
		this.distanciaGhostUP = distanciaGhostUP;
	}

	public Double getDistanciaGhostDOWN() {
		return distanciaGhostDOWN;
	}

	public void setDistanciaGhostDOWN(Double distanciaGhostDOWN) {
		this.distanciaGhostDOWN = distanciaGhostDOWN;
	}

	public Double getDistanciaPPLEFT() {
		return distanciaPPLEFT;
	}

	public void setDistanciaPPLEFT(Double distanciaPPLEFT) {
		this.distanciaPPLEFT = distanciaPPLEFT;
	}

	public Double getDistanciaPPRIGHT() {
		return distanciaPPRIGHT;
	}

	public void setDistanciaPPRIGHT(Double distanciaPPRIGHT) {
		this.distanciaPPRIGHT = distanciaPPRIGHT;
	}

	public Double getDistanciaPPUP() {
		return distanciaPPUP;
	}

	public void setDistanciaPPUP(Double distanciaPPUP) {
		this.distanciaPPUP = distanciaPPUP;
	}

	public Double getDistanciaPPDOWN() {
		return distanciaPPDOWN;
	}

	public void setDistanciaPPDOWN(Double distanciaPPDOWN) {
		this.distanciaPPDOWN = distanciaPPDOWN;
	}

	public Integer getTiempoComestible() {
		return tiempoComestible;
	}

	public void setTiempoComestible(Integer tiempoComestible) {
		this.tiempoComestible = tiempoComestible;
	}

	public Double getDistanciaPacmanLEFT() {
		return distanciaPacmanLEFT;
	}

	public void setDistanciaPacmanLEFT(Double distanciaPacmanLEFT) {
		this.distanciaPacmanLEFT = distanciaPacmanLEFT;
	}

	public Double getDistanciaPacmanRIGHT() {
		return distanciaPacmanRIGHT;
	}

	public void setDistanciaPacmanRIGHT(Double distanciaPacmanRIGHT) {
		this.distanciaPacmanRIGHT = distanciaPacmanRIGHT;
	}

	public Double getDistanciaPacmanUP() {
		return distanciaPacmanUP;
	}

	public void setDistanciaPacmanUP(Double distanciaPacmanUP) {
		this.distanciaPacmanUP = distanciaPacmanUP;
	}

	public Double getDistanciaPacmanDOWN() {
		return distanciaPacmanDOWN;
	}

	public void setDistanciaPacmanDOWN(Double distanciaPacmanDOWN) {
		this.distanciaPacmanDOWN = distanciaPacmanDOWN;
	}

	public Integer getnPP() {
		return nPP;
	}

	public void setnPP(Integer nPP) {
		this.nPP = nPP;
	}

	public void setDistanciaGhost(ArrayList<Double> ghost) {
		distanciaGhostLEFT=ghost.get(0);
		distanciaGhostRIGHT=ghost.get(1);
		distanciaGhostUP=ghost.get(2);
		distanciaGhostDOWN=ghost.get(3);
	}
	public void setDistanciaPacman(ArrayList<Double> ghost) {
		distanciaPacmanLEFT=ghost.get(0);
		distanciaPacmanRIGHT=ghost.get(1);
		distanciaPacmanUP=ghost.get(2);
		distanciaPacmanDOWN=ghost.get(3);
	}
	public void setDistanciaPP(ArrayList<Double> ghost) {
		distanciaPPLEFT=ghost.get(0);
		distanciaPPRIGHT=ghost.get(1);
		distanciaPPUP=ghost.get(2);
		distanciaPPDOWN=ghost.get(3);
	}
	
	public GHOST getGhost() {
		return ghost;
	}

	public void setGhost(GHOST ghost) {
		this.ghost = ghost;
	}

	@Override
	public Attribute getIdAttribute() {
		return new Attribute("id", GhostDescription.class);
	}

	@Override
	public String toString() {
		return "GhostDescription [id=" + id +", Distancia Ghost=" + distanciaGhostLEFT + distanciaGhostRIGHT+  distanciaGhostUP+
				distanciaGhostDOWN+", Tiempo Comestible="+ tiempoComestible+
				", DistanciaPP=" + distanciaPPLEFT+distanciaPPRIGHT+distanciaPPUP+distanciaPPDOWN + ", DistanciaPacman=" + distanciaPacmanLEFT+
				distanciaPacmanRIGHT+distanciaPacmanUP+distanciaPacmanDOWN+ "]";
	}
}