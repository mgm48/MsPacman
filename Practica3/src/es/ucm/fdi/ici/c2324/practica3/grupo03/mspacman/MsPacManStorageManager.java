package es.ucm.fdi.ici.c2324.practica3.grupo03.mspacman;

import java.util.Vector;

import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRCase;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRCaseBase;
import es.ucm.fdi.gaia.jcolibri.method.retain.StoreCasesMethod;
import pacman.game.Game;

public class MsPacManStorageManager {

	Game game;
	CBRCaseBase caseBase;
	Vector<CBRCase> buffer;

	private final static int TIME_WINDOW = 3;// cada cuantos casos miras
	
	public MsPacManStorageManager()
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
		MsPacManDescription description = (MsPacManDescription)bCase.getDescription();
		int oldScore = description.getScore();
		int oldNivel =description.getNivel();
		int oldVidas =description.getNumeroVidas();
		int oldPP = description.getNPP();
		int currentScore = game.getScore();
		int currentNivel =game.getCurrentLevel();
		int currentVidas =game.getPacmanNumberOfLivesRemaining();
		int currentnPP =game.getNumberOfActivePowerPills();
		
		/*Score+vidas+powerpill+pasarnivel: 
			por cada 50 de score le damos 0.1, por cada vida que pierdas te quita -1, 
			por cada powerpill comida te quita -0.1 y +1 si pasa de nivel.
		*/
		double puntosObtenidos = 0.1*((currentScore - oldScore)/50);// cuanto le sumamos por puntos
		int vidas= currentVidas-oldVidas;
		int nivel= currentNivel-oldNivel;
		double PP =0.1*(currentnPP-oldPP);
		resultado=vidas+nivel+PP+puntosObtenidos;
		MsPacManResult result = (MsPacManResult)bCase.getResult();
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
