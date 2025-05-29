package es.ucm.fdi.ici.c2324.practica2.grupo03;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import es.ucm.fdi.ici.fsm.CompoundState;
import es.ucm.fdi.ici.fsm.FSM;
import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2324.practica2.grupo03.mspacman.MsPacManInput;
import es.ucm.fdi.ici.c2324.practica2.grupo03.mspacman.actions.AttractGhostsToPowerPillAction;
import es.ucm.fdi.ici.c2324.practica2.grupo03.mspacman.actions.ChaseClosestEdibleAction;
import es.ucm.fdi.ici.c2324.practica2.grupo03.mspacman.actions.DifferentPathToPowerPillAction;
import es.ucm.fdi.ici.c2324.practica2.grupo03.mspacman.actions.EatPillsAction;
import es.ucm.fdi.ici.c2324.practica2.grupo03.mspacman.actions.EatPowerPillsAction;
import es.ucm.fdi.ici.c2324.practica2.grupo03.mspacman.actions.EscapeRouteAction;
import es.ucm.fdi.ici.c2324.practica2.grupo03.mspacman.actions.RandomAction;
import es.ucm.fdi.ici.c2324.practica2.grupo03.mspacman.actions.RunAwayClosestChasingGhostAction;
import es.ucm.fdi.ici.c2324.practica2.grupo03.mspacman.actions.SectionMoveByGhostAction;
import es.ucm.fdi.ici.c2324.practica2.grupo03.mspacman.actions.SectionMoveByPillsAction;
import es.ucm.fdi.ici.c2324.practica2.grupo03.mspacman.transitions.ChasingInMyWayTransition;
import es.ucm.fdi.ici.c2324.practica2.grupo03.mspacman.transitions.ChasingNearTransition;
import es.ucm.fdi.ici.c2324.practica2.grupo03.mspacman.transitions.ChasingNotInMyWayTransition;
import es.ucm.fdi.ici.c2324.practica2.grupo03.mspacman.transitions.CloseToPillsTransition;
import es.ucm.fdi.ici.c2324.practica2.grupo03.mspacman.transitions.EdibleNearAndReachableTransition;
import es.ucm.fdi.ici.c2324.practica2.grupo03.mspacman.transitions.EdibleNonReachableAndChasingNearTransition;
import es.ucm.fdi.ici.c2324.practica2.grupo03.mspacman.transitions.EdibleReachableTransition;
import es.ucm.fdi.ici.c2324.practica2.grupo03.mspacman.transitions.NoChasingNearTransition;
import es.ucm.fdi.ici.c2324.practica2.grupo03.mspacman.transitions.NotNearPillsTransition;
import es.ucm.fdi.ici.c2324.practica2.grupo03.mspacman.transitions.NothingInterestingTransition;
import es.ucm.fdi.ici.c2324.practica2.grupo03.mspacman.transitions.PPillNearNoEdibleGhostTransition;
import es.ucm.fdi.ici.c2324.practica2.grupo03.mspacman.transitions.PPillNearTransition;
import es.ucm.fdi.ici.c2324.practica2.grupo03.mspacman.transitions.PacmanDeadTransition;
import es.ucm.fdi.ici.c2324.practica2.grupo03.mspacman.transitions.RandomTransition;
import es.ucm.fdi.ici.fsm.SimpleState;
import es.ucm.fdi.ici.fsm.Transition;
import es.ucm.fdi.ici.fsm.observers.GraphFSMObserver;
import pacman.controllers.PacmanController;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

/*
 * The Class NearestPillPacMan.
 */
public class MsPacMan extends PacmanController {

	FSM fsm;
	public MsPacMan() {
		setName("MsPacMan 03");
		
		
		
    	fsm = new FSM("MsPacMan");
    	
    	GraphFSMObserver observer = new GraphFSMObserver(fsm.toString());
    	fsm.addObserver(observer);
    	
    	SimpleState random_ini = new SimpleState("Random Initial", new RandomAction());
    	PacmanDeadTransition pacman_dead = new PacmanDeadTransition();
    	RandomTransition randomtrans = new RandomTransition(1);
    	
    	FSM pacman = new FSM("MsPacmanCompound");
    	GraphFSMObserver genobserver = new GraphFSMObserver(pacman.toString());
    	pacman.addObserver(genobserver);
    	
    	SimpleState perseguir = new SimpleState("Perseguir", new ChaseClosestEdibleAction());
    	SimpleState huir = new SimpleState("Huir", new RunAwayClosestChasingGhostAction());
    	SimpleState via_escape = new SimpleState("Buscar via de escape", new EscapeRouteAction());
    	
    	ChasingInMyWayTransition fnc_en_camino = new ChasingInMyWayTransition("edible");
    	//EdibleReachableTransition fc_alcanzable_1 =  new EdibleReachableTransition();
    	//EdibleReachableTransition fc_alcanzable_2 = new EdibleReachableTransition();
    	EdibleReachableTransition fc_alcanzable = new EdibleReachableTransition();
    	
    	//EdibleNonReachableAndChasingNearTransition fc_no_alcanzable_fnc_cerca_a = new  EdibleNonReachableAndChasingNearTransition();
    	//EdibleNonReachableAndChasingNearTransition fc_ = new  EdibleNonReachableAndChasingNearTransition();
    	
    	ChasingNearTransition fnc_cerca = new ChasingNearTransition();
    	NoChasingNearTransition fnc_lejos = new NoChasingNearTransition();
    	PPillNearTransition ppill_cerca_1 = new PPillNearTransition();
    	PPillNearTransition ppill_cerca_2 = new PPillNearTransition();
    	NothingInterestingTransition nadaint_1 = new NothingInterestingTransition();
    	NothingInterestingTransition nadaint_2 = new NothingInterestingTransition();
    	PPillNearNoEdibleGhostTransition ppill_no_fc = new PPillNearNoEdibleGhostTransition();
    	
    	
    	pacman.add(perseguir, fnc_en_camino, via_escape);
    	pacman.add(via_escape, new EdibleReachableTransition(), perseguir);
    	pacman.add(via_escape, new  EdibleNonReachableAndChasingNearTransition(), huir);
    	pacman.add(huir, new EdibleReachableTransition(), perseguir);
    	pacman.add(huir, new  EdibleNonReachableAndChasingNearTransition(), perseguir);
    	
    	
    	
    	
    	//Compound State: Pills
    	FSM pills = new FSM("Pills");
    	GraphFSMObserver c1observer = new GraphFSMObserver(pills.toString());
    	pills.addObserver(c1observer);
    	
    	
    	SimpleState comer_pills = new SimpleState("Comer Pills", new EatPillsAction());
    	SimpleState cambiar_zona_pills = new SimpleState("Cambiar Zona Pills", new SectionMoveByPillsAction());
    	SimpleState cambiar_zona_ghost = new SimpleState("Cambiar Zona Ghosts", new SectionMoveByGhostAction());
    	ChasingInMyWayTransition chasing_in_path = new ChasingInMyWayTransition("pill");
    	CloseToPillsTransition pills_cerca = new CloseToPillsTransition();
    	CloseToPillsTransition pills_cerca_2 = new CloseToPillsTransition();
    	NotNearPillsTransition pills_lejos = new NotNearPillsTransition();
    	
    	pills.add(comer_pills, pills_lejos, cambiar_zona_pills);
    	pills.add(cambiar_zona_pills, pills_cerca, comer_pills);
    	pills.add(comer_pills, chasing_in_path, cambiar_zona_ghost);
    	pills.add(cambiar_zona_ghost, pills_cerca_2, comer_pills);
    	pills.ready(comer_pills);
    	CompoundState compound_pills = new CompoundState("Compound Pills", pills);
    	
    	pacman.add(huir, nadaint_1, compound_pills);
    	pacman.add(perseguir, nadaint_2, compound_pills);
    	pacman.add(compound_pills, fnc_cerca, huir);
    	
    	
    	
    	//Compound State: PowerPills
    	FSM powerpills = new FSM("PowerPills");
    	GraphFSMObserver c2observer = new GraphFSMObserver(powerpills.toString());
    	powerpills.addObserver(c2observer);
    	
    	SimpleState comer_powerpill = new SimpleState("Comer PowerPill", new EatPowerPillsAction());
    	SimpleState otro_camino_ppill = new SimpleState("Otro Camino PowerPill", new DifferentPathToPowerPillAction());
    	SimpleState atraer_fantasmas = new SimpleState("Atraer Fantasmas a PowerPill", new AttractGhostsToPowerPillAction());
      	ChasingNotInMyWayTransition fnc_nocam_ppill = new ChasingNotInMyWayTransition("powerpill");
      	ChasingInMyWayTransition fnc_cam_ppill = new ChasingInMyWayTransition("powerpill");
    	
    	powerpills.add(comer_powerpill, fnc_lejos, atraer_fantasmas);
    	powerpills.add(atraer_fantasmas, fnc_cerca, comer_powerpill);
    	powerpills.add(otro_camino_ppill, fnc_nocam_ppill, comer_powerpill);
    	powerpills.add(comer_powerpill, fnc_cam_ppill, otro_camino_ppill);
    	
    	powerpills.ready(comer_powerpill);
    	
    	CompoundState compound_powerpills = new CompoundState("Compound PowerPills", powerpills);
    	pacman.add(perseguir,ppill_no_fc,compound_powerpills);
    	pacman.add(huir,ppill_cerca_1,compound_powerpills);
    	pacman.add(compound_pills, ppill_cerca_2, compound_powerpills);
    	pacman.add(compound_powerpills, fc_alcanzable, perseguir);
    	
    	pacman.ready(huir);
    	CompoundState compound_pacman = new CompoundState("Compound Pacman", pacman);
    	
    	fsm.add(compound_pacman, pacman_dead, random_ini);
    	fsm.add(random_ini, fnc_cerca, huir);
    	fsm.add(random_ini, fc_alcanzable, perseguir);
    	fsm.add(random_ini, randomtrans, compound_pacman);
    	
    	fsm.ready(random_ini);
    	
    	
    	
    	
    	
    	JFrame frame = new JFrame();
    	JPanel main = new JPanel();
    	main.setLayout(new BorderLayout());
    	main.add(observer.getAsPanel(true, null), BorderLayout.CENTER);
    	main.add(c1observer.getAsPanel(true, null), BorderLayout.SOUTH);
    	frame.getContentPane().add(main);
    	frame.pack();
    	frame.setVisible(true);
    	
	}
	
	
	public void preCompute(String opponent) {
    		fsm.reset();
    }
	
	
	
    /* (non-Javadoc)
     * @see pacman.controllers.Controller#getMove(pacman.game.Game, long)
     */
    @Override
    public MOVE getMove(Game game, long timeDue) {
    	Input in = new MsPacManInput(game); 
    	return fsm.run(in);
    }
    
    
}