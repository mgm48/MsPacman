package es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.actions;

import java.util.ArrayList;
import java.util.Random;

import es.ucm.fdi.ici.Action;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class BlockSectionAction implements Action {
	
	GHOST ghost;
	public BlockSectionAction(GHOST ghost) {
		this.ghost = ghost;
	}
	
	@Override
	public MOVE execute(Game game) {

		 if (game.doesGhostRequireAction(ghost)){        //if it requires an action
	        
			int[] cruces = game.getJunctionIndices();
	  		int coorx=game.getNodeXCood(game.getPacmanCurrentNodeIndex());
	  		int coory=game.getNodeYCood(game.getPacmanCurrentNodeIndex());
	  	
	  		ArrayList<Integer> cuadrante= new ArrayList<Integer>();

	  		for(int i = 0; i < cruces.length; i++){

	  			int coorxJ=game.getNodeXCood(cruces[i]);
	  			int cooryJ=game.getNodeYCood(cruces[i]);

	  			if(isInSection(coorx,coory,coorxJ,cooryJ)) 
	  				cuadrante.add(cruces[i]);
	  			
	  		}

	  		if(cuadrante.isEmpty()) 
	  			return game.getApproximateNextMoveTowardsTarget(game.getGhostCurrentNodeIndex(ghost), game.getPacmanCurrentNodeIndex(), game.getGhostLastMoveMade(ghost), DM.PATH);
	  		
			Random r = new Random();// va a un cruce aleatorio del cuadrante

	  		return game.getApproximateNextMoveTowardsTarget(game.getGhostCurrentNodeIndex(ghost), cuadrante.get(r.nextInt(cuadrante.size())), game.getGhostLastMoveMade(ghost), DM.PATH);
	     }

		return MOVE.NEUTRAL;
	}
	
	private boolean isInSection(int coorx, int coory, int coorxJ, int cooryJ) {
		int minxleft=coorx-10,maxxleft=coorx-20,minyup=coory-10,maxyup=coory-20;
  		int minxright=coorx+10,maxxright=coorx+20,minydown=coory+10,maxydown=coory+20;
		return (cooryJ<maxyup && maxydown<cooryJ && coorxJ>maxxleft && minxleft<coorxJ)|| (cooryJ<maxyup && maxydown<cooryJ && coorxJ<maxxright && minxright<coorxJ)
	  			||(cooryJ<maxyup && minyup>cooryJ && coorxJ>maxxleft && maxxright<coorxJ) || (cooryJ>maxydown && minydown<cooryJ && coorxJ>maxxleft && maxxright<coorxJ);
	}

	@Override
	public String getActionId() {
		return ghost+" is blocking a section";
	}
}