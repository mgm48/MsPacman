package es.ucm.fdi.ici.c2324.practica5.grupoYY.mspacman.actions;


import java.util.Random;

import es.ucm.fdi.ici.Action;
import pacman.game.Constants.DM;
import pacman.game.Constants.MOVE;
import pacman.game.Game;
import pacman.game.internal.Maze;

public class GoToPPillAction implements Action {
    
	private Random rnd = new Random();
	public GoToPPillAction() {
	}
	
	@Override
	public MOVE execute(Game game) {
		Maze maze = game.getCurrentMaze();
		int[] Ppill = maze.powerPillIndices;
		MOVE[] possibleMoves = game.getPossibleMoves(game.getPacmanCurrentNodeIndex(), game.getPacmanLastMoveMade());
		System.out.println("I am Powerpilling");
		double distance = Double.MAX_VALUE;
		int mascercana=-1;
		for(int pill:Ppill) {
			if(game.isNodeObservable(pill)&& game.getPowerPillIndex(pill)!=-1){
				if(game.isPowerPillStillAvailable(game.getPowerPillIndex(pill)) && distance>game.getDistance(game.getPacmanCurrentNodeIndex(), pill,game.getPacmanLastMoveMade(), DM.PATH)){
					mascercana=pill;
					distance=game.getDistance(game.getPacmanCurrentNodeIndex(), pill,game.getPacmanLastMoveMade(), DM.PATH);
				}
			}
			
		}
		if(mascercana != -1){
			return game.getApproximateNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), mascercana, game.getPacmanLastMoveMade(), DM.PATH);
		}

		return possibleMoves[rnd.nextInt(possibleMoves.length)];		
    }

	@Override
	public String getActionId() {
		return "GoToPPill";
	}
            
}
