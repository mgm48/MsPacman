package es.ucm.fdi.ici.c2324.practica2.grupo03;

import java.util.EnumMap;

import es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.GhostsInput;
import es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.actions.BlockClosePPAction;
import es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.actions.BlockNextJuntionAction;
import es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.actions.BlockSectionAction;
import es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.actions.ChaseAction;
import es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.actions.GetCloseToAliveGhostAction;
import es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.actions.MoveAwayGhostAction;
import es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.actions.MoveAwayPPAction;
import es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.actions.MoveToEmptySectionAction;
import es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.actions.ProtectAction;
import es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.actions.RunAwayAction;
import es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.transitions.CloseToPacmanAndFarFromPPTransition;
import es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.transitions.ClosestGhosttoPacmanTransition;
import es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.transitions.ClosestToPacmanAndFarFromPPTransition;
import es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.transitions.EdibleGhostNearTransition;
import es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.transitions.FarFromPacmanAndFarFromPP;
import es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.transitions.FarFromPacmanTransition;
import es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.transitions.GhostNotEdibletransition;
import es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.transitions.GhostsEdibleTransition;
import es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.transitions.NoClosestGhostToPacmanTransition;
import es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.transitions.NoNonEdibleGhostNearAndPacmanFarTransition;
import es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.transitions.NonEdibleGhostNearTransition;
import es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.transitions.NonEdibleSecurePathTransition;
import es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.transitions.PacManFurtherThanGhostPPillTransition;
import es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.transitions.PacmanCloseTransition;
import es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.transitions.PacmanCloserThanGhostsToPPilltransition;
import es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.transitions.PacmanFarFromPPillsTransition;
import es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.transitions.ProtectTransition;
import es.ucm.fdi.ici.c2324.practica2.grupo03.ghosts.transitions.noGhostEdibletransition;
import es.ucm.fdi.ici.fsm.CompoundState;
import es.ucm.fdi.ici.fsm.FSM;
import es.ucm.fdi.ici.fsm.SimpleState;
import pacman.controllers.GhostController;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class Ghosts extends GhostController {

	EnumMap<GHOST,FSM> fsms;
	public Ghosts()
	{
		setName("Ghosts 03");

		fsms = new EnumMap<GHOST,FSM>(GHOST.class);
		for(GHOST ghost: GHOST.values()) {
			FSM fsm = new FSM(ghost.name());
			FSM huir= new FSM(ghost.name());
			FSM atacar= new FSM(ghost.name());
			
			SimpleState blockPP = new SimpleState(new BlockClosePPAction(ghost));
			SimpleState blockJuntion = new SimpleState(new BlockNextJuntionAction(ghost));
			SimpleState blockSection = new SimpleState(new BlockSectionAction(ghost));
			SimpleState chase = new SimpleState(new ChaseAction(ghost));
			SimpleState closeAlive = new SimpleState(new GetCloseToAliveGhostAction(ghost));
			SimpleState runAwayGhost = new SimpleState(new MoveAwayGhostAction(ghost));
			SimpleState runAwayPP = new SimpleState(new MoveAwayPPAction(ghost));
			SimpleState emptySection = new SimpleState(new MoveToEmptySectionAction(ghost));
			SimpleState protect = new SimpleState(new ProtectAction(ghost));
			SimpleState runAway = new SimpleState(new RunAwayAction(ghost));

			//estado compuesto de huir
			huir.add(runAway, new EdibleGhostNearTransition(ghost) , runAwayGhost);
			huir.add(runAway, new NonEdibleSecurePathTransition(ghost), closeAlive);
			huir.add(runAwayGhost, new NonEdibleGhostNearTransition(ghost) , runAway);
			huir.add(runAwayGhost,new NonEdibleSecurePathTransition(ghost) , closeAlive);
			huir.add(runAwayGhost, new NoNonEdibleGhostNearAndPacmanFarTransition(ghost),emptySection );
			huir.add(closeAlive, new EdibleGhostNearTransition(ghost), runAwayGhost);
			huir.add(closeAlive, new NoNonEdibleGhostNearAndPacmanFarTransition(ghost),emptySection);
			huir.add(emptySection, new PacmanCloseTransition(ghost), runAway);// con pacman cerca sirve,ya que si deja de estar edible sale de estado huir
			huir.add(emptySection, new EdibleGhostNearTransition(ghost), runAwayGhost);
			huir.add(emptySection, new NonEdibleGhostNearTransition(ghost), closeAlive);
			
			huir.ready(runAway);
			CompoundState huirState = new CompoundState("Huir",huir);
			
			// estado compuesto atacar
			atacar.add(chase, new PacManFurtherThanGhostPPillTransition(ghost), blockPP);// esto hara que todos cuando este cerca de la PP todos pasen a proteger la PP
			atacar.add(chase, new NoClosestGhostToPacmanTransition(ghost), blockJuntion);
			atacar.add(blockPP, new ClosestToPacmanAndFarFromPPTransition(ghost) , chase);
			atacar.add(blockPP,new FarFromPacmanAndFarFromPP(ghost) , blockSection);
			atacar.add(blockPP, new CloseToPacmanAndFarFromPPTransition(ghost), blockJuntion);
			atacar.add(blockSection,new PacManFurtherThanGhostPPillTransition(ghost) , blockPP);
			atacar.add(blockSection, new PacmanCloseTransition(ghost), blockJuntion);
			atacar.add(blockJuntion, new FarFromPacmanTransition(ghost), blockSection);
			atacar.add(blockJuntion,new PacManFurtherThanGhostPPillTransition(ghost) , blockPP);
			atacar.add(blockJuntion,new ClosestGhosttoPacmanTransition(ghost) , chase);
			atacar.ready(chase);
			CompoundState atacarState = new CompoundState("atacar",atacar);

			//maquina de estados
			fsm.add(atacarState, new PacmanCloserThanGhostsToPPilltransition(), runAwayPP);
			fsm.add(atacarState, new GhostsEdibleTransition(ghost), huirState);
			fsm.add(runAwayPP, new PacmanFarFromPPillsTransition(), atacarState);
			fsm.add(runAwayPP, new GhostsEdibleTransition(ghost) , huirState);
			fsm.add(huirState,  new ProtectTransition(ghost), protect);
			fsm.add(huirState, new GhostNotEdibletransition(ghost), atacarState);
			fsm.add(protect, new noGhostEdibletransition(), atacarState);
			fsm.add(protect, new GhostsEdibleTransition(ghost), huirState);
			fsm.ready(protect);
			
			fsms.put(ghost, fsm);
		}
	}
	
	public void preCompute(String opponent) {
    	for(FSM fsm: fsms.values())
    		fsm.reset();
    }
	
	@Override
	public EnumMap<GHOST, MOVE> getMove(Game game, long timeDue) {
		EnumMap<GHOST,MOVE> result = new EnumMap<GHOST,MOVE>(GHOST.class);
		
		GhostsInput in = new GhostsInput(game);
		
		for(GHOST ghost: GHOST.values())
		{
			FSM fsm = fsms.get(ghost);
			MOVE move = fsm.run(in);
			result.put(ghost, move);
		}
		
		return result;		
	}
}
