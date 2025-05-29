package es.ucm.fdi.ici.c2324.practica3.grupo03.mspacman;


import java.util.ArrayList;

import es.ucm.fdi.gaia.jcolibri.cbrcore.Attribute;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CaseComponent;

public class MsPacManDescription implements CaseComponent {

	Integer id;
	
	Double distanciaGhostLEFT;
	Double distanciaGhostRIGHT;
	Double distanciaGhostUP;
	Double distanciaGhostDOWN;
	Integer tiempoComestibleLEFT;
	Integer tiempoComestibleRIGHT;
	Integer tiempoComestibleUP;
	Integer tiempoComestibleDOWN;
	Double distanciaPPLEFT;
	Double distanciaPPRIGHT;
	Double distanciaPPUP;
	Double distanciaPPDOWN;
	Double distanciaPillLEFT;
	Double distanciaPillRIGHT;
	Double distanciaPillUP;
	Double distanciaPillDOWN;
	
	Integer numeroVidas;
	Integer nivel;
	Integer score;
	Integer nPP;
	
	
	public Integer getNPP() {
		return nPP;
	}

	public void setNPP(Integer nPP) {
		this.nPP = nPP;
	}

	public Integer getNumeroVidas() {
		return numeroVidas;
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
		distanciaGhostLEFT = distanciaGhostLEFT;
	}

	public Double getDistanciaGhostRIGHT() {
		return distanciaGhostRIGHT;
	}

	public void setDistanciaGhostRIGHT(Double distanciaGhostRIGHT) {
		distanciaGhostRIGHT = distanciaGhostRIGHT;
	}

	public Double getDistanciaGhostUP() {
		return distanciaGhostUP;
	}

	public void setDistanciaGhostUP(Double distanciaGhostUP) {
		distanciaGhostUP = distanciaGhostUP;
	}

	public Double getDistanciaGhostDOWN() {
		return distanciaGhostDOWN;
	}

	public void setDistanciaGhostDOWN(Double distanciaGhostDOWN) {
		distanciaGhostDOWN = distanciaGhostDOWN;
	}

	public Integer getTiempoComestibleLEFT() {
		return tiempoComestibleLEFT;
	}

	public void setTiempoComestibleLEFT(Integer tiempoComestibleLEFT) {
		tiempoComestibleLEFT = tiempoComestibleLEFT;
	}

	public Integer getTiempoComestibleRIGHT() {
		return tiempoComestibleRIGHT;
	}

	public void setTiempoComestibleRIGHT(Integer tiempoComestibleRIGHT) {
		this.tiempoComestibleRIGHT = tiempoComestibleRIGHT;
	}

	public Integer getTiempoComestibleUP() {
		return tiempoComestibleUP;
	}

	public void setTiempoComestibleUP(Integer tiempoComestibleUP) {
		this.tiempoComestibleUP = tiempoComestibleUP;
	}

	public Integer getTiempoComestibleDOWN() {
		return tiempoComestibleDOWN;
	}

	public void setTiempoComestibleDOWN(Integer tiempoComestibleDOWN) {
		this.tiempoComestibleDOWN = tiempoComestibleDOWN;
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

	public Double getDistanciaPillLEFT() {
		return distanciaPillLEFT;
	}

	public void setDistanciaPillLEFT(Double distanciaPillLEFT) {
		this.distanciaPillLEFT = distanciaPillLEFT;
	}

	public Double getDistanciaPillRIGHT() {
		return distanciaPillRIGHT;
	}

	public void setDistanciaPillRIGHT(Double distanciaPillRIGHT) {
		this.distanciaPillRIGHT = distanciaPillRIGHT;
	}

	public Double getDistanciaPillUP() {
		return distanciaPillUP;
	}

	public void setDistanciaPillUP(Double distanciaPillUP) {
		this.distanciaPillUP = distanciaPillUP;
	}

	public Double getDistanciaPillDOWN() {
		return distanciaPillDOWN;
	}

	public void setDistanciaPillDOWN(Double distanciaPillDOWN) {
		this.distanciaPillDOWN = distanciaPillDOWN;
	}

	public void setDistanciaGhost(ArrayList<Double> ghost) {
		distanciaGhostLEFT=ghost.get(0);
		distanciaGhostRIGHT=ghost.get(1);
		distanciaGhostUP=ghost.get(2);
		distanciaGhostDOWN=ghost.get(3);
	}
	public void setDistanciaPill(ArrayList<Double> ghost) {
		distanciaPillLEFT=ghost.get(0);
		distanciaPillRIGHT=ghost.get(1);
		distanciaPillUP=ghost.get(2);
		distanciaPillDOWN=ghost.get(3);
	}
	public void setDistanciaPP(ArrayList<Double> ghost) {
		distanciaPPLEFT=ghost.get(0);
		distanciaPPRIGHT=ghost.get(1);
		distanciaPPUP=ghost.get(2);
		distanciaPPDOWN=ghost.get(3);
	}
	public void setTiempoComestible(ArrayList<Integer> ghost) {
		tiempoComestibleLEFT=ghost.get(0);
		tiempoComestibleRIGHT=ghost.get(1);
		tiempoComestibleUP=ghost.get(2);
		tiempoComestibleDOWN=ghost.get(3);
	}
	
	@Override
	public Attribute getIdAttribute() {
		return new Attribute("id", MsPacManDescription.class);
	}

	@Override
	public String toString() {
		return "MsPacManDescription [id=" + id +", Distancia Ghost=" + distanciaGhostLEFT + distanciaGhostRIGHT+  distanciaGhostUP+
				distanciaGhostDOWN+", Tiempo Comestible="+ tiempoComestibleLEFT+tiempoComestibleRIGHT+tiempoComestibleUP+tiempoComestibleDOWN +
				", DistanciaPP=" + distanciaPPLEFT+distanciaPPRIGHT+distanciaPPUP+distanciaPPDOWN + ", DistanciaPill=" + distanciaPillLEFT+
				distanciaPillRIGHT+distanciaPillUP+distanciaPillDOWN+ "]";
	}

}
