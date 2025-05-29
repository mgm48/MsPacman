package es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts;

import pacman.game.Constants.GHOST;

public class Utiles {

	static private GHOST ghostSection1 = null;
	static private GHOST ghostSection2 = null;
	static private GHOST ghostSection3 = null;
	static private GHOST ghostSection4 = null;
	
	
	
	public Utiles() {
			
	}

	public static GHOST getGhostSection1() {
		return ghostSection1;
	}

	public static void setGhostSection1(GHOST ghostSection1) {
		Utiles.ghostSection1 = ghostSection1;
	}

	public static GHOST getGhostSection2() {
		return ghostSection2;
	}

	public static void setGhostSection2(GHOST ghostSection2) {
		Utiles.ghostSection2 = ghostSection2;
	}

	public static GHOST getGhostSection3() {
		return ghostSection3;
	}

	public static void setGhostSection3(GHOST ghostSection3) {
		Utiles.ghostSection3 = ghostSection3;
	}

	public static GHOST getGhostSection4() {
		return ghostSection4;
	}

	public static void setGhostSection4(GHOST ghostSection4) {
		Utiles.ghostSection4 = ghostSection4;
	}
	
	public static void emptySection(GHOST ghost) { //Limpiar variable cuando ya no sea necesaria 
		
		if(ghostSection1 == ghost) {
			ghostSection1 = null;
		}
		else if(ghostSection2 == ghost) {
			ghostSection2 = null;
		}
		else if(ghostSection3 == ghost) {
			ghostSection3 = null;
		}
		else if(ghostSection4 == ghost) {
			ghostSection4 = null;
		}
	}
}
