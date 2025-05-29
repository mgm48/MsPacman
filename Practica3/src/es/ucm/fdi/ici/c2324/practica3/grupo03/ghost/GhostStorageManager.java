package es.ucm.fdi.ici.c2324.practica3.grupo03.ghost;

import java.util.Vector;

import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRCase;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRCaseBase;
import es.ucm.fdi.gaia.jcolibri.method.retain.StoreCasesMethod;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Game;

public class GhostStorageManager {

	Game game;
	CBRCaseBase caseBase;
	Vector<CBRCase> buffer;

	private final static int TIME_WINDOW = 3;// cada cuantos casos miras
	
	public GhostStorageManager()
	{
		this.buffer = new Vector<CBRCase>();
	}
	
	public void setGame(Game game) {
		this.game = game;
	}
	
	public void setCaseBase(CBRCaseBase caseBase)
	{
		this.caseBase = caseBase;
	}
	
	public void reviseAndRetain(CBRCase newCase, boolean esSimilar)
	{			
		this.buffer.add(newCase);
		
		//Buffer not full yet.
		if(this.buffer.size()<TIME_WINDOW)
			return;
		
		
		CBRCase bCase = this.buffer.remove(0);
		double resultado=-1;
		reviseCase(bCase,resultado);
		if(resultado>0 || !esSimilar)
			retainCase(bCase);
	}
	
	private void reviseCase(CBRCase bCase,Double resultado) {
		GhostDescription description = (GhostDescription)bCase.getDescription();
		GHOST ghost =description.getGhost();
		double olddistancia = description.getDistancia();
		
		int oldScore = description.getScore();
		int oldNivel =description.getNivel();
		int oldVidas =description.getNumeroVidas();
		int oldPP = description.getNPP();
		int currentScore = game.getScore();
		int currentNivel =game.getCurrentLevel();
		int currentVidas =game.getPacmanNumberOfLivesRemaining();
		int currentnPP =game.getNumberOfActivePowerPills();
		
		/*Score+vidas+powerpill+pasarnivel: 
			por cada 50 de score le quitamos 0.1, por cada vida que pierdas te damos 1, 
			por cada powerpill comida te damos 0.1 y -1 si pasa de nivel.
		*/
		double puntosObtenidos = ((oldScore-currentScore)/50);// cuanto le sumamos por puntos
		int vidas= oldVidas-currentVidas;
		int nivel= oldNivel-currentNivel;
		double PP =(oldPP-currentnPP);
		double distancia = 0;
		if(game.getGhostLairTime(ghost) == 0){
			double currentdistancia =game.getDistance(game.getGhostCurrentNodeIndex(ghost), game.getPacmanCurrentNodeIndex(),game.getGhostLastMoveMade(ghost) , DM.PATH);
			distancia =(olddistancia - currentdistancia)/50;
			if(game.getGhostEdibleTime(ghost) > 0){
				distancia = distancia*-1;
			}
		}
		resultado=vidas+nivel+PP+puntosObtenidos+distancia;
		GhostResult result = (GhostResult)bCase.getResult();
		result.setScore(resultado);	
	}

	private void retainCase(CBRCase bCase)	
	{
		//Store the old case right now into the case base
		//Alternatively we could store all them when game finishes in close() method
		
		//here you should also check if the case must be stored into persistence (too similar to existing ones, etc.)
		StoreCasesMethod.storeCase(this.caseBase, bCase);
	}

	public void close() {
		double resultado=-1;
		for(CBRCase oldCase: this.buffer)
		{
			reviseCase(oldCase,resultado);
			retainCase(oldCase);
		}
		this.buffer.removeAllElements();
	}

	public int getPendingCases() {
		return this.buffer.size();
	}
}
