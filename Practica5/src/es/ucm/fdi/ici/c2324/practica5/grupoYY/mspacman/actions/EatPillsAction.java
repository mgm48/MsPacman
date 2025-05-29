package es.ucm.fdi.ici.c2324.practica5.grupoYY.mspacman.actions;


import java.util.Random;

import es.ucm.fdi.ici.Action;
import pacman.game.Constants.DM;
import pacman.game.Constants.MOVE;
import pacman.game.internal.Maze;
import pacman.game.Game;

public class EatPillsAction implements Action {
    
	private Random rnd = new Random();
    private MOVE[] allMoves = MOVE.values();
	public EatPillsAction() {
	}
	
	@Override
	public MOVE execute(Game game) {
		Maze maze = game.getCurrentMaze();
		int[] Ppill = maze.pillIndices;
		System.out.println("I am eating pills");
		double distance = Double.MAX_VALUE;
		int mascercana=-1;

		for(int pill:Ppill) {
			if(game.isNodeObservable(pill)){
				if(game.isPillStillAvailable(pill) && distance>game.getDistance(game.getPacmanCurrentNodeIndex(), pill,game.getPacmanLastMoveMade(), DM.PATH)){
					mascercana=pill;
					distance=game.getDistance(game.getPacmanCurrentNodeIndex(), pill,game.getPacmanLastMoveMade(), DM.PATH);
				}
			}
		}

		if(mascercana != -1)
			return game.getApproximateNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), mascercana, game.getPacmanLastMoveMade(), DM.PATH);
		else
			return allMoves[rnd.nextInt(allMoves.length)];
    }

	@Override
	public String getActionId() {
		return "EatPills";
	}
}