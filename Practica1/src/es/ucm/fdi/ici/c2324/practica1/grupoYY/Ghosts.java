package es.ucm.fdi.ici.c2324.practica1.grupoYY;


import java.util.EnumMap;
import java.util.Random;
import java.util.HashMap;

import pacman.controllers.GhostController;
import pacman.game.Game;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;


public final class Ghosts extends GhostController {
    private EnumMap<GHOST, MOVE> moves = new EnumMap<GHOST, MOVE>(GHOST.class);
    private MOVE[] allMoves = MOVE.values();
    private Random rnd = new Random();
    private int limit = 15;
    private int pacman = 0;
    private HashMap<GHOST, Integer> personalidad = new HashMap<GHOST, Integer>();
    private int cambio = 0;
    private int powerpill = 0;//la siguiente powerpill a la que va a ir
    private int powerpillsola = 0;// si hay solo una powerpill(evitar juego sucio)
    private int juntion=0;// cruce mas cercano a pacman
    private int cercano=0;//fantasma mas cerca de pacman
    @Override
    
    public EnumMap<GHOST, MOVE> getMove(Game game, long timeDue) {
        moves.clear();
      //inicial, nopowerpills, tiempo, puntos y vidas
    	if(game.getNumberOfActivePowerPills()==0) cambio=1;
    	else if(game.getPacmanNumberOfLivesRemaining()==1) cambio=2; 
    	else if(game.getTotalTime()>4000*game.getCurrentLevel()+1) cambio=3;// TODO mirar tiempo partida
    	else if(game.getScore()>2000) cambio=4;// TODO mirar puntuacion alta
    	cambioComportamiento(game);
        for (GHOST ghostType : GHOST.values()) {
           comportamiento(game,ghostType);
        }
        return moves;
    }  
    
    private void comportamiento(Game game,GHOST ghostType) {
    	
    	switch(personalidad.get(ghostType)) {
    	case 0:
    		fantasmaPerseguir(game,ghostType);	
    	break;
    	case 1:
    		fantasmaPowerpills(game,ghostType);	
    	break;
    	case 2:
    		proximoCruce(game,ghostType);	
    	break;
    	case 3:
    		imitarCercano(game,ghostType);	
    	break;
    	}
    
 
    }
    
    private void cambioComportamiento(Game game) {
		// 0 perseguir 1 vigilar pastillas 2 los cruces y 3 imitar
    	personalidad.clear();
    	switch(cambio) {
    	
    	case 0:											//caso inicial de comportamientos
    		int i = 0;
    		for (GHOST ghostType : GHOST.values()) {
    			personalidad.put(ghostType, i);
    			i++;
    		}
    		break;

    	case 1:											//caso no quedan powerpills
    		
    		personalidad.put(GHOST.BLINKY, 0);
    		personalidad.put(GHOST.PINKY, 3);
    		personalidad.put(GHOST.INKY, 2);
    		personalidad.put(GHOST.SUE, 3);
    			
    		break;
    	case 2: 										// no le quedan vidas
    		personalidad.put(GHOST.BLINKY, 0);
    		personalidad.put(GHOST.PINKY, 0);
    		personalidad.put(GHOST.INKY, 2);
    		personalidad.put(GHOST.SUE, 2);
    			
    		break;
    	case 3:											// mucho tiempo
    		personalidad.put(GHOST.BLINKY, 2);
    		personalidad.put(GHOST.PINKY, 2);
    		personalidad.put(GHOST.INKY, 0);
    		personalidad.put(GHOST.SUE, 3);
    			
    		break;
    	case 4:										// muchos puntos
    		personalidad.put(GHOST.BLINKY, 1);
    		personalidad.put(GHOST.PINKY, 0);
    		personalidad.put(GHOST.INKY, 1);
    		personalidad.put(GHOST.SUE, 2);
    			
    		break;
    	}
    }
    
    private void fantasmaPerseguir(Game game,GHOST ghostType){// perseguir a pacman
    	if (game.doesGhostRequireAction(ghostType)) {
        	pacman = game.getPacManInitialNodeIndex();
            
        	if(game.getGhostEdibleTime(ghostType)<=0 || !PowerPillNear(game,limit)) 
             	 moves.put(ghostType, game.getApproximateNextMoveTowardsTarget(game.getGhostCurrentNodeIndex(ghostType), pacman, game.getGhostLastMoveMade(ghostType), DM.PATH));	
            else
            	 moves.put(ghostType, huir(game,ghostType));
			}
    }
    
    private void fantasmaPowerpills(Game game,GHOST ghostType){// vigila las powerpills
    	
    	limit = 5;
    	
    	if (game.doesGhostRequireAction(ghostType)) {
        	pacman = game.getPacManInitialNodeIndex();
            
        	if(game.getGhostEdibleTime(ghostType)<=0 || !PowerPillNear(game,limit)) {
             	 if(game.getDistance(game.getGhostCurrentNodeIndex(ghostType), pacman, DM.PATH) < limit)
             		 moves.put(ghostType, game.getApproximateNextMoveTowardsTarget(game.getGhostCurrentNodeIndex(ghostType), pacman, game.getGhostLastMoveMade(ghostType), DM.PATH));	
             	 else if(game.getDistance(game.getGhostCurrentNodeIndex(ghostType), game.getActivePowerPillsIndices()[powerpill], DM.PATH) < limit && game.getNumberOfActivePowerPills() > 1) {
             		 powerpill++;
             		 powerpill = (powerpill % game.getNumberOfActivePowerPills()) - 1;
             		 moves.put(ghostType, game.getApproximateNextMoveTowardsTarget(game.getGhostCurrentNodeIndex(ghostType), powerpillActual(game), game.getGhostLastMoveMade(ghostType), DM.PATH));   
             	 }
             	 else if(game.getDistance(game.getGhostCurrentNodeIndex(ghostType), game.getActivePowerPillsIndices()[powerpill], DM.PATH) < limit && game.getNumberOfActivePowerPills() == 1) {
            		moves.put(ghostType, game.getApproximateNextMoveTowardsTarget(game.getGhostCurrentNodeIndex(ghostType), powerpillActual(game), game.getGhostLastMoveMade(ghostType), DM.PATH));
             	 }
        	}
             else
            	 moves.put(ghostType, huir(game,ghostType));
			}
        	
    }
    
    private void proximoCruce(Game game, GHOST ghostType){// va al siguiente cruce y si pacman se lo puede comer huye
    	if (game.doesGhostRequireAction(ghostType)) {   
    		if(game.getGhostEdibleTime(ghostType)<=0 || !PowerPillNear(game,limit)) {
				juntion =  getNearestJuntion(game,limit);
             	 moves.put(ghostType, game.getApproximateNextMoveAwayFromTarget(game.getGhostCurrentNodeIndex(ghostType), juntion, game.getGhostLastMoveMade(ghostType), DM.PATH));	
			}
            else
            	 moves.put(ghostType, huir(game,ghostType));
			}
    }
    private void imitarCercano(Game game, GHOST ghostType){// sigue al fantasma m�s cercano a pacman
    	if (game.doesGhostRequireAction(ghostType)) {   
			if(game.getGhostEdibleTime(ghostType)<=0 || !PowerPillNear(game,limit)) {
				cercano=getNearestGhost(game,limit,ghostType);
             	 moves.put(ghostType, game.getApproximateNextMoveTowardsTarget(game.getGhostCurrentNodeIndex(ghostType), cercano, game.getGhostLastMoveMade(ghostType), DM.PATH));	
			}
            else
             	 moves.put(ghostType, huir(game,ghostType));
    	}
    }
    
 
    
    private boolean PacManEdible(Game game,GHOST ghostType) {// devuelve true si el fantasma es comible

    		return game.getGhostEdibleTime(ghostType)>0 ;
    	 	
    }
    
    private boolean PowerPillNear(Game game,int limit) {
    	
    	if(getNearestPillDistance(game, limit) < limit)
    		return true;
    	else
    		return false;  
    }
    
    private double getNearestPillDistance(Game game, int limit){
		
		int[] pills = game.getActivePillsIndices();
		int[] pPills = game.getActivePowerPillsIndices();
		double d = limit;

		for(int i = 0; i < game.getNumberOfActivePills(); i++){
			
			if(game.getDistance(pills[i], game.getPacmanCurrentNodeIndex(), DM.PATH) <= d) {
				d = game.getDistance(pills[i], game.getPacmanCurrentNodeIndex(), DM.PATH);
			}
		}
		
		for(int i = 0; i < game.getNumberOfActivePowerPills(); i++){
			
			if(game.getDistance(pPills[i], game.getPacmanCurrentNodeIndex(), DM.PATH) <= d) {
				d = game.getDistance(pPills[i], game.getPacmanCurrentNodeIndex(), DM.PATH);
			}
		}
		return d;
	}
    private int getNearestJuntion(Game game, int limit){
		
  		int[] cruces = game.getJunctionIndices();

  		double d = limit;
  		int res=0;
  		for(int i = 0; i < cruces.length; i++){
  			
  			if(game.getDistance(cruces[i], game.getPacmanCurrentNodeIndex(), DM.PATH) <= d) {
  				d = game.getDistance(cruces[i], game.getPacmanCurrentNodeIndex(), DM.PATH);
  				res=cruces[i];
  			}
  		}
  		
  		
  		return res;
  	}
    
    private int getNearestGhost(Game game, int limit,GHOST ghostactual){// metodo que devuelve el fantasma mas cercano a pacman
		

  		double d = limit;
  		int res=0;
  		 for (GHOST ghostType : GHOST.values()) {
  			 if(!ghostType.equals(ghostactual) && game.getDistance(game.getGhostCurrentNodeIndex(ghostType),game.getPacmanCurrentNodeIndex() , DM.PATH) <= d) {
  				res= game.getGhostCurrentNodeIndex(ghostType);
  			 }
  		 }
  		
  		
  		return res;
  	}
    
    private int powerpillActual(Game game) { //metodo que devuelve la posicion de la powerpill a la que se dirige el fantasma
    	
    	int res = 0;
    	
    	if(game.getNumberOfActivePowerPills() > 1) {
	    	int[] powerpills = game.getActivePowerPillsIndices();
	    	res = powerpills[powerpill];
	    	
    	}else {
    		
    		if(powerpillsola == 0) {
    			
    		}else
    			powerpillsola = 0;
    		
    		int[] powerpills = game.getPowerPillIndices();
	    	res = powerpills[powerpillsola];
    		
    	}
    	
    	return res;
    }
    private MOVE huir(Game game, GHOST ghostType) {
    	int fantasma=fantasmaVivo(game,ghostType);
    	if(fantasma>-1) {
    		return game.getApproximateNextMoveTowardsTarget(game.getGhostCurrentNodeIndex(ghostType),fantasma ,game.getGhostLastMoveMade(ghostType) , DM.PATH);
    	}
		return game.getApproximateNextMoveAwayFromTarget(game.getGhostCurrentNodeIndex(ghostType), game.getPacmanCurrentNodeIndex(), game.getGhostLastMoveMade(ghostType), DM.PATH);
    	
    }
    private int fantasmaVivo(Game game, GHOST ghostactual) {
    	
  		int res=-1;
    	 for (GHOST ghostType : GHOST.values()) {
  			 if(!ghostType.equals(ghostactual) && game.getGhostEdibleTime(ghostType)<=0 ) {
  				res= game.getGhostCurrentNodeIndex(ghostType);
  			 }
  		 }
		return res;
    	
    }
    
}

