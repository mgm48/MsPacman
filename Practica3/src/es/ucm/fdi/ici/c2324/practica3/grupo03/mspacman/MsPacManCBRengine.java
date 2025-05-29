package es.ucm.fdi.ici.c2324.practica3.grupo03.mspacman;

import java.io.File;
import java.util.Collection;

import es.ucm.fdi.gaia.jcolibri.cbraplications.StandardCBRApplication;
import es.ucm.fdi.gaia.jcolibri.cbrcore.Attribute;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRCase;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRCaseBase;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRQuery;
import es.ucm.fdi.gaia.jcolibri.exception.ExecutionException;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.RetrievalResult;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.NNConfig;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.NNScoringMethod;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.Equal;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.Interval;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.selection.SelectCases;
import es.ucm.fdi.gaia.jcolibri.util.FileIO;
import es.ucm.fdi.ici.c2324.practica3.grupo03.CBRengine.Average;
import es.ucm.fdi.ici.c2324.practica3.grupo03.CBRengine.CachedLinearCaseBase;
import es.ucm.fdi.ici.c2324.practica3.grupo03.CBRengine.CustomPlainTextConnector;
import pacman.game.Constants.MOVE;

public class MsPacManCBRengine implements StandardCBRApplication {

	private String opponent;
	private MOVE action;
	private MsPacManStorageManager storageManager;
	CustomPlainTextConnector connectorteam;
	
	CBRCaseBase caseTeam;
	NNConfig simConfig;
	
	
	final static String TEAM = "grupo03";  //Cuidado!! poner el grupo aquí
	
	//la base de datos de cada equipo
	 String TEAM_CONNECTOR_FILE_PATH = "es/ucm/fdi/ici/c2324/practica3/"+TEAM+"/mspacman/plaintextconfig.xml";
	 String TEAM_CASE_BASE_PATH = "cbrdata"+File.separator+opponent+File.separator+"pacman"+File.separator;

	
	public MsPacManCBRengine(MsPacManStorageManager storageManager)
	{
		this.storageManager = storageManager;
	}
	
	public void setOpponent(String opponent) {
		this.opponent = opponent;
	}
	
	@Override
	public void configure() throws ExecutionException {
		connectorteam = new CustomPlainTextConnector();
		caseTeam = new CachedLinearCaseBase();
		connectorteam.initFromXMLfile(FileIO.findFile(TEAM_CONNECTOR_FILE_PATH));
		connectorteam.setCaseBaseFile(TEAM_CASE_BASE_PATH, opponent+".csv");
		
		
		//Do not use default case base path in the xml file. Instead use custom file path for each opponent.
		//Note that you can create any subfolder of files to store the case base inside your "cbrdata/grupoXX" folder.
		
		
		this.storageManager.setCaseBase(caseTeam);
		
		simConfig = new NNConfig();
		simConfig.setDescriptionSimFunction(new Average());
		//distancias ghost

		Attribute distGLEFT = new Attribute("distanciaGhostLEFT",MsPacManDescription.class);
		simConfig.addMapping(distGLEFT, new Interval(650));
		simConfig.setWeight(distGLEFT, 0.1);

		Attribute distGRIGHT = new Attribute("distanciaGhostRIGHT",MsPacManDescription.class);
		simConfig.addMapping(distGRIGHT, new Interval(650));
		simConfig.setWeight(distGRIGHT, 0.1);

		Attribute distGUP = new Attribute("distanciaGhostUP",MsPacManDescription.class);
		simConfig.addMapping(distGUP, new Interval(650));
		simConfig.setWeight(distGUP, 0.1);

		Attribute distGDOWN = new Attribute("distanciaGhostDOWN",MsPacManDescription.class);
		simConfig.addMapping(distGDOWN, new Interval(650));
		simConfig.setWeight(distGDOWN, 0.1);

		//TIEMPOS COMESTIBLES 
		Attribute tComestibleLEFT = new Attribute("tiempoComestibleLEFT",MsPacManDescription.class);
		simConfig.addMapping(tComestibleLEFT, new Interval(250));
		simConfig.setWeight(tComestibleLEFT, 0.0625);

		Attribute tComestibleRIGHT = new Attribute("tiempoComestibleRIGHT",MsPacManDescription.class);
		simConfig.addMapping(tComestibleRIGHT, new Interval(250));
		simConfig.setWeight(tComestibleRIGHT, 0.0625);

		Attribute tComestibleUP = new Attribute("tiempoComestibleUP",MsPacManDescription.class);
		simConfig.addMapping(tComestibleUP, new Interval(250));
		simConfig.setWeight(tComestibleUP, 0.0625);

		Attribute tComestibleDOWN = new Attribute("tiempoComestibleDOWN",MsPacManDescription.class);
		simConfig.addMapping(tComestibleDOWN, new Interval(250));
		simConfig.setWeight(tComestibleDOWN, 0.0625);
		//Distancia PP
		Attribute distPPLEFT = new Attribute("distanciaPPLEFT",MsPacManDescription.class);
		simConfig.addMapping(distPPLEFT, new Interval(650));
		simConfig.setWeight(distPPLEFT, 0.075);

		Attribute distPPRIGHT = new Attribute("distanciaPPRIGHT",MsPacManDescription.class);
		simConfig.addMapping(distPPRIGHT, new Interval(650));
		simConfig.setWeight(distPPRIGHT, 0.075);

		Attribute distPPUP = new Attribute("distanciaPPUP",MsPacManDescription.class);
		simConfig.addMapping(distPPUP, new Interval(650));
		simConfig.setWeight(distPPUP, 0.075);

		Attribute distPPDOWN = new Attribute("distanciaPPDOWN",MsPacManDescription.class);
		simConfig.addMapping(distPPDOWN, new Interval(650));
		simConfig.setWeight(distPPDOWN, 0.075);

		//Distancia pill
		Attribute distPillLEFT = new Attribute("distanciaPillLEFT",MsPacManDescription.class);
		simConfig.addMapping(distPillLEFT, new Interval(650));
		simConfig.setWeight(distPillLEFT, 0.0125);

		Attribute distPillRIGHT = new Attribute("distanciaPillRIGHT",MsPacManDescription.class);
		simConfig.addMapping(distPillRIGHT, new Interval(650));
		simConfig.setWeight(distPillRIGHT, 0.0125);

		Attribute distPillUP = new Attribute("distanciaPillUP",MsPacManDescription.class);
		simConfig.addMapping(distPillUP, new Interval(650));
		simConfig.setWeight(distPillUP, 0.0125);

		Attribute distPillDOWN = new Attribute("distanciaPillDOWN",MsPacManDescription.class);
		simConfig.addMapping(distPillDOWN, new Interval(650));
		simConfig.setWeight(distPillDOWN, 0.0125);
		// 0.4 distanciafantasmas+0.05distanciapill+0.3distanciapp+0.25tiempocomestible;

	}

	@Override
	public CBRCaseBase preCycle() throws ExecutionException {
		caseTeam.init(connectorteam);
		return caseTeam;
	}

	@Override
	public void cycle(CBRQuery query) throws ExecutionException {
		RetrievalResult masSimilar=null;
		boolean esSimilar=false;
		if(caseTeam.getCases().isEmpty()) {
			this.action = MOVE.NEUTRAL;
		}
		else {
			//Compute retrieve
			Collection<RetrievalResult> eval = NNScoringMethod.evaluateSimilarity(caseTeam.getCases(), query, simConfig);
			//Compute reuse
			this.action = reuse(eval,masSimilar);
			if(masSimilar!=null) {
				esSimilar=masSimilar.getEval()>0.95;
			}else {
				esSimilar=false;
			}
			
		}
		//Compute revise & retain
		CBRCase newCase = createNewCase(query);
		//Nos sirve para si guardamos el caso o no
		this.storageManager.reviseAndRetain(newCase,esSimilar);
		
	}

	private MOVE reuse(Collection<RetrievalResult> eval,RetrievalResult masSimilar)
	{
		//Cogemos los 3 mejores casos
		Collection<RetrievalResult> top3 = SelectCases.selectTopKRR(eval, 3);
		if(!top3.isEmpty())
			masSimilar=(RetrievalResult) top3.toArray()[0];
		
		//para saber si son buenos los casos
		double puntuacion=-1;
		//calculamos la soluci�n teniendo en cuenta el score y la similitud
		MOVE action = calcularSolucion(top3,puntuacion);
		// si los casos seleccionados son muy malos puntuacion negativa(mal score) hacemos un movimientoa aleatorio
		if(puntuacion<0) {
			int index = (int)Math.floor(Math.random()*4);
			if(MOVE.values()[index]==action) 
				index= (index+1)%4;
			action = MOVE.values()[index];
		}
		return action;
	}
	
	
	

	private MOVE calcularSolucion(Collection<RetrievalResult> top3,double puntuacion) {
	 double maxSimilarityProduct = Double.NEGATIVE_INFINITY;
	    MOVE selectedAction = MOVE.NEUTRAL;
	    if(!top3.isEmpty()) {
		    for (RetrievalResult result : top3) {
		        // Obtener la similitud y el puntaje del caso actual
		        double similarity = result.getEval();
		        MsPacManResult result2 = (MsPacManResult) result.get_case().getResult();
				double score = result2.getScore();
	
		        // Calcular el producto de similitud y puntaje
		        double similarityProduct = similarity * score;
		        //Si tienen la misma soluci�n le sumo el producto
		        if(selectedAction.equals(result2)) {
		        	maxSimilarityProduct +=similarityProduct;
		        }
		        // Actualizar la acci�n seleccionada si encontramos un producto mayor
		        if (similarityProduct > maxSimilarityProduct) {
		            maxSimilarityProduct = similarityProduct;
		            selectedAction = ((MsPacManSolution) result.get_case().getSolution()).getAction();
		        }
		        puntuacion=maxSimilarityProduct;   
		    }
	    }else {// No hay casos en la BBDD
	    	int index = (int)Math.floor(Math.random()*4);
			selectedAction = MOVE.values()[index];
	    }
	    // Devolver la acci�n seleccionada
	    return selectedAction;
	}

	/**
	 * Creates a new case using the query as description, 
	 * storing the action into the solution and 
	 * setting the proper id number
	 */
	private CBRCase createNewCase(CBRQuery query) {
		CBRCase newCase = new CBRCase();
		MsPacManDescription newDescription = (MsPacManDescription) query.getDescription();
		MsPacManResult newResult = new MsPacManResult();
		MsPacManSolution newSolution = new MsPacManSolution();
		int newId = this.caseTeam.getCases().size();
		newId+= storageManager.getPendingCases();
		newDescription.setId(newId);
		newResult.setId(newId);
		newSolution.setId(newId);
		newSolution.setAction(this.action);
		newCase.setDescription(newDescription);
		newCase.setResult(newResult);
		newCase.setSolution(newSolution);
		return newCase;
	}
	
	public MOVE getSolution() {
		return this.action;
	}

	@Override
	public void postCycle() throws ExecutionException {
		this.storageManager.close();
		this.caseTeam.close();
	}

}
